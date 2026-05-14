package com.renyigesai.bakeries.menu;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractMachineMenu extends AbstractContainerMenu {
    protected final Container container;
    protected final int machineSlotCount;
    protected final ContainerData data;

    protected AbstractMachineMenu(MenuType<?> type, int syncId, Inventory playerInventory, int machineSlotCount, int dataCount) {
        this(type, syncId, playerInventory, new SimpleContainer(machineSlotCount), machineSlotCount, new SimpleContainerData(dataCount));
    }

    protected AbstractMachineMenu(MenuType<?> type, int syncId, Inventory playerInventory, Container container, int machineSlotCount, ContainerData data) {
        super(type, syncId);
        this.container = container;
        this.machineSlotCount = machineSlotCount;
        this.data = data;
        container.startOpen(playerInventory.player);
        this.addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();

            if (slotIndex < machineSlotCount) {
                if (!this.moveItemStackTo(stack, machineSlotCount, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, machineSlotCount, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return result;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    protected void addPlayerInventorySlots(Inventory playerInventory, int inventoryStartX, int inventoryStartY, int hotbarY) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, inventoryStartX + col * 18, inventoryStartY + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, inventoryStartX + col * 18, hotbarY));
        }
    }

    public int getProgress() {
        return data.get(0);
    }

    public int getMaxProgress() {
        return data.get(1);
    }

    public boolean isCrafting() {
        return getProgress() > 0 && getMaxProgress() > 0;
    }
}
