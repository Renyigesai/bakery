package com.renyigesai.bakeries.block.oven;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.inventory.oven.OvenMenu;
import com.renyigesai.bakeries.recipe.oven.OvenRecipe;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class OvenBlockEntity extends BaseContainerBlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4){
        @Override
        public int getSlotLimit(int slot)
        {
            return 1;
        }
    };
    public final Component name = Component.translatable("container.oven");
    private LazyOptional<IItemHandler> lazyItemHandlers = LazyOptional.empty();
    public final int[] cooking_times = new int[4];
    public final int[] max_cooking_times = new int[4];
    private final int[] min_temperatures = new int[4];
    private final int[] max_temperatures = new int[4];
    public int temperature;

    public final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> OvenBlockEntity.this.temperature;
                case 1 -> calculateProgress(0);
                case 2 -> calculateProgress(1);
                case 3 -> calculateProgress(2);
                case 4 -> calculateProgress(3);
                default -> 0;
            };
        }

        private int calculateProgress(int index) {
            if (OvenBlockEntity.this.max_cooking_times[index] == 0) {
                return 0;
            }
            return Math.min((int) ((OvenBlockEntity.this.cooking_times[index] / (double) OvenBlockEntity.this.max_cooking_times[index]) * 14), 14);
        }

        @Override
        public void set(int pIndex, int pValue) {
            if (pIndex == 0) {
                OvenBlockEntity.this.temperature = pValue;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    };


    public OvenBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.OVEN_BLOCK_ENTITY.get(), pPos, pBlockState);
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }
    @Override
    protected @NotNull Component getDefaultName() {
        return name;
    }
    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new OvenMenu(pContainerId, pInventory,  new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition),this, this.dataAccess);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
//        if (this.oven != null) pTag.put("oven", this.oven.copy());
        pTag.putIntArray("cooking_times", this.cooking_times);
        pTag.putIntArray("max_cooking_times", this.max_cooking_times);
        pTag.putIntArray("min_temperatures", this.min_temperatures);
        pTag.putIntArray("max_temperatures", this.max_temperatures);
        pTag.putInt("temperature", this.temperature);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        if (pTag.contains("CookingTimes", 11)) {
            int[] aint = pTag.getIntArray("CookingTimes");
            System.arraycopy(aint, 0, this.cooking_times, 0, Math.min(this.max_cooking_times.length, aint.length));
        }
        if (pTag.contains("MinTemperatures", 11)) {
            int[] aint = pTag.getIntArray("MinTemperatures");
            System.arraycopy(aint, 0, this.min_temperatures, 0, Math.min(this.min_temperatures.length, aint.length));
        }
        if (pTag.contains("MaxTemperatures", 11)) {
            int[] aint = pTag.getIntArray("MaxTemperatures");
            System.arraycopy(aint, 0, this.max_temperatures, 0, Math.min(this.max_temperatures.length, aint.length));
        }
        this.temperature = pTag.getInt("temperature");
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER)
            return lazyItemHandlers.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandlers = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandlers.invalidate();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, OvenBlockEntity pOvenBlockEntity) {
        boolean flag = false;
        updateBlock(pOvenBlockEntity);
        setFire(pLevel, pPos, pState, pOvenBlockEntity);
        for (int i = 0; i < pOvenBlockEntity.itemHandler.getSlots(); i++) {
            flag = true;
            recipeItem(pLevel, pPos, pState, i, pOvenBlockEntity);
        }
        if (flag) {
            setChanged(pLevel, pPos, pState);
            updateBlock(pOvenBlockEntity);
        }
    }
    public static void setFire(Level world, BlockPos pos, BlockState state, OvenBlockEntity pOvenBlockEntity) {
        updateBlock(pOvenBlockEntity);
        boolean isLit = pOvenBlockEntity.cooking_times[0] > 0 || pOvenBlockEntity.cooking_times[1] > 0 || pOvenBlockEntity.cooking_times[2] > 0 || pOvenBlockEntity.cooking_times[3] > 0;

        world.setBlock(pos, pOvenBlockEntity.getBlockState().setValue(OvenBlock.LIT, isLit), 3);
        world.sendBlockUpdated(pos, state, state, 3);
        setChanged(world, pos, state);
    }
    public int getTemperature(OvenBlockEntity ovenBlockEntity) {
        return ovenBlockEntity.temperature;
    }

    public void setTemperature(OvenBlockEntity ovenBlockEntity, int temperature) {
        updateBlock(ovenBlockEntity);
        ovenBlockEntity.temperature = Math.min(Math.max(temperature, 0), 500);
    }

    public void addTemperature(OvenBlockEntity ovenBlockEntity, int temperature) {
        updateBlock(ovenBlockEntity);
        ovenBlockEntity.temperature = Math.min(Math.max(this.getTemperature(ovenBlockEntity) + temperature, 0), 500);
    }

    public void subTemperature(OvenBlockEntity ovenBlockEntity, int temperature) {
        updateBlock(ovenBlockEntity);
        ovenBlockEntity.temperature = Math.min(Math.max(this.getTemperature(ovenBlockEntity) - temperature, 0), 500);
    }

    public static void updateBlock(OvenBlockEntity ovenBlockEntity) {
        Level world = ovenBlockEntity.getLevel();
        BlockPos pos = ovenBlockEntity.getBlockPos();
        BlockState state = world.getBlockState(pos);
        setChanged(world, pos, state);
        world.sendBlockUpdated(pos, state, state, 3);
    }

    private static void recipeItem(Level world, BlockPos pos, BlockState state, int slot, OvenBlockEntity ovenBlockEntity) {
        Optional<OvenRecipe> recipe = ovenBlockEntity.getCurrentRecipe(slot);
        int temperature = ovenBlockEntity.temperature;
        recipe.ifPresent(ovenRecipe -> {
            ovenBlockEntity.max_cooking_times[slot] = ovenRecipe.getTime();
            ovenBlockEntity.min_temperatures[slot] = Math.max(recipe.get().getMin_temperature(), 0);
            ovenBlockEntity.max_temperatures[slot] = Math.min(recipe.get().getMax_temperature(), 500);
        });
        if (ovenBlockEntity.hasRecipe(slot) && recipe.isPresent() && Math.max(ovenBlockEntity.min_temperatures[slot], 0) <= temperature) {


            if (!world.isClientSide()) {
                int cookingTime = ovenBlockEntity.cooking_times[slot]++;
                int max_cooking_time = ovenBlockEntity.max_cooking_times[slot];

                int craft_temperature = Math.min(ovenBlockEntity.max_temperatures[slot], 500);

                if (cookingTime >= max_cooking_time) {
                    if (temperature <= craft_temperature) {
                        boolean perfect = temperature == recipe.get().getPerfect_temperature ();
                        ovenBlockEntity.craftItem(ovenBlockEntity, slot, perfect);
                    } else {
                        ovenBlockEntity.itemHandler.setStackInSlot(slot, new ItemStack(Items.CHARCOAL, 1));
                    }
                    world.sendBlockUpdated(pos, state, state, 3);
                    world.playSound(null, pos, SoundEvents.NOTE_BLOCK_BELL.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    resetProgress(ovenBlockEntity, slot);
                }
            }
        } else {
            if (!world.isClientSide()) {
                world.sendBlockUpdated(pos, state, state, 3);
                resetProgress(ovenBlockEntity, slot);
            }
        }
    }

    private static void resetProgress(OvenBlockEntity ovenBlockEntity, int slot) {
        ovenBlockEntity.max_cooking_times[slot] = 0;
        ovenBlockEntity.cooking_times[slot] = 0;
        ovenBlockEntity.min_temperatures[slot] = 0;
        ovenBlockEntity.max_temperatures[slot] = 0;
    }
    private void craftItem(OvenBlockEntity ovenBlockEntity, int slot, boolean perfect) {
        updateBlock(ovenBlockEntity);
        Optional<OvenRecipe> recipe = getCurrentRecipe(slot);
        if (recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(null);
            ItemStack takeItem = new ItemStack(result.getItem(), result.getCount());
            takeItem.getOrCreateTag().putBoolean("perfect", perfect);
            //去除int温度nbt
//            takeItem.getOrCreateTag().putInt("perfect_temperature", temperature);
            this.itemHandler.setStackInSlot(slot, takeItem);
            updateBlock(ovenBlockEntity);
        }
    }

    private boolean hasRecipe(int slot) {
        Optional<OvenRecipe> recipe = getCurrentRecipe(slot);
        return recipe.isPresent() && recipe.get().getIngredients().get(0).test(itemHandler.getStackInSlot(slot));
    }

    public Optional<OvenRecipe> getCurrentRecipe(int slot) {
        return this.level.getRecipeManager().getRecipeFor(OvenRecipe.Type.INSTANCE, new SimpleContainer(this.itemHandler.getStackInSlot(slot)), level);
    }

//    public double getMinTemperature(int slot) {
//        return this.getOven().getDouble("min_temperature_" + slot);
//    }
//
//    public double getMaxTemperature(int slot) {
//        return this.getOven().getDouble("max_temperature_" + slot);
//    }


    @Override
    public int getContainerSize() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < this.itemHandler.getSlots(); i++) {
            if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int pSlot) {
        return this.itemHandler.getStackInSlot(pSlot);
    }

    @Override
    public @NotNull ItemStack removeItem(int pSlot, int pAmount) {
        return removeItem(this.itemHandler, pSlot, pAmount);
    }
    public static ItemStack removeItem(ItemStackHandler itemHandler, int pIndex, int pAmount) {
        return pIndex >= 0 && pIndex < itemHandler.getSlots() && !itemHandler.getStackInSlot(pIndex).isEmpty() && pAmount > 0 ? itemHandler.getStackInSlot(pIndex).split(pAmount) : ItemStack.EMPTY;
    }
    @Override
    public @NotNull ItemStack removeItemNoUpdate(int pSlot) {
        return takeItem(this.itemHandler, pSlot);
    }
    public static ItemStack takeItem(ItemStackHandler itemHandler, int pSlot) {
        return pSlot >= 0 && pSlot < itemHandler.getSlots() ? itemHandler.insertItem(pSlot, ItemStack.EMPTY, false) : ItemStack.EMPTY;
    }
    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        ItemStack itemstack = this.itemHandler.getStackInSlot(pSlot);
        boolean flag = !pStack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, pStack);
        this.itemHandler.insertItem(pSlot,pStack, false);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }
        if (pSlot == 0 && !flag) {
            this.setChanged();
        }
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }


}
