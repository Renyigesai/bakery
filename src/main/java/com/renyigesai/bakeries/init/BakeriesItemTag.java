package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import lombok.Getter;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;

public class BakeriesItemTag {

    public static final TagKey<Item> RAE_FOOD = create("raw_food");
    public static final TagKey<Item> FLOUR = create("flour");
    public static final TagKey<Item> MAIN_FOOD = create("main_food");
    public static final TagKey<Item> FLAVORING = create("flavoring");
    public static final TagKey<Item> ADDITIVE = create("additive");
    public static final TagKey<Item> ADDITIVE_FOOD = create("additive_food");
    public static final TagKey<Item> WHOLE_WHEAT_FLOUR = create("whole_wheat_flour");
    public static final TagKey<Item> BREAD_KNIFE = create("bread_knife");
    private static TagKey<Item> create(String name) {
        return ItemTags.create(BakeriesMod.prefix(name));
    }

}
