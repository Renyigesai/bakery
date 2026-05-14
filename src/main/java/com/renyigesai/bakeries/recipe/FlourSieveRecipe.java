package com.renyigesai.bakeries.recipe;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class FlourSieveRecipe extends SimpleMachineRecipe {
    public FlourSieveRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int count) {
        super(id, ingredient, result, count,
                new ResourceLocation(BakeriesMod.MODID, "flour_sieve"),
                new ResourceLocation(BakeriesMod.MODID, "flour_sieve"));
    }
}
