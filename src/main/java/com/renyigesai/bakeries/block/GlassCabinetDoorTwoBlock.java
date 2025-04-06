package com.renyigesai.bakeries.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlassCabinetDoorTwoBlock extends HorizontalDirectionalBlock {

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public GlassCabinetDoorTwoBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState().setValue(OPEN,false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (!state.getValue(OPEN)){
            return switch (state.getValue(FACING)) {
                default -> box(0, 0, 13, 16, 16, 16);
                case NORTH -> box(0, 0, 0, 16, 16, 3);
                case EAST -> box(13, 0, 0, 16, 16, 16);
                case WEST -> box(0, 0, 0, 3, 16, 16);
            };
        }
        return switch (state.getValue(FACING)) {
            default -> box(14, 0, 13, 16, 16, 16);
            case NORTH -> box(0, 0, 0, 2, 16, 3);
            case EAST -> box(13, 0, 0, 16, 16, 2);
            case WEST -> box(0, 0, 14, 3, 16, 16);
        };

    }

    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pState.getValue(OPEN)){
            level.setBlock(pos,pState.setValue(OPEN,false),3);
            level.playSound(null, pos, SoundEvents.IRON_DOOR_CLOSE, SoundSource.PLAYERS, 0.8F, 0.8F);
        }else {
            level.setBlock(pos,pState.setValue(OPEN,true),3);
            level.playSound(null, pos, SoundEvents.IRON_DOOR_OPEN, SoundSource.PLAYERS, 0.8F, 0.8F);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(OPEN,FACING);
    }
}
