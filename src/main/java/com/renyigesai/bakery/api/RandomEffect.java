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
    public static boolean raise(double p1) {
        Random rand = new Random();
        double p = rand.nextDouble();
        return !(p > p1);
    }
}
