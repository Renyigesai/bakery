package com.renyigesai.bakeries.recipe.flour_sieve;

import com.renyigesai.bakeries.api.HardRecipe;
import net.minecraft.world.item.ItemStack;

public class FlourSieveHardRecipe implements HardRecipe {


    public final ItemStack input;
    public final ItemStack output;

    public FlourSieveHardRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public ItemStack getinput() {
        return new ItemStack(this.input.getItem());
    }

    @Override
    public ItemStack getoutput() {
        return new ItemStack(this.output.getItem());
    }
}
