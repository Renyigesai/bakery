package com.renyigesai.bakeries.common.blocks.sofa;

import com.renyigesai.bakeries.common.blocks.HorizontalConnectBlock;
import com.renyigesai.bakeries.common.init.BakeriesEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SofaBlock extends HorizontalConnectBlock {

    private final Color color;

    public SofaBlock(Properties pProperties, Color color) {
        super(pProperties);
        this.color = color;
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
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }
        sit(level,pos,player);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected boolean canConnectTo(BlockGetter level, BlockPos pos, Direction facing, BlockState oleState, boolean isRight) {
        Direction direction = getLeft(facing);
        BlockPos directionPos = pos.relative(isRight?direction.getOpposite():direction);
        BlockState state = level.getBlockState(directionPos);
        return state.is(BlockTags.create(ResourceLocation.fromNamespaceAndPath("bakeries" ,"sofa"))) && state.getValue(SofaBlock.FACING) == oleState.getValue(FACING);
    }

    private void sit(Level world, BlockPos pos, Player pPlayer){
        if (world.isClientSide)
            return;
        SofaEntity seat = new SofaEntity(BakeriesEntityTypes.SOFA.get(), world);
        seat.setPos(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f);
        world.addFreshEntity(seat);
        pPlayer.startRiding(seat, true);
    }

    public Color getColor() {
        return color;
    }

    public enum Color {
        RED("red"),
        LIGHT_GRAY("light_gray"),
        WHITE("white");
        String color;

        Color(String color) {
            this.color = color;
        }

        public String getColorKey() {
            return color;
        }
    }
}
