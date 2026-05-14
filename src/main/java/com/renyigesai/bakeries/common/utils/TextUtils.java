package com.renyigesai.bakeries.common.utils;

import net.minecraft.network.chat.Component;

public final class TextUtils {
    private TextUtils() {
    }

    public static Component translatable(String key) {
        return Component.translatable(key);
    }
}
