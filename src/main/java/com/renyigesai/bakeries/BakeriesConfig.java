package com.renyigesai.bakeries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class BakeriesConfig {
    public enum TemperatureUnit {
        CELSIUS,
        FAHRENHEIT,
        KELVIN
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "bakeries.json";

    public static final boolean aprilFoolsDayEffect = true;
    public static final double eternalBaguetteDamageUp = 2.0D;
    public static final boolean fermentationGameplay = true;

    private static TemperatureUnit temperatureUnit = TemperatureUnit.CELSIUS;

    private BakeriesConfig() {
    }

    public static void init() {
        load();
        ConfigMapping.init();
    }

    public static TemperatureUnit getTemperatureUnit() {
        return temperatureUnit;
    }

    public static String formatFromCelsius(int celsius) {
        return switch (temperatureUnit) {
            case CELSIUS -> celsius + "\u00B0C";
            case FAHRENHEIT -> String.format(Locale.ROOT, "%.1f\u00B0F", celsius * 9.0D / 5.0D + 32.0D);
            case KELVIN -> String.format(Locale.ROOT, "%.2fK", celsius + 273.15D);
        };
    }

    private static void load() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
        JsonObject root = createDefaultConfigJson();

        try {
            if (Files.exists(path)) {
                try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                    JsonObject parsed = JsonParser.parseReader(reader).getAsJsonObject();
                    if (parsed.has("temperatureUnit")) {
                        String name = parsed.get("temperatureUnit").getAsString();
                        temperatureUnit = parseTemperatureUnit(name);
                    }
                    boolean changed = ensureInfoFields(parsed);
                    if (changed) {
                        Files.writeString(path, GSON.toJson(parsed), StandardCharsets.UTF_8);
                    }
                }
            } else {
                writeDefault(path, root);
                temperatureUnit = TemperatureUnit.CELSIUS;
            }
        } catch (Exception e) {
            BakeriesMod.LOGGER.warn("Failed to load bakeries config, using defaults.", e);
            temperatureUnit = TemperatureUnit.CELSIUS;
        }
    }

    private static TemperatureUnit parseTemperatureUnit(String raw) {
        if (raw == null) {
            return TemperatureUnit.CELSIUS;
        }
        try {
            return TemperatureUnit.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return TemperatureUnit.CELSIUS;
        }
    }

    private static void writeDefault(Path path, JsonObject root) throws IOException {
        Files.createDirectories(path.getParent());
        Files.writeString(path, GSON.toJson(root), StandardCharsets.UTF_8);
    }

    private static JsonObject createDefaultConfigJson() {
        JsonObject root = new JsonObject();
        root.addProperty("_comment", "Bakeries config file");
        root.addProperty("temperatureUnit", TemperatureUnit.CELSIUS.name());
        root.addProperty("_temperatureUnit_info", "Valid values: CELSIUS, FAHRENHEIT, KELVIN");
        root.addProperty("_temperatureUnit_default", "CELSIUS");
        root.addProperty("_temperatureUnit_note", "Displayed values are converted from internal Celsius.");
        return root;
    }

    private static boolean ensureInfoFields(JsonObject root) {
        boolean changed = false;
        if (!root.has("_comment")) {
            root.addProperty("_comment", "Bakeries config file");
            changed = true;
        }
        if (!root.has("_temperatureUnit_info")) {
            root.addProperty("_temperatureUnit_info", "Valid values: CELSIUS, FAHRENHEIT, KELVIN");
            changed = true;
        }
        if (!root.has("_temperatureUnit_default")) {
            root.addProperty("_temperatureUnit_default", "CELSIUS");
            changed = true;
        }
        if (!root.has("_temperatureUnit_note")) {
            root.addProperty("_temperatureUnit_note", "Displayed values are converted from internal Celsius.");
            changed = true;
        }
        return changed;
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
            register("temperatureUnit", BakeriesConfig::getTemperatureUnit);
        }
    }
}
