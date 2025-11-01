package com.renyigesai.bakeries.common.potion;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;

public class EnjoyMobEffect extends MobEffect {
    public EnjoyMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -13312);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }


    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        if (pLivingEntity.getHealth() < pLivingEntity.getMaxHealth()) {
            float livingEntityHealth = pLivingEntity.getHealth();
            int lv = pAmplifier==0?1:pAmplifier;
            if (livingEntityHealth > 0.0F) {
                pLivingEntity.setHealth(livingEntityHealth + (float) 0.05*lv);
            }
        }

        Iterator<MobEffectInstance> itr = pLivingEntity.getActiveEffects().iterator();
        ArrayList<Holder<MobEffect>> compatibleEffects = new ArrayList<>();
        MobEffectInstance selectedEffect;
        while(itr.hasNext()) {
            selectedEffect = itr.next();
            MobEffect effect = selectedEffect.getEffect().value();

            if (effect.getCategory() == MobEffectCategory.HARMFUL) {
                compatibleEffects.add(selectedEffect.getEffect());
            }
        }
        if (!compatibleEffects.isEmpty()){
            for (Holder<MobEffect> compatibleEffect : compatibleEffects) {
                    pLivingEntity.removeEffect(compatibleEffect);
                    break;
            }
        }
        return true;
    }

}
