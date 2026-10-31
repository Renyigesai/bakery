package com.renyigesai.bakeries.mixin;

import com.renyigesai.bakeries.init.BakeriesMobEffects;
import com.renyigesai.bakeries.item.FlourSieveItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow protected abstract void spawnItemParticles(ItemStack pStack, int pAmount);

    @Shadow public abstract ItemStack getMainHandItem();

    @Shadow public abstract SoundEvent getEatingSound(ItemStack pStack);

    @Shadow public abstract RandomSource getRandom();

    @Shadow public abstract ItemStack getItemInHand(InteractionHand pHand);

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

    @Inject(method = "triggerItemUseEffects",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/LivingEntity;spawnItemParticles(Lnet/minecraft/world/item/ItemStack;I)V"),cancellable = true)
    public void onTriggerItemUseEffects(ItemStack pStack, int pAmount, CallbackInfo ci){
        if (pStack.getItem() instanceof FlourSieveItem sieveItem){
            ItemStack ingredient = this.getItemInHand(InteractionHand.MAIN_HAND).is(sieveItem) ? this.getItemInHand(InteractionHand.OFF_HAND) : this.getItemInHand(InteractionHand.MAIN_HAND);
            if (!ingredient.isEmpty()){
                this.spawnItemParticles(ingredient,pAmount);
            }
            if ((Object)this instanceof Entity entity){
                entity.playSound(getEatingSound(pStack), 0.5F + 0.5F * (float)this.getRandom().nextInt(2), (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
            }
            ci.cancel();
        }
    }

}
