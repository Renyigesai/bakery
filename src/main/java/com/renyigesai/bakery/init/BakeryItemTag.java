package com.renyigesai.bakery.init;


import com.renyigesai.bakery.BakeryMod;
import lombok.Getter;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;

public class BakeryItemTag {

    public static final TagKey<Item> RAE_FOOD = create("raw_food");
    public static final TagKey<Item> MAIN_FOOD = create("main_food");
    public static final TagKey<Item> FLAVORING = create("flavoring");
    public static final TagKey<Item> ADDITIVE = create("additive");
    public static final TagKey<Item> ADDITIVE_FOOD = create("additive_food");
    private static TagKey<Item> create(String name) {
        return ItemTags.create(BakeryMod.prefix(name));
    }

}
