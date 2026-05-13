package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public final class BakeriesItems {
    public static final CreativeModeTab BAKERIES_TAB = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            new ResourceLocation(BakeriesMod.MODID, "main"),
            FabricItemGroup.builder()
                    .title(Component.translatable("item_group.bakeries.bakeries_tab"))
                    .icon(() -> new ItemStack(Items.BREAD))
                    .displayItems((parameters, entries) -> {
                        for (Item item : allItems()) {
                            entries.accept(item);
                        }
                    })
                    .build()
    );

    public static final Item FLOUR = register("flour");
    public static final Item WHOLE_WHEAT_FLOUR = register("whole_wheat_flour");
    public static final Item COCOA_POWDER = register("cocoa_powder");
    public static final Item MATCHA_POWDER = register("matcha_powder");
    public static final Item SALT = register("salt");
    public static final Item BOTTLE_YEAST = register("bottle_yeast");
    public static final Item BOTTLE_MILK = register("bottle_milk");
    public static final Item BOTTLE_CREAM = register("bottle_cream");
    public static final Item BOTTLE_BUTTER = register("bottle_butter");
    public static final Item BUTTER_CUBE = register("butter_cube");
    public static final Item FOAMED_CREAM = register("foamed_cream");
    public static final Item CHEESE_CREAM = register("cheese_cream");
    public static final Item BUTTER_FLOUR_SAND = register("butter_flour_sand");
    public static final Item HONEY_BUTTER = register("honey_butter");
    public static final Item WHOLE_EGG = register("whole_egg");
    public static final Item RAW_PROTEIN = register("raw_protein");
    public static final Item RAW_EGG_YOLK = register("raw_egg_yolk");
    public static final Item SALT_YOLK = register("salt_yolk");
    public static final Item CHEESE_CUBE = register("cheese_cube");
    public static final Item FRESH_CHEESE_CUBE = register("fresh_cheese_cube");
    public static final Item BROWN_SUGAR_CUBE = register("brown_sugar_cube");
    public static final Item COFFEE_BEAN = register("coffee_bean");
    public static final Item GROUND_COFFEE = register("ground_coffee");
    public static final Item BEARNAISE = register("bearnaise");
    public static final Item OLIVE_OIL = register("olive_oil");
    public static final Item MEAT_FLOSS = register("meat_floss");
    public static final Item ICE_CUBES = register("ice_cubes");
    public static final Item SCONE = register("scone");
    public static final Item OLIVE = register("olive");
    public static final Item TARO = register("taro");
    public static final Item COOKED_TARO = register("cooked_taro");
    public static final Item MASHED_TARO = register("mashed_taro");
    public static final Item SLICED_TOAST = register("sliced_toast");
    public static final Item BAKE_SLICED_TOAST = register("bake_sliced_toast");
    public static final Item HONEY_BUTTER_SPREAD_TOAST = register("honey_butter_spread_toast");
    public static final Item SLICED_CHEESE_COCOA_TOAST = register("sliced_cheese_cocoa_toast");
    public static final Item COUNTRY_BREAD_SLICE = register("country_bread_slice");
    public static final Item HONEY_BUTTER_SPREAD_COUNTRY_BREAD = register("honey_butter_spread_country_bread");
    public static final Item SWEET_DOUGH = register("sweet_dough");
    public static final Item COCOA_DOUGH = register("cocoa_dough");
    public static final Item SALTED_DOUGH = register("salted_dough");
    public static final Item WHOLE_WHEAT_DOUGH = register("whole_wheat_dough");
    public static final Item PASTRY = register("pastry");
    public static final Item EGG_TART_SHELL = register("egg_tart_shell");
    public static final Item RAW_EGG_TART = register("raw_egg_tart");
    public static final Item BAGEL_DOUGH = register("bagel_dough");
    public static final Item WHOLE_WHEAT_BAGEL_DOUGH = register("whole_wheat_bagel_dough");
    public static final Item ROUND_BREAD_DOUGH = register("round_bread_dough");
    public static final Item BROWN_SUGAR_ROLL_DOUGH = register("brown_sugar_roll_dough");
    public static final Item PINEAPPLE_BUN_DOUGH = register("pineapple_bun_dough");
    public static final Item CROISSANT_DOUGH = register("croissant_dough");
    public static final Item SALT_CROISSANT_DOUGH = register("salt_croissant_dough");
    public static final Item BAGUETTE_DOUGH = register("baguette_dough");
    public static final Item CIABATTA_DOUGH = register("ciabatta_dough");
    public static final Item FOCACCIA_DOUGH = register("focaccia_dough");
    public static final Item COUNTRY_BREAD_DOUGH = register("country_bread_dough");
    public static final Item MOULD_TOAST_DOUGH = register("mould_toast_dough");
    public static final Item MOULD_CHEESE_COCOA_TOAST_DOUGH = register("mould_cheese_cocoa_toast_dough");
    public static final Item RICE_BREAD_DOUGH = register("rice_bread_dough");
    public static final Item OVEN = registerBlock("oven", BakeriesBlocks.OVEN);
    public static final Item TOASTER = registerBlock("toaster", BakeriesBlocks.TOASTER);
    public static final Item BLENDER = registerBlock("blender", BakeriesBlocks.BLENDER);
    public static final Item FERMENTATION_BOX = registerBlock("fermentation_box", BakeriesBlocks.FERMENTATION_BOX);
    public static final Item DOUGH_CRAFTING_TABLE = registerBlock("dough_crafting_table", BakeriesBlocks.DOUGH_CRAFTING_TABLE);
    public static final Item CUPBOARD = registerBlock("cupboard", BakeriesBlocks.CUPBOARD);
    public static final Item WOOD_COUNTER = registerBlock("wood_counter", BakeriesBlocks.WOOD_COUNTER);
    public static final Item COFFEE_TABLE = registerBlock("coffee_table", BakeriesBlocks.COFFEE_TABLE);
    public static final Item BREAD_RACK = registerBlock("bread_rack", BakeriesBlocks.BREAD_RACK);
    public static final Item GLASS_BREAD_RACK = registerBlock("glass_bread_rack", BakeriesBlocks.GLASS_BREAD_RACK);
    public static final Item BREAD_BASKET = registerBlock("bread_basket", BakeriesBlocks.BREAD_BASKET);
    public static final Item GLASS_CABINET_DOOR = registerBlock("glass_cabinet_door", BakeriesBlocks.GLASS_CABINET_DOOR);
    public static final Item MENU = registerBlock("menu", BakeriesBlocks.MENU);
    public static final Item MOULD = registerBlock("mould", BakeriesBlocks.MOULD);
    public static final Item DRINK_CUP = registerBlock("drink_cup", BakeriesBlocks.DRINK_CUP);
    public static final Item TOAST = registerBlock("toast", BakeriesBlocks.TOAST);
    public static final Item CHEESE_COCOA_TOAST = registerBlock("cheese_cocoa_toast", BakeriesBlocks.CHEESE_COCOA_TOAST);
    public static final Item BAGEL = registerBlock("bagel", BakeriesBlocks.BAGEL);
    public static final Item BAGUETTE = registerBlock("baguette", BakeriesBlocks.BAGUETTE);
    public static final Item CROISSANT = registerBlock("croissant", BakeriesBlocks.CROISSANT);
    public static final Item ROUND_BREAD = registerBlock("round_bread", BakeriesBlocks.ROUND_BREAD);
    public static final Item RICE_BREAD = registerBlock("rice_bread", BakeriesBlocks.RICE_BREAD);
    public static final Item WHOLE_WHEAT_BAGEL = registerBlock("whole_wheat_bagel", BakeriesBlocks.WHOLE_WHEAT_BAGEL);
    public static final Item PINEAPPLE_BUN = registerBlock("pineapple_bun", BakeriesBlocks.PINEAPPLE_BUN);
    public static final Item FOCACCIA = registerBlock("focaccia", BakeriesBlocks.FOCACCIA);
    public static final Item CIABATTA = registerBlock("ciabatta", BakeriesBlocks.CIABATTA);
    public static final Item EGG_TART = registerBlock("egg_tart", BakeriesBlocks.EGG_TART);
    public static final Item SALT_CROISSANT = registerBlock("salt_croissant", BakeriesBlocks.SALT_CROISSANT);
    public static final Item COUNTRY_BREAD = registerBlock("country_bread", BakeriesBlocks.COUNTRY_BREAD);

    private BakeriesItems() {
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            entries.accept(FLOUR);
            entries.accept(WHOLE_WHEAT_FLOUR);
            entries.accept(COCOA_POWDER);
            entries.accept(MATCHA_POWDER);
            entries.accept(SALT);
            entries.accept(BOTTLE_YEAST);
            entries.accept(BOTTLE_MILK);
            entries.accept(BOTTLE_CREAM);
            entries.accept(BOTTLE_BUTTER);
            entries.accept(BUTTER_CUBE);
            entries.accept(FOAMED_CREAM);
            entries.accept(CHEESE_CREAM);
            entries.accept(BUTTER_FLOUR_SAND);
            entries.accept(HONEY_BUTTER);
            entries.accept(WHOLE_EGG);
            entries.accept(RAW_PROTEIN);
            entries.accept(RAW_EGG_YOLK);
            entries.accept(SALT_YOLK);
            entries.accept(CHEESE_CUBE);
            entries.accept(FRESH_CHEESE_CUBE);
            entries.accept(BROWN_SUGAR_CUBE);
            entries.accept(COFFEE_BEAN);
            entries.accept(GROUND_COFFEE);
            entries.accept(BEARNAISE);
            entries.accept(OLIVE_OIL);
            entries.accept(MEAT_FLOSS);
            entries.accept(OLIVE);
            entries.accept(TARO);
            entries.accept(COOKED_TARO);
            entries.accept(MASHED_TARO);
            entries.accept(SWEET_DOUGH);
            entries.accept(COCOA_DOUGH);
            entries.accept(SALTED_DOUGH);
            entries.accept(WHOLE_WHEAT_DOUGH);
            entries.accept(PASTRY);
            entries.accept(EGG_TART_SHELL);
            entries.accept(RAW_EGG_TART);
            entries.accept(BAGEL_DOUGH);
            entries.accept(WHOLE_WHEAT_BAGEL_DOUGH);
            entries.accept(ROUND_BREAD_DOUGH);
            entries.accept(BROWN_SUGAR_ROLL_DOUGH);
            entries.accept(PINEAPPLE_BUN_DOUGH);
            entries.accept(CROISSANT_DOUGH);
            entries.accept(SALT_CROISSANT_DOUGH);
            entries.accept(BAGUETTE_DOUGH);
            entries.accept(CIABATTA_DOUGH);
            entries.accept(FOCACCIA_DOUGH);
            entries.accept(COUNTRY_BREAD_DOUGH);
            entries.accept(MOULD_TOAST_DOUGH);
            entries.accept(MOULD_CHEESE_COCOA_TOAST_DOUGH);
            entries.accept(RICE_BREAD_DOUGH);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.accept(FOAMED_CREAM);
            entries.accept(CHEESE_CREAM);
            entries.accept(CHEESE_CUBE);
            entries.accept(FRESH_CHEESE_CUBE);
            entries.accept(MEAT_FLOSS);
            entries.accept(ICE_CUBES);
            entries.accept(SCONE);
            entries.accept(OLIVE);
            entries.accept(COOKED_TARO);
            entries.accept(MASHED_TARO);
            entries.accept(SLICED_TOAST);
            entries.accept(BAKE_SLICED_TOAST);
            entries.accept(HONEY_BUTTER_SPREAD_TOAST);
            entries.accept(SLICED_CHEESE_COCOA_TOAST);
            entries.accept(COUNTRY_BREAD_SLICE);
            entries.accept(HONEY_BUTTER_SPREAD_COUNTRY_BREAD);
        });

        BakeriesMod.LOGGER.info("Registered Bakeries base items.");
    }

    private static Item[] allItems() {
        return new Item[]{
                FLOUR, WHOLE_WHEAT_FLOUR, COCOA_POWDER, MATCHA_POWDER, SALT,
                BOTTLE_YEAST, BOTTLE_MILK, BOTTLE_CREAM, BOTTLE_BUTTER, BUTTER_CUBE,
                FOAMED_CREAM, CHEESE_CREAM, BUTTER_FLOUR_SAND, HONEY_BUTTER, WHOLE_EGG,
                RAW_PROTEIN, RAW_EGG_YOLK, SALT_YOLK, CHEESE_CUBE, FRESH_CHEESE_CUBE,
                BROWN_SUGAR_CUBE, COFFEE_BEAN, GROUND_COFFEE, BEARNAISE, OLIVE_OIL,
                MEAT_FLOSS, ICE_CUBES, SCONE, OLIVE, TARO, COOKED_TARO, MASHED_TARO,
                SLICED_TOAST, BAKE_SLICED_TOAST, HONEY_BUTTER_SPREAD_TOAST, SLICED_CHEESE_COCOA_TOAST,
                COUNTRY_BREAD_SLICE, HONEY_BUTTER_SPREAD_COUNTRY_BREAD, SWEET_DOUGH, COCOA_DOUGH,
                SALTED_DOUGH, WHOLE_WHEAT_DOUGH, PASTRY, EGG_TART_SHELL, RAW_EGG_TART,
                BAGEL_DOUGH, WHOLE_WHEAT_BAGEL_DOUGH, ROUND_BREAD_DOUGH, BROWN_SUGAR_ROLL_DOUGH,
                PINEAPPLE_BUN_DOUGH, CROISSANT_DOUGH, SALT_CROISSANT_DOUGH, BAGUETTE_DOUGH,
                CIABATTA_DOUGH, FOCACCIA_DOUGH, COUNTRY_BREAD_DOUGH, MOULD_TOAST_DOUGH,
                MOULD_CHEESE_COCOA_TOAST_DOUGH, RICE_BREAD_DOUGH,
                OVEN, TOASTER, BLENDER, FERMENTATION_BOX, DOUGH_CRAFTING_TABLE, CUPBOARD,
                WOOD_COUNTER, COFFEE_TABLE, BREAD_RACK, GLASS_BREAD_RACK, BREAD_BASKET,
                GLASS_CABINET_DOOR, MENU, MOULD, DRINK_CUP, TOAST, CHEESE_COCOA_TOAST,
                BAGEL, BAGUETTE, CROISSANT, ROUND_BREAD, RICE_BREAD, WHOLE_WHEAT_BAGEL,
                PINEAPPLE_BUN, FOCACCIA, CIABATTA, EGG_TART, SALT_CROISSANT, COUNTRY_BREAD
        };
    }

    private static Item register(String id) {
        ResourceLocation key = new ResourceLocation(BakeriesMod.MODID, id);
        return Registry.register(BuiltInRegistries.ITEM, key, new Item(new Item.Properties()));
    }

    private static Item registerBlock(String id, Block block) {
        ResourceLocation key = new ResourceLocation(BakeriesMod.MODID, id);
        return Registry.register(BuiltInRegistries.ITEM, key, new BlockItem(block, new Item.Properties()));
    }
}
