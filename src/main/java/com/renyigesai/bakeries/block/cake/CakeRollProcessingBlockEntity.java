package com.renyigesai.bakeries.block.cake;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;

public class CakeRollProcessingBlockEntity extends BlockEntity {
    protected final ItemStackHandler inventory = new ItemStackHandler(4);
    public CakeRollProcessingBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.CAKE_ROLL_PROCESSING_ENTITY.get(), pPos, pBlockState);
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
        return this.inventory;
    }

    public boolean isEmpty(){
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            if (!this.getInventory().getStackInSlot(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    public boolean addItem(ItemStack stack){
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                inventory.setStackInSlot(i, stack.copy());
                setChanged();
                return true;
            }
        }
        return false;
    }

    public Vec2 getItemOffest(int i) {
        float x = 0.1875f;
        float y = 0.1875f;
        Vec2[] offest = new Vec2[]{
                new Vec2(x,y),
                new Vec2(-x,y),
                new Vec2(x,-y),
                new Vec2(-x,-y)
        };
        return offest[i];
    }
}
