package com.renyigesai.bakeries;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesBlockEntities;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.init.BakeriesMenuTypes;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BakeriesMod implements ModInitializer {
    public static final String MODID = "bakeries";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        BakeriesBlocks.init();
        BakeriesBlockEntities.init();
        BakeriesMenuTypes.init();
        BakeriesItems.init();
        LOGGER.info("Bakeries Fabric bootstrap initialized.");
    }
}
