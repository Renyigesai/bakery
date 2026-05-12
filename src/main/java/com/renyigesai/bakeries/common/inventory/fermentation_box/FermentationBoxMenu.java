package com.renyigesai.bakeries.common.inventory.fermentation_box;

import com.renyigesai.bakeries.common.blocks.blander.BlenderBlockEntity;
import com.renyigesai.bakeries.common.blocks.fermentation_box.FermentationBoxBlockEntity;
import com.renyigesai.bakeries.common.init.BakeriesMenuType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

import java.util.Objects;

public class FermentationBoxMenu extends AbstractContainerMenu {
    private FermentationBoxBlockEntity blockEntity;
    private final Player player;
    private final IItemHandler playerInventory;

    public FermentationBoxMenu(int id, Inventory inv,FriendlyByteBuf byteBuf) {
        this(id,inv,getTileEntity(inv,byteBuf));
    }

    public FermentationBoxMenu(int windowId, Inventory playerInventory,FermentationBoxBlockEntity fermentationBoxBlockEntity) {
        super(BakeriesMenuType.FERMENTATION_BOX_MENU.get(), windowId);
        this.player = playerInventory.player;
        this.playerInventory = new InvWrapper(playerInventory);
        blockEntity = fermentationBoxBlockEntity;
        // ÃÌº” ‰»Î≤€ (0-5)
        ItemStackHandler items = blockEntity.getHandlerItems();
        addSlot(new FermentationBoxSlot(items,0,52, 16));
        addSlot(new FermentationBoxSlot(items,1, 70, 16));
        addSlot(new FermentationBoxSlot(items,2, 88, 16));
        addSlot(new FermentationBoxSlot(items,3, 52, 46));
        addSlot(new FermentationBoxSlot(items,4, 70, 46));
        addSlot(new FermentationBoxSlot(items,5, 88, 46));

        addPlayerSlots(8,84);
    }

    public static FermentationBoxMenu create(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        BlockPos pos = data.readBlockPos();
        BlockEntity blockEntity = playerInventory.player.level().getBlockEntity(pos);
        if (blockEntity instanceof FermentationBoxBlockEntity) {
            return new FermentationBoxMenu(windowId, playerInventory,(FermentationBoxBlockEntity) blockEntity);
        }
        throw new IllegalStateException("Block entity is not an FermentationBoxBlockEntity!");
    }

    protected void addPlayerSlots(int x, int y) {
        for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot)
            this.addSlot(new SlotItemHandler(playerInventory, hotbarSlot, x + hotbarSlot * 18, y + 58));
        for (int row = 0; row < 3; ++row)
            for (int col = 0; col < 9; ++col)
                this.addSlot(new SlotItemHandler(playerInventory, col + row * 9 + 9, x + col * 18, y + row * 18));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack originalStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            originalStack = stackInSlot.copy();
            if (slotIndex < 6) {
                if (!this.moveItemStackTo(stackInSlot, 6, 39, false)) { // ≥¢ ‘“∆µΩÕÊº“±≥∞¸£®36≤€£©
                    return ItemStack.EMPTY;
                }
            }
            else{
                if (!this.moveItemStackTo(stackInSlot, 0, 6, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (stackInSlot.getCount() == originalStack.getCount()) {
                return ItemStack.EMPTY;
            }
        }

        return originalStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity.stillValid(player);
    }

    public FermentationBoxBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        FermentationBoxBlockEntity.onCloseMenu(blockEntity);
    }

    private static FermentationBoxBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof FermentationBoxBlockEntity) {
            return (FermentationBoxBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public static class FermentationBoxSlot extends SlotItemHandler{

        public FermentationBoxSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    }
}
