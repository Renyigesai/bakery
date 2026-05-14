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
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceKey;

public final class BakeriesItems {
    public static final CreativeModeTab BAKERIES_TAB;
    public static final Item FLOUR, WHOLE_WHEAT_FLOUR, COCOA_POWDER, MATCHA_POWDER, SALT, BOTTLE_YEAST, BOTTLE_MILK, BOTTLE_CREAM, BOTTLE_BUTTER, BUTTER_CUBE, FOAMED_CREAM, CHEESE_CREAM, BUTTER_FLOUR_SAND, HONEY_BUTTER, WHOLE_EGG, RAW_PROTEIN, RAW_EGG_YOLK, SALT_YOLK, CHEESE_CUBE, FRESH_CHEESE_CUBE, BROWN_SUGAR_CUBE, COFFEE_BEAN, GROUND_COFFEE, BEARNAISE, OLIVE_OIL, MEAT_FLOSS, ICE_CUBES, SCONE, OLIVE, TARO, COOKED_TARO, MASHED_TARO, SLICED_TOAST, BAKE_SLICED_TOAST, HONEY_BUTTER_SPREAD_TOAST, SLICED_CHEESE_COCOA_TOAST, COUNTRY_BREAD_SLICE, HONEY_BUTTER_SPREAD_COUNTRY_BREAD, SWEET_DOUGH, COCOA_DOUGH, SALTED_DOUGH, WHOLE_WHEAT_DOUGH, PASTRY, EGG_TART_SHELL, RAW_EGG_TART, BAGEL_DOUGH, WHOLE_WHEAT_BAGEL_DOUGH, ROUND_BREAD_DOUGH, BROWN_SUGAR_ROLL_DOUGH, PINEAPPLE_BUN_DOUGH, CROISSANT_DOUGH, SALT_CROISSANT_DOUGH, BAGUETTE_DOUGH, CIABATTA_DOUGH, FOCACCIA_DOUGH, COUNTRY_BREAD_DOUGH, MOULD_TOAST_DOUGH, MOULD_CHEESE_COCOA_TOAST_DOUGH, RICE_BREAD_DOUGH, OVEN, TOASTER, BLENDER, FERMENTATION_BOX, FERMENTATION_TANK, DOUGH_CRAFTING_TABLE, CUPBOARD, WOOD_COUNTER, COFFEE_TABLE, CHEESE_TANK, MILK_TANK, YEAST_TANK, MOKA_POT, BREAD_RACK, GLASS_BREAD_RACK, BREAD_BASKET, GLASS_CABINET_DOOR, MENU, MOULD, DRINK_CUP, TOAST, CHEESE_COCOA_TOAST, BAGEL, BAGUETTE, CROISSANT, ROUND_BREAD, RICE_BREAD, WHOLE_WHEAT_BAGEL, PINEAPPLE_BUN, FOCACCIA, CIABATTA, EGG_TART, SALT_CROISSANT, COUNTRY_BREAD, CREAM_BINGLE_COFFEE, MATCHA_PARFAIT, COFFEE_PLANT, TOMATO, MIX_BLOCK, BAGEL_FILLED_SAUCE, BAGUETTE_WITH_FILLING, BAKING_GUIDE, BERRY_BAGEL, BREAD_KNIFE, CASH_REGISTER_COMPUTER, CHEESE_CREAM_BREAD, DEEPSLATE_SALT_ORE, DIRTY_CHOCO_CROISSANT, DOUGH_CRAFTING, FLAT_CROISSANT, FLOUR_BAG, FLOUR_SIEVE, MEAT_FLOSS_BREAD_ROLL, PINEAPPLE_OIL, RAW_COFFEE_BEAN, RAW_SALT_BLOCK, SALT_ORE, SOFA_LIGHT_GRAY, SOFA_RED, SOFA_WHITE, TARO_SALT_YOLK_BREAD, TOMATO_CHEESE_CROISSANT_SANDWICH, WHOLE_WHEAT_FLOUR_BAG, WOOD_TRAY;

    static {
        BAKERIES_TAB = Registry.register(
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

        FLOUR = register("flour");
        WHOLE_WHEAT_FLOUR = register("whole_wheat_flour");
        COCOA_POWDER = register("cocoa_powder");
        MATCHA_POWDER = register("matcha_powder");
        SALT = register("salt");
        BOTTLE_YEAST = register("bottle_yeast", new Item.Properties().craftRemainder(Items.GLASS_BOTTLE));
        BOTTLE_MILK = register("bottle_milk", new Item.Properties().craftRemainder(Items.GLASS_BOTTLE));
        BOTTLE_CREAM = register("bottle_cream", new Item.Properties().craftRemainder(Items.GLASS_BOTTLE));
        BOTTLE_BUTTER = register("bottle_butter", new Item.Properties().craftRemainder(Items.GLASS_BOTTLE));
        BUTTER_CUBE = register("butter_cube");
        FOAMED_CREAM = register("foamed_cream", food(1, 1.0F, true));
        CHEESE_CREAM = register("cheese_cream", food(1, 1.0F, true));
        BUTTER_FLOUR_SAND = register("butter_flour_sand");
        HONEY_BUTTER = register("honey_butter");
        WHOLE_EGG = register("whole_egg");
        RAW_PROTEIN = register("raw_protein");
        RAW_EGG_YOLK = register("raw_egg_yolk");
        SALT_YOLK = register("salt_yolk");
        CHEESE_CUBE = register("cheese_cube", food(1, 1.0F, false));
        FRESH_CHEESE_CUBE = register("fresh_cheese_cube", food(1, 1.0F, false));
        BROWN_SUGAR_CUBE = register("brown_sugar_cube");
        COFFEE_BEAN = register("coffee_bean");
        GROUND_COFFEE = register("ground_coffee");
        BEARNAISE = register("bearnaise");
        OLIVE_OIL = register("olive_oil");
        MEAT_FLOSS = register("meat_floss", food(2, 0.8F, false));
        ICE_CUBES = register("ice_cubes");
        SCONE = register("scone", food(4, 0.5F, true));
        OLIVE = register("olive", food(2, 0.5F, false));
        TARO = registerBlock("taro", BakeriesBlocks.TARO);
        COOKED_TARO = register("cooked_taro", food(6, 0.6F, false));
        MASHED_TARO = register("mashed_taro", food(1, 0.5F, false));
        SLICED_TOAST = register("sliced_toast", food(4, 0.4F, true));
        BAKE_SLICED_TOAST = register("bake_sliced_toast", food(4, 0.4F, true));
        HONEY_BUTTER_SPREAD_TOAST = register("honey_butter_spread_toast", food(8, 0.5F, true));
        SLICED_CHEESE_COCOA_TOAST = register("sliced_cheese_cocoa_toast", food(4, 0.8F, true));
        COUNTRY_BREAD_SLICE = register("country_bread_slice", food(4, 0.4F, false));
        HONEY_BUTTER_SPREAD_COUNTRY_BREAD = register("honey_butter_spread_country_bread", food(6, 0.65F, true));
        SWEET_DOUGH = register("sweet_dough");
        COCOA_DOUGH = register("cocoa_dough");
        SALTED_DOUGH = register("salted_dough");
        WHOLE_WHEAT_DOUGH = register("whole_wheat_dough");
        PASTRY = register("pastry");
        EGG_TART_SHELL = register("egg_tart_shell");
        RAW_EGG_TART = register("raw_egg_tart");
        BAGEL_DOUGH = register("bagel_dough");
        WHOLE_WHEAT_BAGEL_DOUGH = register("whole_wheat_bagel_dough");
        ROUND_BREAD_DOUGH = register("round_bread_dough");
        BROWN_SUGAR_ROLL_DOUGH = register("brown_sugar_roll_dough");
        PINEAPPLE_BUN_DOUGH = register("pineapple_bun_dough");
        CROISSANT_DOUGH = register("croissant_dough");
        SALT_CROISSANT_DOUGH = register("salt_croissant_dough");
        BAGUETTE_DOUGH = register("baguette_dough");
        CIABATTA_DOUGH = register("ciabatta_dough");
        FOCACCIA_DOUGH = register("focaccia_dough");
        COUNTRY_BREAD_DOUGH = register("country_bread_dough");
        MOULD_TOAST_DOUGH = register("mould_toast_dough");
        MOULD_CHEESE_COCOA_TOAST_DOUGH = register("mould_cheese_cocoa_toast_dough");
        RICE_BREAD_DOUGH = register("rice_bread_dough");
        OVEN = registerBlock("oven", BakeriesBlocks.OVEN);
        TOASTER = registerBlock("toaster", BakeriesBlocks.TOASTER);
        BLENDER = registerBlock("blender", BakeriesBlocks.BLENDER);
        FERMENTATION_BOX = registerBlock("fermentation_box", BakeriesBlocks.FERMENTATION_BOX);
        FERMENTATION_TANK = registerBlock("fermentation_tank", BakeriesBlocks.FERMENTATION_TANK);
        DOUGH_CRAFTING_TABLE = registerBlock("dough_crafting_table", BakeriesBlocks.DOUGH_CRAFTING_TABLE);
        CUPBOARD = registerBlock("cupboard", BakeriesBlocks.CUPBOARD);
        WOOD_COUNTER = registerBlock("wood_counter", BakeriesBlocks.WOOD_COUNTER);
        COFFEE_TABLE = registerBlock("coffee_table", BakeriesBlocks.COFFEE_TABLE);
        CHEESE_TANK = registerBlock("cheese_tank", BakeriesBlocks.CHEESE_TANK);
        MILK_TANK = registerBlock("milk_tank", BakeriesBlocks.MILK_TANK);
        YEAST_TANK = registerBlock("yeast_tank", BakeriesBlocks.YEAST_TANK);
        MOKA_POT = registerBlock("moka_pot", BakeriesBlocks.MOKA_POT);
        BREAD_RACK = registerBlock("bread_rack", BakeriesBlocks.BREAD_RACK);
        GLASS_BREAD_RACK = registerBlock("glass_bread_rack", BakeriesBlocks.GLASS_BREAD_RACK);
        BREAD_BASKET = registerBlock("bread_basket", BakeriesBlocks.BREAD_BASKET);
        GLASS_CABINET_DOOR = registerBlock("glass_cabinet_door", BakeriesBlocks.GLASS_CABINET_DOOR);
        MENU = registerBlock("menu", BakeriesBlocks.MENU);
        MOULD = registerBlock("mould", BakeriesBlocks.MOULD);
        DRINK_CUP = registerBlock("drink_cup", BakeriesBlocks.DRINK_CUP, new Item.Properties().stacksTo(1));
        TOAST = registerBlock("toast", BakeriesBlocks.TOAST);
        CHEESE_COCOA_TOAST = registerBlock("cheese_cocoa_toast", BakeriesBlocks.CHEESE_COCOA_TOAST);
        BAGEL = registerBlock("bagel", BakeriesBlocks.BAGEL, food(6, 0.5F, false));
        BAGUETTE = registerBlock("baguette", BakeriesBlocks.BAGUETTE, food(8, 0.25F, false));
        CROISSANT = registerBlock("croissant", BakeriesBlocks.CROISSANT, food(6, 1.0F, false));
        ROUND_BREAD = registerBlock("round_bread", BakeriesBlocks.ROUND_BREAD, food(3, 0.6F, false));
        RICE_BREAD = registerBlock("rice_bread", BakeriesBlocks.RICE_BREAD, food(12, 0.4F, false));
        WHOLE_WHEAT_BAGEL = registerBlock("whole_wheat_bagel", BakeriesBlocks.WHOLE_WHEAT_BAGEL, food(8, 0.35F, false));
        PINEAPPLE_BUN = registerBlock("pineapple_bun", BakeriesBlocks.PINEAPPLE_BUN, food(6, 1.5F, false));
        FOCACCIA = registerBlock("focaccia", BakeriesBlocks.FOCACCIA, food(8, 1.0F, false));
        CIABATTA = registerBlock("ciabatta", BakeriesBlocks.CIABATTA, food(4, 0.4F, false));
        EGG_TART = registerBlock("egg_tart", BakeriesBlocks.EGG_TART, food(8, 0.5F, false));
        SALT_CROISSANT = registerBlock("salt_croissant", BakeriesBlocks.SALT_CROISSANT, food(6, 1.0F, false));
        COUNTRY_BREAD = registerBlock("country_bread", BakeriesBlocks.COUNTRY_BREAD, food(4, 0.4F, false));
        CREAM_BINGLE_COFFEE = registerBlock("cream_bingle_coffee", BakeriesBlocks.CREAM_BINGLE_COFFEE, food(1, 0.5F, true));
        MATCHA_PARFAIT = registerBlock("matcha_parfait", BakeriesBlocks.MATCHA_PARFAIT, food(9, 0.5F, true));
        COFFEE_PLANT = registerBlock("coffee_plant", BakeriesBlocks.COFFEE_PLANT);
        TOMATO = registerBlock("tomato", BakeriesBlocks.TOMATO, food(2, 0.5F, false));
        MIX_BLOCK = registerBlock("mix_block", BakeriesBlocks.MIX_BLOCK);
        BAGEL_FILLED_SAUCE = register("bagel_filled_sauce", food(8, 0.6F, false));
        BAGUETTE_WITH_FILLING = register("baguette_with_filling", food(10, 0.6F, false));
        BAKING_GUIDE = register("baking_guide");
        BERRY_BAGEL = register("berry_bagel", food(8, 0.6F, false));
        BREAD_KNIFE = register("bread_knife");
        CASH_REGISTER_COMPUTER = register("cash_register_computer");
        CHEESE_CREAM_BREAD = register("cheese_cream_bread", food(8, 0.6F, false));
        DEEPSLATE_SALT_ORE = register("deepslate_salt_ore");
        DIRTY_CHOCO_CROISSANT = register("dirty_choco_croissant", food(8, 0.6F, false));
        DOUGH_CRAFTING = register("dough_crafting");
        FLAT_CROISSANT = register("flat_croissant", food(6, 0.6F, false));
        FLOUR_BAG = register("flour_bag");
        FLOUR_SIEVE = register("flour_sieve");
        MEAT_FLOSS_BREAD_ROLL = register("meat_floss_bread_roll", food(10, 0.7F, false));
        PINEAPPLE_OIL = register("pineapple_oil");
        RAW_COFFEE_BEAN = register("raw_coffee_bean");
        RAW_SALT_BLOCK = register("raw_salt_block");
        SALT_ORE = register("salt_ore");
        SOFA_LIGHT_GRAY = register("sofa_light_gray");
        SOFA_RED = register("sofa_red");
        SOFA_WHITE = register("sofa_white");
        TARO_SALT_YOLK_BREAD = register("taro_salt_yolk_bread", food(10, 0.7F, false));
        TOMATO_CHEESE_CROISSANT_SANDWICH = register("tomato_cheese_croissant_sandwich", food(10, 0.7F, false));
        WHOLE_WHEAT_FLOUR_BAG = register("whole_wheat_flour_bag");
        WOOD_TRAY = register("wood_tray");
    }

    private BakeriesItems() {
    }

    public static void init() {
        registerTabEntries(
                CreativeModeTabs.INGREDIENTS,
                FLOUR, WHOLE_WHEAT_FLOUR, COCOA_POWDER, MATCHA_POWDER, SALT,
                BOTTLE_YEAST, BOTTLE_MILK, BOTTLE_CREAM, BOTTLE_BUTTER, BUTTER_CUBE,
                FOAMED_CREAM, CHEESE_CREAM, BUTTER_FLOUR_SAND, HONEY_BUTTER, WHOLE_EGG,
                RAW_PROTEIN, RAW_EGG_YOLK, SALT_YOLK, CHEESE_CUBE, FRESH_CHEESE_CUBE,
                BROWN_SUGAR_CUBE, COFFEE_BEAN, GROUND_COFFEE, BEARNAISE, OLIVE_OIL,
                MEAT_FLOSS, OLIVE, TARO, COOKED_TARO, MASHED_TARO,
                SWEET_DOUGH, COCOA_DOUGH, SALTED_DOUGH, WHOLE_WHEAT_DOUGH, PASTRY,
                EGG_TART_SHELL, RAW_EGG_TART, BAGEL_DOUGH, WHOLE_WHEAT_BAGEL_DOUGH,
                ROUND_BREAD_DOUGH, BROWN_SUGAR_ROLL_DOUGH, PINEAPPLE_BUN_DOUGH,
                CROISSANT_DOUGH, SALT_CROISSANT_DOUGH, BAGUETTE_DOUGH, CIABATTA_DOUGH,
                FOCACCIA_DOUGH, COUNTRY_BREAD_DOUGH, MOULD_TOAST_DOUGH,
                MOULD_CHEESE_COCOA_TOAST_DOUGH, RICE_BREAD_DOUGH
        );
        registerTabEntries(
                CreativeModeTabs.FOOD_AND_DRINKS,
                FOAMED_CREAM, CHEESE_CREAM, CHEESE_CUBE, FRESH_CHEESE_CUBE,
                MEAT_FLOSS, ICE_CUBES, SCONE, OLIVE, COOKED_TARO, MASHED_TARO,
                SLICED_TOAST, BAKE_SLICED_TOAST, HONEY_BUTTER_SPREAD_TOAST,
                SLICED_CHEESE_COCOA_TOAST, COUNTRY_BREAD_SLICE, HONEY_BUTTER_SPREAD_COUNTRY_BREAD
        );

        BakeriesMod.LOGGER.info("Registered Bakeries base items.");
    }

    private static Item[] allItems() {
        return new Item[]{
                FLOUR, WHOLE_WHEAT_FLOUR, COCOA_POWDER, MATCHA_POWDER, SALT,
                BOTTLE_YEAST, BOTTLE_MILK, BOTTLE_CREAM, BOTTLE_BUTTER, BUTTER_CUBE,
                FOAMED_CREAM, CHEESE_CREAM, BUTTER_FLOUR_SAND, HONEY_BUTTER, WHOLE_EGG,
                RAW_PROTEIN, RAW_EGG_YOLK, SALT_YOLK, CHEESE_CUBE, FRESH_CHEESE_CUBE,
                BROWN_SUGAR_CUBE, COFFEE_BEAN, GROUND_COFFEE, BEARNAISE, OLIVE_OIL,
                MEAT_FLOSS, ICE_CUBES, SCONE, OLIVE, COOKED_TARO, MASHED_TARO,
                SLICED_TOAST, BAKE_SLICED_TOAST, HONEY_BUTTER_SPREAD_TOAST, SLICED_CHEESE_COCOA_TOAST,
                COUNTRY_BREAD_SLICE, HONEY_BUTTER_SPREAD_COUNTRY_BREAD, SWEET_DOUGH, COCOA_DOUGH,
                SALTED_DOUGH, WHOLE_WHEAT_DOUGH, PASTRY, EGG_TART_SHELL, RAW_EGG_TART,
                BAGEL_DOUGH, WHOLE_WHEAT_BAGEL_DOUGH, ROUND_BREAD_DOUGH, BROWN_SUGAR_ROLL_DOUGH,
                PINEAPPLE_BUN_DOUGH, CROISSANT_DOUGH, SALT_CROISSANT_DOUGH, BAGUETTE_DOUGH,
                CIABATTA_DOUGH, FOCACCIA_DOUGH, COUNTRY_BREAD_DOUGH, MOULD_TOAST_DOUGH,
                MOULD_CHEESE_COCOA_TOAST_DOUGH, RICE_BREAD_DOUGH,
                OVEN, TOASTER, BLENDER, FERMENTATION_BOX, FERMENTATION_TANK, DOUGH_CRAFTING_TABLE, CUPBOARD,
                CHEESE_TANK, MILK_TANK, YEAST_TANK, MOKA_POT,
                WOOD_COUNTER, COFFEE_TABLE, BREAD_RACK, GLASS_BREAD_RACK, BREAD_BASKET,
                GLASS_CABINET_DOOR, MENU, MOULD, DRINK_CUP, TOAST, CHEESE_COCOA_TOAST,
                BAGEL, BAGUETTE, CROISSANT, ROUND_BREAD, RICE_BREAD, WHOLE_WHEAT_BAGEL,
                PINEAPPLE_BUN, FOCACCIA, CIABATTA, EGG_TART, SALT_CROISSANT, COUNTRY_BREAD,
                CREAM_BINGLE_COFFEE, MATCHA_PARFAIT, COFFEE_PLANT, TOMATO, TARO, MIX_BLOCK,
                BAGEL_FILLED_SAUCE, BAGUETTE_WITH_FILLING, BAKING_GUIDE, BERRY_BAGEL, BREAD_KNIFE,
                CASH_REGISTER_COMPUTER, CHEESE_CREAM_BREAD, DEEPSLATE_SALT_ORE, DIRTY_CHOCO_CROISSANT,
                DOUGH_CRAFTING, FLAT_CROISSANT, FLOUR_BAG, FLOUR_SIEVE, MEAT_FLOSS_BREAD_ROLL,
                PINEAPPLE_OIL, RAW_COFFEE_BEAN, RAW_SALT_BLOCK, SALT_ORE, SOFA_LIGHT_GRAY, SOFA_RED,
                SOFA_WHITE, TARO_SALT_YOLK_BREAD, TOMATO_CHEESE_CROISSANT_SANDWICH, WHOLE_WHEAT_FLOUR_BAG, WOOD_TRAY
        };
    }

    private static Item register(String id) {
        return register(id, new Item.Properties());
    }

    private static Item register(String id, Item.Properties properties) {
        ResourceLocation key = new ResourceLocation(BakeriesMod.MODID, id);
        return Registry.register(BuiltInRegistries.ITEM, key, new Item(properties));
    }

    private static Item registerBlock(String id, Block block) {
        return registerBlock(id, block, new Item.Properties());
    }

    private static Item registerBlock(String id, Block block, Item.Properties properties) {
        ResourceLocation key = new ResourceLocation(BakeriesMod.MODID, id);
        return Registry.register(BuiltInRegistries.ITEM, key, new BlockItem(block, properties));
    }

    private static void registerTabEntries(ResourceKey<CreativeModeTab> tab, Item... items) {
        ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> {
            for (Item item : items) {
                entries.accept(item);
            }
        });
    }

    private static Item.Properties food(int nutrition, float saturation, boolean alwaysEdible) {
        FoodProperties.Builder builder = new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation);
        if (alwaysEdible) {
            builder.alwaysEat();
        }
        return new Item.Properties().food(builder.build());
    }
}
