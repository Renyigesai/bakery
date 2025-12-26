package com.renyigesai.bakeries.block.menu;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;

public class MenuBlockEntity extends BlockEntity {
    protected final ItemStackHandler inventory = new ItemStackHandler(1);
    public MenuBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.MENU_ENTITY.get(), pPos, pBlockState);
    }
    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(tag.getCompound("Inventory"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public void addItem(ItemStack stack, Level level,BlockPos pos,BlockState state){
        inventory.setStackInSlot(0,stack);
        this.setChanged();
        if (!level.isClientSide){
            level.sendBlockUpdated(pos,state,state,3);
        }
    }

    public void deleteItem(Level level,BlockPos pos,BlockState state){
        this.inventory.setStackInSlot(0,ItemStack.EMPTY);
        this.setChanged();
        if (!level.isClientSide){
            level.sendBlockUpdated(pos,state,state,3);
        }
    }
}
