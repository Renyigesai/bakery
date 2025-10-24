package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.api.block.PileBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MatchaParfaitBlock extends ColdDrinkBlock {

    private static final VoxelShape BOX = box(4.0, 0, 4.0, 12, 7.5, 12);
    private static final VoxelShape BOX_2 = box(4.0, 0, 4.0, 12, 7.5, 12);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        int value = pState.getValue(integerProperty);
        return value==1?BOX:BOX_2;
    }
    public MatchaParfaitBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(0.1F,0.1F));
    }
}
