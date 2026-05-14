package com.renyigesai.bakeries.common.blocks.toaster;

import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesSounds;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public class ToasterBlockEntity extends BlockEntity {
    private final ItemStackHandler items = new ItemStackHandler(2) {
        // FIX: Limit slots to 1 item to prevent voiding excess stack items on craft.
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }

        // FIX: Sync data and safely initialize cooking times for items inserted by pipes.
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                ItemStack stack = getStackInSlot(slot);
                // If a pipe inserted a raw item, initialize its cooking time dynamically
                if (!stack.isEmpty() && cookingTime[slot] <= 0) {
                    getSmokerRecipe(stack).ifPresent(recipe -> {
                        cookingTime[slot] = recipe.value().getCookingTime() / 3;
                        cookingProgress[slot] = 0;
                    });
                } else if (stack.isEmpty()) {
                    // Reset if a pipe extracted the item
                    cookingTime[slot] = 0;
                    cookingProgress[slot] = 0;
                }
                updateBlock();
            }
        }
    };
    private Optional<IItemHandler> optionalIItemHandler;
    private final int[] cookingProgress;
    private final int[] cookingTime;
    private final RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> quickCheck;

    public float progress;
    public float progressOld;
    private AnimationStatus animationStatus = AnimationStatus.IDLE;


    public ToasterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.Entities.TOASTER_ENTITY.get(), pPos, pBlockState);
        this.cookingProgress = new int[2];
        this.cookingTime = new int[2];
        this.quickCheck = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
        this.optionalIItemHandler = Optional.empty();
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(items.getSlots());
        for (int i = 0; i < items.getSlots(); i++) {
            inventory.setItem(i, items.getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    public void addItem(ItemStack item, int time){
        for (int i = 0; i < this.items.getSlots(); i++) {
            ItemStack stack = this.items.getStackInSlot(i);
            if (stack.isEmpty()){
                item.setCount(1);
                this.items.setStackInSlot(i,item);
                this.cookingTime[i] = (int) (time / 3/*BakeriesConfig.toasterDoubleSpeed*/);
                this.cookingProgress[i] = 0;
                updateBlock();
                break;
            }
        }
    }

    public void getItem(Player player){
        for (int i = 0; i < this.items.getSlots(); i++) {
            ItemStack stackInSlot = this.items.getStackInSlot(i);
            if (!stackInSlot.isEmpty()){
                ItemUtils.givePlayerItem(player,stackInSlot);
                this.items.setStackInSlot(i,ItemStack.EMPTY);
                this.cookingTime[i] = 0;
                this.cookingProgress[i] = 0;
            }
        }
        this.animationStatus = AnimationStatus.IDLE;
    }

    public float getProgress(float pPartialTicks) {
        return Mth.lerp(pPartialTicks, this.progressOld, this.progress);
    }

    @Override
    public boolean triggerEvent(int pId, int pType) {
        if (pId == 1) {
            if (pType == 0) {
                this.animationStatus = AnimationStatus.LIT;
            }
            if (pType == 1) {
                this.animationStatus = AnimationStatus.FINISH;
            }
            doNeighborUpdates(this.getLevel(), this.worldPosition, this.getBlockState());
            return true;
        } else {
            return super.triggerEvent(pId, pType);
        }
    }

    private static void doNeighborUpdates(Level pLevel, BlockPos pPos, BlockState pState) {
        pState.updateNeighbourShapes(pLevel, pPos, 3);
    }

    public void changeState(int type){
        this.progress = 0.0f;
        this.level.blockEvent(worldPosition,this.getBlockState().getBlock(), 1,type);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState blockstate, ToasterBlockEntity blockEntity){
        blockEntity.progressOld = blockEntity.progress;
        switch (blockEntity.animationStatus){
            case LIT:
                blockEntity.progress -= 0.25F;
                if (blockEntity.progress <= -1.0F){
                    blockEntity.progress = -1.0F;
                }
                break;
            case FINISH:
                blockEntity.progress += 0.5F;
                if (blockEntity.progress >= 0.0F) {
                    blockEntity.progress = 0.0F;
                }
                break;
            case IDLE:
                blockEntity.progress = 0.0F;
            break;
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items.deserializeNBT(registries,tag.getCompound("Items"));
        int[] $$2;
        if (tag.contains("CookingTimes", 11)) {
            $$2 = tag.getIntArray("CookingTimes");
            System.arraycopy($$2, 0, this.cookingProgress, 0, Math.min(this.cookingTime.length, $$2.length));
        }

        if (tag.contains("CookingTotalTimes", 11)) {
            $$2 = tag.getIntArray("CookingTotalTimes");
            System.arraycopy($$2, 0, this.cookingTime, 0, Math.min(this.cookingTime.length, $$2.length));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Items",this.items.serializeNBT(registries));
        tag.putIntArray("CookingTimes", this.cookingProgress);
        tag.putIntArray("CookingTotalTimes", this.cookingTime);
    }


    public ItemStackHandler getItems() {
        return items;
    }

    public int[] getCookingTime() {
        return cookingTime;
    }

    public int[] getCookingProgress() {
        return cookingProgress;
    }

    public boolean isEmpty(){
        for (int i = 0; i < this.items.getSlots(); i++) {
            if (!this.items.getStackInSlot(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    public Optional<RecipeHolder<CampfireCookingRecipe>> getSmokerRecipe(ItemStack stack) {
        if (level == null) {
            return Optional.empty();
        }
        return level.getRecipeManager()
                .getRecipeFor(RecipeType.CAMPFIRE_COOKING, new SingleRecipeInput(stack), level);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ToasterBlockEntity toaster){
        if (!toaster.isEmpty() && pState.getValue(ToasterBlock.STATE) == ToasterBlock.State.LIT){
            cookTick(pLevel, pPos, pState, toaster);
        }
    }

    public static void cookTick(Level pLevel, BlockPos pPos, BlockState pState, ToasterBlockEntity toaster) {
        boolean flag = false;
        for (int slot = 0; slot < toaster.items.getSlots(); slot++) {
            ItemStack stackInSlot = toaster.items.getStackInSlot(slot);
            if (!stackInSlot.isEmpty()){
                flag = true;
                if (toaster.cookingProgress[slot] < toaster.cookingTime[slot]){
                    toaster.cookingProgress[slot] ++;
                }else {
                    SingleRecipeInput singleRecipeInput = new SingleRecipeInput(stackInSlot);
                    ItemStack result = toaster.quickCheck.getRecipeFor(singleRecipeInput, pLevel).map((recipe) -> recipe.value().assemble(singleRecipeInput, pLevel.registryAccess())).orElse(stackInSlot);
                    toaster.items.setStackInSlot(slot,result);
                    toaster.cookingTime[slot] = 0;
                    toaster.cookingProgress[slot] = 0;
                    pLevel.sendBlockUpdated(pPos, pState, pState, 3);
                    pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pState));
                }
            }
        }
        if (toaster.cookingProgress[0] == 0 && toaster.cookingProgress[1] == 0){
            toaster.changeState(1);
            pLevel.setBlock(pPos,pState.setValue(ToasterBlock.STATE, ToasterBlock.State.FINISH),3);
            if (!pLevel.isClientSide){
                pLevel.playSound(null,pPos, BakeriesSounds.TOASTER_OUT.get(), SoundSource.BLOCKS);
                pLevel.playSound(null, pPos, SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
        if (flag) {
            setChanged(pLevel, pPos, pState);
        }
    }

    public void updateBlock() {
        Level world = this.level;
        BlockPos pos = this.getBlockPos();
        BlockState state = world.getBlockState(pos);
        setChanged(world, pos, state);
        world.sendBlockUpdated(pos, state, state, 3);
    }

    public Vec2[] getVec2(){
        Vec2[] vec2s;
        float size = 0.15f;
        vec2s = new Vec2[]{
                new Vec2(0.5f, 0.5f + size),
                new Vec2(0.5f, 0.5f - size)
        };
        return vec2s;
    }

    public enum AnimationStatus {
        IDLE,
        LIT,
        FINISH
    }

}
