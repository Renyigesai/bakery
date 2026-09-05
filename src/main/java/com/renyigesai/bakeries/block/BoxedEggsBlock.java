package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.api.block.PileBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BoxedEggsBlock extends PileBlock {

    public static final VoxelShape BOX_1;
    public static final VoxelShape BOX_2;

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return box(3,0,3,13,state.getValue(integerProperty) > 1 ? 6 : 3,13);
    }

    @Override
    public int getMaxPile() {
        return 2;
    }

    static {
        BOX_1 = box(3,0,3,13,3,13);
        BOX_2 = box(3,0,3,13,6,13);
    }
}
