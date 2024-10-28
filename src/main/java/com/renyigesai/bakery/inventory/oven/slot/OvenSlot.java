package com.renyigesai.bakery.inventory.oven.slot;

import com.renyigesai.bakery.init.BakeryItemTag;
import lombok.Getter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class OvenSlot extends SlotItemHandler {
    public Boolean isPickup;
    @Getter
    private final int slot;
    public OvenSlot(IItemHandler iItemHandler, int pSlot, int pX, int pY, boolean isPickup) {
        super(iItemHandler, pSlot, pX, pY);
        this.isPickup = isPickup;
        this.slot = pSlot;
    }

    @Override
    public boolean mayPickup(Player pPlayer) {
        return isPickup;
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
