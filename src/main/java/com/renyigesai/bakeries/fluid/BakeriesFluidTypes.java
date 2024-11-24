package com.renyigesai.bakeries.fluid;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeriesFluidTypes {
    public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, BakeriesMod.MODID);
    public static final RegistryObject<FluidType> SALT_WATER_TYPE = REGISTRY.register("salt_water", () -> new SaltWaterFluidType());
}
