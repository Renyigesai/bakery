package com.renyigesai.bakeries.inventory.fermentation_barrel;

import com.renyigesai.bakeries.block.fermentation_barrel.FermentationBarrelBlockEntity;
import com.renyigesai.bakeries.init.BakeriesMenuType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

public class FermentationBarrelMenu extends AbstractContainerMenu {
    protected final FermentationBarrelBlockEntity blockEntity;
    private final Player player;
    private final IItemHandler playerInventory;

    public FermentationBarrelMenu(int windowId, Inventory playerInventory, FermentationBarrelBlockEntity blockEntity) {
        super(BakeriesMenuType.FERMENTATION_BARREL_MENU.get(), windowId);
        this.blockEntity = blockEntity;
        this.player = playerInventory.player;
        this.playerInventory = new InvWrapper(playerInventory);

        int inputStartX = 31;
        int inputStartY = 21;
        int borderSlotSize = 18;
        for (int row = 0; row < 2; ++row) {
            for (int column = 0; column < 3; ++column) {
                this.addSlot(new SlotItemHandler(blockEntity.getInventory(), (row * 3) + column,
                        inputStartX + (column * borderSlotSize),
                        inputStartY + (row * borderSlotSize)));
            }
        }
        addSlot(new SlotItemHandler(blockEntity.getInventory(), 7, 122, 24){
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player playerIn) {
                return false;
            }
        });
        addSlot(new SlotItemHandler(blockEntity.getInventory(), 8, 122, 53));
        layoutPlayerInventorySlots(8, 84);
    }

    public static FermentationBarrelMenu create(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        BlockPos pos = data.readBlockPos();
        BlockEntity blockEntity = playerInventory.player.level().getBlockEntity(pos);
        if (blockEntity instanceof FermentationBarrelBlockEntity) {
            return new FermentationBarrelMenu(windowId, playerInventory, (FermentationBarrelBlockEntity) blockEntity);
        }
        throw new IllegalStateException("Block entity is not an FermentationBarrelBlockEntity!");
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // 玩家物品栏
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // 玩家快捷栏
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack originalStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            originalStack = stackInSlot.copy();
            if (slotIndex < 6 || slotIndex == 7) {
                if (!this.moveItemStackTo(stackInSlot, 8, 43, false)) { // 尝试移到玩家背包（36槽）
                    return ItemStack.EMPTY;
                }
            }
            else{
                if (!this.moveItemStackTo(stackInSlot, 0, 6, false)) { // 10~18是原料槽
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

    public FermentationBarrelBlockEntity getBlockEntity() {
        return blockEntity;
    }
}
