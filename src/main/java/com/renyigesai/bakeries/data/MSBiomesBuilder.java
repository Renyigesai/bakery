package com.renyigesai.bakeries.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import com.renyigesai.bakeries.BakeriesMod;


public class MSBiomesBuilder {
    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, BakeriesMod.rl(name));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {

    }
}
