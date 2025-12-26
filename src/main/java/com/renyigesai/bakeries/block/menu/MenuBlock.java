package com.renyigesai.bakeries.block.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MenuBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public MenuBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (state.getValue(FACING)) {
            default -> box(0, -8, 15, 16, 16, 16);//0, -8, 0, 16, 16, 1
            case NORTH -> box(0, -8, 0, 16, 16, 1);
            case EAST -> box(15, -8, 0, 16, 16, 16);//0, -8, 0, 1, 16, 16
            case WEST -> box(0, -8, 0, 1, 16, 16);
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MenuBlockEntity menuBlockEntity) {
            ItemStack handStack = pPlayer.getItemInHand(pHand);
            if (!handStack.isEmpty()) {
                menuBlockEntity.addItem(handStack.copy().split(1),level,pos,pState);
                playSound(level,pos,SoundEvents.ITEM_FRAME_ADD_ITEM);
                return InteractionResult.SUCCESS;
            }else if (!menuBlockEntity.inventory.getStackInSlot(0).isEmpty()){
                menuBlockEntity.deleteItem(level,pos,pState);
                level.sendBlockUpdated(pos,pState,pState,3);
                playSound(level,pos,SoundEvents.ITEM_FRAME_REMOVE_ITEM);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }
        return super.use(pState, level, pos, pPlayer, pHand, pHit);
    }

    private void playSound(Level level, BlockPos pos, SoundEvent soundEvent){
        if (level instanceof ServerLevel serverLevel){
            serverLevel.playSound(null,pos,soundEvent,SoundSource.BLOCKS);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
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
        return new MenuBlockEntity(pPos,pState);
    }
    //bypasses_invulnerability
}
