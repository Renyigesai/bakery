package com.renyigesai.bakeries.block.cake_box;

import com.renyigesai.bakeries.block.wooden_tray.WoodenTrayBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class CakeBoxBlockEntity extends BlockEntity {
    protected final ItemStackHandler inventory = new ItemStackHandler(1);
    public CakeBoxBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.CAKE_BOX_ENTITY.get(), pPos, pBlockState);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(1);
        inventory.setItem(0,this.inventory.getStackInSlot(0));
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
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

    public boolean isEmpty(){
        return inventory.getStackInSlot(0).isEmpty();
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }
}
