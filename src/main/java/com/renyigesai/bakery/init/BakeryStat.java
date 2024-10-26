package com.renyigesai.bakery.init;

import com.renyigesai.bakery.BakeryMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.StatType;

public class BakeryStat {
    public static final StatType<ResourceLocation> CUSTOM = makeRegistryStatType("custom", BuiltInRegistries.CUSTOM_STAT);
    public static final ResourceLocation INTERACT_OVEN = makeCustomStat("interact_oven", StatFormatter.DEFAULT);
    private static ResourceLocation makeCustomStat(String pKey, StatFormatter pFormatter) {
        ResourceLocation resourcelocation = BakeryMod.prefix(pKey);
        Registry.register(BuiltInRegistries.CUSTOM_STAT, pKey, resourcelocation);
        CUSTOM.get(resourcelocation, pFormatter);
        return resourcelocation;
    }
    private static <T> StatType<T> makeRegistryStatType(String pKey, Registry<T> pRegistry) {
        return Registry.register(BuiltInRegistries.STAT_TYPE, pKey, new StatType<>(pRegistry));
    }
}
