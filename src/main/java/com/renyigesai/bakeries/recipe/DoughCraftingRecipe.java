package com.renyigesai.bakeries.recipe;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

@SuppressWarnings("unused")
public class DoughCraftingRecipe extends SimpleMachineRecipe {
    public DoughCraftingRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int count) {
        super(id, ingredient, result, count,
                new ResourceLocation(BakeriesMod.MODID, "dough_crafting"),
                new ResourceLocation(BakeriesMod.MODID, "dough_crafting"));
    }

    public static class Serializer extends SimpleMachineRecipe.Serializer {
        public Serializer() {
            super(DoughCraftingRecipe::new);
        }
    }
}
