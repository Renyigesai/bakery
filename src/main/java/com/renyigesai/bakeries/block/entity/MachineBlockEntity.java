package com.renyigesai.bakeries.block.entity;

import com.renyigesai.bakeries.init.BakeriesBlockEntities;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.init.blocks.StateBlocks;
import com.renyigesai.bakeries.init.blocks.ToasterState;
import com.renyigesai.bakeries.menu.BlenderMenu;
import com.renyigesai.bakeries.menu.CupboardMenu;
import com.renyigesai.bakeries.menu.DoughCraftingTableMenu;
import com.renyigesai.bakeries.menu.FermentationBoxMenu;
import com.renyigesai.bakeries.menu.OvenMenu;
import com.renyigesai.bakeries.recipe.CoffeeRecipe;
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
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.MenuProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("unused")
public class MachineBlockEntity extends BlockEntity implements ImplementedInventory, MenuProvider {
    private static final int SIZE = 27;
    private static final int DEFAULT_MAX_PROGRESS = 100;
    private static final int OVEN_MAX_PROGRESS = 120;
    private static final int BLENDER_MAX_PROGRESS = 80;
    private static final int DOUGH_MAX_PROGRESS = 60;
    private static final int BREAD_KNIFE_MAX_PROGRESS = 40;
    private static final int FLOUR_SIEVE_MAX_PROGRESS = 50;
    private static final int DRINK_MAX_PROGRESS = 70;
    private static final int FERMENTATION_MAX_PROGRESS = 200;
    private final NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private int progress;
    private int maxProgress = DEFAULT_MAX_PROGRESS;
    private int ovenTemperature;
    private final int[] toasterCookingProgress = new int[2];
    private final int[] toasterCookingTime = new int[2];
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
    private final ContainerData fermentationMenuData = new ContainerData() {
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
        tag.putIntArray("ToasterProgress", toasterCookingProgress);
        tag.putIntArray("ToasterTime", toasterCookingTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        net.minecraft.world.ContainerHelper.loadAllItems(tag, items);
        progress = tag.getInt("Progress");
        maxProgress = tag.contains("MaxProgress") ? tag.getInt("MaxProgress") : DEFAULT_MAX_PROGRESS;
        ovenTemperature = tag.getInt("OvenTemperature");
        if (tag.contains("ToasterProgress")) {
            int[] values = tag.getIntArray("ToasterProgress");
            System.arraycopy(values, 0, toasterCookingProgress, 0, Math.min(values.length, toasterCookingProgress.length));
        }
        if (tag.contains("ToasterTime")) {
            int[] values = tag.getIntArray("ToasterTime");
            System.arraycopy(values, 0, toasterCookingTime, 0, Math.min(values.length, toasterCookingTime.length));
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (getBlockState().is(BakeriesBlocks.OVEN)) return Component.translatable("block.bakeries.oven");
        if (getBlockState().is(BakeriesBlocks.BLENDER)) return Component.translatable("container.bakeries.blender");
        if (getBlockState().is(BakeriesBlocks.FERMENTATION_BOX)) return Component.translatable("container.bakeries.fermentation_box");
        if (getBlockState().is(BakeriesBlocks.DOUGH_CRAFTING_TABLE)) return Component.translatable("block.bakeries.dough_crafting_table");
        if (getBlockState().is(BakeriesBlocks.CUPBOARD)) return Component.translatable("container.bakeries.cupboard");
        if (getBlockState().is(BakeriesBlocks.MIX_BLOCK)) return Component.translatable("container.bakeries.flour_sieve");
        if (getBlockState().is(BakeriesBlocks.DRINK_CUP)) return Component.translatable("container.bakeries.drink");
        return Component.translatable("container.bakeries.machine");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, net.minecraft.world.entity.player.Player player) {
        if (getBlockState().is(BakeriesBlocks.OVEN)) return new OvenMenu(syncId, playerInventory, this, ovenMenuData);
        if (getBlockState().is(BakeriesBlocks.BLENDER)) return new BlenderMenu(syncId, playerInventory, this, machineMenuData);
        if (getBlockState().is(BakeriesBlocks.FERMENTATION_BOX)) return new FermentationBoxMenu(syncId, playerInventory, this, fermentationMenuData);
        if (getBlockState().is(BakeriesBlocks.MENU)) return null;
        if (getBlockState().is(BakeriesBlocks.DRINK_CUP)) return null;
        if (getBlockState().is(BakeriesBlocks.DOUGH_CRAFTING_TABLE)) return new DoughCraftingTableMenu(syncId, playerInventory, this, machineMenuData);
        if (getBlockState().is(BakeriesBlocks.CUPBOARD)) return new CupboardMenu(syncId, playerInventory, this);
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
        } else if (state.is(BakeriesBlocks.TOASTER)) {
            machine.tickToaster();
        } else if (state.is(BakeriesBlocks.FERMENTATION_BOX)) {
            machine.tickFermentationBox();
        } else if (state.is(BakeriesBlocks.DOUGH_CRAFTING_TABLE)) {
            machine.resetProgressIfNeeded();
        } else if (state.is(BakeriesBlocks.MIX_BLOCK)) {
            machine.tickRecipe(BakeriesRecipeTypes.FLOUR_SIEVE, 1, FLOUR_SIEVE_MAX_PROGRESS);
        } else if (state.is(BakeriesBlocks.DRINK_CUP)) {
            machine.tickCoffeeRecipe();
        }
    }

    private void tickCoffeeRecipe() {
        if (level == null) {
            return;
        }
        maxProgress = DRINK_MAX_PROGRESS;
        CoffeeRecipe recipe = findCoffeeRecipe();
        if (recipe == null) {
            resetProgressIfNeeded();
            return;
        }
        ItemStack outputSlot = getItem(4);
        ItemStack crafted = recipe.getResultItem(level.registryAccess());
        if (!canOutput(outputSlot, crafted)) {
            resetProgressIfNeeded();
            return;
        }
        progress++;
        if (progress < maxProgress) {
            setChanged();
            return;
        }
        consumeCoffeeInputs(recipe);
        if (outputSlot.isEmpty()) {
            setItem(4, crafted.copy());
        } else {
            outputSlot.grow(crafted.getCount());
            setItem(4, outputSlot);
        }
        progress = 0;
        setChanged();
    }

    private CoffeeRecipe findCoffeeRecipe() {
        if (level == null) {
            return null;
        }
        SimpleContainer container = new SimpleContainer(4);
        for (int i = 0; i < 4; i++) {
            container.setItem(i, getItem(i).copy());
        }
        List<CoffeeRecipe> recipes = level.getRecipeManager().getAllRecipesFor(BakeriesRecipeTypes.COFFEE);
        for (CoffeeRecipe recipe : recipes) {
            if (recipe.matches(container, level)) {
                return recipe;
            }
        }
        return null;
    }

    private void consumeCoffeeInputs(CoffeeRecipe recipe) {
        List<Integer> usedSlots = new java.util.ArrayList<>();
        for (var ingredient : recipe.getIngredientsList()) {
            for (int i = 0; i < 4; i++) {
                if (usedSlots.contains(i)) {
                    continue;
                }
                ItemStack stack = getItem(i);
                if (!stack.isEmpty() && ingredient.test(stack)) {
                    stack.shrink(1);
                    usedSlots.add(i);
                    break;
                }
            }
        }
    }

    private void tickRecipe(RecipeType<SimpleMachineRecipe> recipeType, int outputSlot, int craftTime) {
        if (level == null) {
            return;
        }
        SimpleMachineRecipe matched = findRecipe(recipeType, 0, outputSlot);
        if (matched == null) {
            resetProgressIfNeeded();
            return;
        }
        maxProgress = matched.getCraftTime() > 0 ? matched.getCraftTime() : craftTime;
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
        Match matched = findRecipeAcrossInputs();
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

    private void tickFermentationBox() {
        if (level == null) {
            return;
        }
        if (maxProgress <= 0) {
            maxProgress = FERMENTATION_MAX_PROGRESS;
        }
        Match matched = findFermentationRecipeAcrossInputs();
        if (matched == null) {
            resetProgressIfNeeded();
            return;
        }
        progress++;
        if (progress < maxProgress) {
            setChanged();
            return;
        }
        craftReplaceInput(matched.inputSlot, matched.recipe);
        progress = 0;
        setChanged();
    }

    private Match findFermentationRecipeAcrossInputs() {
        for (int slot = 0; slot <= 5; slot++) {
            SimpleMachineRecipe recipe = findFermentationRecipe(slot);
            if (recipe != null) {
                return new Match(slot, recipe);
            }
        }
        return null;
    }

    private SimpleMachineRecipe findFermentationRecipe(int inputSlot) {
        if (level == null) {
            return null;
        }
        ItemStack input = getItem(inputSlot);
        if (input.isEmpty()) {
            return null;
        }
        List<SimpleMachineRecipe> recipes = level.getRecipeManager().getAllRecipesFor(BakeriesRecipeTypes.FERMENTATION_BOX);
        for (SimpleMachineRecipe recipe : recipes) {
            if (recipe.getIngredient().test(input)) {
                return recipe;
            }
        }
        return null;
    }

    private void craftReplaceInput(int inputSlot, SimpleMachineRecipe recipe) {
        if (level == null) {
            return;
        }
        ItemStack current = getItem(inputSlot);
        if (current.isEmpty()) {
            return;
        }
        ItemStack result = recipe.getResultItem(level.registryAccess()).copy();
        if (result.isEmpty()) {
            return;
        }
        result.setCount(Math.max(1, result.getCount()));
        setItem(inputSlot, result);
    }

    private void tickToaster() {
        if (level == null || !isToasterLit()) {
            return;
        }
        boolean hadWork = false;
        for (int slot = 0; slot < 2; slot++) {
            ItemStack stack = getItem(slot);
            if (stack.isEmpty()) {
                toasterCookingProgress[slot] = 0;
                toasterCookingTime[slot] = 0;
                continue;
            }
            hadWork = true;
            if (toasterCookingTime[slot] <= 0) {
                CampfireCookingRecipe holder = findCampfireRecipe(stack);
                if (holder == null) {
                    continue;
                }
                toasterCookingTime[slot] = Math.max(1, holder.getCookingTime() / 3);
                toasterCookingProgress[slot] = 0;
            }
            toasterCookingProgress[slot]++;
            if (toasterCookingProgress[slot] < toasterCookingTime[slot]) {
                continue;
            }
            CampfireCookingRecipe holder = findCampfireRecipe(stack);
            if (holder != null) {
                ItemStack output = holder.assemble(new SimpleContainer(stack), level.registryAccess());
                if (!output.isEmpty()) {
                    setItem(slot, output.copy());
                }
            }
            toasterCookingProgress[slot] = 0;
            toasterCookingTime[slot] = 0;
        }
        if (!hadWork || (toasterCookingProgress[0] == 0 && toasterCookingProgress[1] == 0)) {
            changeToasterState(ToasterState.FINISH);
        }
        setChanged();
    }

    private CampfireCookingRecipe findCampfireRecipe(ItemStack stack) {
        if (level == null || stack.isEmpty()) {
            return null;
        }
        return level.getRecipeManager()
                .getRecipeFor(RecipeType.CAMPFIRE_COOKING, new SimpleContainer(stack), level)
                .orElse(null);
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

    private Match findRecipeAcrossInputs() {
        for (int slot = 0; slot <= 8; slot++) {
            SimpleMachineRecipe recipe = findRecipe(BakeriesRecipeTypes.BLENDER, slot, 10);
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

    public void setFermentationTime(int value) {
        maxProgress = Math.max(20, Math.min(1200, value));
        setChanged();
    }

    public boolean canAcceptToasterInput(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (findCampfireRecipe(stack) == null) {
            return false;
        }
        return getItem(0).isEmpty() || getItem(1).isEmpty();
    }

    public boolean addToasterItem(ItemStack stack, int cookingTime) {
        for (int i = 0; i < 2; i++) {
            if (getItem(i).isEmpty()) {
                ItemStack copy = stack.copy();
                copy.setCount(1);
                setItem(i, copy);
                toasterCookingTime[i] = Math.max(1, cookingTime / 3);
                toasterCookingProgress[i] = 0;
                setChanged();
                return true;
            }
        }
        return false;
    }

    public void popToasterItems(net.minecraft.world.entity.player.Player player) {
        for (int i = 0; i < 2; i++) {
            ItemStack stack = getItem(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (!player.getInventory().add(stack.copy())) {
                player.drop(stack.copy(), false);
            }
            setItem(i, ItemStack.EMPTY);
            toasterCookingProgress[i] = 0;
            toasterCookingTime[i] = 0;
        }
        setChanged();
    }

    public boolean isToasterIdle() {
        return getItem(0).isEmpty() && getItem(1).isEmpty();
    }

    public boolean isToasterLit() {
        return getBlockState().hasProperty(StateBlocks.ToasterBlock.STATE)
                && getBlockState().getValue(StateBlocks.ToasterBlock.STATE) == ToasterState.LIT;
    }

    public void changeToasterState(ToasterState state) {
        if (level == null) {
            return;
        }
        if (getBlockState().hasProperty(StateBlocks.ToasterBlock.STATE)) {
            level.setBlock(worldPosition, getBlockState().setValue(StateBlocks.ToasterBlock.STATE, state), 3);
        }
    }

    private record Match(int inputSlot, SimpleMachineRecipe recipe) {
    }
}
