package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MokaPotFillItem extends Item {
    public MokaPotFillItem() {
        super(new Properties().stacksTo(1).craftRemainder(BakeriesItems.MOKA_POT.get()));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 5592575;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return 13;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

}
