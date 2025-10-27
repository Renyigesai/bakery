package com.renyigesai.bakeries.data;


import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import com.renyigesai.bakeries.BakeriesMod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
//            .add(Registries.DAMAGE_TYPE, MSModDamageTypes::bootstrap)
//            .add(Registries.BIOME, MSBiomesBuilder::bootstrap)
//            .add(Registries.CONFIGURED_FEATURE, MSConfiguredFeatures::bootstrap)
//            .add(Registries.PLACED_FEATURE, MSPlacedFeature::bootstrap)
            ;


    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(BakeriesMod.MODID));
    }

}
