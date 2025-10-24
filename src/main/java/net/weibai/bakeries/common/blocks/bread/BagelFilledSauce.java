package net.weibai.bakeries.common.blocks.bread;

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

public class BagelFilledSauce extends BreadBlock {
    @Override
    public List<VoxelShape> createShapes(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        List<VoxelShape> shapes = new ArrayList<>();
        int value = state.getValue(PILE_4);
        Direction facing = state.getValue(FACING);

        if (value == 1) {
            shapes.add(rotateBox(5.2d,0,5.2d,5.6d,3,5.6d, facing));
        } else if (value == 2) {
            shapes.add(rotateBox(2,0,4.4d,12.9d,3,7.2d, facing));
        } else if (value == 3) {
            shapes.add(rotateBox(2,0,0.6d,12.9d,3,14.5d, facing));
        } else if (value == 4) {
            shapes.add(rotateBox(0.8d,0,0.5d,14.2d,3,14.6d, facing));
        }
        return shapes;
    }
}
