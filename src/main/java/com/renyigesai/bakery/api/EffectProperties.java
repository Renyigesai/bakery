package com.renyigesai.bakery.api;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;
import java.util.function.Supplier;

public class EffectProperties {
    private final List<Pair<java.util.function.Supplier<MobEffectInstance>, Float>> effects;
    private EffectProperties(EffectProperties.Builder builder) {
        this.effects = builder.effects;
    }
    public List<Pair<MobEffectInstance, Float>> getEffects() {
        return this.effects.stream().map(pair -> Pair.of(pair.getFirst() != null ? pair.getFirst().get() : null, pair.getSecond())).collect(java.util.stream.Collectors.toList());
    }
    public static class Builder {
        private final List<Pair<Supplier<MobEffectInstance>, Float>> effects = Lists.newArrayList();


        public EffectProperties.Builder effect (java.util.function.Supplier < MobEffectInstance > effectIn,float probability){
            this.effects.add(Pair.of(effectIn, probability));
            return this;
        }
        @Deprecated
        public EffectProperties.Builder effect(MobEffectInstance pEffect, float pProbability) {
            this.effects.add(Pair.of(() -> pEffect, pProbability));
            return this;
        }
        public EffectProperties build() {
            return new EffectProperties(this);
        }
    }
}
