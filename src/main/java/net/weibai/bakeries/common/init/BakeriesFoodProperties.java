package net.weibai.bakeries.common.init;

import net.minecraft.world.food.FoodProperties;

public class BakeriesFoodProperties {
    public static final FoodProperties ROUND_BREAD;
    static {
        ROUND_BREAD =  new FoodProperties.Builder().nutrition(3).saturationModifier(0.6f).build();
    }
}
