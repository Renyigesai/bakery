package com.renyigesai.bakeries.util;

import com.renyigesai.bakeries.api.event.AnvilLandingEvent;
import com.renyigesai.bakeries.api.event.SnifferDropSeedEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

public class BakeriesHooks {

    public static void onAnvilLanding(Entity entity, Level level){
        AnvilLandingEvent anvilLandingEvent = new AnvilLandingEvent(entity,level);
        MinecraftForge.EVENT_BUS.post(anvilLandingEvent);
    }

    public static void onSnifferDropSeed(Level level,BlockPos pos){
        SnifferDropSeedEvent snifferDropSeedEvent = new SnifferDropSeedEvent(level,pos);
        MinecraftForge.EVENT_BUS.post(snifferDropSeedEvent);
    }
}
