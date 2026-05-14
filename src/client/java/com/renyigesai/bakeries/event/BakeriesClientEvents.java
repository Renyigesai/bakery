package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.BakeriesMod;

public final class BakeriesClientEvents {
    private BakeriesClientEvents() {
    }

    public static void init() {
        BakeriesMod.LOGGER.info("BakeriesClientEvents initialized.");
    }
}
