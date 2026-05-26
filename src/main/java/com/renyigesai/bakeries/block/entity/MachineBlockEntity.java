package com.renyigesai.bakeries.block.entity;

import com.renyigesai.bakeries.init.BakeriesBlockEntities;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.init.blocks.StateBlocks;
import com.renyigesai.bakeries.init.blocks.ToasterState;
import com.renyigesai.bakeries.menu.BlenderMenu;
import com.renyigesai.bakeries.menu.CupboardMenu;
import com.renyigesai.bakeries.menu.DoughCraftingTableMenu;
import com.renyigesai.bakeries.menu.FermentationBoxMenu;
import com.renyigesai.bakeries.menu.OvenMenu;
import com.renyigesai.bakeries.recipe.BlenderRecipe;
import com.renyigesai.bakeries.recipe.CoffeeRecipe;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
    private static final int MOKA_POT_MAX_PROGRESS = 200;
    private final NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private int progress;
    private int maxProgress = DEFAULT_MAX_PROGRESS;
    private int ovenTemperature;
    private int fermentationTemperature = 23;
    private int fermentationPerfectTime = 600;
    private long fermentationTemperatureDay = Long.MIN_VALUE;
    private int fermentationTemperatureRevision = -1;
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
                case 2 -> MachineBlockEntity.this.fermentationTemperature;
                case 3 -> MachineBlockEntity.this.fermentationPerfectTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> MachineBlockEntity.this.progress = value;
                case 1 -> MachineBlockEntity.this.maxProgress = value;
                case 2 -> MachineBlockEntity.this.fermentationTemperature = value;
                case 3 -> MachineBlockEntity.this.fermentationPerfectTime = value;
                default -> {
                }
            }
        }

        @Override
        public int getCount() {
            return 4;
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
        tag.putInt("FermentationTemperature", fermentationTemperature);
        tag.putInt("FermentationPerfectTime", fermentationPerfectTime);
        tag.putLong("FermentationTemperatureDay", fermentationTemperatureDay);
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
        fermentationTemperature = tag.contains("FermentationTemperature") ? tag.getInt("FermentationTemperature") : 23;
        fermentationPerfectTime = tag.contains("FermentationPerfectTime") ? tag.getInt("FermentationPerfectTime") : getNowPerfectTime(fermentationTemperature);
        fermentationTemperatureDay = Long.MIN_VALUE;
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
        if (getBlockState().is(BakeriesBlocks.FERMENTATION_BOX)) {
            forceRefreshFermentationTemperature();
            return new FermentationBoxMenu(syncId, playerInventory, this, fermentationMenuData);
        }
        if (getBlockState().is(BakeriesBlocks.MENU)) return null;
        if (getBlockState().is(BakeriesBlocks.DRINK_CUP)) return null;
        if (getBlockState().is(BakeriesBlocks.DOUGH_CRAFTING_TABLE)) return ChestMenu.threeRows(syncId, playerInventory, this);
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
            machine.tickOvenRecipe();
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
        } else if (state.is(BakeriesBlocks.MOKA_POT)) {
            machine.tickMokaPot();
        }
    }

    private void tickMokaPot() {
        if (level == null) {
            return;
        }
        maxProgress = MOKA_POT_MAX_PROGRESS;
        if (!getItem(0).is(BakeriesItems.GROUND_COFFEE) || !hasMokaPotHeatSource()) {
            resetProgressIfNeeded();
            return;
        }
        progress++;
        if (progress < maxProgress) {
            setChanged();
            return;
        }
        setItem(0, ItemStack.EMPTY);
        progress = 0;
        BlockState filledState = BakeriesBlocks.MOKA_POT_FILL.defaultBlockState();
        if (filledState.hasProperty(BlockStateProperties.HORIZONTAL_FACING) && getBlockState().hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            filledState = filledState.setValue(BlockStateProperties.HORIZONTAL_FACING, getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING));
        }
        level.setBlock(worldPosition, filledState, 3);
    }

    private boolean hasMokaPotHeatSource() {
        if (level == null) {
            return false;
        }
        BlockState below = level.getBlockState(worldPosition.below());
        if (below.getBlock() instanceof CampfireBlock) {
            return below.hasProperty(BlockStateProperties.LIT) && below.getValue(BlockStateProperties.LIT);
        }
        if (below.getBlock() instanceof AbstractFurnaceBlock) {
            return below.hasProperty(BlockStateProperties.LIT) && below.getValue(BlockStateProperties.LIT);
        }
        return false;
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

    private void tickOvenRecipe() {
        if (level == null) {
            return;
        }
        OvenMatch matched = findOvenRecipe();
        if (matched == null) {
            resetProgressIfNeeded();
            return;
        }
        maxProgress = matched.recipe.getCraftTime() > 0 ? matched.recipe.getCraftTime() : OVEN_MAX_PROGRESS;
        progress++;
        if (progress < maxProgress) {
            setChanged();
            return;
        }
        if (matched.burnt) {
            craftOvenCharcoal(matched.recipe);
        } else {
            craftOnce(0, 5, matched.recipe);
        }
        progress = 0;
        setChanged();
    }

    private void tickRecipeAcrossInputs() {
        if (level == null) {
            return;
        }
        maxProgress = MachineBlockEntity.BLENDER_MAX_PROGRESS;
        BlenderMatch matched = findBlenderRecipe();
        if (matched == null) {
            resetProgressIfNeeded();
            return;
        }
        setBlenderPowered(true);
        progress++;
        if (progress < maxProgress) {
            setChanged();
            return;
        }
        craftBlenderOnce(matched);
        progress = 0;
        if (findBlenderRecipe() == null) {
            setBlenderPowered(false);
        }
        setChanged();
    }

    private void tickFermentationBox() {
        if (level == null) {
            return;
        }
        refreshFermentationTemperature();
        if (maxProgress < 430) {
            resetProgressIfNeeded();
            return;
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
        craftReplaceInput(matched.inputSlot, matched.recipe, isPerfectFermentationTime());
        progress = 0;
        setChanged();
    }

    private void refreshFermentationTemperature() {
        if (level == null) {
            return;
        }
        long currentDay = level.getDayTime() / 24000L;
        BakeriesMod.refreshFloatingTemperature(currentDay);
        if (currentDay == fermentationTemperatureDay && fermentationTemperatureRevision == BakeriesMod.floatingTemperatureRevision) {
            return;
        }
        fermentationTemperatureDay = currentDay;
        fermentationTemperatureRevision = BakeriesMod.floatingTemperatureRevision;
        fermentationTemperature = calculateFermentationTemperature(level, worldPosition);
        fermentationPerfectTime = getNowPerfectTime(fermentationTemperature);
        setChanged();
    }

    public void forceRefreshFermentationTemperature() {
        fermentationTemperatureDay = Long.MIN_VALUE;
        fermentationTemperatureRevision = -1;
        refreshFermentationTemperature();
    }

    public static int calculateFermentationTemperature(Level level, BlockPos pos) {
        int floatingTemperature = BakeriesMod.floatingTemperature;
        float biomeTemperature = level.getBiome(pos).value().getBaseTemperature();
        if (biomeTemperature <= 0.2F) {
            return floatingTemperature;
        }
        if (biomeTemperature >= 2.0F) {
            return 30 + floatingTemperature;
        }
        return 23 + floatingTemperature;
    }

    public static int getNowPerfectTime(double currentTemperature) {
        double temperature = Math.max(-5.0D, Math.min(40.0D, currentTemperature));
        int ticks;
        if (temperature > 23.0D) {
            ticks = (int) (600 - (temperature - 23.0D) * 10);
        } else {
            ticks = (int) (600 + (23.0D - temperature) * 20);
        }
        return Math.min(1200, Math.max(100, ticks));
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

    private boolean isPerfectFermentationTime() {
        return Math.abs(maxProgress - fermentationPerfectTime) <= 100;
    }

    private void craftReplaceInput(int inputSlot, SimpleMachineRecipe recipe, boolean perfectFermentation) {
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
        if (perfectFermentation) {
            addPerfectFermentationLore(result);
        }
        setItem(inputSlot, result);
    }

    private static void addPerfectFermentationLore(ItemStack stack) {
        CompoundTag display = stack.getOrCreateTagElement("display");
        ListTag lore = display.getList("Lore", net.minecraft.nbt.Tag.TAG_STRING);
        String tooltip = Component.Serializer.toJson(
                Component.translatable("tooltips.bakeries.perfect_fermentation").withStyle(ChatFormatting.LIGHT_PURPLE)
        );
        StringTag tooltipTag = StringTag.valueOf(tooltip);
        if (!lore.contains(tooltipTag)) {
            lore.add(tooltipTag);
        }
        display.put("Lore", lore);
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

    private OvenMatch findOvenRecipe() {
        Level currentLevel = level;
        if (currentLevel == null) {
            return null;
        }
        ItemStack input = getItem(0);
        if (input.isEmpty()) {
            return null;
        }
        for (SimpleMachineRecipe recipe : currentLevel.getRecipeManager().getAllRecipesFor(BakeriesRecipeTypes.OVEN)) {
            if (!recipe.getIngredient().test(input)) {
                continue;
            }
            int min = recipe.getMinTemperature();
            int max = recipe.getMaxTemperature();
            if (min >= 0 && ovenTemperature < min) {
                continue;
            }
            boolean burnt = max >= 0 && ovenTemperature > max;
            ItemStack crafted = burnt ? new ItemStack(Items.CHARCOAL) : recipe.getResultItem(currentLevel.registryAccess());
            if (canOutput(getItem(5), crafted)) {
                return new OvenMatch(recipe, burnt);
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

    private void craftOvenCharcoal(SimpleMachineRecipe recipe) {
        ItemStack input = getItem(0);
        ItemStack output = getItem(5);
        ItemStack crafted = new ItemStack(Items.CHARCOAL);
        if (input.isEmpty() || !recipe.getIngredient().test(input) || !canOutput(output, crafted)) {
            return;
        }
        input.shrink(1);
        if (output.isEmpty()) {
            setItem(5, crafted);
        } else {
            output.grow(crafted.getCount());
            setItem(5, output);
        }
    }

    private void resetProgressIfNeeded() {
        setBlenderPowered(false);
        if (progress != 0) {
            progress = 0;
            setChanged();
        }
    }

    private void setBlenderPowered(boolean powered) {
        if (level == null || !getBlockState().is(BakeriesBlocks.BLENDER) || !getBlockState().hasProperty(BlockStateProperties.POWERED)) {
            return;
        }
        BlockState state = getBlockState();
        if (state.getValue(BlockStateProperties.POWERED) != powered) {
            level.setBlock(worldPosition, state.setValue(BlockStateProperties.POWERED, powered), 3);
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

    private BlenderMatch findBlenderRecipe() {
        Level currentLevel = level;
        if (currentLevel == null) {
            return null;
        }
        List<SimpleMachineRecipe> recipes = currentLevel.getRecipeManager().getAllRecipesFor(BakeriesRecipeTypes.BLENDER);
        for (SimpleMachineRecipe recipe : recipes) {
            ItemStack crafted = recipe.getResultItem(currentLevel.registryAccess());
            if (!canOutput(getItem(10), crafted)) {
                continue;
            }
            if (recipe instanceof BlenderRecipe blenderRecipe) {
                int[] plannedUse = new int[9];
                List<Integer> inputSlots = new java.util.ArrayList<>();
                boolean matched = true;
                for (Ingredient ingredient : blenderRecipe.getInputIngredients()) {
                    int slot = findMatchingBlenderInputSlot(ingredient, plannedUse);
                    if (slot < 0) {
                        matched = false;
                        break;
                    }
                    plannedUse[slot]++;
                    inputSlots.add(slot);
                }
                if (!matched) {
                    continue;
                }
                if (blenderRecipe.hasContainer() && !blenderRecipe.getContainerIngredient().test(getItem(9))) {
                    continue;
                }
                return new BlenderMatch(inputSlots, blenderRecipe);
            }
            Match single = findRecipeAcrossInputs();
            if (single != null) {
                return new BlenderMatch(List.of(single.inputSlot), single.recipe);
            }
        }
        return null;
    }

    private int findMatchingBlenderInputSlot(Ingredient ingredient, int[] plannedUse) {
        for (int slot = 0; slot <= 8; slot++) {
            ItemStack stack = getItem(slot);
            if (!stack.isEmpty() && stack.getCount() > plannedUse[slot] && ingredient.test(stack)) {
                return slot;
            }
        }
        return -1;
    }

    private void craftBlenderOnce(BlenderMatch matched) {
        Level currentLevel = level;
        if (currentLevel == null) {
            return;
        }
        ItemStack crafted = matched.recipe.getResultItem(currentLevel.registryAccess());
        if (!canOutput(getItem(10), crafted)) {
            return;
        }
        for (int slot : matched.inputSlots) {
            getItem(slot).shrink(1);
        }
        if (matched.recipe instanceof BlenderRecipe blenderRecipe && blenderRecipe.hasContainer()) {
            getItem(9).shrink(1);
        }
        ItemStack output = getItem(10);
        if (output.isEmpty()) {
            setItem(10, crafted.copy());
        } else {
            output.grow(crafted.getCount());
            setItem(10, output);
        }
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

    public boolean isMokaPotBrewing() {
        return getBlockState().is(BakeriesBlocks.MOKA_POT) && progress > 0;
    }

    public int getOvenTemperature() {
        return ovenTemperature;
    }

    public void addOvenTemperature(int value) {
        ovenTemperature = Math.max(0, Math.min(500, ovenTemperature + value));
        setChanged();
    }

    public void setFermentationTime(int value) {
        maxProgress = Math.max(0, Math.min(1200, value));
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

    private record BlenderMatch(List<Integer> inputSlots, SimpleMachineRecipe recipe) {
    }

    private record OvenMatch(SimpleMachineRecipe recipe, boolean burnt) {
    }
}
