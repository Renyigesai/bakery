package com.renyigesai.bakeries.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class MultiOutputSingleItemRecipe extends SimpleMachineRecipe {
    private final NonNullList<ItemStack> allResults;

    public MultiOutputSingleItemRecipe(ResourceLocation id, Ingredient ingredient, NonNullList<ItemStack> results, ResourceLocation typeId, ResourceLocation serializerId) {
        super(id, ingredient, results.isEmpty() ? ItemStack.EMPTY : results.get(0), results.isEmpty() ? 1 : Math.max(1, results.get(0).getCount()), typeId, serializerId);
        this.allResults = results;
    }

    public NonNullList<ItemStack> getAllResults() {
        return allResults;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return allResults.isEmpty() ? ItemStack.EMPTY : allResults.get(0).copy();
    }
}
