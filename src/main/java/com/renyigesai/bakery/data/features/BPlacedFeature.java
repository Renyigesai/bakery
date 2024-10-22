package com.renyigesai.bakery.data.features;

import com.renyigesai.bakery.BakeryMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class BPlacedFeature {
//    public static final ResourceKey<PlacedFeature> ORE_COULD_PATTERNED_IRON_UPPER = createKey("could_patterned_iron_ore_upper");
//    public static final ResourceKey<PlacedFeature> ORE_DARK_IRON_SMALL = createKey("dark_iron_ore_small");
    public static void bootstrap(BootstapContext<PlacedFeature> pContext) {
//        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = pContext.lookup(Registries.CONFIGURED_FEATURE);
//        Holder<ConfiguredFeature<?, ?>> holder = holdergetter.getOrThrow(MSConfiguredFeatures.ORE_COULD_PATTERNED_IRON);
//        Holder<ConfiguredFeature<?, ?>> holder1 = holdergetter.getOrThrow(MSConfiguredFeatures.ORE_COULD_PATTERNED_IRON_SMALL);
//        Holder<ConfiguredFeature<?, ?>> holder2 = holdergetter.getOrThrow(MSConfiguredFeatures.ORE_DARK_IRON);
//        Holder<ConfiguredFeature<?, ?>> holder3 = holdergetter.getOrThrow(MSConfiguredFeatures.ORE_DARK_IRON_SMALL);
//
//        PlacementUtils.register(pContext, ORE_COULD_PATTERNED_IRON_UPPER, holder,
//                commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));
//        PlacementUtils.register(pContext, ORE_COULD_PATTERNED_IRON_MIDDLE, holder,
//                commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
//        PlacementUtils.register(pContext, ORE_COULD_PATTERNED_IRON_SMALL, holder1,
//                commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
//
//        PlacementUtils.register(pContext, ORE_DARK_IRON_UPPER, holder2, commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));
//        PlacementUtils.register(pContext, ORE_DARK_IRON_MIDDLE, holder2, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
//        PlacementUtils.register(pContext, ORE_DARK_IRON_SMALL, holder3, commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));






    }
    private static List<PlacementModifier> orePlacement(PlacementModifier pCountPlacement, PlacementModifier pHeightRange) {
        return List.of(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome());
    }
    private static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }
    private static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }
    public static ResourceKey<PlacedFeature> createKey(String pKey) {
        return ResourceKey.create(Registries.PLACED_FEATURE, BakeryMod.prefix(pKey));
    }
}
