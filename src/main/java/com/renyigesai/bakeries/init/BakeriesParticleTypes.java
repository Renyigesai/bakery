package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeriesParticleTypes {
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BakeriesMod.MODID);
    public static final RegistryObject<SimpleParticleType> TOMATO_SAUCE = REGISTRY.register("tomato_sauce", () -> new SimpleParticleType(false));
}
