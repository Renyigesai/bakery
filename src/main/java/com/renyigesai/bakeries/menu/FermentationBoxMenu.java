package com.renyigesai.bakeries.menu;

import com.renyigesai.bakeries.init.BakeriesMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class FermentationBoxMenu extends AbstractMachineMenu {
    private static final int SLOT_COUNT = 6;

    public FermentationBoxMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, null);
    }

    public FermentationBoxMenu(int syncId, Inventory playerInventory, Container container) {
        super(BakeriesMenuTypes.FERMENTATION_BOX, syncId, playerInventory, container == null ? new SimpleContainer(SLOT_COUNT) : container, SLOT_COUNT);
        this.addSlot(new Slot(this.container, 0, 52, 16) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.container, 1, 70, 16) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.container, 2, 88, 16) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.container, 3, 52, 46) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.container, 4, 70, 46) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.container, 5, 88, 46) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addPlayerInventorySlots(playerInventory, 8, 84, 142);
    }
}
