package com.renyigesai.bakeries.data;

import com.renyigesai.bakeries.data.recipes.BlenderRecipes;
import com.renyigesai.bakeries.data.recipes.OvenRecipes;
import com.renyigesai.bakeries.data.recipes.SingleItemRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;


public class Recipe extends RecipeProvider {

    public Recipe(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        OvenRecipes.register(consumer);
        BlenderRecipes.register(consumer);
        SingleItemRecipes.register(consumer);
    }

    public void minecraft(RecipeOutput recipeOutput){

    }
}