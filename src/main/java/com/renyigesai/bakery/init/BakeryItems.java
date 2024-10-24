package com.renyigesai.bakery.init;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.item.BaguetteItem;
import com.renyigesai.bakery.item.BakeryBlockFoodItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeryItems {

    public static String SWEET_DOUGH = "raw_item.tips.bakery.sweet_dough";
    public static String SALTED_DOUGH = "raw_item.tips.bakery.salted_dough";
    public static String WHOLE_WHEAT_DOUGH = "raw_item.tips.bakery.whole_wheat_dough";
    public static String TEMPERATURE_100 ="§7100°C";
    public static String TEMPERATURE_150 ="§7150°C";
    public static String TEMPERATURE_200 ="§7200°C";
    public static String TEMPERATURE_250 ="§7250°C";
    public static String TEMPERATURE_300 ="§7300°C";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BakeryMod.MODID);

    public static final RegistryObject<Item> FLOUR = ITEMS.register("flour",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLOUR_RYE = ITEMS.register("flour_rye",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> SALT = ITEMS.register("salt",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> BUTTER_CUBE = ITEMS.register("butter_cube",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> BROWN_SUGAR_CUBE = ITEMS.register("brown_sugar_cube",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> BAGEL_DOUGH = ITEMS.register("bagel_dough",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> BAGUETTE_DOUGH = ITEMS.register("baguette_dough",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> CINNAMON_ROLL_DOUGH = ITEMS.register("cinnamon_roll_dough",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> COUNTRY_BREAD_DOUGH = ITEMS.register("country_bread_dough",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> CROISSANT_DOUGH = ITEMS.register("croissant_dough",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> PINEAPPLE_BUN_DOUGH = ITEMS.register("pineapple_bun_dough",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_EGG_TART = ITEMS.register("raw_egg_tart",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_PUMPKIN_PIE = ITEMS.register("raw_pumpkin_pie",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_TARE_CRUST = ITEMS.register("raw_tare_crust",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> ROUND_BREAD_DOUGH = ITEMS.register("round_bread_dough",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> SALT_CROISSANT_DOUGH = ITEMS.register("salt_croissant_dough",() ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> TART_SHELL = ITEMS.register("tart_shell",() ->
            new Item(new Item.Properties()));

    //Bread Items
    public static final RegistryObject<Item> BAGEL = ITEMS.register("bagel",() ->
            new BakeryBlockFoodItem(BakeryBlocks.BAGEL_BLOCK,foodItem(BakeryFoods.BAGEL)));

    public static final RegistryObject<Item> BAGUETTE = ITEMS.register("baguette",() ->
            new BaguetteItem(foodItem(BakeryFoods.BAGUETTE)));

    public static final RegistryObject<Item> CINNAMON_ROLL = ITEMS.register("cinnamon_roll",() ->
            new BakeryBlockFoodItem(BakeryBlocks.CINNAMON_ROLL_BLOCK,foodItem(BakeryFoods.CINNAMON_ROLL)));

    public static final RegistryObject<Item> COUNTRY_BREAD = ITEMS.register("country_bread",() ->
            new BakeryBlockFoodItem(BakeryBlocks.COUNTRY_BREAD_BLOCK,foodItem(BakeryFoods.COUNTRY_BREAD)));

    public static final RegistryObject<Item> CROISSANT = ITEMS.register("croissant",() ->
            new BakeryBlockFoodItem(BakeryBlocks.CROISSANT_BLOCK,foodItem(BakeryFoods.CROISSANT)));

    public static final RegistryObject<Item> PINEAPPLE_BUN = ITEMS.register("pineapple_bun",() ->
            new BakeryBlockFoodItem(BakeryBlocks.PINEAPPLE_BUN_BLOCK,foodItem(BakeryFoods.PINEAPPLE_BUN)));

    public static final RegistryObject<Item> ROUND_BREAD = ITEMS.register("round_bread",() ->
            new BakeryBlockFoodItem(BakeryBlocks.ROUND_BREAD_BLOCK,foodItem(BakeryFoods.ROUND_BREAD)));

    public static final RegistryObject<Item> SALT_CROISSANT = ITEMS.register("salt_croissant",() ->
            new BakeryBlockFoodItem(BakeryBlocks.SALT_CROISSANTt_BLOCK,foodItem(BakeryFoods.SALT_CROISSANT)));

    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food);
    }

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
