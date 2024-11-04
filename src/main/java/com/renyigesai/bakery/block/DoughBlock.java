package com.renyigesai.bakery.block;

import com.renyigesai.bakery.init.BakeryItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoughBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty KNEAD = IntegerProperty.create("knead",0,6);
    public static final BooleanProperty BUTTER = BooleanProperty.create("butter");
    protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);

    public DoughBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(KNEAD, 0)
                .setValue(BUTTER, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack hand = pPlayer.getItemInHand(pHand);
        int knead = pState.getValue(KNEAD);
        boolean butter = pState.getValue(BUTTER);
        if (knead < 3){
            return setKnead(pLevel,pPos,pState,pPlayer,pHand);
        }else if (knead == 3 && !butter && hand.is(BakeryItems.BUTTER_CUBE.get())){
            return setButter(pLevel,pPos,pState,pPlayer,pHand);
        }else if (butter && knead < 6){
            return setKnead(pLevel,pPos,pState,pPlayer,pHand);
        }
        return InteractionResult.FAIL;
    }
    public InteractionResult setKnead(Level level, BlockPos pos, BlockState state, Player playerIn, InteractionHand pHand){
        int knead = state.getValue(KNEAD);
        level.setBlock(pos, state.setValue(KNEAD, Math.min(knead + 1, 6)), 3);
        return InteractionResult.SUCCESS;
    }

    public InteractionResult setButter(Level level, BlockPos pos, BlockState state, Player playerIn, InteractionHand pHand){
        ItemStack hand = playerIn.getItemInHand(pHand);
        level.setBlock(pos, state.setValue(BUTTER,true),0);
        hand.shrink(1);
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(KNEAD,BUTTER,FACING);
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
