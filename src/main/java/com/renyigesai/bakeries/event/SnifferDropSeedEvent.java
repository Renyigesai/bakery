package com.renyigesai.bakeries.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class SnifferDropSeedEvent {
    private final Level level;
    private final BlockPos blockPos;

    public SnifferDropSeedEvent(Level level, BlockPos blockPos) {
        this.level = level;
        this.blockPos = blockPos;
    }

    public Level getLevel() {
        return level;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}
