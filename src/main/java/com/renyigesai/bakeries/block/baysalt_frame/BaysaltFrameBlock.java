package com.renyigesai.bakeries.block.baysalt_frame;

import com.renyigesai.bakeries.fluid.BakeriesFluids;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaysaltFrameBlock extends BaseEntityBlock {
    //方块状态
    public static final IntegerProperty LIT = IntegerProperty.create("lit", 0, 2);
    public BaysaltFrameBlock() {
        super(Properties.of().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, 0));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return box(0, 0, 0, 16, 5, 16);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof BaysaltFrameBlockEntity baysaltFrameBlockEntity) {
            if (!pLevel.isClientSide) {
                if (pPlayer.getItemInHand(pHand).is(BakeriesItems.SALT_WATER_BUCKET.get())) {
                    if (baysaltFrameBlockEntity.getFluidTank().fill(new FluidStack(BakeriesFluids.FLOWING_SALT_WATER.get(), 1000), IFluidHandler.FluidAction.SIMULATE) >= 1000) {
                        baysaltFrameBlockEntity.addFluid(pPlayer, pPos,
                                baysaltFrameBlockEntity.getFluidTank(),
                                SoundEvents.BUCKET_EMPTY,
                                pPlayer.getItemInHand(pHand),
                                new ItemStack(Items.BUCKET),
                                new FluidStack(BakeriesFluids.FLOWING_SALT_WATER.get(), 1000));
                        return InteractionResult.SUCCESS;
                    }
                }else if (pPlayer.getItemInHand(pHand).is(Items.BUCKET)) {
                    if (baysaltFrameBlockEntity.getFluidTank().drain(new FluidStack(BakeriesFluids.FLOWING_SALT_WATER.get(), 1000), IFluidHandler.FluidAction.SIMULATE).getAmount() >= 1000) {
                        baysaltFrameBlockEntity.getFluid(pPlayer, pPos,
                                baysaltFrameBlockEntity.getFluidTank(),
                                SoundEvents.BUCKET_FILL,
                                pPlayer.getItemInHand(pHand),
                                new ItemStack(BakeriesItems.SALT_WATER_BUCKET.get()),
                                new FluidStack(BakeriesFluids.FLOWING_SALT_WATER.get(), 1000));
                        return InteractionResult.SUCCESS;
                    }
                }else if(pPlayer.getItemInHand(pHand).is(BakeriesItems.SALT_SCRAPER_RAKE.get())){
                    if(baysaltFrameBlockEntity.salts >= 10){
                        baysaltFrameBlockEntity.salts -= 10;
                        pPlayer.getInventory().placeItemBackInInventory(BakeriesItems.SALT.get().getDefaultInstance());
                        if (!pPlayer.getAbilities().instabuild) {
                            pPlayer.getItemInHand(pHand).hurt(1, RandomSource.create(), null);
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                return InteractionResult.PASS;
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean triggerEvent(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, BakeriesBlocks.BAYSALT_FRAME_ENTITY.get(),
                BaysaltFrameBlockEntity::serverTick);
    }

    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new BaysaltFrameBlockEntity(pPos, pState);
    }

}
