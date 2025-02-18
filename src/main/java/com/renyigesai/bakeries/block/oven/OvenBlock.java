package com.renyigesai.bakeries.block.oven;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class OvenBlock extends BaseEntityBlock{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape S_BASE = Block.box(0, 0, 0, 16, 12, 12);
    protected static final VoxelShape S_BOX_B = Block.box(13.5, 2, 12, 15.5, 4, 12.5);
    protected static final VoxelShape S_BOX_A_1 = Block.box(13.5, 6, 12, 15.5, 8, 12.5);
    protected static final VoxelShape S_BOX_A_2 = Block.box(1, 10, 12, 13, 11, 13);
    protected static final VoxelShape N_BASE = Block.box(0, 0, 4, 16, 12, 16);
    protected static final VoxelShape N_BOX_B = Block.box(0.5, 2, 3.5, 2.5, 4, 4);
    protected static final VoxelShape N_BOX_A_1 = Block.box(0.5, 6, 3.5, 2.5, 8, 4);
    protected static final VoxelShape N_BOX_A_2 = Block.box(3, 10, 3, 15, 11, 4);
    protected static final VoxelShape E_BASE = Block.box(0, 0, 0, 12, 12, 16);
    protected static final VoxelShape E_BOX_B = Block.box(12, 2, 0.5, 12.5, 4, 2.5);
    protected static final VoxelShape E_BOX_A_1 = Block.box(12, 6, 0.5, 12.5, 8, 2.5);
    protected static final VoxelShape E_BOX_A_2 = Block.box(2, 10, 3, 13, 11, 15);
    protected static final VoxelShape W_BASE = Block.box(4, 0, 0, 16, 12, 16);
    protected static final VoxelShape W_BOX_B = Block.box(3.5, 2, 13.5, 4, 4, 15.5);
    protected static final VoxelShape W_BOX_A_1 = Block.box(3.5, 6, 13.5, 4, 8, 15.5);
    protected static final VoxelShape W_BOX_A_2 = Block.box(3, 10, 1, 4, 11, 13);
    private static final VoxelShape S_AXIS_BAA = Shapes.or(S_BASE, S_BOX_B, S_BOX_A_1, S_BOX_A_2);
    private static final VoxelShape N_AXIS_BAA = Shapes.or(N_BASE, N_BOX_B, N_BOX_A_1, N_BOX_A_2);
    public static final VoxelShape E_AXIS_BAA = Shapes.or(E_BASE, E_BOX_B, E_BOX_A_1, E_BOX_A_2);
    public static final VoxelShape W_AXIS_BAA = Shapes.or(W_BASE, W_BOX_B, W_BOX_A_1, W_BOX_A_2);
    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public OvenBlock() {
        super(Properties.of().mapColor(MapColor.METAL).strength(3.5F, 3.5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return switch (pState.getValue(FACING)){
            default -> N_AXIS_BAA;
            case NORTH -> S_AXIS_BAA;
            case EAST -> W_AXIS_BAA;
            case WEST -> E_AXIS_BAA;
        };
    }

    @Override
    public int getLightEmission(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if(pState.getValue(LIT)){
            return 5;
        }else {
            return 0;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, FACING);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection();
        BlockPos blockpos = pContext.getClickedPos();
        return this.defaultBlockState().setValue(FACING, direction);
    }
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new OvenBlockEntity(pPos, pState);
    }
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }
    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {

        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, BakeriesBlocks.OVEN_BLOCK_ENTITY.get(),
                OvenBlockEntity::serverTick);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public boolean triggerEvent(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
    }
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockstate, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player entity, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        super.use(blockstate, world, pos, entity, hand, hit);
        if(!world.isClientSide()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            super.use(blockstate, world, pos, entity, hand, hit);
            if (blockEntity instanceof OvenBlockEntity ovenBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer) entity), ovenBlockEntity, pos);
                world.playSound(entity, pos, BakeriesSounds.OVEN_OPEN.get(), SoundSource.PLAYERS);
                return InteractionResult.CONSUME;
            }else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }
    @Override
    public void onRemove(BlockState state, @NotNull Level world, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof OvenBlockEntity ov) {
                ov.drops();
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }
    public @NotNull BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    public void animateTick(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            double d0 = (double)pPos.getX() + 0.5D;
            double d1 = (double)pPos.getY();
            double d2 = (double)pPos.getZ() + 0.5D;
            if (pRandom.nextDouble() < 0.3D) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = pState.getValue(FACING).getClockWise();
            Direction.Axis direction$axis = direction.getAxis();
            for (int i = 0; i < 4 ; i++) {
            double d3 = 0.52D;
            double d4 = pRandom.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.6D : d4;
            double d6 = pRandom.nextDouble() * 6.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.6D : d4;
                pLevel.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
