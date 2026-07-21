package com.renyigesai.bakeries.block.fermentation_box;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FermentationBoxBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public FermentationBoxBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FermentationBoxBlockEntity(blockPos,blockState);
    }

    @Override
    public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
        super.use(blockstate, world, pos, entity, hand, hit);
        if(!world.isClientSide()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            super.use(blockstate, world, pos, entity, hand, hit);
            if (blockEntity instanceof FermentationBoxBlockEntity boxBlock) {
                NetworkHooks.openScreen(((ServerPlayer) entity), boxBlock, pos);
                world.blockEvent(pos,blockstate.getBlock(),0,0);
                world.playSound(null,pos, SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS);
                return InteractionResult.CONSUME;
            }else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof FermentationBoxBlockEntity boxBlock){
            boxBlock.setNowPerfectTime(pLevel,pPos,boxBlock);
        }
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? createTickerHelper(pBlockEntityType, BakeriesBlocks.FERMENTATION_BOX_ENTITY.get(),
                FermentationBoxBlockEntity::clientTick) : createTickerHelper(pBlockEntityType, BakeriesBlocks.FERMENTATION_BOX_ENTITY.get(),
                FermentationBoxBlockEntity::tick);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FermentationBoxBlockEntity boxBlock) {
                boxBlock.drops(boxBlock);
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

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection();
        return this.defaultBlockState().setValue(FACING, direction);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING,POWERED);
    }

    @Override
    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof FermentationBoxBlockEntity fermentationBoxBlock){
            int size = 0;
            for (int i = 0; i < fermentationBoxBlock.getItems().getSlots(); i++) {
                if (!fermentationBoxBlock.getItems().getStackInSlot(i).isEmpty()){
                    size ++;
                }
            }
            return size;
        }
        return 0;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }
}
