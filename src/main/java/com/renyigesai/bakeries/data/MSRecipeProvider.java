package com.renyigesai.bakeries.data;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.data.recipes.DoughCraftingBuilder;
import com.renyigesai.bakeries.data.recipes.OvenRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;


public class MSRecipeProvider extends RecipeProvider {

    public MSRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        minecraft(consumer);
        mod(consumer);
    }
    public void mod(RecipeOutput recipeOutput){
        OvenRecipeBuilder.oven(BakeriesItems.ROUND_BREAD, 1, 20, 155, 205,180,
                Ingredient.of(new ItemStack(BakeriesItems.ROUND_BREAD_DOUGH.get())))
                .save(recipeOutput, name(BakeriesItems.ROUND_BREAD.get()));

        addDoughCrafting(Ingredient.of(new ItemStack(BakeriesItems.SWEET_DOUGH.get())),BakeriesItems.BAGEL_DOUGH,4,recipeOutput);
        addDoughCrafting(Ingredient.of(new ItemStack(BakeriesItems.WHOLE_WHEAT_DOUGH.get())),BakeriesItems.WHOLE_WHEAT_BAGEL_DOUGH,4,recipeOutput);
        addDoughCrafting(Ingredient.of(new ItemStack(BakeriesItems.SWEET_DOUGH.get())),BakeriesItems.ROUND_BREAD_DOUGH,6,recipeOutput);
        addDoughCrafting(Ingredient.of(new ItemStack(BakeriesItems.PASTRY.get())),BakeriesItems.CROISSANT_DOUGH,2,recipeOutput);
        addDoughCrafting(Ingredient.of(new ItemStack(BakeriesItems.SALTED_DOUGH.get())),BakeriesItems.BAGUETTE_DOUGH,2,recipeOutput);
        addDoughCrafting(Ingredient.of(new ItemStack(BakeriesItems.SALTED_DOUGH.get())),BakeriesItems.CIABATTA_DOUGH,4,recipeOutput);
        addDoughCrafting(Ingredient.of(new ItemStack(BakeriesItems.SALTED_DOUGH.get())),BakeriesItems.COUNTRY_BREAD_DOUGH,1,recipeOutput);

    }
    public void minecraft(RecipeOutput recipeOutput){


    }

    private void addDoughCrafting(Ingredient recipeItems, ItemLike output, int count,RecipeOutput recipeOutput){
        DoughCraftingBuilder.doughCrafting(recipeItems,output,count).save(recipeOutput,name(output.asItem()));
    }

    private ResourceLocation name(Block block) {
        return BakeriesMod.rl(BuiltInRegistries.BLOCK.getKey(block).getPath());
    }
    private ResourceLocation name(Item item) {
        return BakeriesMod.rl(BuiltInRegistries.ITEM.getKey(item).getPath());
    }
    private ResourceLocation name(Block block, String name) {
        return BakeriesMod.rl(BuiltInRegistries.BLOCK.getKey(block).getPath()+"_"+name);
    }
    private ResourceLocation name(Item item, String name) {
        return BakeriesMod.rl(BuiltInRegistries.ITEM.getKey(item).getPath()+"_"+name);
    }
}