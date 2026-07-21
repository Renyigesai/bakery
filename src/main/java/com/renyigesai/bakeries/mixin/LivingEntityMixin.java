package com.renyigesai.bakeries.mixin;

import com.renyigesai.bakeries.init.BakeriesMobEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyVariable(method = "hurt", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private DamageSource modifyDamageSource(DamageSource source) {
        Entity entity = source.getEntity();
        if (entity instanceof LivingEntity living && living.hasEffect(BakeriesMobEffects.BERRY_SOUR.get())){
            return new DamageSource(
                    living.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.LIGHTNING_BOLT), entity, entity
            );
        }
        return source;
    }

}
