package com.renyigesai.bakeries.common.recipe;

import com.renyigesai.bakeries.common.init.BakeriesRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class BreadKnifeRecipe extends MultiOutputSingleItemRecipe {

    public BreadKnifeRecipe(String group, Ingredient ingredient, NonNullList<ItemStack> results) {
        super(BakeriesRecipeTypes.BREAD_KNIFE_TYPE.get(),BakeriesRecipeTypes.BREAD_KNIFE_SERIALIZERS.get(), group, ingredient, results);
    }


//    public BreadKnifeRecipe(String group, Ingredient ingredient, ItemStack result) {
//        super(BakeriesRecipeTypes.BREAD_KNIFE_TYPE.get(),BakeriesRecipeTypes.BREAD_KNIFE_SERIALIZERS.get(), group, ingredient, result);
//    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.ingredient.test(input.item());
    }
}
