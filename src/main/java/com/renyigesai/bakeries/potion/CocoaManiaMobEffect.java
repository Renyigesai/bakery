package com.renyigesai.bakeries.potion;

import com.renyigesai.bakeries.config.BakeriesConfig;
import com.renyigesai.bakeries.init.BakeriesMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class CocoaManiaMobEffect extends MobEffect {

    public CocoaManiaMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -3972305);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
    @Mod.EventBusSubscriber
    public static class CocoaManiaPotionEffect{
        @SubscribeEvent
        @SuppressWarnings("unused")
        public static void onChocolateMania(LivingHurtEvent event){
            Entity attacker = event.getSource().getEntity();
            Entity entity = event.getEntity();
            if (attacker instanceof LivingEntity livingEntity && livingEntity.hasEffect(BakeriesMobEffects.COCOA_MANIA.get())){
                if (BakeriesConfig.cocoaManinDamageEffect) {
                    entity.invulnerableTime = 0;
                }
            }
        }
    }
}
