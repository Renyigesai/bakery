package com.renyigesai.bakeries.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Iterator;

public class EnjoyMobEffect extends MobEffect {
    public EnjoyMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -13312);
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        if (pLivingEntity.getHealth() < pLivingEntity.getMaxHealth()) {
            float livingEntityHealth = pLivingEntity.getHealth();
            int lv = pAmplifier==0?1:pAmplifier;
            if (livingEntityHealth > 0.0F) {
                pLivingEntity.setHealth(livingEntityHealth + (float) 0.05*lv);
            }
        }

        Iterator<MobEffectInstance> itr = pLivingEntity.getActiveEffects().iterator();
        ArrayList<MobEffect> compatibleEffects = new ArrayList<>();
        MobEffectInstance selectedEffect;
        Level level = pLivingEntity.level();
        while(itr.hasNext()) {
            selectedEffect = itr.next();
            if (selectedEffect.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
                compatibleEffects.add(selectedEffect.getEffect());
            }
        }
        if (!compatibleEffects.isEmpty()){
            for (MobEffect compatibleEffect : compatibleEffects) {
                if (level.random.nextInt(100) == 0) {
                    pLivingEntity.removeEffect(compatibleEffect);
                    break;
                }
            }
        }
    }

    //    @Mod.EventBusSubscriber
//    public static class EnjoyPotionEffect{
//        @SubscribeEvent
//        @SuppressWarnings("unused")
//        public static void onEnjoy(LivingEvent.LivingTickEvent event){
//            Entity attacker = event.getSource().getEntity();
//            Entity entity = event.getEntity();
//            if (attacker instanceof LivingEntity livingEntity && livingEntity.hasEffect(BakeriesMobEffects.COCOA_MANIA.get())){
//                entity.invulnerableTime = 0;
//            }
//        }
//    }
}
