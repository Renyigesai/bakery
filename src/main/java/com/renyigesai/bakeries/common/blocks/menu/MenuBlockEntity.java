package com.renyigesai.bakeries.common.blocks.menu;

import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class MenuBlockEntity extends BlockEntity {
    protected final ItemStackHandler inventory = new ItemStackHandler(1);
    public MenuBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.Entities.MENU_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Inventory")){
            this.inventory.deserializeNBT(registries,tag.getCompound("Inventory"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", inventory.serializeNBT(registries));
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
