//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.renyigesai.bakeries.common.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CommonTags {
    public static final TagKey<Item> SALT = commonItemTag("salt");
    public static final TagKey<Item> MILK = commonItemTag("foods/milk");
    public static final TagKey<Item> BUTTER = commonItemTag("butter");
    public static final TagKey<Item> FLOUR = commonItemTag("flours");
    public static final TagKey<Item> WHOLE_WHEAT_FLOUR = commonItemTag("flours/wheat");
    public static final TagKey<Item> KNIFE = commonItemTag("tools/knife");
    public static final TagKey<Item> TOMATO = commonItemTag("crops/tomato");

    public CommonTags() {
    }

    private static TagKey<Block> commonBlockTag(String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private static TagKey<Item> commonItemTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private static TagKey<Item> commonItemTag(String modId,String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(modId, path));
    }
}
