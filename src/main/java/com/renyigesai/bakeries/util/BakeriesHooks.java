package com.renyigesai.bakeries.util;

import com.renyigesai.bakeries.api.event.AnvilLandingEvent;
import com.renyigesai.bakeries.api.event.PlayerLookBlockEvent;
import com.renyigesai.bakeries.api.event.SnifferDropSeedEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;

public class BakeriesHooks {

    public static void onAnvilLanding(Entity entity, Level level){
        AnvilLandingEvent anvilLandingEvent = new AnvilLandingEvent(entity,level);
        MinecraftForge.EVENT_BUS.post(anvilLandingEvent);
    }

    public static void onPlayerLookBlock(LocalPlayer player, BlockPos pos, BlockState state){
        PlayerLookBlockEvent playerLookBlockEvent = new PlayerLookBlockEvent(player, pos,state);
        MinecraftForge.EVENT_BUS.post(playerLookBlockEvent);
    }

    public static void onSnifferDropSeed(Level level,BlockPos pos){
        SnifferDropSeedEvent snifferDropSeedEvent = new SnifferDropSeedEvent(level,pos);
        MinecraftForge.EVENT_BUS.post(snifferDropSeedEvent);
    }
}
