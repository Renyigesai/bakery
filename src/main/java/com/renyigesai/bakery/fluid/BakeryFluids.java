package com.renyigesai.bakery.fluid;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.init.BakeryBlocks;
import com.renyigesai.bakery.init.BakeryItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeryFluids {

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, BakeryMod.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_SALT_WATER = FLUIDS.register("flesh_mud_fluid",
            () -> new ForgeFlowingFluid.Source(BakeryFluids.FLESH_MUD_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_SALT_WATER = FLUIDS.register("flowing_flesh_mud",
            () -> new ForgeFlowingFluid.Flowing(BakeryFluids.FLESH_MUD_PROPERTIES));

    public static final ForgeFlowingFluid.Properties FLESH_MUD_PROPERTIES = new ForgeFlowingFluid.Properties(
            BakeryFluidTypes.SALT_WATER_FLUID_TYPE,SOURCE_SALT_WATER,FLOWING_SALT_WATER)
            .levelDecreasePerBlock(1).slopeFindDistance(1).block(BakeryBlocks.SALT_WATER_BLOCK).bucket(BakeryItems.SALT_WATER_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}

