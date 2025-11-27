package com.renyigesai.bakeries.common.blocks.bread_basket;

import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
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
import org.jetbrains.annotations.NotNull;

public class BreadBasketBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(5,ItemStack.EMPTY);
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState) {
        }

        @Override
        protected void onClose(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState) {
        }

        @Override
        protected void openerCountChanged(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, int pCount, int pOpenCount) {

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
        super(BakeriesBlocks.Entities.BREAD_BASKET_ENTITY.get(),pPos,pBlockState);
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> pItemStacks) {
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
    protected @NotNull Component getDefaultName() {
        return Component.translatable("block.bakeries.bread_basket");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new HopperMenu(pContainerId,pInventory,this);
    }

    @Override
    public int getContainerSize() {
        return 5;
    }

    @Override
    public void startOpen(@NotNull Player player){
        if (this.remove && !player.isSpectator()){
            this.openersCounter.incrementOpeners(player,this.getLevel(),this.getBlockPos(),this.getBlockState());
        }
    }

    @Override
    public void stopOpen(@NotNull Player player){
        if (this.remove && !player.isSpectator()){
            this.openersCounter.decrementOpeners(player,this.getLevel(),this.getBlockPos(),this.getBlockState());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag,this.items,registries);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(),ItemStack.EMPTY);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.loadAllItems(tag,this.items,registries);
        }
    }

}
