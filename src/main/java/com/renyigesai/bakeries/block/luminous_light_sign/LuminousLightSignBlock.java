package com.renyigesai.bakeries.block.luminous_light_sign;

import com.renyigesai.bakeries.block.HorizontalConnectBlock;
import com.renyigesai.bakeries.block.sofa.SofaBlock;
import com.renyigesai.bakeries.util.ItemUtils;
import com.renyigesai.bakeries.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.NameTagItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
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
import org.jetbrains.annotations.Nullable;

public class LuminousLightSignBlock extends HorizontalConnectBlock implements EntityBlock {
//    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT = BooleanProperty.create("right");
    public LuminousLightSignBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.GLASS).lightLevel((BlockState) -> 5).hasPostProcess((bs, br, bp) -> true));
//        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LEFT,false).setValue(RIGHT,false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            default -> box(0, 10, 6, 16, 14, 10);
            case NORTH -> box(0, 10, 6, 16, 14, 10);
            case EAST -> box(6, 10, 0, 10, 14, 16);
            case WEST -> box(6, 10, 0, 10, 14, 16);
        };
    }


    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (itemInHand.getItem() instanceof NameTagItem){
            if (blockEntity instanceof LuminousLightSignBlockEntity sign){
                Component hoverName = itemInHand.getHoverName();
                int length = TextUtils.getLength(hoverName.getString(), 45);
                String string = hoverName.getString(length);
                sign.setText(string);
                return InteractionResult.SUCCESS;
            }
        }else {
            if (itemInHand.getItem() instanceof DyeItem dye){
                if (blockEntity instanceof LuminousLightSignBlockEntity sign){
                    sign.setColor(dye.getDyeColor().getTextColor());
                    ItemUtils.shrink(itemInHand,1,pPlayer);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

//    public BlockState getStateForPlacement(BlockPlaceContext context) {
//        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection());
//    }
//
//    public BlockState rotate(BlockState pState, Rotation pRot) {
//        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
//    }
//
//    public BlockState mirror(BlockState pState, Mirror pMirror) {
//        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
//    }
//
//    @Override
//    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
//        Direction facing = state.getValue(FACING);
//        boolean shouldConnect1 = canConnectTo(level, currentPos, facing,state,false);
//        boolean shouldConnect2 = canConnectTo(level, currentPos, facing,state,true);
//        return state.setValue(LEFT, shouldConnect1).setValue(RIGHT,shouldConnect2);
//    }
//
//    protected boolean canConnectTo(BlockGetter level, BlockPos pos, Direction facing, BlockState oleState, boolean isRight) {
//        Direction direction = getLeft(facing);
//        BlockPos directionPos = pos.relative(isRight?direction.getOpposite():direction);
//        BlockState state = level.getBlockState(directionPos);
//        return state.is(this) && state.getValue(SofaBlock.FACING) == oleState.getValue(FACING);
//    }
//
//    protected Direction getLeft(Direction direction){
//        return direction.getCounterClockWise();
//    }
//
//    @Override
//    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
//        pBuilder.add(FACING,LEFT,RIGHT);
//    }

    public boolean propagatesSkylightDown(BlockState p_154824_, BlockGetter p_154825_, BlockPos p_154826_) {
        return true;
    }

    public int getLightBlock(BlockState p_154828_, BlockGetter p_154829_, BlockPos p_154830_) {
        return 0;
    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pSide);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LuminousLightSignBlockEntity(pPos,pState);
    }
}
