package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.api.block.PileBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ColdDrinkBlock extends PileBlock {

    private static final VoxelShape BOX = box(6.0, 0, 6.0, 10, 9.5, 10);
    private static final VoxelShape BOX_S = Shapes.or(box(9, 0, 5, 13, 9.5, 9), box(3, 0, 7, 7, 9.5, 11));
    private static final VoxelShape BOX_N = Shapes.or(box(3, 0, 7, 7, 9.5, 11), box(9, 0, 5, 13, 9.5, 9));
    private static final VoxelShape BOX_E = Shapes.or(box(5, 0, 3, 9, 9.5, 7), box(7, 0, 9, 11, 9.5, 13));
    private static final VoxelShape BOX_W = Shapes.or(box(7, 0, 9, 11, 9.5, 13), box(5, 0, 3, 9, 9.5, 7));

    public ColdDrinkBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int pile = pState.getValue(integerProperty);
        if (pile == 1){
            return BOX;
        }
        return switch (pState.getValue(FACING)) {
            default -> BOX_S;
            case NORTH -> BOX_N;
            case EAST -> BOX_E;
            case WEST -> BOX_W;
        };
    }

    @Override
    public int getMaxPile() {
        return 2;
    }

}
