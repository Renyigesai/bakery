package com.renyigesai.bakeries.fluid;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeriesFluids {
    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS, BakeriesMod.MODID);
    public static final RegistryObject<FlowingFluid> SALT_WATER = REGISTRY.register("salt_water", () -> new SaltWaterFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_SALT_WATER = REGISTRY.register("flowing_salt_water", () -> new SaltWaterFluid.Flowing());

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientSideHandler {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(SALT_WATER.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_SALT_WATER.get(), RenderType.translucent());
        }
    }
}

