package com.renyigesai.bakeries.common.recipe;

import com.renyigesai.bakeries.common.init.BakeriesRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class DoughCraftingRecipe extends SingleItemRecipe {

    public DoughCraftingRecipe(String group, Ingredient ingredient, ItemStack result) {
        super(BakeriesRecipeTypes.DOUGH_CRAFTING_TYPE.get(),BakeriesRecipeTypes.DOUGH_CRAFTING_SERIALIZERS.get(),group, ingredient, result);
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.ingredient.test(input.item());
    }
}
