package com.renyigesai.bakeries.api;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import java.util.function.Supplier;

public class LazyMobEffectInstance {
    public Holder<MobEffect> effect;
    private int duration;
    private int amplifier;

    public LazyMobEffectInstance(Holder<MobEffect> effect, int duration) {
        this.effect = effect;
        this.duration = duration;
        this.amplifier = 0;
    }

    public LazyMobEffectInstance(Holder<MobEffect> effect, int duration, int amplifier) {
        this.effect = effect;
        this.duration = duration;
        this.amplifier = Math.min(amplifier, 255);

    }

    public LazyMobEffectInstance() {
    }

    public Holder<MobEffect> getEffect() {
        return effect;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public static LazyMobEffectInstance of(){
        return new LazyMobEffectInstance();
    }
}
