package com.renyigesai.bakeries.recipe;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class DrinkRecipe extends SimpleMachineRecipe {
    public DrinkRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int count) {
        super(id, ingredient, result, count,
                new ResourceLocation(BakeriesMod.MODID, "drink"),
                new ResourceLocation(BakeriesMod.MODID, "drink"));
    }
}
