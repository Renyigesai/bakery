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

public class BerryBagel extends BreadBlock {
//    @Override
//    public List<VoxelShape> createShapes(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
//        List<VoxelShape> shapes = new ArrayList<>();
//        int value = state.getValue(PILE_4);
//        Direction facing = state.getValue(FACING);
//
//        if (value == 1) {
//            shapes.add(rotateBox(5.5d,0,5.5d,5,2.3d,5, facing));
//        } else if (value == 2) {
//            shapes.add(rotateBox(1,0,4.6d,13,2.3d,6.8d, facing));
//        } else if (value == 3) {
//            shapes.add(rotateBox(1.2d,0,1,13.7d,2.3d,13.3d, facing));
//        } else if (value == 4) {
//            shapes.add(rotateBox(1.2d,0,0.9d,13.6d,2.3d,14.1d, facing));
//        }
//        return shapes;
//    }
}
