package com.renyigesai.bakeries.util.measurer;

import com.renyigesai.bakeries.init.BakeriesMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

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

    /**如果列表内同时存在可可狂热，莓酸和茶涩，则列表内最后一个药水效果等级增长一级*/
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
            rule.set(end,new MobEffectInstance(effect,duration,amplifier));
        }
        return rule;
    }

    /**如果列表内有4个或以上的药水效果，列表内所有药水效果时长增加20%*/
    public static List<MobEffectInstance> amplification(List<MobEffectInstance> rule){
        if (rule.size() >= 4){
            for (int i = 0; i < rule.size(); i++) {
                MobEffectInstance mobEffectInstance = rule.get(i);
                MobEffect effect = mobEffectInstance.getEffect();
                int amplifier = mobEffectInstance.getAmplifier();
                int duration = mobEffectInstance.getDuration();
                int newDuration = duration + (int) (duration * 0.2);
                rule.set(i,new MobEffectInstance(effect,newDuration,amplifier));
            }
        }
        return rule;
    }
}
