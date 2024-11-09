package com.renyigesai.bakery.api;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.Random;

public class RandomEffect {

    public MobEffectInstance getRandomEffect(){
        if (raise(0.5)){
            return new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0);
        }else {
            return new MobEffectInstance(MobEffects.SATURATION, 600, 0);
        }
    }
    /**
     * 生成一个在指定范围内的随机整数。
     *
     * @param max 范围的最大值（包含）。
     * @param min 范围的最小值（包含）。
     * @return 在 [min, max] 范围内的一个随机整数。
     */
    public static Integer random(int max, int min) {
        Random rand = new Random();
        int value = 0;
        for (int i = 0; i < max; i++) {
            value = rand.nextInt(max - min + 1) + min;
        }
        return value;
    }
    /**
     * 根据给定的概率生成一个布尔值。
     *
     * @param p1 生成 true 的概率，范围在 [0, 1] 之间。
     * @return 如果生成的随机数小于或等于 p1，则返回 true；否则返回 false。
     */
    public static boolean raise(double p1) {
        Random rand = new Random();
        double p = rand.nextDouble();
        return !(p > p1);
    }
}
