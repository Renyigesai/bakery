package com.renyigesai.bakery.init;

import com.mojang.blaze3d.shaders.Effect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
        BAGEL = new FoodProperties.Builder().nutrition(4).saturationMod(1.25f).build();

        BAGUETTE = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();

        CINNAMON_ROLL = new FoodProperties.Builder().nutrition(3).saturationMod(1.3f)
                .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0F).build();

        COUNTRY_BREAD = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();

        CROISSANT = new FoodProperties.Builder().nutrition(3).saturationMod(3.0f).build();

        PINEAPPLE_BUN = new FoodProperties.Builder().nutrition(3).saturationMod(1.3f)
                .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0F).build();

        ROUND_BREAD = new FoodProperties.Builder().nutrition(3).saturationMod(0.6f).build();

        SALT_CROISSANT = new FoodProperties.Builder().nutrition(3).saturationMod(2.0f)
                .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0F)
                .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0), 1.0F).build();

        SLICED_TOAST = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();
    }
}
