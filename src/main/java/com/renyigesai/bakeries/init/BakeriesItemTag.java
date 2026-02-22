package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class BakeriesItemTag {

    public static final TagKey<Item> FLOUR = create("flour");
    public static final TagKey<Item> BREAD_KNIFE = create("bread_knife");
    public static final TagKey<Item> UPRIGHT_ON_OVEN = create("upright_on_oven");
    private static TagKey<Item> create(String name) {
        return ItemTags.create(BakeriesMod.prefix(name));
    }

}
