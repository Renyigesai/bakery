package com.renyigesai.bakeries.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class PlayerLookBlockEvent {
    private final Player player;
    private final BlockPos blockPos;
    private final BlockState blockState;

    public PlayerLookBlockEvent(Player player, BlockPos blockPos, BlockState blockState) {
        this.player = player;
        this.blockPos = blockPos;
        this.blockState = blockState;
    }

    public Player getPlayer() {
        return player;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public BlockState getBlockState() {
        return blockState;
    }
}
