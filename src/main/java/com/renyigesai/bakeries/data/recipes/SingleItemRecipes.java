package com.renyigesai.bakeries.data.recipes;

import com.renyigesai.bakeries.api.conditions.ConfigCondition;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.tag.CommonTags;
import com.renyigesai.bakeries.data.builder.BreadKnifeBuilder;
import com.renyigesai.bakeries.data.builder.DoughCraftingBuilder;
import com.renyigesai.bakeries.data.builder.FlourSieveBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleItemRecipes extends Recipes{
    public static void register(RecipeOutput output) {
        onDoughCrafting(output);
        onBreadKnife(output);
        onFlourSieve(output);
    }

    private static void onDoughCrafting(RecipeOutput output){
//        List<ICondition> conditions = List.of(
//                new ConfigCondition("","","")
//        );
//        ConfigCondition configCondition = new ConfigCondition("fermentationGameplay", "boolean", "false");
        addDoughCraftingFermentationGameplay(Ingredient.of(BakeriesItems.SWEET_DOUGH),Ingredient.of(BakeriesItems.SWEET_DOUGH_FERMENTATION),BakeriesItems.BAGEL_DOUGH,4,output);
        addDoughCraftingFermentationGameplay(Ingredient.of(BakeriesItems.WHOLE_WHEAT_DOUGH),Ingredient.of(BakeriesItems.WHOLE_WHEAT_DOUGH_FERMENTATION),BakeriesItems.WHOLE_WHEAT_BAGEL_DOUGH,4,output);
        addDoughCraftingFermentationGameplay(Ingredient.of(BakeriesItems.SWEET_DOUGH),Ingredient.of(BakeriesItems.SWEET_DOUGH_FERMENTATION),BakeriesItems.ROUND_BREAD_DOUGH,6,output);
        addDoughCrafting(Ingredient.of(BakeriesItems.PASTRY),BakeriesItems.CROISSANT_DOUGH,2,output);
        addDoughCraftingFermentationGameplay(Ingredient.of(BakeriesItems.SALTED_DOUGH),Ingredient.of(BakeriesItems.SALTED_DOUGH_FERMENTATION),BakeriesItems.BAGUETTE_DOUGH,2,output);
        addDoughCraftingFermentationGameplay(Ingredient.of(BakeriesItems.SALTED_DOUGH),Ingredient.of(BakeriesItems.SALTED_DOUGH_FERMENTATION),BakeriesItems.CIABATTA_DOUGH,4,output);
        addDoughCraftingFermentationGameplay(Ingredient.of(BakeriesItems.SALTED_DOUGH),Ingredient.of(BakeriesItems.SALTED_DOUGH_FERMENTATION),BakeriesItems.COUNTRY_BREAD_DOUGH,1,output);
        addDoughCrafting(Ingredient.of(BakeriesItems.PASTRY),BakeriesItems.EGG_TART_SHELL,3,output);
    }

    private static void onBreadKnife(RecipeOutput output){
        addBreadKnife(Ingredient.of(BakeriesItems.WHOLE_EGG),getItems(new ItemStack(BakeriesItems.RAW_EGG_YOLK.get()),new ItemStack(BakeriesItems.RAW_PROTEIN.get())),output);
        addBreadKnife(Ingredient.of(BakeriesItems.TOAST),new ItemStack(BakeriesItems.SLICED_TOAST.get(),4) ,output);
        addBreadKnife(Ingredient.of(BakeriesItems.CHEESE_COCOA_TOAST),new ItemStack(BakeriesItems.SLICED_CHEESE_COCOA_TOAST.get(),4),output);
        addBreadKnife(Ingredient.of(BakeriesItems.COUNTRY_BREAD),new ItemStack(BakeriesItems.COUNTRY_BREAD_SLICE.get(),6),output);
        addBreadKnife(Ingredient.of(Items.EGG),new ItemStack(BakeriesItems.WHOLE_EGG.get(),1),output);
    }

    private static void onFlourSieve(RecipeOutput output){
        addFlourSieve(Ingredient.of(CommonTags.WHOLE_WHEAT_FLOUR),BakeriesItems.FLOUR,1,output);
        addFlourSieve(Ingredient.of(ItemTags.LEAVES),BakeriesItems.MATCHA_POWDER,2,output);
        addFlourSieve(Ingredient.of(BakeriesItems.RAW_SALT_BLOCK),BakeriesItems.SALT,9,output);
        addFlourSieve(Ingredient.of(Items.COCOA_BEANS),BakeriesItems.COCOA_POWDER,1,output);
    }


    private static  void addBreadKnife(Ingredient recipeItems, NonNullList<ItemStack> outputs, RecipeOutput recipeOutput){
        BreadKnifeBuilder.breadKnife(recipeItems,outputs).build(recipeOutput);
    }

    private static  void addBreadKnife(Ingredient recipeItems, ItemStack output, RecipeOutput recipeOutput){
        BreadKnifeBuilder.breadKnife(recipeItems,output).build(recipeOutput);
    }

    private static  void addDoughCrafting(Ingredient recipeItems, ItemLike output, int count, RecipeOutput recipeOutput){
        DoughCraftingBuilder.doughCrafting(recipeItems,output,count).save(recipeOutput,name(output.asItem()));
    }

    private static  void addDoughCraftingFermentationGameplay(Ingredient recipeItems, Ingredient recipeItemsFG,ItemLike output, int count, RecipeOutput recipeOutput){
        ConfigCondition configConditionFalse = new ConfigCondition("fermentationGameplay", "boolean", "false");
        DoughCraftingBuilder.doughCrafting(recipeItems,output,count).save(recipeOutput.withConditions(configConditionFalse),name(output.asItem()));
        ConfigCondition configConditionTrue = new ConfigCondition("fermentationGameplay", "boolean", "true");
        DoughCraftingBuilder.doughCrafting(recipeItemsFG,output,count).save(recipeOutput.withConditions(configConditionTrue),name(output.asItem(),"_fermentation_gameplay"));
    }

    private static  void addFlourSieve(Ingredient recipeItems, ItemLike output, int count, RecipeOutput recipeOutput){
        FlourSieveBuilder.flourSieve(recipeItems,output,count).save(recipeOutput,name(output.asItem()));
    }

    private static NonNullList<ItemStack> getItems(ItemStack... stacks){
        List<ItemStack> list = Arrays.asList(stacks);
        NonNullList<ItemStack> nonNullList = NonNullList.create();
        nonNullList.addAll(list);
        return nonNullList;
    }
}
