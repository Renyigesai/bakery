package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.potion.BakeriesMobEffect;
import com.renyigesai.bakeries.common.potion.CocoaManiaMobEffect;
import com.renyigesai.bakeries.common.potion.EnjoyMobEffect;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class BakeriesMobEffects {
    public static final MobEffect COCOA_MANIA = register(
            "cocoa_mania",
            new CocoaManiaMobEffect().addAttributeModifier(
                    Attributes.ATTACK_SPEED,
                    "e37f4862-25fb-4768-88c5-a7abafc59577",
                    0.2F,
                    AttributeModifier.Operation.MULTIPLY_TOTAL
            )
    );
    public static final MobEffect CHEESE_POWER = register(
            "cheese_power",
            new BakeriesMobEffect(MobEffectCategory.BENEFICIAL, -3972305).addAttributeModifier(
                    Attributes.ATTACK_DAMAGE,
                    "82b4d33f-dbf0-45d0-9012-5ad1daf8343c",
                    5.0D,
                    AttributeModifier.Operation.ADDITION
            )
    );
    public static final MobEffect ENJOY = register("enjoy", new EnjoyMobEffect());

    private BakeriesMobEffects() {
    }

    public static void init() {
        AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (!level.isClientSide && player.hasEffect(COCOA_MANIA) && entity instanceof LivingEntity livingEntity) {
                livingEntity.invulnerableTime = 0;
            }
            return InteractionResult.PASS;
        });
        BakeriesMod.LOGGER.info("Registered Bakeries mob effects.");
    }

    private static MobEffect register(String id, MobEffect effect) {
        return Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(BakeriesMod.MODID, id), effect);
    }
}
