package com.renyigesai.bakeries.recipe.oven;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class AbstractOvenRecipe extends SimpleMachineRecipe {
    protected AbstractOvenRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int count) {
        super(id, ingredient, result, count,
                new ResourceLocation(BakeriesMod.MODID, "oven"),
                new ResourceLocation(BakeriesMod.MODID, "oven"));
    }
}
