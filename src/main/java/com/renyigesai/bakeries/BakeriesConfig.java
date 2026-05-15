package com.renyigesai.bakeries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class BakeriesConfig {
    public static final boolean aprilFoolsDayEffect = true;
    public static final double eternalBaguetteDamageUp = 2.0D;
    public static final boolean fermentationGameplay = true;

    private BakeriesConfig() {
    }

    public static final class ConfigMapping {
        private static final Map<String, Supplier<?>> MAP = new HashMap<>();

        static {
            init();
        }

        private ConfigMapping() {
        }

        public static void register(String key, Supplier<?> supplier) {
            MAP.put(key, supplier);
        }

        public static Supplier<?> getValue(String key) {
            return MAP.get(key);
        }

        public static Map<String, Supplier<?>> view() {
            return MAP;
        }

        public static void init() {
            MAP.clear();
            register("aprilFoolsDayEffect", () -> aprilFoolsDayEffect);
            register("eternalBaguetteDamageUp", () -> eternalBaguetteDamageUp);
            register("fermentationGameplay", () -> fermentationGameplay);
        }
    }
}
