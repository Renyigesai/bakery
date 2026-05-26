package com.renyigesai.bakeries.menu;

import com.renyigesai.bakeries.init.BakeriesMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class FermentationBoxMenu extends AbstractMachineMenu {
    private static final int SLOT_COUNT = 6;

    public FermentationBoxMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, null, new net.minecraft.world.inventory.SimpleContainerData(4));
    }

    public FermentationBoxMenu(int syncId, Inventory playerInventory, Container container) {
        this(syncId, playerInventory, container, new net.minecraft.world.inventory.SimpleContainerData(4));
    }

    public FermentationBoxMenu(int syncId, Inventory playerInventory, Container container, ContainerData data) {
        super(BakeriesMenuTypes.FERMENTATION_BOX, syncId, playerInventory, container == null ? new SimpleContainer(SLOT_COUNT) : container, SLOT_COUNT, data);
        this.addSlot(new SingleItemSlot(this.container, 0, 52, 16));
        this.addSlot(new SingleItemSlot(this.container, 1, 70, 16));
        this.addSlot(new SingleItemSlot(this.container, 2, 88, 16));
        this.addSlot(new SingleItemSlot(this.container, 3, 52, 46));
        this.addSlot(new SingleItemSlot(this.container, 4, 70, 46));
        this.addSlot(new SingleItemSlot(this.container, 5, 88, 46));
        this.addPlayerInventorySlots(playerInventory);
    }

    public int getTemperature() {
        return this.data.get(2);
    }

    public int getPerfectTime() {
        return this.data.get(3);
    }
}
