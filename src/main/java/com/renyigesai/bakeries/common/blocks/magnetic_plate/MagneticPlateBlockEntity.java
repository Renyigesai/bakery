package com.renyigesai.bakeries.common.blocks.magnetic_plate;

import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class MagneticPlateBlockEntity extends BlockEntity {

    private String blockId = "minecraft:air";
    private final ItemStackHandler items = new ItemStackHandler(2);
    private float[] xyo = new float[]{0,0,0,0};
    private int rotationFlag = 1;

    public MagneticPlateBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.Entities.MAGNETIC_PLATE_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.saveAdditional(pTag, registries);
        pTag.putString("BlockId",this.blockId);
        pTag.put("Items",items.serializeNBT(registries));

        int[] intArray0 = new int[xyo.length];
        for (int i = 0; i < xyo.length; i++) {
            intArray0[i] = Float.floatToRawIntBits(xyo[i]);
        }
        pTag.putIntArray("Xyo",intArray0);
        pTag.putInt("RotationFlag",this.rotationFlag);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        this.blockId = pTag.getString("BlockId");
        if (pTag.contains("Items")) {
            items.deserializeNBT(registries,pTag.getCompound("Items"));
        }

        int[] intArray0 = pTag.getIntArray("Xyo");
        xyo = new float[intArray0.length];
        for (int i = 0; i < intArray0.length; i++) {
            xyo[i] = Float.intBitsToFloat(intArray0[i]);
        }

        this.rotationFlag = pTag.getInt("RotationFlag");
    }

    public Block getBlock(){
        return BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(blockId));
    }

    public void drops(MagneticPlateBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.items.getSlots());
        for (int i = 0; i < blockEntity.items.getSlots(); i++) {
            inventory.setItem(i, blockEntity.items.getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getBlockId() {
        return blockId;
    }

    public ItemStackHandler getItems() {
        return items;
    }


    public void setXyo0(float[] xyo) {
        if (xyo.length >= 2){
            this.xyo[0] = xyo[0];
            this.xyo[1] = xyo[1];
        }
    }

    public void setXyo1(float[] xyo) {
        if (xyo.length >= 2){
            this.xyo[2] = xyo[0];
            this.xyo[3] = xyo[1];
        }
    }

    public float[] getXyo() {
        return xyo;
    }

    public int getRotationFlag() {
        return rotationFlag;
    }

    public void setRotationFlag(int rotationFlag) {
        this.rotationFlag = rotationFlag;
    }

    public void update() {
        if (level == null){
            return;
        }
        BlockState state = level.getBlockState(worldPosition);
        setChanged(level, worldPosition, state);
        level.sendBlockUpdated(worldPosition, state, state, 3);
    }
}
