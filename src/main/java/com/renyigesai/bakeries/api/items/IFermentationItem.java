package com.renyigesai.bakeries.api.items;

import com.renyigesai.bakeries.common.init.BakeriesDataComponents;
import net.minecraft.world.item.ItemStack;

public interface IFermentationItem {

    int getCraftingCount();

    default boolean isPerfectFermentation(ItemStack stack){
        return stack.has(BakeriesDataComponents.PERFECT_FERMENTATION) && Boolean.TRUE.equals(stack.get(BakeriesDataComponents.PERFECT_FERMENTATION));
    }

    default int fermentationCraftingCount(ItemStack stack){
        if (isPerfectFermentation(stack)){
            return getCraftingCount();
        }else {
            return 1;
        }
    }
}
