package net.weibai.bakeries.common.inventory.oven;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.weibai.bakeries.common.blocks.oven.OvenBlockEntity;
import net.weibai.bakeries.common.init.BakeriesMenuType;
import net.weibai.bakeries.common.inventory.oven.slot.OvenSlot;
import org.jetbrains.annotations.NotNull;

public class OvenMenu extends AbstractContainerMenu{
    public final Level world;
    public final Player entity;
    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    public OvenBlockEntity ovenBlockEntity = null;
    public final ContainerData data;
    private final Container container;
    public OvenMenu(int id, Inventory inv, FriendlyByteBuf byteBuf) {
        this(id, inv, byteBuf, new SimpleContainer(6), new SimpleContainerData(7));
    }
    public OvenMenu(int id, Inventory inv, FriendlyByteBuf byteBuf, Container pContainer, ContainerData pData) {
        super(BakeriesMenuType.OVEN_MENU.get(), id);
        checkContainerSize(pContainer, 6);
        checkContainerDataCount(pData, 7);
        this.entity = inv.player;
        this.world = inv.player.level();
        this.data = pData;

        BlockPos pos = null;
        if (byteBuf != null) {
            pos = byteBuf.readBlockPos();
            access = ContainerLevelAccess.create(world, pos);
        }
        if(pos!=null){
            ovenBlockEntity = (OvenBlockEntity) this.world.getBlockEntity(pos);
        }

        this.container = pContainer;
        this.addDataSlots(pData);

        addSlot(new OvenSlot(pContainer,0,52, 16));
        addSlot(new OvenSlot(pContainer,1, 70, 16));
        addSlot(new OvenSlot(pContainer,2, 88, 16));
        addSlot(new OvenSlot(pContainer,3, 52, 46));
        addSlot(new OvenSlot(pContainer,4, 70, 46));
        addSlot(new OvenSlot(pContainer,5, 88, 46));

        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, 8 + si * 18, 142));
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 6) {
                if (!this.moveItemStackTo(itemstack1, 6, this.slots.size(), true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (!this.moveItemStackTo(itemstack1, 0, 6, false)) {
                if (index < 6 + 27) {
                    if (!this.moveItemStackTo(itemstack1, 6 + 27, this.slots.size(), true))
                        return ItemStack.EMPTY;
                } else {
                    if (!this.moveItemStackTo(itemstack1, 6, 6 + 27, false))
                        return ItemStack.EMPTY;
                }
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
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        if(ovenBlockEntity != null){
            return AbstractContainerMenu.stillValid(this.access, player, this.ovenBlockEntity.getBlockState().getBlock());
        }else return false;
    }

    public OvenBlockEntity getBlockEntity() {
        return this.ovenBlockEntity;
    }
}
