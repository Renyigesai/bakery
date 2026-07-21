package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.potion.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeriesMobEffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BakeriesMod.MODID);

    public static final RegistryObject<MobEffect> COCOA_MANIA = REGISTRY.register("cocoa_mania", ()->
        new CocoaManiaMobEffect().addAttributeModifier(Attributes.ATTACK_SPEED,"d4329a50-1b15-469d-8fbb-970edc72d805", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<MobEffect> CHEESE_POWER = REGISTRY.register("cheese_power", ()->
            new BakeriesMobEffect(MobEffectCategory.BENEFICIAL, -13312).addAttributeModifier(Attributes.ATTACK_DAMAGE,"5e1e13cc-f2c4-49b2-b636-b217f125e7a4",5.0,  AttributeModifier.Operation.ADDITION));

    public static final RegistryObject<MobEffect> ENJOY = REGISTRY.register("enjoy", EnjoyMobEffect::new);

    public static final RegistryObject<MobEffect> SOFT = REGISTRY.register("soft", ()-> new SoftMobEffect().addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,"e561bed7-8068-42e6-a010-d25e2ac47f71",0.5,AttributeModifier.Operation.ADDITION).addAttributeModifier(Attributes.ARMOR,"8a296c1d-b2e2-41e9-96df-b49e4f6370cf",5,AttributeModifier.Operation.ADDITION));

    public static final RegistryObject<MobEffect> BERRY_SOUR = REGISTRY.register("berry_sour", BerrySourMobEffect::new);

    public static final RegistryObject<MobEffect> TEA_ASTRINGENT = REGISTRY.register("tea_astringent", TeaAstringentMobEffect::new);


}
