package com.renyigesai.bakeries.compat.jei.recipe;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Unmodifiable;

public interface IListRecipe {

    @Unmodifiable
    ItemStack inputs();

    @Unmodifiable
    ItemStack outputs();

}
