package com.renyigesai.bakeries.menu;

import com.renyigesai.bakeries.init.BakeriesMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;

public class OvenMenu extends AbstractMachineMenu {
    private static final int SLOT_COUNT = 6;

    public OvenMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, null, null);
    }

    public OvenMenu(int syncId, Inventory playerInventory, Container container) {
        this(syncId, playerInventory, container, null);
    }

    public OvenMenu(int syncId, Inventory playerInventory, Container container, ContainerData data) {
        super(BakeriesMenuTypes.OVEN, syncId, playerInventory, container == null ? new SimpleContainer(SLOT_COUNT) : container, SLOT_COUNT, data == null ? new SimpleContainerData(3) : data);
        this.addSlot(new Slot(this.container, 0, 52, 16));
        this.addSlot(new Slot(this.container, 1, 70, 16));
        this.addSlot(new Slot(this.container, 2, 88, 16));
        this.addSlot(new Slot(this.container, 3, 52, 46));
        this.addSlot(new Slot(this.container, 4, 70, 46));
        this.addSlot(new Slot(this.container, 5, 88, 46));
        this.addPlayerInventorySlots(playerInventory);
    }

    public int getOvenTemperature() {
        return this.data.get(2);
    }
}
