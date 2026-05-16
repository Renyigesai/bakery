package com.renyigesai.bakeries.recipe;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

@SuppressWarnings("unused")
public class FermentationBoxRecipe extends SimpleMachineRecipe {
    public FermentationBoxRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int count) {
        super(id, ingredient, result, count,
                new ResourceLocation(BakeriesMod.MODID, "fermentation_box"),
                new ResourceLocation(BakeriesMod.MODID, "fermentation_box"));
    }

    public static class Serializer extends SimpleMachineRecipe.Serializer {
        public Serializer() {
            super(FermentationBoxRecipe::new);
        }
    }
}
