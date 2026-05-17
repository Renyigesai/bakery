package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.items.BreadKnifeItem;
import com.renyigesai.bakeries.items.DescriptionItem;
import com.renyigesai.bakeries.items.FlourSieveItem;
import com.renyigesai.bakeries.items.WholeEggItem;
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

@SuppressWarnings("unused")
public final class BakeriesItems {
    public static final CreativeModeTab BAKERIES_TAB;
    public static final Item FLOUR, WHOLE_WHEAT_FLOUR, COCOA_POWDER, MATCHA_POWDER, SALT, BOTTLE_YEAST, BOTTLE_MILK, BOTTLE_CREAM, BOTTLE_BUTTER, BUTTER_CUBE, FOAMED_CREAM, CHEESE_CREAM, BUTTER_FLOUR_SAND, HONEY_BUTTER, WHOLE_EGG, RAW_PROTEIN, RAW_EGG_YOLK, SALT_YOLK, CHEESE_CUBE, FRESH_CHEESE_CUBE, BROWN_SUGAR_CUBE, COFFEE_BEAN, GROUND_COFFEE, MOKA_POT_FILL, BEARNAISE, OLIVE_OIL, MEAT_FLOSS, ICE_CUBES, SCONE, OLIVE, TARO, COOKED_TARO, MASHED_TARO, SLICED_TOAST, BAKE_SLICED_TOAST, HONEY_BUTTER_SPREAD_TOAST, SLICED_CHEESE_COCOA_TOAST, COUNTRY_BREAD_SLICE, HONEY_BUTTER_SPREAD_COUNTRY_BREAD, BROWN_SUGAR_LATTE, ICED_AMERICAN, ICED_LATTE, MATCHA_LATTE, ORANGE_AMERICAN, TARO_MILK, SWEET_DOUGH, COCOA_DOUGH, SALTED_DOUGH, WHOLE_WHEAT_DOUGH, COCOA_DOUGH_FERMENTATION, SALTED_DOUGH_FERMENTATION, SWEET_DOUGH_FERMENTATION, WHOLE_WHEAT_DOUGH_FERMENTATION, PASTRY, EGG_TART_SHELL, RAW_EGG_TART, BAGEL_DOUGH, WHOLE_WHEAT_BAGEL_DOUGH, ROUND_BREAD_DOUGH, BROWN_SUGAR_ROLL_DOUGH, PINEAPPLE_BUN_DOUGH, CROISSANT_DOUGH, SALT_CROISSANT_DOUGH, BAGUETTE_DOUGH, CIABATTA_DOUGH, FOCACCIA_DOUGH, COUNTRY_BREAD_DOUGH, MOULD_TOAST_DOUGH, MOULD_CHEESE_COCOA_TOAST_DOUGH, RICE_BREAD_DOUGH, OVEN, TOASTER, BLENDER, FERMENTATION_BOX, FERMENTATION_TANK, DOUGH_CRAFTING_TABLE, CUPBOARD, WOOD_COUNTER, COFFEE_TABLE, CHEESE_TANK, MILK_TANK, YEAST_TANK, MOKA_POT, BREAD_RACK, GLASS_BREAD_RACK, BREAD_BASKET, GLASS_CABINET_DOOR, MENU, MOULD, DRINK_CUP, TOAST, CHEESE_COCOA_TOAST, BAGEL, BAGUETTE, CROISSANT, ROUND_BREAD, RICE_BREAD, WHOLE_WHEAT_BAGEL, PINEAPPLE_BUN, FOCACCIA, CIABATTA, EGG_TART, SALT_CROISSANT, COUNTRY_BREAD, CREAM_BINGLE_COFFEE, MATCHA_PARFAIT, COFFEE_PLANT, TOMATO, MIX_BLOCK, BAGEL_FILLED_SAUCE, BAGUETTE_WITH_FILLING, BAKING_GUIDE, BERRY_BAGEL, BREAD_KNIFE, CASH_REGISTER_COMPUTER, CHEESE_CREAM_BREAD, DEEPSLATE_SALT_ORE, DIRTY_CHOCO_CROISSANT, DOUGH_CRAFTING, FLAT_CROISSANT, FLOUR_BAG, FLOUR_SIEVE, MEAT_FLOSS_BREAD_ROLL, PINEAPPLE_OIL, RAW_COFFEE_BEAN, RAW_SALT_BLOCK, SALT_ORE, SOFA_LIGHT_GRAY, SOFA_RED, SOFA_WHITE, TARO_SALT_YOLK_BREAD, TOMATO_CHEESE_CROISSANT_SANDWICH, WHOLE_WHEAT_FLOUR_BAG, WOOD_TRAY;
    public static final Item BROWN_SUGAR_ROLL;
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
        BAKING_GUIDE = register("baking_guide");
        BREAD_KNIFE = register("bread_knife", new BreadKnifeItem(new Item.Properties().durability(250)));
        FLOUR_SIEVE = register("flour_sieve", new FlourSieveItem(new Item.Properties().durability(128)));

        FLOUR = register("flour");
        WHOLE_WHEAT_FLOUR = register("whole_wheat_flour");
        COCOA_POWDER = register("cocoa_powder");
        MATCHA_POWDER = register("matcha_powder");
        SALT = register("salt");
        BROWN_SUGAR_CUBE = register("brown_sugar_cube");
        ICE_CUBES = register("ice_cubes");
        RAW_COFFEE_BEAN = register("raw_coffee_bean", new DescriptionItem(new Item.Properties(), "bakeries.raw_coffee_bean.description"));
        COFFEE_BEAN = register("coffee_bean");
        GROUND_COFFEE = register("ground_coffee");
        OLIVE = register("olive", new DescriptionItem(food(2, 0.5F, false), "bakeries.olive.description"));
        MEAT_FLOSS = register("meat_floss", food(2, 0.8F, false));

        BOTTLE_YEAST = register("bottle_yeast", new DescriptionItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE), "bakeries.bottle_yeast.description"));
        BOTTLE_MILK = register("bottle_milk", new Item.Properties().craftRemainder(Items.GLASS_BOTTLE));
        BOTTLE_CREAM = register("bottle_cream", new Item.Properties().craftRemainder(Items.GLASS_BOTTLE));
        BOTTLE_BUTTER = register("bottle_butter", new Item.Properties().craftRemainder(Items.GLASS_BOTTLE));
        BUTTER_CUBE = register("butter_cube");
        FOAMED_CREAM = register("foamed_cream", food(1, 1.0F, true));
        CHEESE_CREAM = register("cheese_cream", food(1, 1.0F, true));
        CHEESE_CUBE = register("cheese_cube", new DescriptionItem(food(1, 1.0F, false), "bakeries.cheese_cube.description"));
        FRESH_CHEESE_CUBE = register("fresh_cheese_cube", food(1, 1.0F, false));

        WHOLE_EGG = register("whole_egg", new WholeEggItem());
        RAW_PROTEIN = register("raw_protein");
        RAW_EGG_YOLK = register("raw_egg_yolk");
        SALT_YOLK = register("salt_yolk");

        BUTTER_FLOUR_SAND = register("butter_flour_sand");
        HONEY_BUTTER = register("honey_butter");
        BEARNAISE = register("bearnaise");
        OLIVE_OIL = register("olive_oil");

        TARO = registerBlock("taro", BakeriesBlocks.TARO);
        COOKED_TARO = register("cooked_taro", food(6, 0.6F, false));
        MASHED_TARO = register("mashed_taro", food(1, 0.5F, false));

        MOKA_POT_FILL = registerBlock("moka_pot_fill", BakeriesBlocks.MOKA_POT_FILL);
        BROWN_SUGAR_LATTE = register("brown_sugar_latte", food(1, 0.5F, true));
        ICED_AMERICAN = register("iced_american", food(1, 0.5F, true));
        ICED_LATTE = register("iced_latte", food(1, 0.5F, true));
        MATCHA_LATTE = register("matcha_latte", food(1, 0.5F, true));
        ORANGE_AMERICAN = register("orange_american", food(1, 0.5F, true));
        TARO_MILK = register("taro_milk", food(1, 0.5F, true));

        SWEET_DOUGH = register("sweet_dough");
        COCOA_DOUGH = register("cocoa_dough");
        SALTED_DOUGH = register("salted_dough");
        WHOLE_WHEAT_DOUGH = register("whole_wheat_dough");
        COCOA_DOUGH_FERMENTATION = register("cocoa_dough_fermentation");
        SALTED_DOUGH_FERMENTATION = register("salted_dough_fermentation");
        SWEET_DOUGH_FERMENTATION = register("sweet_dough_fermentation");
        WHOLE_WHEAT_DOUGH_FERMENTATION = register("whole_wheat_dough_fermentation");
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
        DOUGH_CRAFTING = register("dough_crafting");

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
        MIX_BLOCK = registerBlock("mix_block", BakeriesBlocks.MIX_BLOCK);
        MOULD = registerBlock("mould", BakeriesBlocks.MOULD);
        DRINK_CUP = registerBlock("drink_cup", BakeriesBlocks.DRINK_CUP, new Item.Properties().stacksTo(1));

        BREAD_RACK = registerBlock("bread_rack", BakeriesBlocks.BREAD_RACK);
        GLASS_BREAD_RACK = registerBlock("glass_bread_rack", BakeriesBlocks.GLASS_BREAD_RACK);
        BREAD_BASKET = registerBlock("bread_basket", BakeriesBlocks.BREAD_BASKET);
        GLASS_CABINET_DOOR = registerBlock("glass_cabinet_door", BakeriesBlocks.GLASS_CABINET_DOOR);
        MENU = registerBlock("menu", BakeriesBlocks.MENU);
        CASH_REGISTER_COMPUTER = registerBlock("cash_register_computer", BakeriesBlocks.CASH_REGISTER_COMPUTER);
        FLOUR_BAG = registerBlock("flour_bag", BakeriesBlocks.FLOUR_BAG);
        WHOLE_WHEAT_FLOUR_BAG = registerBlock("whole_wheat_flour_bag", BakeriesBlocks.WHOLE_WHEAT_FLOUR_BAG);
        WOOD_TRAY = registerBlock("wood_tray", BakeriesBlocks.WOOD_TRAY);
        SOFA_LIGHT_GRAY = registerBlock("sofa_light_gray", BakeriesBlocks.SOFA_LIGHT_GRAY);
        SOFA_RED = registerBlock("sofa_red", BakeriesBlocks.SOFA_RED);
        SOFA_WHITE = registerBlock("sofa_white", BakeriesBlocks.SOFA_WHITE);

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
        BROWN_SUGAR_ROLL = registerBlock("brown_sugar_roll", BakeriesBlocks.BROWN_SUGAR_ROLL, food(7, 0.7F, false));
        BAGEL_FILLED_SAUCE = registerBlock("bagel_filled_sauce", BakeriesBlocks.BAGEL_FILLED_SAUCE, food(8, 0.6F, false));
        BAGUETTE_WITH_FILLING = registerBlock("baguette_with_filling", BakeriesBlocks.BAGUETTE_WITH_FILLING, food(10, 0.6F, false));
        BERRY_BAGEL = registerBlock("berry_bagel", BakeriesBlocks.BERRY_BAGEL, food(8, 0.6F, false));
        CHEESE_CREAM_BREAD = registerBlock("cheese_cream_bread", BakeriesBlocks.CHEESE_CREAM_BREAD, food(8, 0.6F, false));
        DIRTY_CHOCO_CROISSANT = registerBlock("dirty_choco_croissant", BakeriesBlocks.DIRTY_CHOCO_CROISSANT, food(8, 0.6F, false));
        FLAT_CROISSANT = register("flat_croissant", food(6, 0.6F, false));
        MEAT_FLOSS_BREAD_ROLL = registerBlock("meat_floss_bread_roll", BakeriesBlocks.MEAT_FLOSS_BREAD_ROLL, food(10, 0.7F, false));
        PINEAPPLE_OIL = registerBlock("pineapple_oil", BakeriesBlocks.PINEAPPLE_OIL);
        TARO_SALT_YOLK_BREAD = registerBlock("taro_salt_yolk_bread", BakeriesBlocks.TARO_SALT_YOLK_BREAD);
        TOMATO_CHEESE_CROISSANT_SANDWICH = registerBlock("tomato_cheese_croissant_sandwich", BakeriesBlocks.TOMATO_CHEESE_CROISSANT_SANDWICH);
        CREAM_BINGLE_COFFEE = registerBlock("cream_bingle_coffee", BakeriesBlocks.CREAM_BINGLE_COFFEE, food(1, 0.5F, true));
        MATCHA_PARFAIT = registerBlock("matcha_parfait", BakeriesBlocks.MATCHA_PARFAIT, food(9, 0.5F, true));
        SCONE = register("scone", food(4, 0.5F, true));
        SLICED_TOAST = register("sliced_toast", food(4, 0.4F, true));
        BAKE_SLICED_TOAST = register("bake_sliced_toast", food(4, 0.4F, true));
        HONEY_BUTTER_SPREAD_TOAST = register("honey_butter_spread_toast", food(8, 0.5F, true));
        SLICED_CHEESE_COCOA_TOAST = register("sliced_cheese_cocoa_toast", food(4, 0.8F, true));
        COUNTRY_BREAD_SLICE = register("country_bread_slice", food(4, 0.4F, false));
        HONEY_BUTTER_SPREAD_COUNTRY_BREAD = register("honey_butter_spread_country_bread", food(6, 0.65F, true));

        COFFEE_PLANT = registerBlock("coffee_plant", BakeriesBlocks.COFFEE_PLANT);
        TOMATO = registerBlock("tomato", BakeriesBlocks.TOMATO, food(2, 0.5F, false));

        RAW_SALT_BLOCK = registerBlock("raw_salt_block", BakeriesBlocks.RAW_SALT_BLOCK);
        SALT_ORE = registerBlock("salt_ore", BakeriesBlocks.SALT_ORE);
        DEEPSLATE_SALT_ORE = register("deepslate_salt_ore");
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
                BROWN_SUGAR_CUBE, COFFEE_BEAN, GROUND_COFFEE, MOKA_POT_FILL, BEARNAISE, OLIVE_OIL,
                MEAT_FLOSS, OLIVE, TARO, COOKED_TARO, MASHED_TARO,
                SWEET_DOUGH, COCOA_DOUGH, SALTED_DOUGH, WHOLE_WHEAT_DOUGH,
                COCOA_DOUGH_FERMENTATION, SALTED_DOUGH_FERMENTATION, SWEET_DOUGH_FERMENTATION, WHOLE_WHEAT_DOUGH_FERMENTATION,
                PASTRY,
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
                SLICED_CHEESE_COCOA_TOAST, COUNTRY_BREAD_SLICE, HONEY_BUTTER_SPREAD_COUNTRY_BREAD,
                BROWN_SUGAR_LATTE, ICED_AMERICAN, ICED_LATTE, MATCHA_LATTE, ORANGE_AMERICAN, TARO_MILK
        );

        BakeriesMod.LOGGER.info("Registered Bakeries base items.");
    }

    private static Item[] allItems() {
        return new Item[]{
                BAKING_GUIDE, BREAD_KNIFE, FLOUR_SIEVE,

                FLOUR, WHOLE_WHEAT_FLOUR, COCOA_POWDER, MATCHA_POWDER, SALT,
                BROWN_SUGAR_CUBE, ICE_CUBES, RAW_COFFEE_BEAN, COFFEE_BEAN, GROUND_COFFEE,
                OLIVE, MEAT_FLOSS,

                BOTTLE_YEAST, BOTTLE_MILK, BOTTLE_CREAM, BOTTLE_BUTTER,
                BUTTER_CUBE, FOAMED_CREAM, CHEESE_CREAM, CHEESE_CUBE, FRESH_CHEESE_CUBE,

                WHOLE_EGG, RAW_PROTEIN, RAW_EGG_YOLK, SALT_YOLK,

                BUTTER_FLOUR_SAND, HONEY_BUTTER, BEARNAISE, OLIVE_OIL,

                TARO, COOKED_TARO, MASHED_TARO,

                MOKA_POT_FILL,
                BROWN_SUGAR_LATTE, ICED_AMERICAN, ICED_LATTE, MATCHA_LATTE, ORANGE_AMERICAN, TARO_MILK,

                SWEET_DOUGH, COCOA_DOUGH, SALTED_DOUGH, WHOLE_WHEAT_DOUGH,
                COCOA_DOUGH_FERMENTATION, SALTED_DOUGH_FERMENTATION,
                SWEET_DOUGH_FERMENTATION, WHOLE_WHEAT_DOUGH_FERMENTATION,
                PASTRY, EGG_TART_SHELL, RAW_EGG_TART,
                BAGEL_DOUGH, WHOLE_WHEAT_BAGEL_DOUGH, ROUND_BREAD_DOUGH, BROWN_SUGAR_ROLL_DOUGH,
                PINEAPPLE_BUN_DOUGH, CROISSANT_DOUGH, SALT_CROISSANT_DOUGH, BAGUETTE_DOUGH,
                CIABATTA_DOUGH, FOCACCIA_DOUGH, COUNTRY_BREAD_DOUGH, MOULD_TOAST_DOUGH,
                MOULD_CHEESE_COCOA_TOAST_DOUGH, RICE_BREAD_DOUGH, DOUGH_CRAFTING,

                OVEN, TOASTER, BLENDER, FERMENTATION_BOX, FERMENTATION_TANK,
                DOUGH_CRAFTING_TABLE, CUPBOARD, WOOD_COUNTER, COFFEE_TABLE,
                CHEESE_TANK, MILK_TANK, YEAST_TANK, MOKA_POT, MIX_BLOCK, MOULD, DRINK_CUP,

                BREAD_RACK, GLASS_BREAD_RACK, BREAD_BASKET, GLASS_CABINET_DOOR, MENU,
                CASH_REGISTER_COMPUTER, FLOUR_BAG, WHOLE_WHEAT_FLOUR_BAG, WOOD_TRAY,
                SOFA_LIGHT_GRAY, SOFA_RED, SOFA_WHITE,

                TOAST, CHEESE_COCOA_TOAST,
                BAGEL, BAGUETTE, CROISSANT, ROUND_BREAD, RICE_BREAD, WHOLE_WHEAT_BAGEL,
                PINEAPPLE_BUN, FOCACCIA, CIABATTA, EGG_TART, SALT_CROISSANT, COUNTRY_BREAD,
                BROWN_SUGAR_ROLL, BAGEL_FILLED_SAUCE, BAGUETTE_WITH_FILLING, BERRY_BAGEL,
                CHEESE_CREAM_BREAD, DIRTY_CHOCO_CROISSANT, FLAT_CROISSANT,
                MEAT_FLOSS_BREAD_ROLL, PINEAPPLE_OIL, TARO_SALT_YOLK_BREAD,
                TOMATO_CHEESE_CROISSANT_SANDWICH, CREAM_BINGLE_COFFEE, MATCHA_PARFAIT,
                SCONE, SLICED_TOAST, BAKE_SLICED_TOAST, HONEY_BUTTER_SPREAD_TOAST,
                SLICED_CHEESE_COCOA_TOAST, COUNTRY_BREAD_SLICE, HONEY_BUTTER_SPREAD_COUNTRY_BREAD,

                COFFEE_PLANT, TOMATO,

                RAW_SALT_BLOCK, SALT_ORE, DEEPSLATE_SALT_ORE
        };
    }

    private static Item register(String id) {
        return register(id, new Item.Properties());
    }

    private static Item register(String id, Item.Properties properties) {
        return register(id, new Item(properties));
    }

    private static Item register(String id, Item item) {
        ResourceLocation key = new ResourceLocation(BakeriesMod.MODID, id);
        return Registry.register(BuiltInRegistries.ITEM, key, item);
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
