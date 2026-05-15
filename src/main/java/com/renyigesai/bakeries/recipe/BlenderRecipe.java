package com.renyigesai.bakeries.recipe;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

@SuppressWarnings("unused")
public class BlenderRecipe extends SimpleMachineRecipe {
    public BlenderRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int count) {
        super(id, ingredient, result, count,
                new ResourceLocation(BakeriesMod.MODID, "blender"),
                new ResourceLocation(BakeriesMod.MODID, "blender"));
    }
}
