package com.renyigesai.bakeries;

import com.renyigesai.bakeries.init.*;
import com.renyigesai.bakeries.common.loot.BakeriesLootHooks;
import com.renyigesai.bakeries.network.Messages;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BakeriesMod implements ModInitializer {
    public static final String MODID = "bakeries";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        BakeriesConfig.init();
        BakeriesBlocks.init();
        BakeriesBlockEntities.init();
        BakeriesMenuTypes.init();
        BakeriesRecipeTypes.init();
        BakeriesItems.init();
        BakeriesLootHooks.init();
        Messages.init();
        LOGGER.info("Bakeries Fabric bootstrap initialized.");
    }
}
