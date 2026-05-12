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

public class SaltCroissant extends BreadBlock {
//    @Override
//    public List<VoxelShape> createShapes(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
//        List<VoxelShape> shapes = new ArrayList<>();
//        int value = state.getValue(PILE_4);
//        Direction facing = state.getValue(FACING); // 获取当前朝向
//
//        if (value == 1) {
//            shapes.add(rotateBox(3.5d,0,6.2d,9,3.1d,3.1d, facing));
//        } else if (value == 2) {
//            shapes.add(rotateBox(3.5d,0,4.2d,9,3.1d,8.1d, facing));
//        } else if (value == 3) {
//            shapes.add(rotateBox(3.5d,0,2.2d,9,3.1d,11.6d, facing));
//        } else if (value == 4) {
//            shapes.add(rotateBox(2.5d,0,0.4,12,3.1d,15.2d, facing));
//        }
//        return shapes;
//    }
}
