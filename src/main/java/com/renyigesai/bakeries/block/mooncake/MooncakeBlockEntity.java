package com.renyigesai.bakeries.block.mooncake;

import com.renyigesai.bakeries.block.mix_block.MixBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class MooncakeBlockEntity extends BlockEntity {

    private final ItemStackHandler items = new ItemStackHandler(4);

    public MooncakeBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.MOONCAKE_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("Items",items.serializeNBT());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("Items")) {
            items.deserializeNBT(pTag.getCompound("Items"));
        }
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public void drops(MooncakeBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.getItems().getSlots() + 1);
        for (int i = 0; i < blockEntity.getItems().getSlots(); i++) {
            inventory.setItem(i, blockEntity.getItems().getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
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
