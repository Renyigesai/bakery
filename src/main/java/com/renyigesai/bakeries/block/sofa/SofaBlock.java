package com.renyigesai.bakeries.block.sofa;

import com.renyigesai.bakeries.block.HorizontalConnectBlock;
import com.renyigesai.bakeries.init.BakeriesEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SofaBlock extends HorizontalConnectBlock {
    public SofaBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            default -> box(0, 0, 1, 16, 12, 16);
            case NORTH -> box(0, 0, 0, 16, 12, 15);
            case EAST -> box(1, 0, 0, 16, 12, 16);
            case WEST -> box(0, 0, 0, 15, 12, 16);
        };
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player player, InteractionHand pHand, BlockHitResult pHit) {
        if (player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }
        sit(pLevel,pPos,player);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected boolean canConnectTo(BlockGetter level, BlockPos pos, Direction facing, BlockState oleState, boolean isRight) {
        Direction direction = getLeft(facing);
        BlockPos directionPos = pos.relative(isRight?direction.getOpposite():direction);
        BlockState state = level.getBlockState(directionPos);
        return state.is(BlockTags.create(new ResourceLocation("bakeries","sofa"))) && state.getValue(SofaBlock.FACING) == oleState.getValue(FACING);
    }

    private void sit(Level world, BlockPos pos, Player pPlayer){
        if (world.isClientSide)
            return;
        SofaEntity seat = new SofaEntity(BakeriesEntityTypes.SOFA.get(), world);
        seat.setPos(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f);
        world.addFreshEntity(seat);
        pPlayer.startRiding(seat, true);
    }
}
