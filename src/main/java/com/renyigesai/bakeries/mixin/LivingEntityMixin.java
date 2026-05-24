package com.renyigesai.bakeries.mixin;

import com.renyigesai.bakeries.init.BakeriesMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @ModifyVariable(method = "hurt", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float bakeries$applyEnjoyDamage(float amount, DamageSource source) {
        Entity attacker = source.getEntity();
        if (attacker instanceof LivingEntity livingEntity && livingEntity.hasEffect(BakeriesMobEffects.ENJOY)) {
            MobEffectInstance effect = livingEntity.getEffect(BakeriesMobEffects.ENJOY);
            if (effect != null) {
                return amount + 2.0F * (effect.getAmplifier() + 1);
            }
        }
        return amount;
    }
}
