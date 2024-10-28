package com.renyigesai.bakery.init;


import com.renyigesai.bakery.BakeryMod;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class BakeryItemTag {
    public static final TagKey<Item> RAE_FOOD = create("raw_food");
    private static TagKey<Item> create(String name) {
        return ItemTags.create(BakeryMod.prefix(name));
    }

}
