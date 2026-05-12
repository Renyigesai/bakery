package com.renyigesai.bakeries.common.recipe;

import com.renyigesai.bakeries.common.init.BakeriesRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class FermentationBoxRecipe extends MultiOutputSingleItemRecipe{
    public FermentationBoxRecipe(String group, Ingredient ingredient, NonNullList<ItemStack> results) {
        super(BakeriesRecipeTypes.FERMENTATION_BOX_TYPE.get(), BakeriesRecipeTypes.FERMENTATION_BOX_SERIALIZERS.get(), group, ingredient, results);
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.ingredient.test(input.item());
    }
}
