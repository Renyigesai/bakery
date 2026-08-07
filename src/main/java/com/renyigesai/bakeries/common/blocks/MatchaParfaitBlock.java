package com.renyigesai.bakeries.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MatchaParfaitBlock extends DrinkBlock{
    private static final VoxelShape BOX;
    private static final VoxelShape BOX_2;
    public MatchaParfaitBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.1F,0.1F));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int value = pState.getValue(PILE);
        return value == 1 ? BOX : BOX_2;
    }

    static {
        BOX = box(6.0, 0, 6.0, 10, 12, 10);
        BOX_2 = box(3.0, 0, 3.0, 13, 12, 13);
    }
}
