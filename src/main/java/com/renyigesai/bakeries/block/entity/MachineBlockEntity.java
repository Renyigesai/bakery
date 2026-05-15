package com.renyigesai.bakeries.block.entity;

import com.renyigesai.bakeries.init.BakeriesBlockEntities;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.menu.BlenderMenu;
import com.renyigesai.bakeries.menu.DoughCraftingTableMenu;
import com.renyigesai.bakeries.menu.FermentationBoxMenu;
import com.renyigesai.bakeries.menu.OvenMenu;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.MenuProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MachineBlockEntity extends BlockEntity implements ImplementedInventory, MenuProvider {
    private static final int SIZE = 27;
    private static final int DEFAULT_MAX_PROGRESS = 100;
    private static final int OVEN_MAX_PROGRESS = 120;
    private static final int BLENDER_MAX_PROGRESS = 80;
    private static final int DOUGH_MAX_PROGRESS = 60;
    private static final int BREAD_KNIFE_MAX_PROGRESS = 40;
    private static final int FLOUR_SIEVE_MAX_PROGRESS = 50;
    private static final int DRINK_MAX_PROGRESS = 70;
    private final NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private int progress;
    private int maxProgress = DEFAULT_MAX_PROGRESS;
    private int ovenTemperature;
    private final ContainerData machineMenuData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> MachineBlockEntity.this.progress;
                case 1 -> MachineBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> MachineBlockEntity.this.progress = value;
                case 1 -> MachineBlockEntity.this.maxProgress = value;
                default -> {
                }
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
    private final ContainerData ovenMenuData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> MachineBlockEntity.this.progress;
                case 1 -> MachineBlockEntity.this.maxProgress;
                case 2 -> MachineBlockEntity.this.ovenTemperature;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> MachineBlockEntity.this.progress = value;
                case 1 -> MachineBlockEntity.this.maxProgress = value;
                case 2 -> MachineBlockEntity.this.ovenTemperature = value;
                default -> {
                }
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

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
        tag.putInt("Progress", progress);
        tag.putInt("MaxProgress", maxProgress);
        tag.putInt("OvenTemperature", ovenTemperature);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        net.minecraft.world.ContainerHelper.loadAllItems(tag, items);
        progress = tag.getInt("Progress");
        maxProgress = tag.contains("MaxProgress") ? tag.getInt("MaxProgress") : DEFAULT_MAX_PROGRESS;
        ovenTemperature = tag.getInt("OvenTemperature");
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (getBlockState().is(BakeriesBlocks.OVEN)) return Component.translatable("block.bakeries.oven");
        if (getBlockState().is(BakeriesBlocks.BLENDER)) return Component.translatable("container.bakeries.blender");
        if (getBlockState().is(BakeriesBlocks.FERMENTATION_BOX)) return Component.translatable("container.bakeries.fermentation_box");
        if (getBlockState().is(BakeriesBlocks.DOUGH_CRAFTING_TABLE)) return Component.translatable("block.bakeries.dough_crafting_table");
        if (getBlockState().is(BakeriesBlocks.CUPBOARD)) return Component.translatable("container.bakeries.bread_knife");
        if (getBlockState().is(BakeriesBlocks.MIX_BLOCK)) return Component.translatable("container.bakeries.flour_sieve");
        if (getBlockState().is(BakeriesBlocks.MOKA_POT)) return Component.translatable("container.bakeries.drink");
        return Component.translatable("container.bakeries.machine");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, net.minecraft.world.entity.player.Player player) {
        if (getBlockState().is(BakeriesBlocks.OVEN)) return new OvenMenu(syncId, playerInventory, this, ovenMenuData);
        if (getBlockState().is(BakeriesBlocks.BLENDER)) return new BlenderMenu(syncId, playerInventory, this, machineMenuData);
        if (getBlockState().is(BakeriesBlocks.FERMENTATION_BOX)) return new FermentationBoxMenu(syncId, playerInventory, this);
        if (getBlockState().is(BakeriesBlocks.MENU)) return null;
        if (getBlockState().is(BakeriesBlocks.MOKA_POT)) return null;
        if (getBlockState().is(BakeriesBlocks.DOUGH_CRAFTING_TABLE)) return new DoughCraftingTableMenu(syncId, playerInventory, this, machineMenuData);
        if (getBlockState().is(BakeriesBlocks.CUPBOARD)) return new DoughCraftingTableMenu(syncId, playerInventory, this, machineMenuData);
        if (getBlockState().is(BakeriesBlocks.MIX_BLOCK)) return new DoughCraftingTableMenu(syncId, playerInventory, this, machineMenuData);
        return new OvenMenu(syncId, playerInventory, this, ovenMenuData);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MachineBlockEntity machine) {
        if (state.is(BakeriesBlocks.OVEN)) {
            machine.tickRecipe(BakeriesRecipeTypes.OVEN, 5, OVEN_MAX_PROGRESS);
        } else if (state.is(BakeriesBlocks.BLENDER)) {
            machine.tickRecipeAcrossInputs();
        } else if (state.is(BakeriesBlocks.DOUGH_CRAFTING_TABLE)) {
            machine.resetProgressIfNeeded();
        } else if (state.is(BakeriesBlocks.CUPBOARD)) {
            machine.tickRecipe(BakeriesRecipeTypes.BREAD_KNIFE, 1, BREAD_KNIFE_MAX_PROGRESS);
        } else if (state.is(BakeriesBlocks.MIX_BLOCK)) {
            machine.tickRecipe(BakeriesRecipeTypes.FLOUR_SIEVE, 1, FLOUR_SIEVE_MAX_PROGRESS);
        } else if (state.is(BakeriesBlocks.MOKA_POT)) {
            machine.tickRecipe(BakeriesRecipeTypes.DRINK, 1, DRINK_MAX_PROGRESS);
        }
    }

    private void tickRecipe(RecipeType<SimpleMachineRecipe> recipeType, int outputSlot, int craftTime) {
        if (level == null) {
            return;
        }
        maxProgress = craftTime;
        SimpleMachineRecipe matched = findRecipe(recipeType, 0, outputSlot);
        if (matched == null) {
            resetProgressIfNeeded();
            return;
        }
        progress++;
        if (progress < maxProgress) {
            setChanged();
            return;
        }
        craftOnce(0, outputSlot, matched);
        progress = 0;
        setChanged();
    }

    private void tickRecipeAcrossInputs() {
        if (level == null) {
            return;
        }
        maxProgress = MachineBlockEntity.BLENDER_MAX_PROGRESS;
        Match matched = findRecipeAcrossInputs(BakeriesRecipeTypes.BLENDER, 0, 8, 10);
        if (matched == null) {
            resetProgressIfNeeded();
            return;
        }
        progress++;
        if (progress < maxProgress) {
            setChanged();
            return;
        }
        craftOnce(matched.inputSlot, 10, matched.recipe);
        progress = 0;
        setChanged();
    }

    private SimpleMachineRecipe findRecipe(RecipeType<SimpleMachineRecipe> recipeType, int inputSlot, int outputSlot) {
        Level currentLevel = level;
        if (currentLevel == null) {
            return null;
        }
        ItemStack input = this.getItem(inputSlot);
        if (input.isEmpty()) {
            return null;
        }
        List<SimpleMachineRecipe> recipes = currentLevel.getRecipeManager().getAllRecipesFor(recipeType);
        for (SimpleMachineRecipe recipe : recipes) {
            Ingredient ingredient = recipe.getIngredient();
            if (!ingredient.test(input)) {
                continue;
            }
            ItemStack output = this.getItem(outputSlot);
            ItemStack crafted = recipe.getResultItem(currentLevel.registryAccess());
            if (canOutput(output, crafted)) {
                return recipe;
            }
        }
        return null;
    }

    private void craftOnce(int inputSlot, int outputSlot, SimpleMachineRecipe recipe) {
        Level currentLevel = level;
        if (currentLevel == null) {
            return;
        }
        ItemStack input = this.getItem(inputSlot);
        ItemStack output = this.getItem(outputSlot);
        ItemStack crafted = recipe.getResultItem(currentLevel.registryAccess());
        if (!canOutput(output, crafted)) {
            return;
        }
        input.shrink(1);
        if (output.isEmpty()) {
            this.setItem(outputSlot, crafted.copy());
        } else {
            output.grow(crafted.getCount());
            this.setItem(outputSlot, output);
        }
    }

    private void resetProgressIfNeeded() {
        if (progress != 0) {
            progress = 0;
            setChanged();
        }
    }

    private static boolean canOutput(ItemStack existing, ItemStack crafted) {
        if (existing.isEmpty()) {
            return true;
        }
        if (!ItemStack.isSameItemSameTags(existing, crafted)) {
            return false;
        }
        return existing.getCount() + crafted.getCount() <= existing.getMaxStackSize();
    }

    private Match findRecipeAcrossInputs(RecipeType<SimpleMachineRecipe> recipeType, int inputStart, int inputEnd, int outputSlot) {
        for (int slot = inputStart; slot <= inputEnd; slot++) {
            SimpleMachineRecipe recipe = findRecipe(recipeType, slot, outputSlot);
            if (recipe != null) {
                return new Match(slot, recipe);
            }
        }
        return null;
    }

    public void resetMachineProgress() {
        if (progress != 0) {
            progress = 0;
            setChanged();
        }
    }

    public void clearOutputSlot(int outputSlot) {
        if (outputSlot < 0 || outputSlot >= items.size()) {
            return;
        }
        if (!items.get(outputSlot).isEmpty()) {
            items.set(outputSlot, ItemStack.EMPTY);
            setChanged();
        }
    }

    public int getOverlayProgress() {
        return progress;
    }

    public int getOverlayMaxProgress() {
        return maxProgress;
    }

    public int getOvenTemperature() {
        return ovenTemperature;
    }

    public void addOvenTemperature(int value) {
        ovenTemperature = Math.max(0, Math.min(500, ovenTemperature + value));
        setChanged();
    }

    private record Match(int inputSlot, SimpleMachineRecipe recipe) {
    }
}
