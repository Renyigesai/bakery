package com.renyigesai.bakeries.block.moka_pot;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.items.ItemStackHandler;

public class MokaPotBlockEntity extends BlockEntity {
    protected final ItemStackHandler inventory = new ItemStackHandler(1);//11个槽位
    public int cookingTotalTime;
    public boolean fill;
    public MokaPotBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.MOKA_POT_ENTITY.get(), pPos, pBlockState);
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

    public boolean isInventoryFull(){
        return inventory.getStackInSlot(0).isEmpty();
    }

    public int getCookingTotalTime() {
        return cookingTotalTime;
    }

    public boolean getFill(){
        return fill;
    }

    public void addGroundCoffee(ItemStack stack){
        inventory.setStackInSlot(0,stack);
    }

    public boolean isCraft(Level level,BlockPos pos){
        BlockState state = level.getBlockState(pos.below());
        return state.getBlock().getStateDefinition().getProperty("lit") instanceof BooleanProperty booleanProperty && state.getValue(booleanProperty);
    }

    public static void craftTick(Level level, BlockPos pos, BlockState state, MokaPotBlockEntity blockEntity) {
        if (blockEntity.isCraft(level, pos) && !blockEntity.isInventoryFull()) {
            blockEntity.tick();
            setChanged(level, pos, state);
            if (!level.isClientSide) {
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    public void tick(){
        if (inventory.getStackInSlot(0).is(ItemTags.create(new ResourceLocation("forge:coffee_grounds")))){
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
