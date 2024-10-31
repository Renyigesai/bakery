package com.renyigesai.bakery.init;

import net.minecraft.world.food.FoodProperties;

public class BakeryFoodProperties {
    public static final FoodProperties BAGEL;
    public static final FoodProperties BAGUETTE;
    public static final FoodProperties CINNAMON_ROLL;
    public static final FoodProperties COUNTRY_BREAD;
    public static final FoodProperties CROISSANT;
    public static final FoodProperties PINEAPPLE_BUN;
    public static final FoodProperties ROUND_BREAD;
    public static final FoodProperties SALT_CROISSANT;
    public static final FoodProperties SLICED_TOAST;

    static {
        BAGEL = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();
        BAGUETTE = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();
        CINNAMON_ROLL = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();
        COUNTRY_BREAD = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();
        CROISSANT = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();
        PINEAPPLE_BUN = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();
        ROUND_BREAD = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();
        SALT_CROISSANT = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();
        SLICED_TOAST = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();
    }
}
