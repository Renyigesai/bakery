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

public class RoundBread extends BreadBlock {
//    @Override
//    public List<VoxelShape> createShapes(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
//        List<VoxelShape> shapes = new ArrayList<>();
//        int value = state.getValue(PILE_4);
//        Direction facing = state.getValue(FACING); // 获取当前朝向
//
//        if (value == 1) {
//            shapes.add(rotateBox(6, 0, 6, 4, 3, 4, facing));
//        } else if (value == 2) {
//            shapes.add(rotateBox(3,0,5,10,3,6, facing));
//        } else if (value == 3) {
//            shapes.add(rotateBox(3,0,2,10,3,12, facing));
//        } else if (value == 4) {
//            shapes.add(rotateBox(3,0,3,10,3,11, facing));
//        }
//        return shapes;
//    }
}
