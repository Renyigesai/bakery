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
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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

import java.util.Optional;

public class OvenBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4);
    public Component name = Component.translatable("container.oven");
    private LazyOptional<IItemHandler> lazyItemHandlers = LazyOptional.empty();
    public CompoundTag oven;

    public OvenBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeryBlocks.OVEN_BLOCK_ENTITY.get(), pPos, pBlockState);
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
    public @NotNull Component getDisplayName() {
        return name;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new OvenMenu(pContainerId, pPlayerInventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
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

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, OvenBlockEntity pOvenBlockEntity) {
        BlockEntity _blockEntity = world.getBlockEntity(pos);
        if (_blockEntity instanceof OvenBlockEntity ovenBlockEntity) {
            for (int i = 0; i < ovenBlockEntity.itemHandler.getSlots(); i++) {
                recipeItem(world, pos, state, i);
            }
        }
    }
    public static void setTemperature(OvenBlockEntity ovenBlockEntity, int temperature){
        ovenBlockEntity.getOven().putInt("temperature", Math.min(Math.max(temperature, 0),500));
    }
    private static void recipeItem(Level world, BlockPos pos, BlockState state, int slot) {
        BlockEntity _blockEntity = world.getBlockEntity(pos);
        if (_blockEntity instanceof OvenBlockEntity ovenBlockEntity) {
            setChanged( world,pos,state);
            Optional<OvenRecipe> recipe = ovenBlockEntity.getCurrentRecipe();

            if (ovenBlockEntity.hasRecipe(slot)) {
                recipe.ifPresent(ovenRecipe -> {
                    ovenBlockEntity.getOven().putInt("max_progress"+"_"+slot, ovenRecipe.getTime());
                    ovenBlockEntity.getOven().putInt("min_temperature"+"_"+slot, Math.max(ovenRecipe.getMax_temperature(), 0));
                    ovenBlockEntity.getOven().putInt("max_temperature"+"_"+slot, Math.min(ovenRecipe.getMax_temperature(), 500));
                });
                if (!world.isClientSide()) {
                    ovenBlockEntity.getOven().putDouble("progress" + "_" + slot,
                            (ovenBlockEntity.getOven().getDouble("progress" + "_" + slot) + 1));
                    world.sendBlockUpdated(pos, state, state, 3);
                }
                if ( ovenBlockEntity.getOven().getDouble(  "progress"+"_"+slot)
                        >= ovenBlockEntity.getOven().getDouble( "max_progress"+"_"+slot)) {
                    if (!world.isClientSide()) {

                        ovenBlockEntity.getOven().putDouble("progress"+"_"+slot, 0);

                        world.sendBlockUpdated(pos, state, state, 3);
                    }
                    ovenBlockEntity.craftItem(slot);
                }
            } else {
                if (!world.isClientSide()) {
                    ovenBlockEntity.getOven().putDouble("progress"+"_"+slot, 0);

                    world.sendBlockUpdated(pos, state, state, 3);
                }
            }
        }
    }
    private void craftItem(int slot) {
        Optional<OvenRecipe> recipe = getCurrentRecipe();
        if(recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(null);
            this.itemHandler.setStackInSlot(slot, new ItemStack(result.getItem(), result.getCount()));
        }
    }


    private boolean hasRecipe(int slot) {
        Optional<OvenRecipe> recipe = getCurrentRecipe();


        if(recipe.isPresent()) {
            return recipe.get().getIngredients().get(0).test(itemHandler.getStackInSlot(slot));
        }else {
            return false;
        }
    }

    public Optional<OvenRecipe> getCurrentRecipe() {
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
}
