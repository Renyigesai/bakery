package com.renyigesai.bakeries.recipe.flour_sieve;

import com.renyigesai.bakeries.api.HardRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FlourSieveHardRecipe implements HardRecipe {

    public final Item input;
    public final Item output;

    public FlourSieveHardRecipe(Item input, Item output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public ItemStack getinput() {
        return new ItemStack(this.input);
    }

    @Override
    public ItemStack getoutput() {
        return new ItemStack(this.output);
    }
}
