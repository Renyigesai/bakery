package com.renyigesai.bakeries.util.measurer;

import com.renyigesai.bakeries.init.BakeriesMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CakeEffectRules {
    private static final List<Function<List<MobEffectInstance>, List<MobEffectInstance>>> RULES = new ArrayList<>();

    public static List<MobEffectInstance> effectIteration(List<MobEffectInstance> mobEffectInstances){
        for (Function<List<MobEffectInstance>, List<MobEffectInstance>> rule : RULES) {
            mobEffectInstances = rule.apply(mobEffectInstances);
        }
        return mobEffectInstances;
    }

    public static void registerRule(Function<List<MobEffectInstance>, List<MobEffectInstance>> rule) {
        RULES.add(rule);
    }

    public static List<Function<List<MobEffectInstance>, List<MobEffectInstance>>> getRules() {
        return RULES;
    }

    public static List<MobEffectInstance> tooDisgusting(List<MobEffectInstance> rule){
        List<MobEffect> effects = new ArrayList<>();
        for (MobEffectInstance mobEffectInstance : rule) {
            effects.add(mobEffectInstance.getEffect());
        }

        if (effects.contains(BakeriesMobEffects.BERRY_SOUR.get()) && effects.contains(BakeriesMobEffects.TEA_ASTRINGENT.get()) && effects.contains(BakeriesMobEffects.COCOA_MANIA.get())){
            int end = rule.size() - 1;

            MobEffect effect = rule.get(end).getEffect();

            int amplifier = rule.get(end).getAmplifier();
            amplifier ++;

            int duration = rule.get(end).getDuration() * 2;
            System.out.println(duration + " " + amplifier);
            rule.set(end,new MobEffectInstance(effect,duration,amplifier));
        }
        return rule;
    }

//    public static List<MobEffectInstance> amplification(List<MobEffectInstance> rule){
//
//    }
}
