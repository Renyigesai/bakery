package com.renyigesai.bakery.inventory.dough_crafting_table;

import com.renyigesai.bakery.block.oven.OvenBlockEntity;
import com.renyigesai.bakery.init.BakeryMenuType;
import com.renyigesai.bakery.inventory.oven.slot.OvenSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DoughCraftingTableMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {

public final static HashMap<String, Object> guistate = new HashMap<>();
    public final Level world;
    public final Player entity;
    public int x, y, z;
    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private IItemHandler internal;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound = false;
    private Supplier<Boolean> boundItemMatcher = null;
    private Entity boundEntity = null;
    public BlockEntity boundBlockEntity = null;
    private final Container container;
    public final ContainerData data;

    public DoughCraftingTableMenu(int id, Inventory inv, FriendlyByteBuf byteBuf) {
        this(id, inv, byteBuf, new SimpleContainer(4), new SimpleContainerData(1));

    }
    public DoughCraftingTableMenu(int id, Inventory inv, FriendlyByteBuf byteBuf, Container pContainer, ContainerData pData) {
        super(BakeryMenuType.DOUGH_CRAFTING_TABLE_MENU.get(), id);
        checkContainerSize(pContainer, 4);
        checkContainerDataCount(pData, 1);
        this.entity = inv.player;
        this.world = inv.player.level();
        this.container = pContainer;
        this.data = pData;
        this.internal = new ItemStackHandler(4);
        this.addDataSlots(pData);

        BlockPos pos = null;
        if (byteBuf != null) {
            pos = byteBuf.readBlockPos();
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
            access = ContainerLevelAccess.create(world, pos);
        }
        if(pos!=null){
            boundBlockEntity = this.world.getBlockEntity(pos);
            if (boundBlockEntity != null)
                boundBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                    this.internal = capability;
                    this.bound = true;
                });
        }

        if (boundBlockEntity instanceof OvenBlockEntity oven) {
            this.customSlots.put(0, this.addSlot(new OvenSlot(internal, 0, 62, 17) {

            }));
            this.customSlots.put(1, this.addSlot(new OvenSlot(internal, 1, 80, 17) {
            }));
            this.customSlots.put(2, this.addSlot(new OvenSlot(internal, 2, 62, 53) {
            }));
            this.customSlots.put(3, this.addSlot(new OvenSlot(internal, 3, 80, 53) {
            }));
        }


        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, 8 + si * 18, 142));
    }


    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.bound) {
            if (this.boundItemMatcher != null)
                return this.boundItemMatcher.get();
            else if (this.boundBlockEntity != null)
                return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
            else if (this.boundEntity != null)
                return this.boundEntity.isAlive();
        }
        return true;
    }




    public Map<Integer, Slot> get() {
        return customSlots;
    }

}
