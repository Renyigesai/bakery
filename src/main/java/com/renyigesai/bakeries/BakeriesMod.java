package com.renyigesai.bakeries;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BakeriesMod implements ModInitializer {
    public static final String MODID = "bakeries";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        BakeriesBlocks.init();
        BakeriesItems.init();
        LOGGER.info("Bakeries Fabric bootstrap initialized.");
    }
}
