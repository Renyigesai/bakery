package com.renyigesai.bakeries.block.baysalt_frame;

import com.renyigesai.bakeries.fluid.BakeriesFluids;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.network.FluidSyncS2CPacket;
import com.renyigesai.bakeries.network.Messages;
import com.renyigesai.bakeries.util.FluidUtil;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class BaysaltFrameBlockEntity extends BlockEntity {
    @Getter
    private final ItemStackHandler itemHandler = new ItemStackHandler(1);
    private final FluidTank fluidTankInput = new FluidTank(2000, fs -> {
        if (fs.getFluid() == BakeriesFluids.FLOWING_SALT_WATER.get())
            return true;

        return false;
    }) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
            if(!level.isClientSide()) {
                Messages.sendToAllPlayers(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }
    };

    private int progress = 0;
    private final int maxProgress = 200;

    public BaysaltFrameBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.TOASTER_ENTITY.get(), pPos, pBlockState);
    }
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("progress", this.progress);
        pTag.putInt("maxProgress", this.maxProgress);
        pTag.put("fluidTankInput", fluidTankInput.writeToNBT(new CompoundTag()));
        super.saveAdditional(pTag);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        if (pTag.get("fluidTankInput") instanceof CompoundTag compoundTag)
            fluidTankInput.readFromNBT(compoundTag);
        this.progress = pTag.getInt("progress");
    }
    public FluidTank getFluidTank() {
        return this.fluidTankInput;
    }
    public void setFluid(FluidStack stack) {
        this.fluidTankInput.setFluid(stack);
    }
    public void addFluid(Player player, BlockPos pos, FluidTank tank, SoundEvent sound, ItemStack useItem, ItemStack outItem, FluidStack fluid){
        FluidUtil.addFluid(player, pos, tank, sound, useItem, outItem, fluid);
    }
    public void getFluid(Player player, BlockPos pos, FluidTank tank, SoundEvent sound, ItemStack useItem, ItemStack outItem, FluidStack fluid){
        FluidUtil.getFluid(player, pos, tank, sound, useItem, outItem, fluid);
    }
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, BaysaltFrameBlockEntity pToasterBlockEntity) {
        boolean flag = false;
        updateBlock(pToasterBlockEntity);



        if (flag) {
            setChanged(pLevel, pPos, pState);
        }
    }




    public static void updateBlock(BaysaltFrameBlockEntity pToasterBlockEntity) {
        Level world = pToasterBlockEntity.getLevel();
        BlockPos pos = pToasterBlockEntity.getBlockPos();
        BlockState state = world.getBlockState(pos);
        setChanged(world, pos, state);
        world.sendBlockUpdated(pos, state, state, 3);
    }

    private static void recipeItem(Level world, BlockPos pos, BlockState state, BaysaltFrameBlockEntity pToasterBlockEntity) {
//        Optional<ToasterRecipe> recipe = pToasterBlockEntity.getCurrentRecipe();

//        recipe.ifPresent(ovenRecipe -> {
//            pToasterBlockEntity.max_cooking_times[slot] = ovenRecipe.getTime();
//        });
//        if (pToasterBlockEntity.hasRecipe(slot) && recipe.isPresent()) {
//
//            if (!world.isClientSide()) {
//                int cookingTime = pToasterBlockEntity.progress++;
//
//                if (cookingTime >= max_cooking_time) {
//                    pToasterBlockEntity.craftItem(pToasterBlockEntity, slot);
//                    world.sendBlockUpdated(pos, state, state, 3);
//                    resetProgress(pToasterBlockEntity, slot);
//                }
//            }
//        } else {
//            if (!world.isClientSide()) {
//                world.sendBlockUpdated(pos, state, state, 3);
//                resetProgress(pToasterBlockEntity, slot);
//            }
//        }
    }

//
//    private static void resetProgress(BaysaltFrameBlockEntity pToasterBlockEntity) {
//        pToasterBlockEntity.progress = 0;
//    }
//    private void craftItem(BaysaltFrameBlockEntity pToasterBlockEntity, int slot) {
//        updateBlock(pToasterBlockEntity);
//        Optional<ToasterRecipe> recipe = getCurrentRecipe();
//        if (recipe.isPresent()) {
//            ItemStack result = recipe.get().getResultItem(null);
//            ItemStack takeItem = new ItemStack(result.getItem(), result.getCount());
//            this.itemHandler.setStackInSlot(slot, takeItem);
//            updateBlock(pToasterBlockEntity);
//        }
//    }
//    private boolean hasRecipe(int slot) {
//        Optional<ToasterRecipe> recipe = getCurrentRecipe();
//        return recipe.isPresent() && recipe.get().getIngredients().get(0).test(itemHandler.getStackInSlot(slot));
//    }
//    public Optional<ToasterRecipe> getCurrentRecipe() {
//        return this.level.getRecipeManager().getRecipeFor(ToasterRecipe.Type.INSTANCE, new SimpleContainer(this.itemHandler.getStackInSlot()), level);
//    }
}
