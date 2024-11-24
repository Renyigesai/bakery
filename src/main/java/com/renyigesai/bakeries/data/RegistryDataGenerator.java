package com.renyigesai.bakeries.data;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.data.features.BConfiguredFeatures;
import com.renyigesai.bakeries.data.features.BPlacedFeature;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, BConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, BPlacedFeature::bootstrap)
            ;


    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(BakeriesMod.MODID));
    }

}
