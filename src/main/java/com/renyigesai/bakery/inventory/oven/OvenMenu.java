package com.renyigesai.bakery.inventory.oven;

import com.renyigesai.bakery.block.oven.OvenBlockEntity;
import com.renyigesai.bakery.init.BakeryBlocks;
import com.renyigesai.bakery.init.BakeryMenuType;
import com.renyigesai.bakery.inventory.oven.slot.OvenSlot;
import com.renyigesai.bakery.recipe.OvenRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class OvenMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final Level level;
    public final Player entity;
    public int x, y, z;
    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private IItemHandler internal;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private BlockPos pos;
    private boolean bound = false;
    private BlockEntity boundBlockEntity = new OvenBlockEntity(pos, BakeryBlocks.OVEN.get().defaultBlockState());
    public OvenMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(BakeryMenuType.OVEN_MENU.get(), id);
        this.entity = inv.player;
        this.level = inv.player.level();
        this.internal = new ItemStackHandler(2);
        if (extraData != null) {
            pos = extraData.readBlockPos();
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
            access = ContainerLevelAccess.create(level, pos);
        }
        if (pos != null) {
            boundBlockEntity = this.level.getBlockEntity(pos);
            if (boundBlockEntity != null)
                boundBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                    this.internal = capability;
                    this.bound = true;
                });
        }

        this.customSlots.put(0, this.addSlot(new OvenSlot(internal, 0, 79, 17) {
            private final int slot = 0;
        }));
        this.customSlots.put(1, this.addSlot(new OvenSlot(internal, 1, 79, 62) {
            private final int slot = 1;

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public boolean mayPickup(Player playerIn) {
                return super.mayPickup(playerIn);
            }
        }));


        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 7 + sj * 18, 86 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, 7 + si * 18, 142));
    }

    @Override
    public Map<Integer, Slot> get() {
        return customSlots;
    }

    public boolean stillValid(Player pPlayer) {
        if (this.bound) {
            if (this.boundBlockEntity != null)
                return AbstractContainerMenu.stillValid(this.access, pPlayer, this.boundBlockEntity.getBlockState().getBlock());
        }
        return true;
    }
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex != 1 && pIndex != 0) {
                if (this.canSmelt(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= 3 && pIndex < 30) {
                    if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= 30 && pIndex < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }

        return itemstack;
    }
    protected boolean canSmelt(ItemStack pStack) {
        return this.level.getRecipeManager().getRecipeFor(OvenRecipe.Type.INSTANCE, new SimpleContainer(pStack), this.level).isPresent();
    }

    protected boolean isFuel(ItemStack pStack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(pStack, OvenRecipe.Type.INSTANCE) > 0;
    }
}
