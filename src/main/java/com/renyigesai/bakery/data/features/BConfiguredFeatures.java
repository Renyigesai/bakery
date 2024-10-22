package com.renyigesai.bakery.data.features;

import com.renyigesai.bakery.BakeryMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class BConfiguredFeatures {
//    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COULD_PATTERNED_IRON = createKey("could_patterned_iron_ore");
//    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COULD_PATTERNED_IRON_SMALL = createKey("could_patterned_iron_ore_small");
//    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DARK_IRON = createKey("dark_iron_ore");
//    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DARK_IRON_SMALL = createKey("dark_iron_ore_small");


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> pContext) {
        RuleTest ruletest = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        RuleTest ruletest1 = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest ruletest2 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest ruletest3 = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest ruletest4 = new TagMatchTest(BlockTags.BASE_STONE_NETHER);
//        List<OreConfiguration.TargetBlockState> list = List.of(
//                OreConfiguration.target(ruletest1, MSModBlocks.CLOUD_PATTERNED_IRON_ORE.get().defaultBlockState()),
//                OreConfiguration.target(ruletest2,MSModBlocks.DEEPSLATE_CLOUD_PATTERNED_IRON_ORE.get().defaultBlockState()));
//        List<OreConfiguration.TargetBlockState> list1 = List.of(
//                OreConfiguration.target(ruletest1, MSModBlocks.DARK_IRON_ORE.get().defaultBlockState()),
//                OreConfiguration.target(ruletest2,MSModBlocks.DEEPSLATE_DARK_IRON_ORE.get().defaultBlockState()));


//        FeatureUtils.register(pContext, ORE_COULD_PATTERNED_IRON, Feature.ORE, new OreConfiguration(list, 9));
//        FeatureUtils.register(pContext, ORE_COULD_PATTERNED_IRON_SMALL, Feature.ORE, new OreConfiguration(list, 4));
//        FeatureUtils.register(pContext, ORE_DARK_IRON, Feature.ORE, new OreConfiguration(list1, 9));
//        FeatureUtils.register(pContext, ORE_DARK_IRON_SMALL, Feature.ORE, new OreConfiguration(list1, 4));

    }
    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String pName) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, BakeryMod.prefix(pName));
    }
}
