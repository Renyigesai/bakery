package com.renyigesai.bakeries.common.blocks.bread;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.weibai.rcglib.blocks.BreadBlock;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BaguetteWithFilling extends BreadBlock {
    @Override
    public List<VoxelShape> createShapes(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        List<VoxelShape> shapes = new ArrayList<>();
        int value = state.getValue(PILE_4);
        Direction facing = state.getValue(FACING);

        if (value == 1) {
            shapes.add(rotateBox(2.3d,0,4.5d,11.6d,3.6d,5.5d, facing));
        } else if (value == 2) {
            shapes.add(rotateBox(0,0,2,16,3.6d,11.5d, facing));
        } else if (value == 3) {
            shapes.add(rotateBox(0,0,0,16,3.6d,16, facing));
        } else if (value == 4) {
            shapes.add(rotateBox(0,0,0,16,3.6d,16, facing));
        }
        return shapes;
    }
}
