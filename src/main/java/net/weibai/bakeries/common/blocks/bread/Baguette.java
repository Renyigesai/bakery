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

public class Baguette extends BreadBlock {
    @Override
    public List<VoxelShape> createShapes(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        List<VoxelShape> shapes = new ArrayList<>();
        int value = state.getValue(PILE_4);
        Direction facing = state.getValue(FACING);

        if (value == 1) {
            shapes.add(rotateBox(1,0,4.7d,14,1.8d,7, facing));
        } else if (value == 2) {
            shapes.add(rotateBox(1,0,3,14,1.8d,10.5d, facing));
        } else if (value == 3) {
            shapes.add(rotateBox(1,0,2.2d,14,1.8d,12.3d, facing));
        } else if (value == 4) {
            shapes.add(rotateBox(1.3d,0,0.4d,13.7d,1.8d,15.3d, facing));
        }
        return shapes;
    }
}
