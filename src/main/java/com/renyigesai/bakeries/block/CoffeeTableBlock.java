package com.renyigesai.bakeries.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoffeeTableBlock extends HorizontalConnectBlock {

    private static final VoxelShape BOX_A = box(0,14,0,16,16,16);
    private static final VoxelShape BOX_B = box(1,0,1,15,14,15);

    private static final VoxelShape BOX = Shapes.or(BOX_A,BOX_B);
    public CoffeeTableBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BOX;
    }
}
