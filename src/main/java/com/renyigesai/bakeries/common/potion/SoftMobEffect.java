package com.renyigesai.bakeries.common.potion;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesMobEffects;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class SoftMobEffect extends MobEffect {
    public SoftMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -3972305);
    }

    @EventBusSubscriber(modid = BakeriesMod.MODID)
    public static class SoftPotionEffect{
        @SubscribeEvent
        public static void onSoft(LivingIncomingDamageEvent event){
            LivingEntity entity = event.getEntity();
            Entity source = event.getSource().getEntity();
//            Player player = event.getEntity();
//            Entity source = event.getTarget();
            if (source != null && (entity instanceof LivingEntity living && living.hasEffect(BakeriesMobEffects.SOFT))){
                Vec3 vec3 = living.getDeltaMovement();
                double s = 1.5;
                Vec3 vec31 = (new Vec3(Mth.sin(living.getYRot() * ((float)Math.PI / 180F)), 0.8D,(-Mth.cos(living.getYRot() * ((float)Math.PI / 180F))))).normalize().scale(s);
                source.setDeltaMovement(vec3.x / 2.0D - vec31.x, living.onGround() ? Math.min(0.4D, vec3.y / 2.0 + s) : vec3.y, vec3.z / 2.0D - vec31.z);
            }
        }
    }
}
