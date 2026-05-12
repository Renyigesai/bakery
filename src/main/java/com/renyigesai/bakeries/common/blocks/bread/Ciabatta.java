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

public class Ciabatta extends BreadBlock {
//    @Override
//    public List<VoxelShape> createShapes(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
//        List<VoxelShape> shapes = new ArrayList<>();
//        int value = state.getValue(PILE_4);
//        Direction facing = state.getValue(FACING); // 获取当前朝向
//
//        if (value == 1) {
//            shapes.add(rotateBox(5,0,6,6,2.5d,3, facing));
//        } else if (value == 2) {
//            shapes.add(rotateBox(5,0,4,6,2.5d,8, facing));
//        } else if (value == 3) {
//            shapes.add(rotateBox(3,0,4,10,2.5d,8, facing));
//        } else if (value == 4) {
//            shapes.add(rotateBox(1.8d,0,1.6d,5.2d,2.5d,10.4d, facing));
//            shapes.add(rotateBox(7,0,4,6,2.5d,9, facing));
//            shapes.add(rotateBox(1.8d,2.5d,9,6.4d,2.5d,3, facing));
//        }
//        return shapes;
//    }
}
