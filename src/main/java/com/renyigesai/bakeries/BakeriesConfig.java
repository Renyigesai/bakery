package com.renyigesai.bakeries;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber(modid = BakeriesMod.MODID)
public class BakeriesConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue APRIL_FOOLS_DAY_EFFECT = BUILDER.comment("After disabling this option, there will be no April Fool's Day effect.").define("aprilFoolsDayEffect", true);
    private static final ModConfigSpec.DoubleValue ETERNAL_BAGUETTE_DAMAGE_UP = BUILDER.comment("Enjoy effect increases the damage dealt by Eternal Baguette").defineInRange("eternalBaguetteDamageUp", 2d, 0d, Double.MAX_VALUE);
    private static final ModConfigSpec.BooleanValue FERMENTATION_GAMEPLAY = BUILDER.comment("After being turned off, the Cocoa Manin effect will not remove the invincibility frame of the creatures").define("fermentationGameplay", true);
    private static final ModConfigSpec.BooleanValue PROVIDE_TUTORIAL_BOOKS = BUILDER.comment("A tutorial book is provided when players enter the world for the first time").define("provideTutorialBooks", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean provideTutorialBooks;
    public static boolean aprilFoolsDayEffect;
    public static double eternalBaguetteDamageUp;
    public static boolean fermentationGameplay;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        aprilFoolsDayEffect = APRIL_FOOLS_DAY_EFFECT.get();
        eternalBaguetteDamageUp = ETERNAL_BAGUETTE_DAMAGE_UP.get();
        fermentationGameplay = FERMENTATION_GAMEPLAY.get();
        provideTutorialBooks = PROVIDE_TUTORIAL_BOOKS.get();
    }

    static {

    }

    public static class ConfigMapping {
        public static Map<String, Supplier<?>> map = new HashMap<>();

        public static void register(String key, Supplier<?> object) {
            map.put(key, object);
        }

        public static Supplier<?> getValue(String key) {
            return map.get(key);
        }

        public static void init(){
//            register("provideTutorialBooks",PROVIDE_TUTORIAL_BOOKS);
//            register("cocoaManinDamageEffect",COCOA_MANIN_DAMAGE_EFFECT);
            register("aprilFoolsDayEffect",APRIL_FOOLS_DAY_EFFECT);
//            register("toasterDoubleSpeed",TOASTER_DOUBLE_SPEED);
            register("eternalBaguetteDamageUp",ETERNAL_BAGUETTE_DAMAGE_UP);
            register("fermentationGameplay",FERMENTATION_GAMEPLAY);
        }
    }
}
