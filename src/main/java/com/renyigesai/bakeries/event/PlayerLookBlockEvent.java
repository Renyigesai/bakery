package com.renyigesai.bakeries.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("unused")
public record PlayerLookBlockEvent(Player player, BlockPos blockPos, BlockState blockState) {
}
