package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class BakeriesBlocks {
    public static final Block OVEN = register("oven", BlockBehaviour.Properties.copy(Blocks.STONE));
    public static final Block TOASTER = register("toaster", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final Block BLENDER = register("blender", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final Block FERMENTATION_BOX = register("fermentation_box", BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    public static final Block DOUGH_CRAFTING_TABLE = register("dough_crafting_table", BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE));
    public static final Block CUPBOARD = register("cupboard", BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    public static final Block WOOD_COUNTER = register("wood_counter", BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    public static final Block COFFEE_TABLE = register("coffee_table", BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    public static final Block BREAD_RACK = register("bread_rack", BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    public static final Block GLASS_BREAD_RACK = register("glass_bread_rack", BlockBehaviour.Properties.copy(Blocks.GLASS));
    public static final Block BREAD_BASKET = register("bread_basket", BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    public static final Block GLASS_CABINET_DOOR = register("glass_cabinet_door", BlockBehaviour.Properties.copy(Blocks.GLASS));
    public static final Block MENU = register("menu", BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    public static final Block MOULD = register("mould", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final Block DRINK_CUP = register("drink_cup", BlockBehaviour.Properties.copy(Blocks.GLASS));

    public static final Block TOAST = register("toast", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block CHEESE_COCOA_TOAST = register("cheese_cocoa_toast", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block BAGEL = register("bagel", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block BAGUETTE = register("baguette", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block CROISSANT = register("croissant", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block ROUND_BREAD = register("round_bread", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block RICE_BREAD = register("rice_bread", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block WHOLE_WHEAT_BAGEL = register("whole_wheat_bagel", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block PINEAPPLE_BUN = register("pineapple_bun", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block FOCACCIA = register("focaccia", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block CIABATTA = register("ciabatta", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block EGG_TART = register("egg_tart", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block SALT_CROISSANT = register("salt_croissant", BlockBehaviour.Properties.copy(Blocks.CAKE));
    public static final Block COUNTRY_BREAD = register("country_bread", BlockBehaviour.Properties.copy(Blocks.CAKE));

    private BakeriesBlocks() {
    }

    public static void init() {
        BakeriesMod.LOGGER.info("Registered Bakeries base blocks.");
    }

    private static Block register(String id, BlockBehaviour.Properties properties) {
        ResourceLocation key = new ResourceLocation(BakeriesMod.MODID, id);
        return Registry.register(BuiltInRegistries.BLOCK, key, new Block(properties));
    }
}
