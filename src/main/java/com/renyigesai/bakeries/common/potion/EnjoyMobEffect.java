package com.renyigesai.bakeries.common.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class EnjoyMobEffect extends BakeriesMobEffect {
    public EnjoyMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -13312);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return amplifier >= 0;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        super.applyEffectTick(livingEntity, amplifier);
        if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
            livingEntity.heal(0.05F * Math.max(1, amplifier));
        }

        List<MobEffect> harmfulEffects = new ArrayList<>();
        for (MobEffectInstance effectInstance : livingEntity.getActiveEffects()) {
            MobEffect effect = effectInstance.getEffect();
            if (effect.getCategory() == MobEffectCategory.HARMFUL) {
                harmfulEffects.add(effect);
            }
        }
        if (!harmfulEffects.isEmpty()) {
            livingEntity.removeEffect(harmfulEffects.get(0));
        }
    }
}
