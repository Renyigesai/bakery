package com.renyigesai.bakeries.menu;

import com.renyigesai.bakeries.init.BakeriesMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DoughCraftingTableMenu extends AbstractMachineMenu {
    private static final int SLOT_COUNT = 2;

    public DoughCraftingTableMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, null);
    }

    public DoughCraftingTableMenu(int syncId, Inventory playerInventory, Container container) {
        super(BakeriesMenuTypes.DOUGH_CRAFTING_TABLE, syncId, playerInventory, container == null ? new SimpleContainer(SLOT_COUNT) : container, SLOT_COUNT);
        this.addSlot(new Slot(this.container, 0, 20, 33));
        this.addSlot(new Slot(this.container, 1, 143, 33) {
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

            if (slotIndex == 1) {
                if (!this.moveItemStackTo(stack, machineSlotCount, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, result);
            } else if (slotIndex == 0) {
                if (!this.moveItemStackTo(stack, machineSlotCount, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            slot.onTake(player, stack);
        }
        return result;
    }
}
