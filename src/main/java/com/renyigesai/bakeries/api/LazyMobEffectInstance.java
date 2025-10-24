package com.renyigesai.bakeries.api;

import net.minecraft.world.effect.MobEffect;

import java.util.function.Supplier;
public class LazyMobEffectInstance {
    public final Supplier<MobEffect> effect;
    private final int duration;
    private final int amplifier;

    public LazyMobEffectInstance(Supplier<MobEffect> effect, int duration) {
        this.effect = effect;
        this.duration = duration;
        this.amplifier = 0;
    }

    public LazyMobEffectInstance(Supplier<MobEffect> effect, int duration,int amplifier) {
        this.effect = effect;
        this.duration = duration;
        this.amplifier = Math.min(amplifier, 255);

    }

    public Supplier<MobEffect> getEffect() {
        return effect;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmplifier() {
        return amplifier;
    }
}
