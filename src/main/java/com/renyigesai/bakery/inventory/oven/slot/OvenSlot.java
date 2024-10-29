package com.renyigesai.bakery.inventory.oven.slot;

import com.renyigesai.bakery.init.BakeryItemTag;
import lombok.Getter;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class OvenSlot extends SlotItemHandler {
    @Getter
    private final int slot;
    public OvenSlot(IItemHandler iItemHandler, int pSlot, int pX, int pY) {
        super(iItemHandler, pSlot, pX, pY);
        this.slot = pSlot;
    }
    public boolean mayPlace(ItemStack pStack) {
        return isRaeFood(pStack);
    }
    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return 1;
    }

    public static boolean isRaeFood(ItemStack pStack) {
        return pStack.is(BakeryItemTag.RAE_FOOD);
    }

}
