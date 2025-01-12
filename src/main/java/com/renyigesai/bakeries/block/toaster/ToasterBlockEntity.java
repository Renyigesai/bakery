package com.renyigesai.bakeries.block.toaster;

import com.renyigesai.bakeries.api.Shortcuts;
import com.renyigesai.bakeries.block.oven.OvenBlock;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.recipe.toaster.ToasterRecipe;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ToasterBlockEntity extends BlockEntity {
    @Getter
    private final ItemStackHandler itemHandler = new ItemStackHandler(2){
        @Override
        public int getSlotLimit(int slot)
        {
            return 1;
        }
    };
    //    public CompoundTag oven;
    private final int[] cooking_times = new int[2];
    private final int[] max_cooking_times = new int[2];


    public ToasterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.TOASTER_ENTITY.get(), pPos, pBlockState);
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putIntArray("cooking_times", this.cooking_times);
        pTag.putIntArray("max_cooking_times", this.max_cooking_times);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));

        if (pTag.contains("CookingTimes", 11)) {
            int[] aint = pTag.getIntArray("CookingTimes");
            System.arraycopy(aint, 0, this.cooking_times, 0, Math.min(this.max_cooking_times.length, aint.length));
        }
//
//        if (pTag.contains("CookingTimes", 11)) {
//            int[] aint = pTag.getIntArray("CookingTimes");
//            System.arraycopy(aint, 0, this.cooking_times, 0, Math.min(this.max_cooking_times.length, aint.length));
//        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, ToasterBlockEntity pToasterBlockEntity) {
        boolean flag = false;
        updateBlock(pToasterBlockEntity);
        setFire(pLevel, pPos, pState, pToasterBlockEntity);
        for (int i = 0; i < pToasterBlockEntity.itemHandler.getSlots(); i++) {
            flag = true;
            recipeItem(pLevel, pPos, pState, i, pToasterBlockEntity);
        }
        if (flag) {
            setChanged(pLevel, pPos, pState);
        }
    }
    public static void setFire(Level world, BlockPos pos, BlockState state, ToasterBlockEntity pToasterBlockEntity) {
        boolean isLit = pToasterBlockEntity.cooking_times[0] > 0 || pToasterBlockEntity.cooking_times[1] > 0 ;
        world.setBlock(pos, pToasterBlockEntity.getBlockState().setValue(OvenBlock.LIT, isLit), 3);
        world.sendBlockUpdated(pos, state, state, 3);
        setChanged(world, pos, state);
    }
    public void addItem(ItemStack item){
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            ItemStack stack = this.itemHandler.getStackInSlot(i);
            if (stack.isEmpty()){
                this.itemHandler.setStackInSlot(i,item.split(1));
                break;
            }
        }
    }

    public void getItem(Player player){
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            ItemStack stack = this.itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()){
                Shortcuts.givePlayerItem(player,stack);
                this.itemHandler.setStackInSlot(i,ItemStack.EMPTY.split(1));
                break;
            }
        }
    }
    public Vec2 getItemOffset(int i){
        float x = 0.2F;
        float y = 0.2F;
        Vec2[] offset = new Vec2[]{
                new Vec2(x,y),new Vec2(-x,y),
                new Vec2(x,-y),new Vec2(-x,-y)
        };
        return offset[i];
    }
    public static void updateBlock(ToasterBlockEntity pToasterBlockEntity) {
        Level world = pToasterBlockEntity.getLevel();
        BlockPos pos = pToasterBlockEntity.getBlockPos();
        BlockState state = world.getBlockState(pos);
        setChanged(world, pos, state);
        world.sendBlockUpdated(pos, state, state, 3);
    }

    private static void recipeItem(Level world, BlockPos pos, BlockState state, int slot, ToasterBlockEntity pToasterBlockEntity) {
        Optional<ToasterRecipe> recipe = pToasterBlockEntity.getCurrentRecipe(slot);

        recipe.ifPresent(ovenRecipe -> {
            pToasterBlockEntity.max_cooking_times[slot] = ovenRecipe.getTime();
        });
        if (pToasterBlockEntity.hasRecipe(slot) && recipe.isPresent()) {

            if (!world.isClientSide()) {
                int cookingTime = pToasterBlockEntity.cooking_times[slot]++;
                int max_cooking_time = pToasterBlockEntity.max_cooking_times[slot];

                if (cookingTime >= max_cooking_time) {
                    pToasterBlockEntity.craftItem(pToasterBlockEntity, slot);
                    world.sendBlockUpdated(pos, state, state, 3);
                    resetProgress(pToasterBlockEntity, slot);
                }
            }
        } else {
            if (!world.isClientSide()) {
                world.sendBlockUpdated(pos, state, state, 3);
                resetProgress(pToasterBlockEntity, slot);
            }
        }
    }

    private static void resetProgress(ToasterBlockEntity pToasterBlockEntity, int slot) {
        pToasterBlockEntity.max_cooking_times[slot] = 0;
        pToasterBlockEntity.cooking_times[slot] = 0;
    }
    private void craftItem(ToasterBlockEntity pToasterBlockEntity, int slot) {
        updateBlock(pToasterBlockEntity);
        Optional<ToasterRecipe> recipe = getCurrentRecipe(slot);
        if (recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(null);
            ItemStack takeItem = new ItemStack(result.getItem(), result.getCount());
            this.itemHandler.setStackInSlot(slot, takeItem);
            updateBlock(pToasterBlockEntity);
        }
    }
    private boolean hasRecipe(int slot) {
        Optional<ToasterRecipe> recipe = getCurrentRecipe(slot);
        return recipe.isPresent() && recipe.get().getIngredients().get(0).test(itemHandler.getStackInSlot(slot));
    }
    public Optional<ToasterRecipe> getCurrentRecipe(int slot) {
//        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
//        inventory.setItem(slot, this.itemHandler.getStackInSlot(slot));
        return this.level.getRecipeManager().getRecipeFor(ToasterRecipe.Type.INSTANCE, new SimpleContainer(this.itemHandler.getStackInSlot(slot)), level);
    }
}
