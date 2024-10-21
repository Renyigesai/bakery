package com.renyigesai.bakery.item;

import com.renyigesai.bakery.bakery;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, bakery.MODID);

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
            new Item(foodItem(ModFoods.BAGEL)));

    public static final RegistryObject<Item> BAGUETTE = ITEMS.register("baguette",() ->
            new BaguetteItem(foodItem(ModFoods.BAGUETTE)));

    public static final RegistryObject<Item> CINNAMON_ROLL = ITEMS.register("cinnamon_roll",() ->
            new Item(foodItem(ModFoods.cinnamon_roll)));

    public static final RegistryObject<Item> COUNTRY_BREAD = ITEMS.register("country_bread",() ->
            new Item(foodItem(ModFoods.country_bread)));

    public static final RegistryObject<Item> CROISSANT = ITEMS.register("croissant",() ->
            new Item(foodItem(ModFoods.croissant)));

    public static final RegistryObject<Item> PINEAPPLE_BUN = ITEMS.register("pineapple_bun",() ->
            new Item(foodItem(ModFoods.pineapple_bun)));

    public static final RegistryObject<Item> ROUND_BREAD = ITEMS.register("round_bread",() ->
            new Item(foodItem(ModFoods.round_bread)));

    public static final RegistryObject<Item> SALT_CROISSANT = ITEMS.register("salt_croissant",() ->
            new Item(foodItem(ModFoods.salt_croissant)));

    //SALT CROISSANT




    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food);
    }




    //bagel_dough





    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);


    }
}
