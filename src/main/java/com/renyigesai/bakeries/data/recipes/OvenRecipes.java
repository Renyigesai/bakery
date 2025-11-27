package com.renyigesai.bakeries.data.recipes;

import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.data.builder.OvenRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class OvenRecipes extends Recipes {
    public static void register(RecipeOutput output) {
       onOven(output);
    }

    private static void onOven(RecipeOutput output){
        OvenRecipeBuilder.oven(BakeriesItems.ROUND_BREAD, 1, 200, 155, 205,180, Ingredient.of(new ItemStack(BakeriesItems.ROUND_BREAD_DOUGH.get()))).save(output, name(BakeriesItems.ROUND_BREAD.get()));
        OvenRecipeBuilder.oven(BakeriesItems.BAGEL, 1, 200, 200, 250,225, Ingredient.of(new ItemStack(BakeriesItems.BAGEL_DOUGH.get()))).save(output, name(BakeriesItems.BAGEL.get()));
        OvenRecipeBuilder.oven(BakeriesItems.WHOLE_WHEAT_BAGEL, 1, 200, 200, 250,225, Ingredient.of(new ItemStack(BakeriesItems.WHOLE_WHEAT_BAGEL_DOUGH.get()))).save(output, name(BakeriesItems.WHOLE_WHEAT_BAGEL.get()));
        OvenRecipeBuilder.oven(BakeriesItems.BAGUETTE, 1, 300, 230, 250,240, Ingredient.of(new ItemStack(BakeriesItems.BAGUETTE_DOUGH.get()))).save(output, name(BakeriesItems.BAGUETTE.get()));
        OvenRecipeBuilder.oven(BakeriesItems.CIABATTA, 1, 200, 210, 230,220, Ingredient.of(new ItemStack(BakeriesItems.CIABATTA_DOUGH.get()))).save(output, name(BakeriesItems.CIABATTA.get()));
        OvenRecipeBuilder.oven(BakeriesItems.BROWN_SUGAR_ROLL, 1, 300, 155, 185,170, Ingredient.of(new ItemStack(BakeriesItems.BROWN_SUGAR_ROLL_DOUGH.get()))).save(output, name(BakeriesItems.BROWN_SUGAR_ROLL.get()));
        OvenRecipeBuilder.oven(BakeriesItems.COFFEE_BEAN, 1, 300, 200, 250,0, Ingredient.of(new ItemStack(BakeriesItems.RAW_COFFEE_BEAN.get()))).save(output, name(BakeriesItems.COFFEE_BEAN.get()));
        OvenRecipeBuilder.oven(BakeriesItems.CROISSANT, 1, 200, 175, 185,180, Ingredient.of(new ItemStack(BakeriesItems.CROISSANT_DOUGH.get()))).save(output, name(BakeriesItems.CROISSANT.get()));
        OvenRecipeBuilder.oven(BakeriesItems.FOCACCIA, 1, 300, 230, 250,240, Ingredient.of(new ItemStack(BakeriesItems.FOCACCIA_DOUGH.get()))).save(output, name(BakeriesItems.FOCACCIA.get()));
        OvenRecipeBuilder.oven(BakeriesItems.PINEAPPLE_BUN, 1, 200, 170, 190,180, Ingredient.of(new ItemStack(BakeriesItems.PINEAPPLE_BUN_DOUGH.get()))).save(output, name(BakeriesItems.PINEAPPLE_BUN.get()));
        OvenRecipeBuilder.oven(BakeriesItems.SALT_CROISSANT, 1, 200, 180, 190,180, Ingredient.of(new ItemStack(BakeriesItems.SALT_CROISSANT_DOUGH.get()))).save(output, name(BakeriesItems.SALT_CROISSANT.get()));
        OvenRecipeBuilder.oven(BakeriesItems.MOULD_TOAST, 1, 400, 135, 185,0, Ingredient.of(new ItemStack(BakeriesItems.MOULD_TOAST_DOUGH.get()))).save(output, name(BakeriesItems.TOAST.get()));
        OvenRecipeBuilder.oven(BakeriesItems.COUNTRY_BREAD, 1, 300, 225, 275,0, Ingredient.of(new ItemStack(BakeriesItems.COUNTRY_BREAD_DOUGH.get()))).save(output, name(BakeriesItems.COUNTRY_BREAD.get()));
    }
}
