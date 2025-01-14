package com.renyigesai.bakeries.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;


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
//    public static final FoodProperties CIABATTA;

    static {
        BAGEL = new FoodProperties.Builder().nutrition(4).saturationMod(1.25f).build();

        WHOLE_WHEAT_BAGEL = new FoodProperties.Builder().nutrition(5).saturationMod(0.6f)
                .effect(new MobEffectInstance(MobEffects.SATURATION,20,0),1.0F).build();

        BAGUETTE = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();

        BROWN_SUGAR_ROLL = new FoodProperties.Builder().nutrition(3).saturationMod(1.3f)
                .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0F).build();

        COUNTRY_BREAD = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();

        CROISSANT = new FoodProperties.Builder().nutrition(3).saturationMod(3.0f).build();

        PINEAPPLE_BUN = new FoodProperties.Builder().nutrition(3).saturationMod(1.3f).effect(
                new MobEffectInstance(MobEffects.REGENERATION,200,0)
                ,1.0F).build();

        ROUND_BREAD = new FoodProperties.Builder().nutrition(3).saturationMod(0.6f).build();

        SALT_CROISSANT = new FoodProperties.Builder().nutrition(3).saturationMod(2.0f)
                .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0F)
                .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0F).build();

        SLICED_TOAST = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();

        BERRY_BREAD = new FoodProperties.Builder().nutrition(3).saturationMod(0.7f).build();

        COUNTRY_BREAD_SLICE = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).fast().build();

        CIABATTA = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();

        OLIVE = new FoodProperties.Builder().nutrition(2).saturationMod(0.5f).build();

        TOMATO = new FoodProperties.Builder().nutrition(2).saturationMod(0.5f).build();

//        CIABATTA = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();


    }

//    public static class EffectPropertie{
//        public static final EffectProperties PINEAPPLE_BUN;
//
//        static {
//            PINEAPPLE_BUN = new EffectProperties.Builder().effect(() -> new RandomEffect().getRandomEffect(),1.0F).build();
//        }
//    }
}
