package com.renyigesai.bakeries.common.recipe;

import com.renyigesai.bakeries.api.items.IFermentationItem;
import com.renyigesai.bakeries.common.init.BakeriesRecipeTypes;
import net.minecraft.core.HolderLookup;
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

    @Override
    public ItemStack assemble(SingleRecipeInput singleRecipeInput, HolderLookup.Provider registries) {
        ItemStack input = singleRecipeInput.item();
        if (!input.isEmpty()) {
            if (input.getItem() instanceof IFermentationItem fermentationItem) {
                int multiplication = fermentationItem.fermentationCraftingCount(input);
                // 复制原始模板，避免污染
                ItemStack resultOut = this.result.copy();
                resultOut.setCount(resultOut.getCount() * multiplication);
                return resultOut;
            }
        }
        return super.assemble(singleRecipeInput, registries);
    }
}
