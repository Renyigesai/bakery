package com.renyigesai.bakeries.api.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;
import java.util.function.Function;

public class CakeEffectRulesRegistrationEvent extends Event {
    private final List<Function<List<MobEffectInstance>, List<MobEffectInstance>>> rules;

    public CakeEffectRulesRegistrationEvent(List<Function<List<MobEffectInstance>, List<MobEffectInstance>>> rules) {
        this.rules = rules;
    }

    public void registerRule(Function<List<MobEffectInstance>, List<MobEffectInstance>> rule) {
        rules.add(rule);
    }

    public List<Function<List<MobEffectInstance>, List<MobEffectInstance>>> getRules() {
        return rules;
    }
}
