package com.renyigesai.bakeries.data;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.init.BakeriesTags;
import com.renyigesai.bakeries.common.villager.BakeriesVillagers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class Tags extends AbstractTagProvider {

    public Tags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
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
        /*NeoForge±êÇ©*/
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","salt")), BakeriesItems.SALT);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","doughs")), BakeriesItems.SWEET_DOUGH, BakeriesItems.SALTED_DOUGH, BakeriesItems.WHOLE_WHEAT_DOUGH);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","doughs/wheat")), BakeriesItems.WHOLE_WHEAT_DOUGH);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","foods/milk")), BakeriesItems.BOTTLE_MILK);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","flours")), BakeriesItems.WHOLE_WHEAT_FLOUR, BakeriesItems.FLOUR);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","flours/wheat")), BakeriesItems.WHOLE_WHEAT_FLOUR);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","butter")), BakeriesItems.BUTTER_CUBE);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","eggs")), BakeriesItems.WHOLE_EGG);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","tools/knife")), BakeriesItems.BREAD_KNIFE);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","tools")), BakeriesItems.BREAD_KNIFE);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","crops/tomato")), BakeriesItems.TOMATO);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","foods/vegetables")), BakeriesItems.TOMATO);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","foods/tomato")), BakeriesItems.TOMATO);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","matcha")), BakeriesItems.MATCHA_POWDER);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","cheese")), BakeriesItems.CHEESE_CUBE);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","cream")), BakeriesItems.FOAMED_CREAM);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","coffee_grounds")), BakeriesItems.COCOA_POWDER);
        addToTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge","flour/wheat")), BakeriesItems.WHOLE_WHEAT_FLOUR);
        /*ºæ±º·»±êÇ©*/
    }

    private void addBlocks() {
        /*Minecraft±êÇ©*/
        addToHarvestTag(BlockTags.MINEABLE_WITH_PICKAXE,
                BakeriesBlocks.BLENDER,
                BakeriesBlocks.OVEN,
                BakeriesBlocks.DEEPSLATE_SALT_ORE,
                BakeriesBlocks.SALT_ORE,
                BakeriesBlocks.MOULD_TOAST,
                BakeriesBlocks.MOULD_CHEESE_COCOA_TOAST,
                BakeriesBlocks.RAW_SALT_BLOCK,
                BakeriesBlocks.GLASS_CABINET_DOOR
        );
        addToHarvestTag(BlockTags.MINEABLE_WITH_AXE,
                BakeriesBlocks.CUPBOARD,
                BakeriesBlocks.DOUGH_CRAFTING_TABLE,
                BakeriesBlocks.GLASS_CABINET_DOOR,
                BakeriesBlocks.SOFA_WHITE,
                BakeriesBlocks.SOFA_RED,
                BakeriesBlocks.SOFA_LIGHT_GRAY
        );
        /*ºæ±º·»±êÇ©*/
        addToTag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("bakeries","sofa")), BakeriesBlocks.SOFA_WHITE,BakeriesBlocks.SOFA_RED,BakeriesBlocks.SOFA_LIGHT_GRAY);
    }
}
