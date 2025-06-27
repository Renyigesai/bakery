package com.renyigesai.bakeries.block.cake;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CakeBaseBlock extends BCakeBlock {
    public CakeBaseBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE).lightLevel((l) -> 1));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
    }

    @Override
    public float getSaturationLevelModifier() {
        return 0.4f;
    }

    @Override
    public int getFoodLevelModifier() {
        return 5;
    }
}
