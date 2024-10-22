package com.renyigesai.bakery.data.biome;

import com.renyigesai.bakery.BakeryMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;


public class BBiomesBuilder {
    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, BakeryMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {

    }
}
