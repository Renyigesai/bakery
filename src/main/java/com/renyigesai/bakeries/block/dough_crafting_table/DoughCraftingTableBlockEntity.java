package com.renyigesai.bakeries.block.dough_crafting_table;

import com.renyigesai.bakeries.block.cupboard.CupboardBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DoughCraftingTableBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(27,ItemStack.EMPTY);
    private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level pLevel, BlockPos pPos, BlockState pState) {
        }

        @Override
        protected void onClose(Level pLevel, BlockPos pPos, BlockState pState) {
        }

        @Override
        protected void openerCountChanged(Level pLevel, BlockPos pPos, BlockState pState, int pCount, int pOpenCount) {

        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof ChestMenu){
                Container container = ((ChestMenu) player.containerMenu).getContainer();
                return container == DoughCraftingTableBlockEntity.this;
            }else {
                return false;
            }
        }
    };

    public DoughCraftingTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.DOUGH_CRAFTING_TABLE_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {return this.items;}

    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {this.items = pItemStacks;}

    @Override
    protected Component getDefaultName() {return Component.translatable("block.bakeries.dough_crafting_table");}

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return ChestMenu.threeRows(pContainerId, pInventory,this);
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    public void startOpen(Player player){
        if (this.remove && !player.isSpectator()){
            this.openersCounter.incrementOpeners(player,this.getLevel(),this.getBlockPos(),this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player){
        if (this.remove && !player.isSpectator()){
            this.openersCounter.decrementOpeners(player,this.getLevel(),this.getBlockPos(),this.getBlockState());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (!this.trySaveLootTable(pTag)) {
            ContainerHelper.saveAllItems(pTag,this.items);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(),ItemStack.EMPTY);
        if (!this.trySaveLootTable(pTag)) {
            ContainerHelper.loadAllItems(pTag,this.items);
        }
    }

}
