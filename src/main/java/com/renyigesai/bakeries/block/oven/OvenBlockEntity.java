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
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
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

public class OvenBlockEntity extends BaseContainerBlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4);
    public Component name = Component.translatable("container.oven");
    private LazyOptional<IItemHandler> lazyItemHandlers = LazyOptional.empty();
    public CompoundTag oven;
    public int temperature;

    public final ContainerData dataAccess = new ContainerData() {


        @Override
        public int get(int pIndex) {
            switch (pIndex) {
                case 0:
                    return OvenBlockEntity.this.temperature;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0:
                    OvenBlockEntity.this.temperature = pValue;
                    break;
            }
        }

        public int getCount() {
            return 1;
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
    protected Component getDefaultName() {
        return name;
    }
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new OvenMenu(pContainerId, pInventory,  new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition),this, this.dataAccess);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        if (this.oven != null) pTag.put("oven", this.oven.copy());
        pTag.putInt("temperature", this.temperature);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        if (pTag.contains("oven")) this.oven = pTag.getCompound("oven");
        this.temperature = pTag.getInt("temperature");
    }

    public CompoundTag getOven() {
        if (this.oven == null)
            this.oven = new CompoundTag();
        return this.oven;
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

    public static void serverTick(Level world, BlockPos pos, BlockState state, OvenBlockEntity pOvenBlockEntity) {
        for (int i = 0; i < pOvenBlockEntity.itemHandler.getSlots(); i++) {
            recipeItem(world, pos, state, i, pOvenBlockEntity);
        }
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
        setChanged(world, pos, state);
        Optional<OvenRecipe> recipe = ovenBlockEntity.getCurrentRecipe(slot);
        int temperature = ovenBlockEntity.temperature;

        boolean isLit = ovenBlockEntity.getOven().getDouble("progress_0") > 0 ||
                ovenBlockEntity.getOven().getDouble("progress_1") > 0 ||
                ovenBlockEntity.getOven().getDouble("progress_2") > 0 ||
                ovenBlockEntity.getOven().getDouble("progress_3") > 0;

        world.setBlock(pos, ovenBlockEntity.getBlockState().setValue(OvenBlock.LIT, isLit), 3);

        if (ovenBlockEntity.hasRecipe(slot) && recipe.isPresent() && Math.max(recipe.get().getMin_temperature(), 0) <= temperature) {
//            recipe.ifPresent(ovenRecipe -> {
//                ovenBlockEntity.getOven().putDouble("max_progress_" + slot, ovenRecipe.getTime());
//                ovenBlockEntity.getOven().putDouble("min_temperature_" + slot, Math.max(ovenRecipe.getMin_temperature(), 0));
//                ovenBlockEntity.getOven().putDouble("max_temperature_" + slot, Math.min(ovenRecipe.getMax_temperature(), 500));
//            });

            if (!world.isClientSide()) {
                double currentProgress = ovenBlockEntity.getOven().getDouble("progress_" + slot);
                ovenBlockEntity.getOven().putDouble("progress_" + slot, currentProgress + 1);
                world.sendBlockUpdated(pos, state, state, 3);
                setChanged(world, pos, state);

//                if (currentProgress >= ovenBlockEntity.getOven().getDouble("max_progress_" + slot)) {
                if (currentProgress >= recipe.get().getTime()) {
                    if (temperature <= Math.min(recipe.get().getMax_temperature(), 500)) {
                        boolean perfect = temperature == recipe.get().getPerfect_temperature ();
                        ovenBlockEntity.craftItem(ovenBlockEntity, slot, perfect, recipe.get().getPerfect_temperature ());
                    } else if (temperature > Math.min(recipe.get().getMax_temperature(), 500)) {
                        ovenBlockEntity.itemHandler.setStackInSlot(slot, new ItemStack(Items.CHARCOAL, 1));
                    }
                    resetProgress(ovenBlockEntity, slot);
                }
            }
        } else {
            if (!world.isClientSide()) {
                resetProgress(ovenBlockEntity, slot);
            }
        }
    }

    private static void resetProgress(OvenBlockEntity ovenBlockEntity, int slot) {
        ovenBlockEntity.getOven().putDouble("progress_" + slot, 0);
    }
    private void craftItem(OvenBlockEntity ovenBlockEntity, int slot, boolean perfect, int perfect_temperature) {
        updateBlock(ovenBlockEntity);
        Optional<OvenRecipe> recipe = getCurrentRecipe(slot);
        if (recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(null);
            ItemStack takeItem = new ItemStack(result.getItem(), result.getCount());
            takeItem.getOrCreateTag().putBoolean("perfect", perfect);
            takeItem.getOrCreateTag().putInt("perfect_temperature", temperature);
            this.itemHandler.setStackInSlot(slot, takeItem);
            updateBlock(ovenBlockEntity);
        }
    }

    private boolean hasRecipe(int slot) {
        Optional<OvenRecipe> recipe = getCurrentRecipe(slot);
        return recipe.isPresent() && recipe.get().getIngredients().get(0).test(itemHandler.getStackInSlot(slot));
    }

    public Optional<OvenRecipe> getCurrentRecipe(int slot) {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        inventory.setItem(slot, this.itemHandler.getStackInSlot(slot));
        return this.level.getRecipeManager().getRecipeFor(OvenRecipe.Type.INSTANCE, inventory, level);
    }

    public double getMinTemperature(int slot) {
        return this.getOven().getDouble("min_temperature_" + slot);
    }

    public double getMaxTemperature(int slot) {
        return this.getOven().getDouble("max_temperature_" + slot);
    }


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
    public ItemStack getItem(int pSlot) {
        return this.itemHandler.getStackInSlot(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return removeItem(this.itemHandler, pSlot, pAmount);
    }
    public static ItemStack removeItem(ItemStackHandler itemHandler, int pIndex, int pAmount) {
        return pIndex >= 0 && pIndex < itemHandler.getSlots() && !itemHandler.getStackInSlot(pIndex).isEmpty() && pAmount > 0 ? itemHandler.getStackInSlot(pIndex).split(pAmount) : ItemStack.EMPTY;
    }
    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
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
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

}
