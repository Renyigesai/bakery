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
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class BaysaltFrameBlockEntity extends BlockEntity {
    @Getter
    private final FluidTank fluidTank = new FluidTank(2000, fs -> {
        return fs.getFluid() == BakeriesFluids.FLOWING_SALT_WATER.get();
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
    public int salts = 0;
    public final int max_salts = 100;
    public int progress = 0;
    public final int maxProgress = 200;

    public BaysaltFrameBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.BAYSALT_FRAME_ENTITY.get(), pPos, pBlockState);
    }
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("progress", this.progress);
        pTag.putInt("maxProgress", this.maxProgress);
        pTag.putInt("salts", this.salts);
        pTag.putInt("max_salts", this.max_salts);
        pTag.put("FluidTanks", fluidTank.writeToNBT(new CompoundTag()));
        super.saveAdditional(pTag);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        if (pTag.get("FluidTanks") instanceof CompoundTag compoundTag)
            fluidTank.readFromNBT(compoundTag);
        this.progress = pTag.getInt("progress");
        this.salts = pTag.getInt("salts");
    }

    public void setFluid(FluidStack stack) {
        this.fluidTank.setFluid(stack);
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
    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, BaysaltFrameBlockEntity pBaysaltFrameBlockEntity) {
        boolean flag = false;
        updateBlock(pBaysaltFrameBlockEntity);
        if(pBaysaltFrameBlockEntity.getFluidTank().getFluidAmount() >0){
            pLevel.setBlock(pPos, pState.setValue(BaysaltFrameBlock.LIT, 1), 3);
        } else if (pBaysaltFrameBlockEntity.salts>=10){
            pLevel.setBlock(pPos, pState.setValue(BaysaltFrameBlock.LIT, 2), 3);
        }else {
            pLevel.setBlock(pPos, pState.setValue(BaysaltFrameBlock.LIT, 0), 3);
        }
        if(pBaysaltFrameBlockEntity.getFluidTank().isFluidValid(new FluidStack(BakeriesFluids.FLOWING_SALT_WATER.get(), 1000)) &&
                !pLevel.getLevelData().isRaining() && !pLevel.getLevelData().isThundering() && pLevel.canSeeSkyFromBelowWater(pPos) && pLevel.isDay()){
            if (!pLevel.isClientSide()) {
                if( pBaysaltFrameBlockEntity.salts < pBaysaltFrameBlockEntity.max_salts &&
                        pBaysaltFrameBlockEntity.getFluidTank().drain(new FluidStack(BakeriesFluids.FLOWING_SALT_WATER.get(), 10), IFluidHandler.FluidAction.SIMULATE).getAmount() >= 10) {
                    pBaysaltFrameBlockEntity.progress++;
                }
                if (pBaysaltFrameBlockEntity.progress >= pBaysaltFrameBlockEntity.maxProgress ){
                                           pBaysaltFrameBlockEntity.salts ++;
                    pBaysaltFrameBlockEntity.getFluidTank().drain(new FluidStack(BakeriesFluids.FLOWING_SALT_WATER.get(), 10), IFluidHandler.FluidAction.EXECUTE);

                    pLevel.sendBlockUpdated(pPos, pState, pState, 3);
                    pBaysaltFrameBlockEntity.progress = 0;
                }
            }
            flag = true;
        }else {
            if (!pLevel.isClientSide()) {
                pLevel.sendBlockUpdated(pPos, pState, pState, 3);
                pBaysaltFrameBlockEntity.progress = 0;
            }
        }
        if (flag) {
            setChanged(pLevel, pPos, pState);
        }
    }

    public static void updateBlock(BaysaltFrameBlockEntity pBaysaltFrameBlockEntity) {
        Level world = pBaysaltFrameBlockEntity.getLevel();
        BlockPos pos = pBaysaltFrameBlockEntity.getBlockPos();
        BlockState state = world.getBlockState(pos);
        setChanged(world, pos, state);
        world.sendBlockUpdated(pos, state, state, 3);
    }
}
