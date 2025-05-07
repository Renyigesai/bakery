package com.renyigesai.bakeries.block.wooden_tray;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;

public class WoodenTrayBlockEntity extends BlockEntity {
    protected final ItemStackHandler inventory = new ItemStackHandler(2);

    public WoodenTrayBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.WOOD_TRAY_ENTITY.get(), pPos, pBlockState);
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

    public boolean canEmpty(){
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    public boolean addItem(ItemStack stack){
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty()){
                inventory.setStackInSlot(i,stack);
                return true;
            }
        }
        return false;
    }

    public void drops(WoodenTrayBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.inventory.getSlots());
        for (int i = 0; i < blockEntity.inventory.getSlots(); i++) {
            inventory.setItem(i, blockEntity.inventory.getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    public void takeOutItem(Player player){
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stackInSlot = inventory.getStackInSlot(i);
            if (!stackInSlot.isEmpty()){
                ItemUtil.givePlayerItem(player,stackInSlot);
                inventory.extractItem(i,1,false);
            }
        }
    }

    public Vec2 getItemOffest(int i) {
        float x = inventory.getStackInSlot(1).isEmpty()?0.0f:-0.15f;
        float y = -0.315f;
        Vec2[] offest = new Vec2[]{
                new Vec2(x,y),
                new Vec2(-x,y),
                new Vec2(x,-y),
                new Vec2(-x,-y)
        };
        return offest[i];
    }
}
