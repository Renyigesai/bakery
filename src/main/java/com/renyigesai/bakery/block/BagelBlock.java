package com.renyigesai.bakery.block;

import net.minecraft.world.item.ItemStack;

public class BagelBlock extends PileBlock{
    public BagelBlock(Properties p_49795_) {
        super(p_49795_);
    }
    @Override
    public ItemStack getPileItem() {
        return new ItemStack(ModBlocks.BAGEL_BLOCK.get());
    }
}
