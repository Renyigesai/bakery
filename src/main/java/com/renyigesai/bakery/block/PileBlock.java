package com.renyigesai.bakery.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PileBlock extends Block {

public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty PILE = IntegerProperty.create("pile", 1, 4);
    protected static final VoxelShape X_SHAPE = Block.box(6.0D, 0.0D, 5.0D, 10.0D, 4.0D, 11.0D);
    protected static final VoxelShape Z_SHAPE = Block.box(5.0D, 0.0D, 6.0D, 11.0D, 4.0D, 10.0D);

    private static final VoxelShape X_AXIS_SHAPE = Shapes.or(X_SHAPE);
    private static final VoxelShape Z_AXIS_SHAPE = Shapes.or(Z_SHAPE);
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);


    public PileBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PILE, 1));
    }

    public ItemStack getPileItem() {
        return new ItemStack(asItem());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
//        Direction direction = state.getValue(FACING);
//        int pile = state.getValue(PILE);
//        if(pile > 1){
//            return SHAPE;
//        }
//        return direction.getAxis() == Direction.Axis.X ? X_AXIS_SHAPE : Z_AXIS_SHAPE;
        return SHAPE;
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);
        int pile = state.getValue(PILE);
        if (level.isClientSide) {
            if (heldStack.is(getPileItem().getItem()) && pile < 4){
                return pileUp(level, pos, state, player);
            }
        }
        if (heldStack.is(getPileItem().getItem()) && pile < 4){
            return pileUp(level, pos, state, player);
            //heldStack.is(getPileItem().getItem()) && pile < 4
        }
        return InteractionResult.FAIL;

    }

    protected InteractionResult pileUp(Level level, BlockPos pos, BlockState state, Player playerIn){
        int pile = state.getValue(PILE);
        if (pile < 4) {
            level.setBlock(pos, state.setValue(PILE, pile + 1), 4);
            level.playSound(null, pos, SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 0.8F, 0.8F);
        }else return InteractionResult.FAIL;
        return InteractionResult.SUCCESS;

    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PILE);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }
}
