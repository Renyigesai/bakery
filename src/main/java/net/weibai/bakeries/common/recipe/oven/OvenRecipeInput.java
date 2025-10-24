package net.weibai.bakeries.common.recipe.oven;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public record OvenRecipeInput(ItemStack itemStack) implements RecipeInput {
    @Override
    public @NotNull ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> itemStack;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 1;
    }
}
