package com.renyigesai.bakery.block;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class BaguetteBlock extends PileBlock{
    public BaguetteBlock(Properties p_49795_) {
        super(p_49795_);
    }
    @Override
    public ItemStack getPileItem() {
        return new ItemStack(ModBlocks.BAGUETTE_BLOCK.get());
    }
}
