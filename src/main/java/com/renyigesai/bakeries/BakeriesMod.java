package com.renyigesai.bakeries;

import com.renyigesai.bakeries.init.*;
import com.renyigesai.bakeries.common.loot.BakeriesLootHooks;
import com.renyigesai.bakeries.network.Messages;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

@SuppressWarnings("unused")
public final class BakeriesMod implements ModInitializer {
    public static final String MODID = "bakeries";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static int floatingTemperature = new Random().nextInt(-5, 10);
    public static int floatingTemperatureRevision;
    private static long floatingTemperatureDay = Long.MIN_VALUE;

    public static void refreshFloatingTemperature(long day) {
        if (floatingTemperatureDay == day) {
            return;
        }
        floatingTemperatureDay = day;
        floatingTemperature = new Random().nextInt(-5, 10);
        floatingTemperatureRevision++;
    }

    public static void forceRefreshFloatingTemperature(long day) {
        floatingTemperatureDay = day;
        floatingTemperature = new Random().nextInt(-5, 10);
        floatingTemperatureRevision++;
    }

    @Override
    public void onInitialize() {
        BakeriesConfig.init();
        BakeriesBlocks.init();
        BakeriesBlockEntities.init();
        BakeriesMenuTypes.init();
        BakeriesRecipeTypes.init();
        BakeriesMobEffects.init();
        BakeriesItems.init();
        BakeriesLootHooks.init();
        Messages.init();
        BakeriesCommands.init();
        LOGGER.info("Bakeries Fabric bootstrap initialized.");
    }
}
