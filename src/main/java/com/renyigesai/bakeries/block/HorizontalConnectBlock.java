package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.block.sofa.SofaBlock;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class HorizontalConnectBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT = BooleanProperty.create("right");

    protected HorizontalConnectBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LEFT,false).setValue(RIGHT,false));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection())/*.setValue(LEFT,isSofa(left,level)).setValue(RIGHT,isSofa(right,level))*/;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        Direction facing = state.getValue(FACING);
        boolean shouldConnect1 = canConnectTo(level, currentPos, facing,state,false);
        boolean shouldConnect2 = canConnectTo(level, currentPos, facing,state,true);
        return state.setValue(LEFT, shouldConnect1).setValue(RIGHT,shouldConnect2);
    }

    protected boolean canConnectTo(BlockGetter level, BlockPos pos, Direction facing, BlockState oleState, boolean isRight) {
        Direction direction = getLeft(facing);
        BlockPos directionPos = pos.relative(isRight?direction.getOpposite():direction);
        BlockState state = level.getBlockState(directionPos);
        return state.is(this) && state.getValue(SofaBlock.FACING) == oleState.getValue(FACING);
    }

    protected Direction getLeft(Direction direction){
        return direction.getCounterClockWise();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING,LEFT,RIGHT);
    }
}
