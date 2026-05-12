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

public class Croissant extends BreadBlock {
//    @Override
//    public List<VoxelShape> createShapes(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
//        List<VoxelShape> shapes = new ArrayList<>();
//        int value = state.getValue(PILE_4);
//        Direction facing = state.getValue(FACING); // 获取当前朝向
//
//        if (value == 1) {
//            shapes.add(rotateBox(2,0,7,6,3,4, facing));
//            shapes.add(rotateBox(7.3d,0,4.9d,6,3,4, facing));
//            shapes.add(rotateBox(5.3d,0,5.7d,4.8d,4,4.6d, facing));
//        } else if (value == 2) {
//            shapes.add(rotateBox(1.5d,0,3,13,4,11, facing));
//        } else if (value == 3) {
//            shapes.add(rotateBox(0.2d,0,0.2d,15.1d,4,15.6d, facing));
//        } else if (value == 4) {
//            shapes.add(rotateBox(0.2d,0,0.2d,15.2d,4,15.6d, facing));
//            shapes.add(rotateBox(2.1d,4,5.3d,2.9d,3,3.8d, facing));
//            shapes.add(rotateBox(5,4,5.3d,5.3d,4,6.2d, facing));
//            shapes.add(rotateBox(10.3d,4,7.5d,2.9d,3,4, facing));
//        }
//        return shapes;
//    }
}
