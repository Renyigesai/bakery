package com.renyigesai.bakeries.common.potion;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class SoftMobEffect extends MobEffect {
    public SoftMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -3972305);
    }

//    @EventBusSubscriber(modid = BakeriesMod.MODID)
    public static class SoftPotionEffect{
//        @SubscribeEvent
        public static void on(/*LivingDamageEvent event*/){
//            Entity source = event.getSource().getEntity();
//            if (source != null && entity  != null && entity.hasEffect(BakeriesMobEffects.SOFT.get())){
//                Vec3 vec3 = entity.getDeltaMovement();
//                double s = 1.5;
//                Vec3 vec31 = (new Vec3(Mth.sin(entity.getYRot() * ((float)Math.PI / 180F)), 0.8D,(-Mth.cos(entity.getYRot() * ((float)Math.PI / 180F))))).normalize().scale(s);
//                source.setDeltaMovement(vec3.x / 2.0D - vec31.x, entity.onGround() ? Math.min(0.4D, vec3.y / 2.0 + s) : vec3.y, vec3.z / 2.0D - vec31.z);
//            }
        }
    }
}
