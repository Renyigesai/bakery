package com.renyigesai.bakeries.common.potion;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class CocoaManiaMobEffect extends MobEffect {

    public CocoaManiaMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -3972305);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }



    @EventBusSubscriber(modid = BakeriesMod.MODID)
    public static class CocoaManiaPotionEffect{
        @SubscribeEvent
        @SuppressWarnings("unused")
        public static void onChocolateMania(LivingDeathEvent event){
            Entity attacker = event.getSource().getEntity();
            Entity entity = event.getEntity();
            if (attacker instanceof LivingEntity livingEntity && livingEntity.hasEffect(BakeriesMobEffects.COCOA_MANIA)){
/*                if (BakeriesConfig.cocoaManinDamageEffect) {*/
                    entity.invulnerableTime = 0;
                /*}*/
            }
        }
    }
}
