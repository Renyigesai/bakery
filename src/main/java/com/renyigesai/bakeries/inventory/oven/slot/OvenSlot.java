package com.renyigesai.bakeries.inventory.oven.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OvenSlot extends Slot {
    private final int slot;
    public OvenSlot(Container container, int pSlot, int pX, int pY) {
        super(container, pSlot, pX, pY);
        this.slot = pSlot;
    }

    /*public boolean mayPlace(@NotNull ItemStack pStack) {return isRaeFood(pStack);}*/

    @Override
    public int getMaxStackSize(@NotNull ItemStack pStack) {
        return 1;
    }
    @Override
    public int getMaxStackSize()
    {
        return 1;
    }

    /*public static boolean isRaeFood(ItemStack pStack) {return pStack.is(BakeriesItemTag.RAE_FOOD);}*/

}
