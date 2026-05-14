package com.renyigesai.bakeries.menu;

import com.renyigesai.bakeries.init.BakeriesMenuTypes;
import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DoughCraftingTableMenu extends AbstractMachineMenu {
    private static final int SLOT_COUNT = 2;
    private static final int INPUT_SLOT = 0;
    private static final int RESULT_SLOT = 1;
    private static final int RESULT_CONTAINER_SLOT = 0;
    private static final int INV_SLOT_START = 2;
    private static final int INV_SLOT_END = 29;
    private static final int HOTBAR_SLOT_START = 29;
    private static final int HOTBAR_SLOT_END = 38;

    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private final ResultContainer resultContainer = new ResultContainer();
    private final Inventory playerInventory;
    private List<SimpleMachineRecipe> recipes = new ArrayList<>();
    private ItemStack lastInput = ItemStack.EMPTY;
    private Runnable slotUpdateListener = () -> {};

    public DoughCraftingTableMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, null);
    }

    public DoughCraftingTableMenu(int syncId, Inventory playerInventory, Container container) {
        super(BakeriesMenuTypes.DOUGH_CRAFTING_TABLE, syncId, playerInventory, container == null ? new SimpleContainer(SLOT_COUNT) : container, SLOT_COUNT, new net.minecraft.world.inventory.SimpleContainerData(2));
        this.playerInventory = playerInventory;

        this.container.setChanged();
        this.addSlot(new Slot(this.container, INPUT_SLOT, 20, 33));
        this.addSlot(new Slot(this.resultContainer, RESULT_CONTAINER_SLOT, 143, 33) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack craftedStack) {
                craftedStack.onCraftedBy(player.level(), player, craftedStack.getCount());
                ItemStack input = DoughCraftingTableMenu.this.container.getItem(INPUT_SLOT);
                if (!input.isEmpty()) {
                    input.shrink(1);
                    DoughCraftingTableMenu.this.container.setChanged();
                }
                DoughCraftingTableMenu.this.setupResultSlot();
                super.onTake(player, craftedStack);
            }
        });

        this.addPlayerInventorySlots(playerInventory, 8, 84, 142);
        this.addDataSlot(this.selectedRecipeIndex);
        this.slotsChanged(this.container);
    }

    public DoughCraftingTableMenu(int syncId, Inventory playerInventory, Container container, net.minecraft.world.inventory.ContainerData ignoredData) {
        this(syncId, playerInventory, container);
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id >= 0 && id < this.recipes.size()) {
            this.selectedRecipeIndex.set(id);
            this.setupResultSlot();
            return true;
        }
        return false;
    }

    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }

    public List<SimpleMachineRecipe> getRecipes() {
        return this.recipes;
    }

    public int getNumRecipes() {
        return this.recipes.size();
    }

    public boolean hasInputItem() {
        return this.container.getItem(INPUT_SLOT).isEmpty() ? false : !this.recipes.isEmpty();
    }

    public void registerUpdateListener(Runnable listener) {
        this.slotUpdateListener = listener;
    }

    @Override
    public void slotsChanged(Container changedContainer) {
        super.slotsChanged(changedContainer);
        ItemStack input = this.container.getItem(INPUT_SLOT);
        if (!ItemStack.isSameItemSameTags(input, this.lastInput) || input.getCount() != this.lastInput.getCount()) {
            this.lastInput = input.copy();
            this.refreshRecipeList();
        }
    }

    private void refreshRecipeList() {
        this.recipes = this.playerInventory.player.level().getRecipeManager().getRecipesFor(BakeriesRecipeTypes.DOUGH_CRAFTING, this.container, this.playerInventory.player.level());
        this.selectedRecipeIndex.set(this.recipes.isEmpty() ? -1 : 0);
        this.setupResultSlot();
        this.slotUpdateListener.run();
    }

    private void setupResultSlot() {
        int selected = this.selectedRecipeIndex.get();
        if (selected >= 0 && selected < this.recipes.size()) {
            SimpleMachineRecipe recipe = this.recipes.get(selected);
            ItemStack result = recipe.assemble(this.container, this.playerInventory.player.level().registryAccess());
            this.resultContainer.setItem(RESULT_CONTAINER_SLOT, result);
        } else {
            this.resultContainer.setItem(RESULT_CONTAINER_SLOT, ItemStack.EMPTY);
        }
        this.broadcastChanges();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            Item item = stackInSlot.getItem();
            itemstack = stackInSlot.copy();
            if (slotIndex == RESULT_SLOT) {
                item.onCraftedBy(stackInSlot, player.level(), player);
                if (!this.moveItemStackTo(stackInSlot, INV_SLOT_START, HOTBAR_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stackInSlot, itemstack);
            } else if (slotIndex == INPUT_SLOT) {
                if (!this.moveItemStackTo(stackInSlot, INV_SLOT_START, HOTBAR_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                SimpleContainer probe = new SimpleContainer(1);
                probe.setItem(0, stackInSlot.copyWithCount(1));
                if (!this.playerInventory.player.level().getRecipeManager().getRecipesFor(BakeriesRecipeTypes.DOUGH_CRAFTING, probe, this.playerInventory.player.level()).isEmpty()) {
                    if (!this.moveItemStackTo(stackInSlot, INPUT_SLOT, RESULT_SLOT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= INV_SLOT_START && slotIndex < INV_SLOT_END) {
                    if (!this.moveItemStackTo(stackInSlot, HOTBAR_SLOT_START, HOTBAR_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= HOTBAR_SLOT_START && slotIndex < HOTBAR_SLOT_END && !this.moveItemStackTo(stackInSlot, INV_SLOT_START, INV_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (stackInSlot.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, stackInSlot);
            this.broadcastChanges();
        }
        return itemstack;
    }
}
