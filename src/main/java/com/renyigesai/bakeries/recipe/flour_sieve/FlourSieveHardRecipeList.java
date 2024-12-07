package com.renyigesai.bakeries.recipe.flour_sieve;

import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlourSieveHardRecipeList {
    FlourSieveHardRecipe fr_1 = new FlourSieveHardRecipe(new ItemStack(BakeriesItems.WHOLE_WHEAT_FLOUR.get()),new ItemStack(BakeriesItems.FLOUR.get()));
    FlourSieveHardRecipe fr_2 = new FlourSieveHardRecipe(new ItemStack(BakeriesItems.RAW_SALT_BLOCK.get()),new ItemStack(BakeriesItems.SALT.get()));
    List<FlourSieveHardRecipe> flour_recipe = new ArrayList<>(Arrays.asList(fr_1,fr_2));

    public List<FlourSieveHardRecipe> getFlourSieveRecipe(){
        return flour_recipe;
    }
}
