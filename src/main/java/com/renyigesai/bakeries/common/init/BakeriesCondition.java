package com.renyigesai.bakeries.common.init;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.conditions.ConfigCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class BakeriesCondition {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, BakeriesMod.MODID);
    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ConfigCondition>> CONFIG_CONDITION = CONDITION_CODECS.register("config", () -> ConfigCondition.CODEC);
}
