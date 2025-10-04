package net.weibai.bakeries.data;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.weibai.bakeries.BakeriesMod;

import java.util.List;

public class MSPlacedFeature {
    public static final ResourceKey<PlacedFeature> ORE_COULD_PATTERNED_IRON_UPPER = createKey("could_patterned_iron_ore_upper");
    public static final ResourceKey<PlacedFeature> ORE_COULD_PATTERNED_IRON_MIDDLE = createKey("could_patterned_iron_ore_middle");
    public static final ResourceKey<PlacedFeature> ORE_COULD_PATTERNED_IRON_SMALL = createKey("could_patterned_iron_ore_small");
    public static final ResourceKey<PlacedFeature> ORE_DARK_IRON_UPPER = createKey("dark_iron_ore_upper");
    public static final ResourceKey<PlacedFeature> ORE_DARK_IRON_MIDDLE = createKey("dark_iron_ore_middle");
    public static final ResourceKey<PlacedFeature> ORE_DARK_IRON_SMALL = createKey("dark_iron_ore_small");
    public static void bootstrap(BootstrapContext<PlacedFeature> pContext) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = pContext.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> holder = holdergetter.getOrThrow(MSConfiguredFeatures.ORE_COULD_PATTERNED_IRON);
        Holder<ConfiguredFeature<?, ?>> holder1 = holdergetter.getOrThrow(MSConfiguredFeatures.ORE_COULD_PATTERNED_IRON_SMALL);
        Holder<ConfiguredFeature<?, ?>> holder2 = holdergetter.getOrThrow(MSConfiguredFeatures.ORE_DARK_IRON);
        Holder<ConfiguredFeature<?, ?>> holder3 = holdergetter.getOrThrow(MSConfiguredFeatures.ORE_DARK_IRON_SMALL);

        PlacementUtils.register(pContext, ORE_COULD_PATTERNED_IRON_UPPER, holder,
                commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));
        PlacementUtils.register(pContext, ORE_COULD_PATTERNED_IRON_MIDDLE, holder,
                commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        PlacementUtils.register(pContext, ORE_COULD_PATTERNED_IRON_SMALL, holder1,
                commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));

        PlacementUtils.register(pContext, ORE_DARK_IRON_UPPER, holder2, commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));
        PlacementUtils.register(pContext, ORE_DARK_IRON_MIDDLE, holder2, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        PlacementUtils.register(pContext, ORE_DARK_IRON_SMALL, holder3, commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));






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
        return ResourceKey.create(Registries.PLACED_FEATURE, BakeriesMod.rl(pKey));
    }
}
