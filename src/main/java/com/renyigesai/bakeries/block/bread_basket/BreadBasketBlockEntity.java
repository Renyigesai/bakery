package com.renyigesai.bakeries.block.bread_basket;

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
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class BreadBasketBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(5,ItemStack.EMPTY);
//    private ItemStackHandler items = new ItemStackHandler(1){
//        @Override
//        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
//            return 1;
//        }
//    };
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
                return container == BreadBasketBlockEntity.this;
            }else {
                return false;
            }
        }
    };

    public BreadBasketBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.BREAD_BASKET_BLOCK_ENTITY.get(),pPos,pBlockState);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {
            this.items = pItemStacks;
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, BreadBasketBlockEntity breadBasketBlockEntity){
        setChanged(world, pos, state);
        boolean temp = breadBasketBlockEntity.getItem(0) != ItemStack.EMPTY ||
                       breadBasketBlockEntity.getItem(1) != ItemStack.EMPTY ||
                       breadBasketBlockEntity.getItem(2) != ItemStack.EMPTY ||
                       breadBasketBlockEntity.getItem(3) != ItemStack.EMPTY;
        world.setBlock(pos,breadBasketBlockEntity.getBlockState().setValue(BreadBasketBlock.FILL,temp),3);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.bakeries.bread_basket");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new HopperMenu(pContainerId,pInventory,this);
    }

    @Override
    public int getContainerSize() {
        return 5;
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
