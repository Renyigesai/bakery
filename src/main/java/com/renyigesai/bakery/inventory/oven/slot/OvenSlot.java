package com.renyigesai.bakery.inventory.oven.slot;

import lombok.Getter;
import net.minecraft.world.entity.player.Player;
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

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
