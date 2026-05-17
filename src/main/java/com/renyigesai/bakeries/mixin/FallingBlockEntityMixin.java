package com.renyigesai.bakeries.mixin;

import com.renyigesai.bakeries.event.AnvilLandingEvent;
import com.renyigesai.bakeries.event.BakeriesEventHooks;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    @SuppressWarnings("unused") // Required synthetic constructor for Mixin target class.
    public FallingBlockEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "causeFallDamage",
            require = 0,
            at = @At("HEAD")
    )
    @SuppressWarnings("unused") // Called by Mixin injection target.
    private void causeFallDamage(
            float fallDistance,
            float multiplier,
            DamageSource source,
            CallbackInfoReturnable<Boolean> cir
    ) {
        BakeriesEventHooks.fireAnvilLanding(new AnvilLandingEvent(this, this.level()));
    }
}
