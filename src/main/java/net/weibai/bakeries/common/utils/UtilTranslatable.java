package net.weibai.bakeries.common.utils;

import net.weibai.bakeries.BakeriesMod;


public class UtilTranslatable {
    public static String setKeyMapping(String key) {
        return "key." + BakeriesMod.MODID + "." + key;
    }
    public static String setCategory(String key) {
        return "category." + BakeriesMod.MODID + "." + key;
    }
    public static String setCreativeModeTabs(String key) {
        return "item_group." + BakeriesMod.MODID + "." + key;
    }
    public static String setRecipeContainer(String key) {
        return "recipe_container." + BakeriesMod.MODID + "." + key;
    }
    public static String setSounds(String key) {
        return "sound." + BakeriesMod.MODID + "." + key;
    }
}
