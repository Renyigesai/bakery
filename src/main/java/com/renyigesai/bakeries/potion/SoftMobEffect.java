package com.renyigesai.bakeries.potion;

import com.renyigesai.bakeries.init.BakeriesMobEffects;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class SoftMobEffect extends MobEffect {
    public SoftMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -3972305);
    }

    @Mod.EventBusSubscriber
    public static class SoftPotionEffect{
        @SubscribeEvent
        public static void on(LivingAttackEvent event){
            Entity source = event.getSource().getEntity();
            LivingEntity entity = event.getEntity();
            if (source != null && entity  != null && entity.hasEffect(BakeriesMobEffects.SOFT.get())){
                Vec3 vec3 = entity.getDeltaMovement();
                double s = 1.5;
                Vec3 vec31 = (new Vec3(Mth.sin(entity.getYRot() * ((float)Math.PI / 180F)), 0.8D,(-Mth.cos(entity.getYRot() * ((float)Math.PI / 180F))))).normalize().scale(s);
                source.setDeltaMovement(vec3.x / 2.0D - vec31.x, entity.onGround() ? Math.min(0.4D, vec3.y / 2.0 + s) : vec3.y, vec3.z / 2.0D - vec31.z);
            }
        }
    }
}
