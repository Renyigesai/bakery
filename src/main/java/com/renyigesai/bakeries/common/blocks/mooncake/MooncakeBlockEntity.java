package com.renyigesai.bakeries.common.blocks.mooncake;

import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class MooncakeBlockEntity extends BlockEntity {

    private final ItemStackHandler items = new ItemStackHandler(4);

    public MooncakeBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.Entities.MOONCAKE_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Items",items.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Items")) {
            items.deserializeNBT(registries,tag.getCompound("Items"));
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
