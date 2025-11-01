package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.potion.BakeriesMobEffect;
import com.renyigesai.bakeries.common.potion.CocoaManiaMobEffect;
import com.renyigesai.bakeries.common.potion.EnjoyMobEffect;
import com.renyigesai.bakeries.common.potion.SoftMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BakeriesMobEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT,BakeriesMod.MODID);

    public static final Holder<MobEffect> COCOA_MANIA = EFFECTS.register("cocoa_mania", ()->
            new CocoaManiaMobEffect().addAttributeModifier(Attributes.ATTACK_SPEED,ResourceLocation.withDefaultNamespace("effect.cocoa_mania"), 0.2F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final Holder<MobEffect> CHEESE_POWER = EFFECTS.register("cheese_power", ()->
            new BakeriesMobEffect(MobEffectCategory.BENEFICIAL, -13312).addAttributeModifier(Attributes.ATTACK_DAMAGE,ResourceLocation.withDefaultNamespace("effect.cheese_power"),5.0,  AttributeModifier.Operation.ADD_VALUE));

    public static final Holder<MobEffect> ENJOY = EFFECTS.register("enjoy", EnjoyMobEffect::new);

    public static final Holder<MobEffect> SOFT = EFFECTS.register("soft", ()-> new SoftMobEffect().addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,ResourceLocation.withDefaultNamespace("effect.cheese_power"),0.5,AttributeModifier.Operation.ADD_VALUE).addAttributeModifier(Attributes.ARMOR,ResourceLocation.withDefaultNamespace("effect.cheese_power"),5,AttributeModifier.Operation.ADD_VALUE));

}
