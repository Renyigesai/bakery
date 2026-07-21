package com.renyigesai.bakeries.inventory.fermentation_box;

import com.renyigesai.bakeries.block.fermentation_box.FermentationBoxBlockEntity;
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
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

public class FermentationBoxMenu extends AbstractContainerMenu {
    private FermentationBoxBlockEntity blockEntity;
    private final Player player;
    public int x,y,z;
    private final IItemHandler playerInventory;

//    public FermentationBoxMenu(int id, Inventory inv,FriendlyByteBuf byteBuf) {
//        this(id, inv, byteBuf, new SimpleContainer(6),new SimpleContainerData(1));
//    }

    public FermentationBoxMenu(int windowId, Inventory playerInventory, BlockPos pos,FermentationBoxBlockEntity fermentationBoxBlockEntity) {
        super(BakeriesMenuType.FERMENTATION_BOX_MENU.get(), windowId);
        this.player = playerInventory.player;
        this.playerInventory = new InvWrapper(playerInventory);
        blockEntity = fermentationBoxBlockEntity;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        // 添加输入槽 (0-5)
        ItemStackHandler items = blockEntity.getItems();
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
            return new FermentationBoxMenu(windowId, playerInventory,pos,(FermentationBoxBlockEntity) blockEntity);
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
                if (!this.moveItemStackTo(stackInSlot, 6, 39, false)) { // 尝试移到玩家背包（36槽）
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
        blockEntity.getLevel().blockEvent(blockEntity.getBlockPos(),blockEntity.getBlockState().getBlock(),0,1);
    }

    public static class FermentationBoxSlot extends SlotItemHandler{

        public FermentationBoxSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public int getMaxStackSize(@NotNull ItemStack stack) {
            return 1;
        }
    }
}
