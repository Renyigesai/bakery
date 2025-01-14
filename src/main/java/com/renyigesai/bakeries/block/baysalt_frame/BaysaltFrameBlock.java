package com.renyigesai.bakeries.block.baysalt_frame;

import com.renyigesai.bakeries.fluid.BakeriesFluids;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class BaysaltFrameBlock extends BaseEntityBlock {
    public BaysaltFrameBlock() {
        super(Properties.of().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(0, 0, 0, 16, 5, 16);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof BaysaltFrameBlockEntity toasterBlockEntity){
            if (!pLevel.isClientSide){
                if (pPlayer.getItemInHand(pHand).is(BakeriesItems.SALT_WATER_BUCKET.get())) {
                    if(toasterBlockEntity.getFluidTank().fill(new FluidStack(BakeriesFluids.FLOWING_SALT_WATER.get(), 1000), IFluidHandler.FluidAction.SIMULATE) > 0){
                        toasterBlockEntity.addFluid(pPlayer, pPos,
                                toasterBlockEntity.getFluidTank(),
                                SoundEvents.BUCKET_EMPTY,
                                pPlayer.getItemInHand(pHand),
                                new ItemStack(Items.BUCKET),
                                new FluidStack(BakeriesFluids.FLOWING_SALT_WATER.get(), 1000)
                        );
                    }
                }
                if(pPlayer.getItemInHand(pHand).is(Items.BUCKET)){
                    if(toasterBlockEntity.getFluidTank().drain(new FluidStack(BakeriesFluids.FLOWING_SALT_WATER.get(), 1000), IFluidHandler.FluidAction.SIMULATE).getAmount() >= 1000){
                        toasterBlockEntity.getFluid(pPlayer, pPos,
                                toasterBlockEntity.getFluidTank(),
                                SoundEvents.BUCKET_FILL,
                                pPlayer.getItemInHand(pHand),
                                new ItemStack(BakeriesItems.SALT_WATER_BUCKET.get()),
                                new FluidStack(BakeriesFluids.FLOWING_SALT_WATER.get(), 1000));
                    }
                }
                return InteractionResult.FAIL;
            }

        }
        return InteractionResult.CONSUME;
    }
    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, BakeriesBlocks.BAYSALT_FRAME_ENTITY.get(),
                BaysaltFrameBlockEntity::serverTick);
    }

    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BaysaltFrameBlockEntity(pPos, pState);
    }

}
