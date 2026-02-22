package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.api.block.PileBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ColdDrinkBlock extends PileBlock {

    private static final VoxelShape BOX = box(6.0, 0, 6.0, 10, 7.5, 10);
    private static final VoxelShape BOX_2 = box(4.0, 0, 4.0, 12, 7.5, 12);

    public ColdDrinkBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int value = pState.getValue(integerProperty);
        return value==1?BOX:BOX_2;
    }

    @Override
    public int getMaxPile() {
        return 2;
    }

}
