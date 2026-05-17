package com.renyigesai.bakeries.common.blocks.menu;

import com.renyigesai.bakeries.init.blocks.MachineBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class MenuBlock extends MachineBlocks.FacingMachineBlock {
    public MenuBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> box(0, -8, 0, 16, 16, 1);
            case EAST -> box(15, -8, 0, 16, 16, 16);
            case WEST -> box(0, -8, 0, 1, 16, 16);
            default -> box(0, -8, 15, 16, 16, 16);
        };
    }
}
