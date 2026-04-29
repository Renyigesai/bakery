package com.renyigesai.bakeries.api.item;

import net.minecraft.world.item.ItemStack;

public interface IFermentationItem {
    int getCraftingCount();

    default boolean isPerfectFermentation(ItemStack stack){
        return stack.getOrCreateTag().contains("PerfectFermentation") && stack.getOrCreateTag().getBoolean("PerfectFermentation");
    }

    default int fermentationCraftingCount(ItemStack stack){
        if (isPerfectFermentation(stack)){
            return getCraftingCount();
        }else {
            return 1;
        }
    }
}
