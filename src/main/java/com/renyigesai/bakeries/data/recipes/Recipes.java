package com.renyigesai.bakeries.data.recipes;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Recipes {
    protected static ResourceLocation name(Block block) {
        return BakeriesMod.rl(BuiltInRegistries.BLOCK.getKey(block).getPath());
    }
    protected  static ResourceLocation name(Item item) {
        return BakeriesMod.rl(BuiltInRegistries.ITEM.getKey(item).getPath());
    }
    protected static ResourceLocation name(Block block, String name) {
        return BakeriesMod.rl(BuiltInRegistries.BLOCK.getKey(block).getPath()+"_"+name);
    }
    protected static ResourceLocation name(Item item, String name) {
        return BakeriesMod.rl(BuiltInRegistries.ITEM.getKey(item).getPath()+"_"+name);
    }
}
