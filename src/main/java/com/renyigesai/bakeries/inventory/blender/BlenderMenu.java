package com.renyigesai.bakeries.inventory.blender;

import com.renyigesai.bakeries.block.blender.BlenderBlockEntity;
import com.renyigesai.bakeries.init.BakeriesMenuType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

public class BlenderMenu extends AbstractContainerMenu {
    private final BlenderBlockEntity blockEntity;
    private final Player player;
    private final IItemHandler playerInventory;
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(10);

    public BlenderMenu(int windowId, Inventory playerInventory, BlenderBlockEntity blockEntity) {
        super(BakeriesMenuType.BLENDER_MENU.get(), windowId);
        this.blockEntity = blockEntity;
        this.player = playerInventory.player;
        this.playerInventory = new InvWrapper(playerInventory);

        // 添加输入槽 (0-8)
        int ix = 54;
        int iy = 17;
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int slotIndex = y * 3 + x;
                addSlot(new SlotItemHandler(blockEntity.getInventory(), slotIndex, ix + (x * 18) + 1, iy + (y * 18) + 1));
            }
        }
        // 添加容器槽 (9)
        addSlot(new SlotItemHandler(blockEntity.getInventory(), 9, 116, 35));
        // 添加输出槽 (10)
        addSlot(new SlotItemHandler(blockEntity.getInventory(), 10, 153, 35));
        // 添加过滤槽 (0-8)
            int fx = 8; // 起始 X 坐标
            int fy = 17; // 起始 Y 坐标
            for (int y = 0; y < 3; ++y) {
                for (int x = 0; x < 3; ++x) {
                    int slotIndex = (y * 3) + x;
                    addSlot(new SlotItemHandler(blockEntity.getFiltrationinventory(), slotIndex, fx + x * 8, fy + y * 17));
                }
            }
            addSlot(new SlotItemHandler(blockEntity.getFiltrationinventory(), 9, 32, 51));
        // 添加玩家物品栏
        addPlayerSlots(8,84);
    }

    public static BlenderMenu create(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        BlockPos pos = data.readBlockPos();
        BlockEntity blockEntity = playerInventory.player.level().getBlockEntity(pos);
        if (blockEntity instanceof BlenderBlockEntity) {
            return new BlenderMenu(windowId, playerInventory, (BlenderBlockEntity) blockEntity);
        }
        throw new IllegalStateException("Block entity is not an BlenderCoolerBlockEntity!");
    }

    protected void addPlayerSlots(int x, int y) {
        for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot)
            this.addSlot(new SlotItemHandler(playerInventory, hotbarSlot, x + hotbarSlot * 18, y + 58));
        for (int row = 0; row < 3; ++row)
            for (int col = 0; col < 9; ++col)
                this.addSlot(new SlotItemHandler(playerInventory, col + row * 9 + 9, x + col * 18, y + row * 18));
    }

    @Override
    public void clicked(int pSlotId, int pButton, ClickType pClickType, Player pPlayer) {
        if (pSlotId < 11 || pSlotId >= 21){
            super.clicked(pSlotId, pButton, pClickType, pPlayer);
            return;
        }
        if (pClickType== ClickType.THROW)
            return;
        ItemStack held = getCarried();
        if (pClickType == ClickType.CLONE) {
            if (player.isCreative() && held.isEmpty()) {
                ItemStack stackInSlot =this.slots.get(pSlotId).getItem()
                        .copy();
                stackInSlot.setCount(1);
                setCarried(stackInSlot);
                return;
            }
            return;
        }

        ItemStack insert;
        if (held.isEmpty()) {
            insert = ItemStack.EMPTY;
        } else {
            insert = held.copy();
            insert.setCount(1);
        }
        this.slots.get(pSlotId).set(insert);
        getSlot(pSlotId).setChanged();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack originalStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            originalStack = stackInSlot.copy();
            if (slotIndex < 11) {
                if (!this.moveItemStackTo(stackInSlot, 21, 56, false)) { // 尝试移到玩家背包（36槽）
                    return ItemStack.EMPTY;
                }
            }
            else{
                if (!this.moveItemStackTo(stackInSlot, 0, 11, false)) { // 10~18是原料槽
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

    public BlenderBlockEntity getBlockEntity() {
        return blockEntity;
    }
}
