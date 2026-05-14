package com.renyigesai.bakeries.api.conditions;

public final class ConfigCondition {
    private ConfigCondition() {
    }

    public static boolean enabled(String key) {
        return true;
    }
}
