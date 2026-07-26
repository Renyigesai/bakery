package com.renyigesai.bakeries.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class BakeriesFoodProperties {
    public static final FoodProperties BAGEL;
    public static final FoodProperties WHOLE_WHEAT_BAGEL;
    public static final FoodProperties BAGUETTE;
    public static final FoodProperties BROWN_SUGAR_ROLL;
    public static final FoodProperties COUNTRY_BREAD;
    public static final FoodProperties CROISSANT;
    public static final FoodProperties CIABATTA;
    public static final FoodProperties PINEAPPLE_BUN;
    public static final FoodProperties ROUND_BREAD;
    public static final FoodProperties SALT_CROISSANT;
    public static final FoodProperties SLICED_TOAST;
    public static final FoodProperties BERRY_BREAD;
    public static final FoodProperties COUNTRY_BREAD_SLICE;
    public static final FoodProperties OLIVE;
    public static final FoodProperties TOMATO;
    public static final FoodProperties CHEESE_CUBE;
    public static final FoodProperties SLICED_CHEESE_COCOA_TOAST;
    public static final FoodProperties MEAT_FLOSS_BREAD;
    public static final FoodProperties MEAT_FLOSS;
    public static final FoodProperties ICED_AMERICAN;
    public static final FoodProperties ICED_LATTE;
    public static final FoodProperties BROWN_SUGAR_LATTE;
    public static final FoodProperties BUTTER_LATTE;
    public static final FoodProperties FOCACCIA;
    public static final FoodProperties DIRTY_CHOCO_CROISSANT;
    public static final FoodProperties BAGUETTE_WITH_FILLING;
    public static final FoodProperties CREAM_BINGLE_COFFEE;
    public static final FoodProperties TOMATO_CHEESE_CROISSANT_SANDWICH;
    public static final FoodProperties BERRY_BAGEL;
    public static final FoodProperties HONEY_BUTTER_SPREAD_TOAST;
    public static final FoodProperties HONEY_BUTTER_SPREAD_COUNTRY_BREAD;
    public static final FoodProperties SCONE;
    public static final FoodProperties CUP_CAKE;
    public static final FoodProperties CAKE_ROLL;
    public static final FoodProperties FOAMED_CREAM;
    public static final FoodProperties SLICED_POUND_CAKE;
    public static final FoodProperties CREAM_CAKE_CUBE;
    public static final FoodProperties CHEESE_CREAM_BREAD;
    public static final FoodProperties MATCHA_LATTE;
    public static final FoodProperties MATCHA_PARFAIT;
    public static final FoodProperties TARO_MILK;
    public static final FoodProperties BAGEL_FILLED_SAUCE;
    public static final FoodProperties EGG_TART;
    public static final FoodProperties SALMON_SANDWICH;
    public static final FoodProperties PINEAPPLE_OIL;
    public static final FoodProperties FLAT_CROISSANT;
    public static final FoodProperties TARO;
    public static final FoodProperties COOKED_TARO;
    public static final FoodProperties MASHED_TARO;
    public static final FoodProperties TARO_SALT_YOLK_BREAD;
    public static final FoodProperties TARO_CAKE;
    public static final FoodProperties COCOA_CREAM;

    /**模组联动食物属性*/
    public static final FoodProperties RICE_BREAD;
    public static final FoodProperties RICE_BREAD_FARMERSDELIGHT;

    public static final FoodProperties ORANGE_AMERICAN;

    public static final FoodProperties GARLIC_FLAVORED_BAGUETTE;

    public static final FoodProperties YUNTUI_MOONCAKE;

    public static final FoodProperties CREAM_MUSHROOM_SOUP_WITH_BAGUETTE;
    public static final FoodProperties CREAM_MUSHROOM_SOUP_WITH_BAGUETTE_KALEIDOSCOPE_COOKERY;

    public static final FoodProperties DONGPO_PORK_HAMBURG;
    public static final FoodProperties DONGPO_PORK_HAMBURG_KALEIDOSCOPE_COOKERY;


    static {
        BAGEL = new FoodProperties.Builder().nutrition(6).saturationMod(0.5f).build();

        WHOLE_WHEAT_BAGEL = new FoodProperties.Builder().nutrition(8).saturationMod(0.35f)
                .effect(new MobEffectInstance(MobEffects.SATURATION, 20, 0), 1.0F).build();

        BAGUETTE = new FoodProperties.Builder().nutrition(8).saturationMod(0.25f).build();

        BROWN_SUGAR_ROLL = new FoodProperties.Builder().nutrition(7).saturationMod(0.5f)
                .effect(()-> new MobEffectInstance(BakeriesMobEffects.ENJOY.get(), 600, 0), 1.0F).build();

        COUNTRY_BREAD = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();

        CROISSANT = new FoodProperties.Builder().nutrition(6).saturationMod(1f)
                .effect(()-> new MobEffectInstance(BakeriesMobEffects.ENJOY.get(),1200),1F)
                .build();

        PINEAPPLE_BUN = new FoodProperties.Builder().nutrition(6).saturationMod(1.5f)
                .effect(()-> new MobEffectInstance(BakeriesMobEffects.ENJOY.get(),1200),1F)
                .build();

        ROUND_BREAD = new FoodProperties.Builder().nutrition(3).saturationMod(0.6f).build();

        SALT_CROISSANT = new FoodProperties.Builder().nutrition(6).saturationMod(1.0f)
                .effect(()-> new MobEffectInstance(BakeriesMobEffects.ENJOY.get(),600),1F)
                .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0F).build();

        SLICED_TOAST = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).alwaysEat()
                .build();

        SLICED_CHEESE_COCOA_TOAST = new FoodProperties.Builder().nutrition(4).saturationMod(0.8f)
                .effect(() -> new MobEffectInstance(BakeriesMobEffects.COCOA_MANIA.get(),600,0),1f)
                .effect(() -> new MobEffectInstance(BakeriesMobEffects.CHEESE_POWER.get(),600,0),1f).alwaysEat().build();

        BERRY_BREAD = new FoodProperties.Builder().nutrition(3).saturationMod(0.7f).build();

        COUNTRY_BREAD_SLICE = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).fast().build();

        CIABATTA = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();

        MEAT_FLOSS_BREAD = new FoodProperties.Builder().nutrition(7).saturationMod(0.75f).alwaysEat().build();

        FOCACCIA = new FoodProperties.Builder().nutrition(8).saturationMod(1f)
                .effect(()-> new MobEffectInstance(BakeriesMobEffects.ENJOY.get(),1200,1),1F).build();

        OLIVE = new FoodProperties.Builder().nutrition(2).saturationMod(0.5f).build();

        TOMATO = new FoodProperties.Builder().nutrition(2).saturationMod(0.5f).build();

        CHEESE_CUBE = new FoodProperties.Builder().nutrition(1).saturationMod(1f).build();

        MEAT_FLOSS = new FoodProperties.Builder().nutrition(2).saturationMod(0.8f).fast().build();

        ICED_AMERICAN = new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600),1f).alwaysEat().build();

        ICED_LATTE = new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600),1f).alwaysEat().build();

        BROWN_SUGAR_LATTE = new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600),1f).alwaysEat().build();
        BUTTER_LATTE = new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600),1f).effect(new MobEffectInstance(MobEffects.SATURATION,5),1f).alwaysEat().build();

        CREAM_BINGLE_COFFEE = new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600),1f).nutrition(1).saturationMod(0.5f).alwaysEat().build();

        MATCHA_LATTE = new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600),1f).effect(new MobEffectInstance(MobEffects.DIG_SPEED,600),1f).effect(()-> new MobEffectInstance(BakeriesMobEffects.TEA_ASTRINGENT.get(),1200),1f).alwaysEat().build();
        MATCHA_PARFAIT = new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600),1f).effect(new MobEffectInstance(MobEffects.DIG_SPEED,600),1f).effect(()-> new MobEffectInstance(BakeriesMobEffects.TEA_ASTRINGENT.get(),1200),1f).nutrition(9).saturationMod(0.5f).alwaysEat().build();
        TARO_MILK = new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600),1f).effect(new MobEffectInstance(MobEffects.DIG_SPEED,600),1f).nutrition(9).saturationMod(0.5f).alwaysEat().build();

        DIRTY_CHOCO_CROISSANT = new FoodProperties.Builder().nutrition(6).saturationMod(1f)
                .effect(()-> new MobEffectInstance(BakeriesMobEffects.ENJOY.get(),1200),1F)
                .effect(()-> new MobEffectInstance(BakeriesMobEffects.COCOA_MANIA.get(),1200),1F)
                .build();

        BAGUETTE_WITH_FILLING = new FoodProperties.Builder().nutrition(13).saturationMod(0.4f).alwaysEat().build();

        TOMATO_CHEESE_CROISSANT_SANDWICH = new FoodProperties.Builder().nutrition(10).saturationMod(0.9f).effect(()->new MobEffectInstance(BakeriesMobEffects.ENJOY.get(),6000,2),1F).alwaysEat().build();

        BERRY_BAGEL = new FoodProperties.Builder().nutrition(8).saturationMod(0.625f).effect(new MobEffectInstance(MobEffects.REGENERATION,1200),1F).effect(()-> new MobEffectInstance(BakeriesMobEffects.BERRY_SOUR.get(),600),1F).alwaysEat().build();

        HONEY_BUTTER_SPREAD_TOAST = new FoodProperties.Builder().nutrition(8).saturationMod(0.5f).alwaysEat().build();

        HONEY_BUTTER_SPREAD_COUNTRY_BREAD = new FoodProperties.Builder().nutrition(6).saturationMod(0.65f).alwaysEat().build();

        SCONE = new FoodProperties.Builder().nutrition(4).saturationMod(0.5f).alwaysEat().build();

        CUP_CAKE = new FoodProperties.Builder().nutrition(4).saturationMod(0.5f).effect(()->new MobEffectInstance(BakeriesMobEffects.SOFT.get(),1200),1f).alwaysEat().build();

        CAKE_ROLL = new FoodProperties.Builder().nutrition(12).saturationMod(0.3f).effect(()->new MobEffectInstance(BakeriesMobEffects.SOFT.get(),1200),1f).alwaysEat().build();

        FOAMED_CREAM = new FoodProperties.Builder().nutrition(1).saturationMod(1f).alwaysEat().build();

        SLICED_POUND_CAKE = new FoodProperties.Builder().nutrition(1).saturationMod(1f).alwaysEat().build();

        CREAM_CAKE_CUBE = new FoodProperties.Builder().nutrition(5).saturationMod(0.4f).effect(()->new MobEffectInstance(BakeriesMobEffects.SOFT.get(),1200),1f).effect(new MobEffectInstance(MobEffects.REGENERATION,1200),1f).alwaysEat().build();

        CHEESE_CREAM_BREAD = new FoodProperties.Builder().nutrition(6).saturationMod(1f).effect(()->new MobEffectInstance(BakeriesMobEffects.CHEESE_POWER.get(),1200),1f).alwaysEat().build();

        BAGEL_FILLED_SAUCE = new FoodProperties.Builder().nutrition(12).saturationMod(0.4f).alwaysEat().build();

        EGG_TART = new FoodProperties.Builder().nutrition(8).saturationMod(0.5f).effect(new MobEffectInstance(MobEffects.HEAL,1),1f).build();

        PINEAPPLE_OIL = new FoodProperties.Builder().nutrition(8).saturationMod(1.5f).effect(()-> new MobEffectInstance(BakeriesMobEffects.ENJOY.get(),1800,1),1F).alwaysEat().build();

        FLAT_CROISSANT = new FoodProperties.Builder().nutrition(6).saturationMod(1f).effect(()-> new MobEffectInstance(BakeriesMobEffects.ENJOY.get(),600),1F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,600),1f).build();

        TARO = new FoodProperties.Builder().nutrition(1).saturationMod(1f).effect(()-> new MobEffectInstance(MobEffects.POISON,200,2),1f).build();

        COOKED_TARO = new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build();

        MASHED_TARO = new FoodProperties.Builder().nutrition(1).saturationMod(0.5f).build();

        TARO_SALT_YOLK_BREAD = new FoodProperties.Builder().nutrition(10).saturationMod(1f).effect(()-> new MobEffectInstance(BakeriesMobEffects.ENJOY.get(),1800),1F).alwaysEat().build();

        TARO_CAKE = new FoodProperties.Builder().nutrition(8).saturationMod(1f).effect(()-> new MobEffectInstance(BakeriesMobEffects.SOFT.get(),1800),1F).alwaysEat().build();

        COCOA_CREAM = new FoodProperties.Builder().nutrition(1).saturationMod(1f).effect(()-> new MobEffectInstance(BakeriesMobEffects.COCOA_MANIA.get(),400),1F).alwaysEat().build();

        /*模组联动食物属性*/
        RICE_BREAD = new FoodProperties.Builder().nutrition(12).saturationMod(0.4f).effect(()-> new MobEffectInstance(BakeriesMobEffects.COCOA_MANIA.get(),600),1f).build();
        RICE_BREAD_FARMERSDELIGHT = new FoodProperties.Builder().nutrition(12).saturationMod(0.4f).effect(()-> new MobEffectInstance(ModEffects.COMFORT.get(),6000),1f).effect(()-> new MobEffectInstance(BakeriesMobEffects.ENJOY.get(),600),1f).build();

        SALMON_SANDWICH = new FoodProperties.Builder().nutrition(10).saturationMod(0.85f).effect(new MobEffectInstance(MobEffects.REGENERATION,600),1f).alwaysEat().build();

        ORANGE_AMERICAN = new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600),1f).effect(new MobEffectInstance(MobEffects.REGENERATION,600),1f).alwaysEat().build();

        GARLIC_FLAVORED_BAGUETTE = new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,600),1f).nutrition(8).saturationMod(0.5f).build();

        YUNTUI_MOONCAKE = new FoodProperties.Builder().nutrition(3).saturationMod(1.0f).build();

        CREAM_MUSHROOM_SOUP_WITH_BAGUETTE = new FoodProperties.Builder().nutrition(20).saturationMod(0.75F).alwaysEat().build();
        CREAM_MUSHROOM_SOUP_WITH_BAGUETTE_KALEIDOSCOPE_COOKERY = new FoodProperties.Builder().nutrition(20).saturationMod(0.75F).effect(()-> new MobEffectInstance(com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects.WARMTH.get(),6000),1f).alwaysEat().build();

        DONGPO_PORK_HAMBURG = new FoodProperties.Builder().nutrition(15).saturationMod(0.5F).alwaysEat().build();
        DONGPO_PORK_HAMBURG_KALEIDOSCOPE_COOKERY = new FoodProperties.Builder().nutrition(15).saturationMod(0.5F).effect(()-> new MobEffectInstance(com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects.SATIATED_SHIELD.get(),1200),1F).alwaysEat().build();

    }
}
