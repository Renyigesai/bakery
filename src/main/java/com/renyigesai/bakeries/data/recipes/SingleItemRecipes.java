package com.renyigesai.bakeries.data.recipes;

import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.tag.CommonTags;
import com.renyigesai.bakeries.data.builder.BreadKnifeBuilder;
import com.renyigesai.bakeries.data.builder.DoughCraftingBuilder;
import com.renyigesai.bakeries.data.builder.FlourSieveBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class SingleItemRecipes extends Recipes{
    public static void register(RecipeOutput output) {
        onDoughCrafting(output);
        onBreadKnife(output);
        onFlourSieve(output);
    }

    private static void onDoughCrafting(RecipeOutput output){
        addDoughCrafting(Ingredient.of(BakeriesItems.SWEET_DOUGH),BakeriesItems.BAGEL_DOUGH,4,output);
        addDoughCrafting(Ingredient.of(BakeriesItems.WHOLE_WHEAT_DOUGH),BakeriesItems.WHOLE_WHEAT_BAGEL_DOUGH,4,output);
        addDoughCrafting(Ingredient.of(BakeriesItems.SWEET_DOUGH),BakeriesItems.ROUND_BREAD_DOUGH,6,output);
        addDoughCrafting(Ingredient.of(BakeriesItems.PASTRY),BakeriesItems.CROISSANT_DOUGH,2,output);
        addDoughCrafting(Ingredient.of(BakeriesItems.SALTED_DOUGH),BakeriesItems.BAGUETTE_DOUGH,2,output);
        addDoughCrafting(Ingredient.of(BakeriesItems.SALTED_DOUGH),BakeriesItems.CIABATTA_DOUGH,4,output);
        addDoughCrafting(Ingredient.of(BakeriesItems.SALTED_DOUGH),BakeriesItems.COUNTRY_BREAD_DOUGH,1,output);
    }

    private static void onBreadKnife(RecipeOutput output){
        addBreadKnife(Ingredient.of(BakeriesItems.TOAST),BakeriesItems.SLICED_TOAST,4,output);
        addBreadKnife(Ingredient.of(BakeriesItems.CHEESE_COCOA_TOAST),BakeriesItems.SLICED_CHEESE_COCOA_TOAST,4,output);
        addBreadKnife(Ingredient.of(BakeriesItems.COUNTRY_BREAD),BakeriesItems.COUNTRY_BREAD_SLICE,6,output);
    }

    private static void onFlourSieve(RecipeOutput output){
        addFlourSieve(Ingredient.of(CommonTags.WHOLE_WHEAT_FLOUR),BakeriesItems.FLOUR,1,output);
        addFlourSieve(Ingredient.of(ItemTags.LEAVES),BakeriesItems.MATCHA_POWDER,2,output);
        addFlourSieve(Ingredient.of(BakeriesItems.RAW_SALT_BLOCK),BakeriesItems.SALT,9,output);
        addFlourSieve(Ingredient.of(Items.COCOA_BEANS),BakeriesItems.COCOA_POWDER,1,output);
    }


    private static  void addBreadKnife(Ingredient recipeItems, ItemLike output, int count, RecipeOutput recipeOutput){
        BreadKnifeBuilder.breadKnife(recipeItems,output,count).save(recipeOutput,name(output.asItem()));
    }

    private static  void addDoughCrafting(Ingredient recipeItems, ItemLike output, int count, RecipeOutput recipeOutput){
        DoughCraftingBuilder.doughCrafting(recipeItems,output,count).save(recipeOutput,name(output.asItem()));
    }

    private static  void addFlourSieve(Ingredient recipeItems, ItemLike output, int count, RecipeOutput recipeOutput){
        FlourSieveBuilder.flourSieve(recipeItems,output,count).save(recipeOutput,name(output.asItem()));
    }
}
