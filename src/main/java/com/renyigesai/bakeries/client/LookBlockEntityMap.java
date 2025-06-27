package com.renyigesai.bakeries.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LookBlockEntityMap {
    private static final Map<UUID, BlockEntity> blocks = new HashMap<>();

    public static Map<UUID, BlockEntity> getBlocks() {
        return blocks;
    }

    public static void setBlocks(Player player, BlockEntity entity) {
        if (player.level().isClientSide) {
            blocks.put(player.getUUID(), entity);
        }
    }
}
