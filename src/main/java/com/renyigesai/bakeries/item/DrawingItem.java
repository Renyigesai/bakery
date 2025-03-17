package com.renyigesai.bakeries.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DrawingItem extends Item {
    public DrawingItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        return new ItemStack(this);
    }

    @Override
    public boolean isRepairable(ItemStack itemstack) {
        return false;
    }

}
