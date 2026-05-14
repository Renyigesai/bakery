package com.renyigesai.bakeries.tag;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class CommonTags {
    public static final TagKey<Item> BREAD_KNIFE_INGREDIENTS = item("bread_knife_ingredients");

    private CommonTags() {
    }

    private static TagKey<Item> item(String path) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(BakeriesMod.MODID, path));
    }
}
