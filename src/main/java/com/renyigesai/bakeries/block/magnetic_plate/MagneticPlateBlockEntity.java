package com.renyigesai.bakeries.block.magnetic_plate;

import com.renyigesai.bakeries.block.blender.BlenderBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class MagneticPlateBlockEntity extends BlockEntity {
    public static final ModelProperty<String> TARGET_BLOCK = new ModelProperty<>();

    private String blockId = "minecraft:air";
    private final ItemStackHandler items = new ItemStackHandler(2);
    private float[] xyo = new float[]{0,0,0,0};

    public MagneticPlateBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.MAGNETIC_PLATE_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString("BlockId",this.blockId);
        pTag.put("Items",items.serializeNBT());

        int[] intArray0 = new int[xyo.length];
        for (int i = 0; i < xyo.length; i++) {
            intArray0[i] = Float.floatToRawIntBits(xyo[i]);
        }
        pTag.putIntArray("Xyo",intArray0);

    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.blockId = pTag.getString("BlockId");
        if (pTag.contains("Items")) {
            items.deserializeNBT(pTag.getCompound("Items"));
        }

        int[] intArray0 = pTag.getIntArray("Xyo");
        xyo = new float[intArray0.length];
        for (int i = 0; i < intArray0.length; i++) {
            xyo[i] = Float.intBitsToFloat(intArray0[i]);
        }
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
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
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

    @Override
    public @NotNull ModelData getModelData() {
        return ModelData.builder().with(TARGET_BLOCK,getBlockId()).build();
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

}
