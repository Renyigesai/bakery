package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
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
import net.minecraftforge.items.ItemStackHandler;

public class MokaPotBlockEntity extends BlockEntity {
    protected final ItemStackHandler inventory = new ItemStackHandler(1);//11个槽位
    public int cookingTotalTime;
    public boolean fill;
    public MokaPotBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.MOKA_POT_ENTITY.get(), pPos, pBlockState);
    }

    public void addGroundCoffee(ItemStack stack){
        inventory.setStackInSlot(0,stack);
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
        cookingTotalTime = tag.getInt("CookingTotalTime");
        fill = tag.getBoolean("Fill");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.putInt("CookingTotalTime", cookingTotalTime);
        tag.putBoolean("Fill", fill);
    }

    public boolean isFill(){
        return fill;
    }

    public static void craftTick(Level level, BlockPos pos, BlockState state, MokaPotBlockEntity blockEntity) {
        blockEntity.tick();
        setChanged(level, pos, state);
        if (!level.isClientSide) {
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    public void tick(){
        if (!inventory.getStackInSlot(0).is(BakeriesItems.GROUND_COFFEE.get())){
            if (cookingTotalTime < 200){
                ++ cookingTotalTime;
            }else {
                inventory.extractItem(0,1,false);
                cookingTotalTime = 0;
                fill = true;
            }
        }
    }
}
