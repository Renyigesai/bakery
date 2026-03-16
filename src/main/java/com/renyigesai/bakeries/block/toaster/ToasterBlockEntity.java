package com.renyigesai.bakeries.block.toaster;

import com.renyigesai.bakeries.config.BakeriesConfig;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesSounds;
import com.renyigesai.bakeries.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;

public class ToasterBlockEntity extends BlockEntity {
    private final ItemStackHandler items = new ItemStackHandler(2);
    private final int[] cookingProgress;
    private final int[] cookingTime;
    private final RecipeManager.CachedCheck<Container, CampfireCookingRecipe> quickCheck;

    public float progress;
    public float progressOld;
    private ToasterBlockEntity.AnimationStatus animationStatus = AnimationStatus.IDLE;


    public ToasterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.TOASTER_ENTITY.get(), pPos, pBlockState);
        this.cookingProgress = new int[2];
        this.cookingTime = new int[2];
        this.quickCheck = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
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

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("Items",items.serializeNBT());
        tag.putIntArray("CookingTimes",cookingProgress);
        tag.putIntArray("CookingTotalTimes",cookingTime);
        return tag;
    }

    public boolean addItem(ItemStack item, int time){
        for (int i = 0; i < this.items.getSlots(); i++) {
            ItemStack stack = this.items.getStackInSlot(i);
            if (stack.isEmpty()){
                item.setCount(1);
                this.items.setStackInSlot(i,item);
                this.cookingTime[i] = (int) (time / BakeriesConfig.toasterDoubleSpeed);
                this.cookingProgress[i] = 0;
                updateBlock();
                return true;
            }
        }
        return false;
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

    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("Items")){
            items.deserializeNBT(pTag.getCompound("Items"));
        }
        int[] $$2;
        if (pTag.contains("CookingTimes", 11)) {
            $$2 = pTag.getIntArray("CookingTimes");
            System.arraycopy($$2, 0, cookingProgress, 0, Math.min(cookingTime.length, $$2.length));
        }

        if (pTag.contains("CookingTotalTimes", 11)) {
            $$2 = pTag.getIntArray("CookingTotalTimes");
            System.arraycopy($$2, 0, cookingTime, 0, Math.min(cookingTime.length, $$2.length));
        }
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("Items",items.serializeNBT());
        pTag.putIntArray("CookingTimes", cookingProgress);
        pTag.putIntArray("CookingTotalTimes", cookingTime);
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

    public boolean isEmpty2(){
        for (int i = 0; i < this.items.getSlots(); i++) {
            if (!this.items.getStackInSlot(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    public Optional<CampfireCookingRecipe> getSmokerRecipe(ItemStack stack) {
        SimpleContainer inventory = new SimpleContainer(1);
        inventory.setItem(0, stack);
        if (level == null) {
            return Optional.empty();
        }
        return level.getRecipeManager()
                .getRecipeFor(RecipeType.CAMPFIRE_COOKING, inventory, level);
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
                    Container container = new SimpleContainer(stackInSlot);
                    ItemStack result = toaster.quickCheck.getRecipeFor(container, pLevel).map((p_270054_) -> p_270054_.assemble(container, pLevel.registryAccess())).orElse(stackInSlot);
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
                pLevel.playSound(null, pPos, SoundEvents.NOTE_BLOCK_BELL.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
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
