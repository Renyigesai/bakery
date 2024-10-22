package com.renyigesai.bakery.data;


import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.data.biome.BBiomesBuilder;
import com.renyigesai.bakery.data.damage.BModDamageTypes;
import com.renyigesai.bakery.data.features.BConfiguredFeatures;
import com.renyigesai.bakery.data.features.BPlacedFeature;
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
            .add(Registries.DAMAGE_TYPE, BModDamageTypes::bootstrap)
            .add(Registries.BIOME, BBiomesBuilder::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, BConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, BPlacedFeature::bootstrap)
            ;


    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(BakeryMod.MODID));
    }

}
