package com.renyigesai.bakeries.menu;

import com.renyigesai.bakeries.init.BakeriesMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class CupboardMenu extends AbstractMachineMenu {
    private static final int SLOT_COUNT = 21;

    public CupboardMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, null);
    }

    public CupboardMenu(int syncId, Inventory playerInventory, Container container) {
        super(
                BakeriesMenuTypes.CUPBOARD,
                syncId,
                playerInventory,
                container == null ? new SimpleContainer(SLOT_COUNT) : container,
                SLOT_COUNT,
                new net.minecraft.world.inventory.SimpleContainerData(2)
        );

        int startX = 26;
        int startY = 17;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 7; col++) {
                this.addSlot(new Slot(this.container, col + row * 7, startX + col * 18, startY + row * 18));
            }
        }

        this.addPlayerInventorySlots(playerInventory);
    }
}
