package com.renyigesai.bakery.fluid;

import com.renyigesai.bakery.BakeryMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class BakeryFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation(BakeryMod.MODID,"block/flesh_mud_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation(BakeryMod.MODID,"block/flesh_mud_flow");
    public static final ResourceLocation SOAP_OVERLAY_RL = new ResourceLocation(BakeryMod.MODID,"block/flesh_mud_still");

    public static final DeferredRegister<FluidType> FLUID_TYPE =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES,BakeryMod.MODID);

    public static final RegistryObject<FluidType> SALT_WATER_FLUID_TYPE = register("salt_water_fluid",
            FluidType.Properties.create().density(1).viscosity(1));

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPE.register(name, () -> new BaseFluidType(properties, WATER_STILL_RL, WATER_FLOWING_RL,
                SOAP_OVERLAY_RL, 0xC36540, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f)));
    }


    public static void register(IEventBus eventBus) {
        FLUID_TYPE.register(eventBus);
    }

}
