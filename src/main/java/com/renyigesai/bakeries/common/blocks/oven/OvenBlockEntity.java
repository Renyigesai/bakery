package com.renyigesai.bakeries.common.blocks.oven;

import com.renyigesai.bakeries.block.entity.MachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class OvenBlockEntity extends MachineBlockEntity {
    public OvenBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
