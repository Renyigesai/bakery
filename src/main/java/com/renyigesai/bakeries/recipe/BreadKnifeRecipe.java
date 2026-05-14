package com.renyigesai.bakeries.recipe;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class BreadKnifeRecipe extends MultiOutputSingleItemRecipe {
    public BreadKnifeRecipe(ResourceLocation id, Ingredient ingredient, NonNullList<ItemStack> results) {
        super(id, ingredient, results,
                new ResourceLocation(BakeriesMod.MODID, "bread_knife"),
                new ResourceLocation(BakeriesMod.MODID, "bread_knife"));
    }
}
