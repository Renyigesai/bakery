package com.renyigesai.bakeries.utils;

import net.minecraft.world.level.Level;

public final class WorldUtils {
    private WorldUtils() {
    }

    public static boolean isServer(Level level) {
        return level != null && !level.isClientSide;
    }
}
