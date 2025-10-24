package com.renyigesai.bakeries.block.stone_kiln;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.item.StoneKilnShovelItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoneKilnBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public StoneKilnBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT,true).setValue(FACING, Direction.NORTH));
    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pSide);
    }

    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (blockEntity instanceof StoneKilnBlockEntity kiln){
            if (itemInHand.getItem() instanceof StoneKilnShovelItem shovelItem){
                if(kiln.addItem(shovelItem.getInventoryStack(itemInHand))){
                    shovelItem.removeItem(itemInHand);
                    pLevel.playSound(null, pPos, SoundEvents.WOOL_STEP, SoundSource.BLOCKS, 0.8F, 0.8F);
                    return InteractionResult.SUCCESS;
                }else if (kiln.isTurnOver()){
                    kiln.startTurnOver();
                    pLevel.playSound(null, pPos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.8F, 0.8F);
                    return InteractionResult.SUCCESS;
                }
            }
            if (!pState.getValue(LIT)){
                if (itemInHand.getItem() instanceof FlintAndSteelItem){
                    pLevel.setBlock(pPos, pState.setValue(LIT,true),3);
                    if (!pPlayer.getAbilities().instabuild) {
                        itemInHand.shrink(1);
                    }
                    pLevel.playSound(null, pPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 0.8F, 0.8F);
                    return InteractionResult.SUCCESS;
                }
            }else {
                if (itemInHand.is(Items.WATER_BUCKET)){
                    pLevel.setBlock(pPos, pState.setValue(LIT,false),3);
                    if (!pPlayer.getAbilities().instabuild){
                        itemInHand.shrink(1);
                    }
                    pPlayer.addItem(new ItemStack(Items.BUCKET));
                    pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 0.8F, 0.8F);
                    return InteractionResult.SUCCESS;
                }
                if (itemInHand.getItem() instanceof ShovelItem && !(itemInHand.getItem() instanceof StoneKilnShovelItem)){
                    pLevel.setBlock(pPos, pState.setValue(LIT,false),3);
                    if (!pPlayer.getAbilities().instabuild) {
                        itemInHand.shrink(1);
                    }
                    pLevel.playSound(null, pPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.8F, 0.8F);
                    return InteractionResult.SUCCESS;
                }
            }

        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public void animateTick(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            double d0 = (double)pPos.getX() + 0.5D;
            double d1 = (double)pPos.getY() + 0.5D;
            double d2 = (double)pPos.getZ() + 0.5D;
            if (pRandom.nextDouble() < 0.3D) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
            for (int i = 0; i < 2; i++) {
                pLevel.addParticle(ParticleTypes.FLAME, d0 + pRandom.nextDouble() * 0.6D - 0.3D, d1 + pRandom.nextDouble() * 0.6D - 0.3D, d2 + pRandom.nextDouble() * 0.6D - 0.3D , 0.0D, 0.0D, 0.0D);
            }
            pLevel.addParticle(ParticleTypes.LARGE_SMOKE, d0 + pRandom.nextDouble() * 0.6D - 0.3D, d1 + pRandom.nextDouble() * 0.6D - 0.3D, d2 + pRandom.nextDouble() * 0.6D - 0.3D , 0.0D, 0.0D, 0.0D);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new StoneKilnBlockEntity(pPos,pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? createTickerHelper(pBlockEntityType, BakeriesBlocks.STONE_KILN_ENTITY.get(),
                StoneKilnBlockEntity::clientTick) : createTickerHelper(pBlockEntityType, BakeriesBlocks.STONE_KILN_ENTITY.get(),
                StoneKilnBlockEntity::tick);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT,FACING);
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING,direction.rotate(state.getValue(FACING)));
    }

}
