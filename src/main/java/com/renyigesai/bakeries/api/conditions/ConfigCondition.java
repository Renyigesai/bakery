package com.renyigesai.bakeries.api.conditions;

import com.renyigesai.bakeries.BakeriesConfig;

import java.util.function.Supplier;

public final class ConfigCondition {
    private ConfigCondition() {
    }

    public static boolean enabled(String key) {
        Supplier<?> supplier = BakeriesConfig.ConfigMapping.getValue(key);
        if (supplier == null) {
            return true;
        }
        Object value = supplier.get();
        if (value instanceof Boolean bool) {
            return bool;
        }
        if (value instanceof Number number) {
            return number.doubleValue() > 0.0D;
        }
        return value != null;
    }
}
