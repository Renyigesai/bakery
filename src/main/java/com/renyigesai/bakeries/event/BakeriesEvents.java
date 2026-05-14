package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.BakeriesMod;

public final class BakeriesEvents {
    private BakeriesEvents() {
    }

    public static void init() {
        BakeriesMod.LOGGER.info("BakeriesEvents initialized.");
    }
}
