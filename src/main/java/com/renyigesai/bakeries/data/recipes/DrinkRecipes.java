package com.renyigesai.bakeries.data.recipes;

import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.init.BakeriesTags;
import com.renyigesai.bakeries.common.tag.CommonTags;
import com.renyigesai.bakeries.data.builder.DrinkBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

public class DrinkRecipes extends Recipes{
    public static void register(RecipeOutput output) {
        onDrink(output);
    }

    private static void onDrink(RecipeOutput output){
        DrinkBuilder.drink(BakeriesItems.ICED_AMERICAN.get(),1).addIngredient(Items.ICE).addIngredient(Items.WATER_BUCKET).addIngredient(BakeriesItems.MOKA_POT_FILL.get()).build(output);
        DrinkBuilder.drink(BakeriesItems.ICED_LATTE.get(),1).addIngredient(Items.ICE).addIngredient(CommonTags.MILK).addIngredient(BakeriesItems.MOKA_POT_FILL.get()).build(output);
        DrinkBuilder.drink(BakeriesItems.BROWN_SUGAR_LATTE.get(),1).addIngredient(Items.ICE).addIngredient(CommonTags.MILK).addIngredient(BakeriesItems.BROWN_SUGAR_CUBE).addIngredient(BakeriesItems.MOKA_POT_FILL.get()).build(output);
        DrinkBuilder.drink(BakeriesItems.MATCHA_LATTE.get(),1).addIngredient(Items.ICE).addIngredient(CommonTags.MILK).addIngredient(CommonTags.MATCHA).build(output);
        DrinkBuilder.drink(BakeriesItems.CREAM_BINGLE_COFFEE.get(),1).addIngredient(Items.PACKED_ICE).addIngredient(CommonTags.MILK).addIngredient(BakeriesItems.MOKA_POT_FILL.get()).addIngredient(BakeriesItems.FOAMED_CREAM).build(output);
        DrinkBuilder.drink(BakeriesItems.TARO_MILK.get(),1).addIngredient(Items.ICE).addIngredient(CommonTags.MILK).addIngredient(BakeriesItems.MASHED_TARO.get()).build(output);
    }
}
