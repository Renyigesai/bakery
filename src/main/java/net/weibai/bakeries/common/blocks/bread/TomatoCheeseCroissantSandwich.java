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

public class TomatoCheeseCroissantSandwich extends BreadBlock {
    @Override
    public List<VoxelShape> createShapes(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        List<VoxelShape> shapes = new ArrayList<>();
        int value = state.getValue(PILE_4);
        Direction facing = state.getValue(FACING); // 获取当前朝向

        if (value == 1) {
            shapes.add(rotateBox(3.9d,0,5.9d,8.5d,3.6d,4.3d, facing));
        } else if (value == 2) {
            shapes.add(rotateBox(1,0,4,14,3.6d,8, facing));
        } else if (value == 3) {
            shapes.add(rotateBox(1,0,2,14,3.6d,11.6d, facing));
        } else if (value == 4) {
            shapes.add(rotateBox(1,0,1,14,3.6d,14, facing));
        }
        return shapes;
    }
}
