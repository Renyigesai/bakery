package com.renyigesai.bakeries.common.blocks.mould_cake;

import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class MouldCakeBlockEntity extends BlockEntity {

    private ItemStackHandler items = new ItemStackHandler(1);

    public MouldCakeBlockEntity( BlockPos pos, BlockState blockState) {
        super(BakeriesBlocks.Entities.MOULD_CAKE_ENTITY.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Items",items.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Items")){
            items.deserializeNBT(registries,tag.getCompound("Items"));
        }
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public void update(){
        setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(),this.getBlockState(),this.getBlockState(),3);
        }
    }
}
