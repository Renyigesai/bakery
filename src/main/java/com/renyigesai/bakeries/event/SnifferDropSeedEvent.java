package com.renyigesai.bakeries.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public record SnifferDropSeedEvent(Level level, BlockPos blockPos) {
}
