package com.renyigesai.bakeries.integration.jei.recipe;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public interface IListRecipe {

    @Unmodifiable
    ItemStack inputs();

    @Unmodifiable
    ItemStack outputs();

}
