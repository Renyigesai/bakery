package com.renyigesai.bakeries.block.stone_kiln;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.util.ItemUtil;
import com.renyigesai.bakeries.util.WorldUtil;
import lombok.Getter;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
@Getter
public class StoneKilnBlockEntity extends BlockEntity {
    private final ItemStackHandler inventory = new ItemStackHandler(1){
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return super.extractItem(slot, amount, simulate);
        }
    };
    private int cookingTime = 0;
    private float size = 1.0f;

    public StoneKilnBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.STONE_KILN_ENTITY.get(), pPos, pBlockState);
    }

    public int getContainerSize() {
        return 1;
    }

    public boolean isEmpty() {
        return this.inventory.getStackInSlot(0).isEmpty();
    }

    public ItemStack getItem(int pSlot) {
        return this.inventory.getStackInSlot(pSlot);
    }

    public ItemStack removeItem(int pSlot, int pAmount) {
        return inventory.extractItem(pSlot,pAmount,false);
    }

    public ItemStack removeItemNoUpdate(int pSlot) {
        return null;
    }

    public void setItem(int pSlot, ItemStack pStack) {
        this.inventory.setStackInSlot(pSlot,pStack);
    }


    public boolean addItem(ItemStack stack){
        if (isEmpty()){
            this.inventory.setStackInSlot(0,stack);
            this.setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            return true;
        }else {
            return false;
        }
    }

    public void takeOutItem(Player player, Level level, BlockPos pos, BlockState state){
        ItemStack stackInSlot = inventory.getStackInSlot(0);
        if (!stackInSlot.isEmpty()){
            ItemUtil.givePlayerItem(player,stackInSlot);
        }
        this.setChanged();
        if (!level.isClientSide){
            level.sendBlockUpdated(pos,state,state,3);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("CookingTime", cookingTime);
        tag.putFloat("Size", size);
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(tag.getCompound("Inventory"));
        }
        cookingTime = tag.getInt("CookingTime");
        size = tag.getFloat("Size");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.putInt("CookingTime", cookingTime);
        tag.putFloat("Size", size);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, StoneKilnBlockEntity blockEntity){
        if (!blockEntity.isEmpty()){
            blockEntity.cookingTimeTick();

            if (!level.isClientSide) {
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    public void cookingTimeTick(){
        ItemStack stackInSlot = inventory.getStackInSlot(0);
        boolean flag = false;
        if (stackInSlot.is(BakeriesItems.COUNTRY_BREAD_DOUGH.get())){
            if (this.cookingTime < 200){
                this.cookingTime ++;
                this.size += 0.0025f;
            }else {
                flag = true;
            }
        }else {
            this.cookingTime = 0;
            this.size += 0.0f;
        }
        if (flag){
            this.inventory.setStackInSlot(0,new ItemStack(BakeriesItems.COUNTRY_BREAD.get()));
            this.cookingTime = 0;
            this.size += 0.0f;
        }
    }
}
