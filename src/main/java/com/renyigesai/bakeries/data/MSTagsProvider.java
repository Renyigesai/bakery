package com.renyigesai.bakeries.data;

import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import com.renyigesai.bakeries.BakeriesMod;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class MSTagsProvider extends AbstractTagProvider {

    public MSTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BakeriesMod.MODID, existingFileHelper);
    }

    @Override
    protected Collection<? extends Holder<Block>> getAllBlocks() {
        return BakeriesBlocks.REGISTER.getEntries();
    }

    @Override
    protected void registerTags(HolderLookup.Provider registries) {
        addBlocks();
        addItems();
    }

    private void addItems() {
//        addToTag(MSTags.Items.TINY_STONES,
//                MSItems.TINY_STONE,
//                MSItems.TINY_ANDESITE,
//                MSItems.TINY_DIORITE,
//                MSItems.TINY_GRANITE
//        );
//        addToTag(Tags.Items.TOOLS_WRENCH,
//                MSItems.WRENCHES);
//        addToTag(Tags.Items.STRINGS,
//                MSItems.FLAX_STRING);
//        addToTag(MSTags.Items.SHARP_TINY_STONES,
//                MSItems.SHARP_TINY_STONE,
//                MSItems.SHARP_TINY_ANDESITE,
//                MSItems.SHARP_TINY_DIORITE,
//                MSItems.SHARP_TINY_GRANITE
//        );
//        addToTag(ItemTags.AXES,
//                MSItems.STONE_HAND_AXE
//        );
//        addToTag(ItemTags.SWORDS,
//                MSItems.TINY_STONE_KNIFE
//        );
//        addToTag(MSTags.Items.CLOSED_COMBUSTION,
//                MSItems.FLAX_STICK
//                );

    }

    private void addBlocks() {
//        addToTag(MSTags.Blocks.TINY_STONES,
//                MSBlocks.TINY_STONE_BLOCK,
//                MSBlocks.TINY_ANDESITE_BLOCK,
//                MSBlocks.TINY_DIORITE_BLOCK,
//                MSBlocks.TINY_GRANITE_BLOCK
//        );
//        addToHarvestTag(BlockTags.MINEABLE_WITH_PICKAXE,
//                MSBlocks.METEORITE_IRON_ORE,
//                MSBlocks.DEEPSLATE_METEORITE_IRON_ORE,
//                MSBlocks.RAW_METEORITE_IRON_BLOCK,
//                MSBlocks.MAGNETIC_IRON_ORE,
//                MSBlocks.DEEPSLATE_MAGNETIC_IRON_ORE,
//                MSBlocks.RAW_MAGNETIC_IRON_BLOCK,
//                MSBlocks.HEMATITE_IRON_ORE,
//                MSBlocks.DEEPSLATE_HEMATITE_IRON_ORE,
//                MSBlocks.RAW_HEMATITE_IRON_BLOCK,
//                MSBlocks.BITUMINOUS_COAL_ORE,
//                MSBlocks.DEEPSLATE_BITUMINOUS_COAL_ORE,
//                MSBlocks.BITUMINOUS_COAL_BLOCK,
//                MSBlocks.LIGNITE_BLOCK,
//                MSBlocks.LIGNITE_ORE,
//                MSBlocks.DEEPSLATE_LIGNITE_ORE
//        );
//        addToHarvestTag(MSTags.Blocks.MINEABLE_WITH_SCYTHE,
//                MSBlocks.FLAX_BLOCK
//
//        );
//        addToHarvestTag(BlockTags.MINEABLE_WITH_AXE,
//                MSBlocks.OAK_LOG_CAULDRON,
//                MSBlocks.DARK_OAK_LOG_CAULDRON,
//                MSBlocks.BIRCH_LOG_CAULDRON,
//                MSBlocks.JUNGLE_LOG_CAULDRON,
//                MSBlocks.SPRUCE_LOG_CAULDRON,
//                MSBlocks.ACACIA_LOG_CAULDRON,
//                MSBlocks.MANGROVE_LOG_CAULDRON,
//                MSBlocks.CHERRY_LOG_CAULDRON,
//                MSBlocks.CRIMSON_STEM_CAULDRON,
//                MSBlocks.WARPED_STEM_CAULDRON
//        );

//        addToHarvestTag(MSTags.Blocks.MINEABLE_WITH_TINY_STONE_1,
//                Blocks.DIRT.defaultBlockState().getBlockHolder());
//        addToHarvestTag(MSTags.Blocks.MINEABLE_WITH_TINY_STONE_1,
//                Blocks.TALL_GRASS.defaultBlockState().getBlockHolder(),
//                Blocks.GRASS_BLOCK.defaultBlockState().getBlockHolder());

//        addToTag(BlockTags.NEEDS_STONE_TOOL,
//                MSBlocks.MAGNETIC_IRON_ORE,
//                MSBlocks.DEEPSLATE_MAGNETIC_IRON_ORE,
//                MSBlocks.RAW_MAGNETIC_IRON_BLOCK,
//                MSBlocks.HEMATITE_IRON_ORE,
//                MSBlocks.DEEPSLATE_HEMATITE_IRON_ORE,
//                MSBlocks.RAW_HEMATITE_IRON_BLOCK,
//                MSBlocks.OAK_CAMPFIRE,
//                MSBlocks.SPRUCE_CAMPFIRE,
//                MSBlocks.BIRCH_CAMPFIRE,
//                MSBlocks.JUNGLE_CAMPFIRE,
//                MSBlocks.DARK_OAK_CAMPFIRE,
//                MSBlocks.ACACIA_CAMPFIRE,
//                MSBlocks.CRIMSON_STEM_CAMPFIRE,
//                MSBlocks.WARPED_STEM_CAMPFIRE,
//                MSBlocks.MANGROVE_CAMPFIRE,
//                MSBlocks.CHERRY_CAMPFIRE,
//                MSBlocks.BAMBOO_CAMPFIRE,
//                MSBlocks.SOUL_OAK_CAMPFIRE,
//                MSBlocks.SOUL_SPRUCE_CAMPFIRE,
//                MSBlocks.SOUL_BIRCH_CAMPFIRE,
//                MSBlocks.SOUL_JUNGLE_CAMPFIRE,
//                MSBlocks.SOUL_DARK_OAK_CAMPFIRE,
//                MSBlocks.SOUL_ACACIA_CAMPFIRE,
//                MSBlocks.SOUL_CRIMSON_STEM_CAMPFIRE,
//                MSBlocks.SOUL_WARPED_STEM_CAMPFIRE,
//                MSBlocks.SOUL_MANGROVE_CAMPFIRE,
//                MSBlocks.SOUL_CHERRY_CAMPFIRE,
//                MSBlocks.SOUL_BAMBOO_CAMPFIRE
//        );
//        addToTag(BlockTags.NEEDS_DIAMOND_TOOL,
//                MSBlocks.METEORITE_IRON_ORE,
//                MSBlocks.DEEPSLATE_METEORITE_IRON_ORE,
//                MSBlocks.RAW_METEORITE_IRON_BLOCK
//        );
//        addToTag(Tags.Blocks.ORES_IRON,
//                MSBlocks.METEORITE_IRON_ORE,
//                MSBlocks.MAGNETIC_IRON_ORE,
//                MSBlocks.HEMATITE_IRON_ORE
//        );
//        addToTag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE,
//                MSBlocks.DEEPSLATE_METEORITE_IRON_ORE,
//                MSBlocks.DEEPSLATE_MAGNETIC_IRON_ORE,
//                MSBlocks.DEEPSLATE_HEMATITE_IRON_ORE
//        );
//        addToTag(Tags.Blocks.STORAGE_BLOCKS_RAW_IRON,
//                MSBlocks.RAW_METEORITE_IRON_BLOCK,
//                MSBlocks.RAW_MAGNETIC_IRON_BLOCK,
//                MSBlocks.RAW_HEMATITE_IRON_BLOCK
//        );
//        addToTag(BlockTags.CAMPFIRES,
//                MSBlocks.OAK_CAMPFIRE,
//                MSBlocks.SPRUCE_CAMPFIRE,
//                MSBlocks.BIRCH_CAMPFIRE,
//                MSBlocks.JUNGLE_CAMPFIRE,
//                MSBlocks.DARK_OAK_CAMPFIRE,
//                MSBlocks.ACACIA_CAMPFIRE,
//                MSBlocks.CRIMSON_STEM_CAMPFIRE,
//                MSBlocks.WARPED_STEM_CAMPFIRE,
//                MSBlocks.MANGROVE_CAMPFIRE,
//                MSBlocks.CHERRY_CAMPFIRE,
//                MSBlocks.BAMBOO_CAMPFIRE,
//                MSBlocks.SOUL_OAK_CAMPFIRE,
//                MSBlocks.SOUL_SPRUCE_CAMPFIRE,
//                MSBlocks.SOUL_BIRCH_CAMPFIRE,
//                MSBlocks.SOUL_JUNGLE_CAMPFIRE,
//                MSBlocks.SOUL_DARK_OAK_CAMPFIRE,
//                MSBlocks.SOUL_ACACIA_CAMPFIRE,
//                MSBlocks.SOUL_CRIMSON_STEM_CAMPFIRE,
//                MSBlocks.SOUL_WARPED_STEM_CAMPFIRE,
//                MSBlocks.SOUL_MANGROVE_CAMPFIRE,
//                MSBlocks.SOUL_CHERRY_CAMPFIRE,
//                MSBlocks.SOUL_BAMBOO_CAMPFIRE
//                );


    }
}
