package com.renyigesai.bakeries.recipe.oven;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class OvenRecipe extends AbstractOvenRecipe {
    public OvenRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int count) {
        super(id, ingredient, result, count);
    }
}
