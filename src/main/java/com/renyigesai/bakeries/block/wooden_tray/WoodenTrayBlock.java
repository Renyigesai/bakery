package com.renyigesai.bakeries.block.wooden_tray;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WoodenTrayBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape BOX = box(1.0D,0.0D,1.0D,15.0D,1.0D,15.0D);
    public static final BooleanProperty RENDER_BLOCK = BooleanProperty.create("render_block");
    public WoodenTrayBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState().setValue(RENDER_BLOCK,false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BOX;
    }

    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof WoodenTrayBlockEntity woodenTrayBlockEntity) {
            ItemStack handStack = pPlayer.getItemInHand(pHand);
            if (handStack.isEmpty()){
                if (woodenTrayBlockEntity.canEmpty()){
                    Boolean value = pState.getValue(RENDER_BLOCK);
                    level.setBlock(pos,pState.setValue(RENDER_BLOCK,!value),3);
                    playSound(level,pos, SoundEvents.ITEM_FRAME_ADD_ITEM);
                    return InteractionResult.SUCCESS;
                }
                woodenTrayBlockEntity.takeOutItem(pPlayer);
                playSound(level,pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM);
                return InteractionResult.SUCCESS;
            }
            if (!handStack.isEmpty() && woodenTrayBlockEntity.addItem(handStack.copy().split(1))) {
                if (!pPlayer.isCreative()){
                    handStack.shrink(1);
                }
                playSound(level,pos, SoundEvents.ITEM_FRAME_ADD_ITEM);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }
        return super.use(pState, level, pos, pPlayer, pHand, pHit);
    }

    private void playSound(Level level, BlockPos pos, SoundEvent soundEvent){
        if (level instanceof ServerLevel serverLevel){
            serverLevel.playSound(null,pos,soundEvent, SoundSource.BLOCKS);
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WoodenTrayBlockEntity woodenTrayBlockEntity) {
                woodenTrayBlockEntity.drops(woodenTrayBlockEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(RENDER_BLOCK,FACING);
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING,direction.rotate(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WoodenTrayBlockEntity(pPos,pState);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("block.bakeries.wooden_tray.tips").withStyle(ChatFormatting.GRAY));
    }
}
