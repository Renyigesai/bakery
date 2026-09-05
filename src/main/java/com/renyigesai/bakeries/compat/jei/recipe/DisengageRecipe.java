package com.renyigesai.bakeries.compat.jei.recipe;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record DisengageRecipe(ItemStack inputs, ItemStack outputs) implements IListRecipe {

    public static List<IListRecipe> getDisengageRecipes() {
        return List.of(
                new DisengageRecipe(new ItemStack(BakeriesItems.MOULD_TOAST.get()), new ItemStack(BakeriesItems.TOAST.get())),
                new DisengageRecipe(new ItemStack(BakeriesItems.MOULD_CHEESE_COCOA_TOAST.get()), new ItemStack(BakeriesItems.CHEESE_COCOA_TOAST.get())),
                new DisengageRecipe(new ItemStack(BakeriesItems.MOULD_POUND_CAKE.get()), new ItemStack(BakeriesItems.POUND_CAKE.get())),
                new DisengageRecipe(new ItemStack(BakeriesItems.MOULD_CAKE_BASE.get()), new ItemStack(BakeriesItems.CAKE_BASE.get())),
                new DisengageRecipe(new ItemStack(BakeriesItems.MOULD_CAKE_BASE.get()), new ItemStack(BakeriesItems.CAKE_BASE.get())),
                new DisengageRecipe(new ItemStack(BakeriesItems.MOULD_RED_VELVET_CAKE.get()), new ItemStack(BakeriesItems.RED_VELVET_CAKE_BASE.get())),
                new DisengageRecipe(new ItemStack(BakeriesItems.MOULD_MATCHA_CAKE.get()), new ItemStack(BakeriesItems.MATCHA_CAKE.get())),
                new DisengageRecipe(new ItemStack(BakeriesItems.MOULD_CARROT_CAKE.get()), new ItemStack(BakeriesItems.CARROT_CAKE.get())),
                new DisengageRecipe(new ItemStack(BakeriesItems.MOULD_BASQUE_CAKE.get()), new ItemStack(BakeriesItems.BASQUE_CAKE.get()))
        );
    }
}
