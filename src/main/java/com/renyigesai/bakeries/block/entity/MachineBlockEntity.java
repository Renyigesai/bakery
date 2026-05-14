package com.renyigesai.bakeries.block.entity;

import com.renyigesai.bakeries.init.BakeriesBlockEntities;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.menu.BlenderMenu;
import com.renyigesai.bakeries.menu.DoughCraftingTableMenu;
import com.renyigesai.bakeries.menu.FermentationBoxMenu;
import com.renyigesai.bakeries.menu.OvenMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.MenuProvider;
import org.jetbrains.annotations.Nullable;

public class MachineBlockEntity extends BlockEntity implements ImplementedInventory, MenuProvider {
    private static final int SIZE = 27;
    private final NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);

    public MachineBlockEntity(BlockPos pos, BlockState blockState) {
        super(BakeriesBlockEntities.MACHINE, pos, blockState);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        net.minecraft.world.ContainerHelper.saveAllItems(tag, items);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        net.minecraft.world.ContainerHelper.loadAllItems(tag, items);
    }

    @Override
    public Component getDisplayName() {
        if (getBlockState().is(BakeriesBlocks.OVEN)) return Component.translatable("container.bakeries.oven");
        if (getBlockState().is(BakeriesBlocks.BLENDER)) return Component.translatable("container.bakeries.blender");
        if (getBlockState().is(BakeriesBlocks.FERMENTATION_BOX)) return Component.translatable("container.bakeries.fermentation_box");
        if (getBlockState().is(BakeriesBlocks.DOUGH_CRAFTING_TABLE)) return Component.translatable("container.bakeries.dough_crafting_table");
        return Component.translatable("container.bakeries.machine");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, net.minecraft.world.entity.player.Player player) {
        if (getBlockState().is(BakeriesBlocks.OVEN)) return new OvenMenu(syncId, playerInventory, this);
        if (getBlockState().is(BakeriesBlocks.BLENDER)) return new BlenderMenu(syncId, playerInventory, this);
        if (getBlockState().is(BakeriesBlocks.FERMENTATION_BOX)) return new FermentationBoxMenu(syncId, playerInventory, this);
        if (getBlockState().is(BakeriesBlocks.DOUGH_CRAFTING_TABLE)) return new DoughCraftingTableMenu(syncId, playerInventory, this);
        return new OvenMenu(syncId, playerInventory, this);
    }
}
