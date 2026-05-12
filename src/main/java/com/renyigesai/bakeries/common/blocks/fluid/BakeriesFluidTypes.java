package com.renyigesai.bakeries.common.blocks.fluid;

import com.renyigesai.bakeries.BakeriesMod;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class BakeriesFluidTypes {
    public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, BakeriesMod.MODID);
    public static final Supplier<FluidType> SALT_WATER_TYPE = REGISTRY.register("salt_water", BakeriesFluidType::new);
}
