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
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

public class BlenderMenu extends AbstractContainerMenu {
    private final BlenderBlockEntity blockEntity;
    private final Player player;
    private final IItemHandler playerInventory;

    public BlenderMenu(int windowId, Inventory playerInventory, BlenderBlockEntity blockEntity) {
        super(BakeriesMenuType.BLENDER_MENU.get(), windowId);
        this.blockEntity = blockEntity;
        this.player = playerInventory.player;
        this.playerInventory = new InvWrapper(playerInventory);

        // 添加输入槽 (0-8)
        int ix = 61;
        int iy = 16;
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int slotIndex = y * 3 + x;
                addSlot(new SlotItemHandler(blockEntity.getInventory(), slotIndex, ix + (x * 18) + 1, iy + (y * 18) + 1));
            }
        }
        // 添加容器槽 (9)
        addSlot(new SlotItemHandler(blockEntity.getInventory(), 9, 152, 53));
        // 添加输出槽 (10)
        addSlot(new SlotItemHandler(blockEntity.getInventory(), 10, 152, 17));
        // 添加过滤槽 (0-8)
        if (blockEntity.compatibility) {
            int fx = 8; // 起始 X 坐标
            int fy = 18; // 起始 Y 坐标
            for (int y = 0; y < 3; ++y) {
                for (int x = 0; x < 3; ++x) {
                    int slotIndex = (y * 3) + x;
                    addSlot(new SlotItemHandler(blockEntity.getFiltrationinventory(), slotIndex, fx + x * 8, fy + y * 17));
                }
            }
            addSlot(new SlotItemHandler(blockEntity.getFiltrationinventory(), 9, 32, 52));
        }
        // 添加玩家物品栏
        layoutPlayerInventorySlots(8, 84);
    }

    public static BlenderMenu create(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        BlockPos pos = data.readBlockPos();
        BlockEntity blockEntity = playerInventory.player.level().getBlockEntity(pos);
        if (blockEntity instanceof BlenderBlockEntity) {
            return new BlenderMenu(windowId, playerInventory, (BlenderBlockEntity) blockEntity);
        }
        throw new IllegalStateException("Block entity is not an EnchantalCoolerBlockEntity!");
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
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 5) {
                if (!this.moveItemStackTo(itemstack1, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 4, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity.stillValid(player);
    }

    public BlenderBlockEntity getBlockEntity() {
        return blockEntity;
    }
}
