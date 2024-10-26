package com.renyigesai.bakery.block.oven;

import com.renyigesai.bakery.init.BakeryBlocks;
import com.renyigesai.bakery.inventory.oven.OvenMenu;
import com.renyigesai.bakery.recipe.OvenRecipe;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class OvenBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3);
    private LazyOptional<IItemHandler> lazyItemHandlers = LazyOptional.empty();
    public CompoundTag oven;
    public OvenBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeryBlocks.OVEN_BLOCK_ENTITY.get(), pPos, pBlockState);
    }
    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.oven");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new OvenMenu(pContainerId, pPlayerInventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition));
    }
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());
        if (this.oven != null) pTag.put("oven", this.oven.copy());
        super.saveAdditional(pTag);
    }
    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        if (pTag.contains("oven")) this.oven = pTag.getCompound("oven");
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
    public static void serverTick(Level level, BlockPos pos, BlockState blockState, OvenBlockEntity ovenBlockEntity) {
        BlockEntity _blockEntity = level.getBlockEntity(pos);
        if (_blockEntity == ovenBlockEntity) {
            setChanged( level,pos,blockState);
            craftRecipe(0,level, pos, blockState, ovenBlockEntity);
            craftRecipe(1,level, pos, blockState, ovenBlockEntity);
            craftRecipe(2,level, pos, blockState, ovenBlockEntity);
            craftRecipe(3,level, pos, blockState, ovenBlockEntity);
        }
    }
    private static void craftRecipe(int slot, Level level, BlockPos pos, BlockState blockState, OvenBlockEntity ovenBlockEntity) {
        if (ovenBlockEntity.hasRecipe(slot)) {
            if (!level.isClientSide()) {
                ovenBlockEntity.getOven().putDouble("progress" + slot,
                        (ovenBlockEntity.getOven().getDouble(  "progress" + slot) + 1));
                level.sendBlockUpdated(pos, blockState, blockState, 3);
            }
            if ( ovenBlockEntity.getOven().getDouble(  "progress" + slot)
                    >= ovenBlockEntity.getOven().getDouble( "max_progress" + slot)) {
                if (!level.isClientSide()) {
                    ovenBlockEntity.getOven().putDouble("progress" + slot, 0);
                    level.sendBlockUpdated(pos, blockState, blockState, 3);
                }
                ovenBlockEntity.craftItem(slot);
            }
        } else {
            if (!level.isClientSide()) {
                ovenBlockEntity.getOven().putDouble("progress" + slot, 0);
                level.sendBlockUpdated(pos, blockState, blockState, 3);
            }
        }
    }
    private void craftItem(int slot) {
        Optional<OvenRecipe> recipe = getCurrentRecipe();
        if(recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(null);
            this.itemHandler.extractItem(slot, 1, false);
            this.itemHandler.setStackInSlot(slot, new ItemStack(result.getItem(),
                    1));
        }
    }
    private boolean hasRecipe(int slot) {
        if (slot ==0){
            return hasRecipe();
        }else if (slot ==1){
            return hasRecipe1();
        }else if (slot ==2){
            return hasRecipe2();
        }else if (slot ==3){
            return hasRecipe3();
        }
        return false;
    }

    private boolean hasRecipe() {
        Optional<OvenRecipe> recipe = getCurrentRecipe();
        if(recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(Objects.requireNonNull(getLevel()).registryAccess());
            return canInsertAmountIntoOutputSlot(result.getCount())
                    && canInserItemIntoOutputSlot(result.getItem());
        }else {
            return false;
        }
    }
    private boolean hasRecipe1() {
        Optional<OvenRecipe> recipe = getCurrentRecipe();
        if(recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(Objects.requireNonNull(getLevel()).registryAccess());
            return canInsertAmountIntoOutputSlot1(result.getCount())
                    && canInserItemIntoOutputSlot1(result.getItem());
        }else {
            return false;
        }
    }
    private boolean hasRecipe2() {
        Optional<OvenRecipe> recipe = getCurrentRecipe();
        if(recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(Objects.requireNonNull(getLevel()).registryAccess());
            return canInsertAmountIntoOutputSlot2(result.getCount())
                    && canInserItemIntoOutputSlot2(result.getItem());
        }else {
            return false;
        }
    }
    private boolean hasRecipe3() {
        Optional<OvenRecipe> recipe = getCurrentRecipe();
        if(recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(Objects.requireNonNull(getLevel()).registryAccess());
            return canInsertAmountIntoOutputSlot3(result.getCount())
                    && canInserItemIntoOutputSlot3(result.getItem());
        }else {
            return false;
        }
    }
    private Optional<OvenRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i,this.itemHandler.getStackInSlot(i));
        }
        if (this.level != null) {
            return this.level.getRecipeManager().getRecipeFor(OvenRecipe.Type.INSTANCE, inventory, level);
        }else {
            return Optional.empty();
        }

    }

    private boolean canInserItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(0).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(0).getMaxStackSize() == 1;
    }
    private boolean canInserItemIntoOutputSlot1(Item item) {
        return this.itemHandler.getStackInSlot(1).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot1(int count) {
        return this.itemHandler.getStackInSlot(1).getMaxStackSize() == 1
               ;
    }
    private boolean canInserItemIntoOutputSlot2(Item item) {
        return this.itemHandler.getStackInSlot(2).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot2(int count) {
        return this.itemHandler.getStackInSlot(2).getMaxStackSize() == 1;
    }
    private boolean canInserItemIntoOutputSlot3(Item item) {
        return  this.itemHandler.getStackInSlot(3).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot3(int count) {
        return  this.itemHandler.getStackInSlot(3).getMaxStackSize() == 1;
    }



}