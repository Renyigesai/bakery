package com.renyigesai.bakeries.common.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class OliveOilItem extends Item {
    public OliveOilItem(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        ItemStack retval = new ItemStack(this);
        retval.setDamageValue(itemstack.getDamageValue() + 1);
        if (retval.getDamageValue() >= retval.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return retval;

    }

    @Override
    public boolean isRepairable(ItemStack itemstack) {
        return false;
    }
}
