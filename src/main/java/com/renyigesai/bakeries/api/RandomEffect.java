package com.renyigesai.bakeries.api;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomEffect {

    public MobEffectInstance getSweetEffect(){
        MobEffectInstance mobEffectInstance0 = new MobEffectInstance(MobEffects.REGENERATION, 600, 2);
        MobEffectInstance mobEffectInstance1 = new MobEffectInstance(MobEffects.HEALTH_BOOST, 600, 0);
        MobEffectInstance mobEffectInstance2 = new MobEffectInstance(MobEffects.HEAL, 100, 0);
        List<MobEffectInstance> effectList = new ArrayList<>(Arrays.asList(mobEffectInstance0,mobEffectInstance1,mobEffectInstance2));
        return effectList.get(random(2,0));
    }

    public MobEffectInstance getSaltyEffect(){
        MobEffectInstance mobEffectInstance0 = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 2);
        MobEffectInstance mobEffectInstance1 = new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0);
        MobEffectInstance mobEffectInstance2 = new MobEffectInstance(MobEffects.ABSORPTION, 100, 0);
        List<MobEffectInstance> effectList = new ArrayList<>(Arrays.asList(mobEffectInstance0,mobEffectInstance1,mobEffectInstance2));
        return effectList.get(random(2,0));
    }

    public MobEffectInstance getWholeWheatEffect(){
        MobEffectInstance mobEffectInstance0 = new MobEffectInstance(MobEffects.SATURATION, 100, 0);
        MobEffectInstance mobEffectInstance1 = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 2);
        MobEffectInstance mobEffectInstance2 = new MobEffectInstance(MobEffects.JUMP, 100, 0);
        List<MobEffectInstance> effectList = new ArrayList<>(Arrays.asList(mobEffectInstance0,mobEffectInstance1,mobEffectInstance2));
        return effectList.get(random(2,0));
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
