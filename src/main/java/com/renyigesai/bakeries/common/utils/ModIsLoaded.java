package com.renyigesai.bakeries.common.utils;

import net.fabricmc.loader.api.FabricLoader;

public final class ModIsLoaded {
    private ModIsLoaded() {
    }

    public static boolean isLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
