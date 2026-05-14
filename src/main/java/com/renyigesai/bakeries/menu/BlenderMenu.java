package com.renyigesai.bakeries.menu;

import com.renyigesai.bakeries.init.BakeriesMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BlenderMenu extends AbstractMachineMenu {
    private static final int SLOT_COUNT = 11;

    public BlenderMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, null, null);
    }

    public BlenderMenu(int syncId, Inventory playerInventory, Container container) {
        this(syncId, playerInventory, container, null);
    }

    public BlenderMenu(int syncId, Inventory playerInventory, Container container, ContainerData data) {
        super(BakeriesMenuTypes.BLENDER, syncId, playerInventory, container == null ? new SimpleContainer(SLOT_COUNT) : container, SLOT_COUNT, data == null ? new SimpleContainerData(2) : data);

        this.addSlot(new Slot(this.container, 0, 55, 18));
        this.addSlot(new Slot(this.container, 1, 73, 18));
        this.addSlot(new Slot(this.container, 2, 91, 18));
        this.addSlot(new Slot(this.container, 3, 55, 36));
        this.addSlot(new Slot(this.container, 4, 73, 36));
        this.addSlot(new Slot(this.container, 5, 91, 36));
        this.addSlot(new Slot(this.container, 6, 55, 54));
        this.addSlot(new Slot(this.container, 7, 73, 54));
        this.addSlot(new Slot(this.container, 8, 91, 54));
        this.addSlot(new Slot(this.container, 9, 116, 35));
        this.addSlot(new Slot(this.container, 10, 153, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
        this.addPlayerInventorySlots(playerInventory, 8, 84, 142);
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
            } else {
                if (!this.moveItemStackTo(stack, 0, 10, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return result;
    }
}
