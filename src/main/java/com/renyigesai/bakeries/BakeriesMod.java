package com.renyigesai.bakeries;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesBlockEntities;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.init.BakeriesMenuTypes;
import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.common.capabilities.BakeriesCapabilities;
import com.renyigesai.bakeries.common.init.BakeriesCondition;
import com.renyigesai.bakeries.common.init.BakeriesMobEffects;
import com.renyigesai.bakeries.common.init.BakeriesTags;
import com.renyigesai.bakeries.common.init.BakeriesVillagerInit;
import com.renyigesai.bakeries.common.network.Messages;
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
        BakeriesRecipeTypes.init();
        BakeriesItems.init();
        BakeriesCapabilities.init();
        BakeriesCondition.init();
        BakeriesMobEffects.init();
        BakeriesTags.init();
        BakeriesVillagerInit.init();
        Messages.init();
        LOGGER.info("Bakeries Fabric bootstrap initialized.");
    }
}
