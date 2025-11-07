package com.renyigesai.bakeries.data;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
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
        /*NeoForge±Í«©*/
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","salt")), BakeriesItems.SALT);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","foods/dough")), BakeriesItems.SWEET_DOUGH);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","foods/dough")), BakeriesItems.SALTED_DOUGH);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","foods/dough")), BakeriesItems.WHOLE_WHEAT_DOUGH);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","foods/milk")), BakeriesItems.BOTTLE_MILK);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","flour")), BakeriesItems.WHOLE_WHEAT_FLOUR);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","flour")), BakeriesItems.FLOUR);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","matcha")), BakeriesItems.MATCHA_POWDER);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","butter")), BakeriesItems.BUTTER_CUBE);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","cheese")), BakeriesItems.CHEESE_CUBE);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","eggs")), BakeriesItems.WHOLE_EGG);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","cream")), BakeriesItems.FOAMED_CREAM);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","coffee_grounds")), BakeriesItems.COCOA_POWDER);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","crops/tomato")), BakeriesItems.TOMATO);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","flour/wheat")), BakeriesItems.WHOLE_WHEAT_FLOUR);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","tools/knife")), BakeriesItems.BREAD_KNIFE);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","tools")), BakeriesItems.BREAD_KNIFE);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","vegetables/tomato")), BakeriesItems.TOMATO);
        /*∫Ê±∫∑ª±Í«©*/
    }

    private void addBlocks() {
        /*Minecraft±Í«©*/
        addToHarvestTag(BlockTags.MINEABLE_WITH_PICKAXE, BakeriesBlocks.BLENDER);
        addToHarvestTag(BlockTags.MINEABLE_WITH_PICKAXE, BakeriesBlocks.OVEN);
        addToHarvestTag(BlockTags.MINEABLE_WITH_PICKAXE, BakeriesBlocks.DEEPSLATE_SALT_ORE);
        addToHarvestTag(BlockTags.MINEABLE_WITH_PICKAXE, BakeriesBlocks.SALT_ORE);
        addToHarvestTag(BlockTags.MINEABLE_WITH_AXE, BakeriesBlocks.CUPBOARD);
        addToHarvestTag(BlockTags.MINEABLE_WITH_AXE, BakeriesBlocks.DOUGH_CRAFTING_TABLE);
    }
}
