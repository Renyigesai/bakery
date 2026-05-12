package com.renyigesai.bakeries.common.blocks.fluid;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public abstract class BakeriesFluids {
    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(Registries.FLUID, BakeriesMod.MODID);
    public static final Supplier<FlowingFluid> SALT_WATER = REGISTRY.register("salt_water", SaltWaterFluid.Source::new);
    public static final Supplier<FlowingFluid> FLOWING_SALT_WATER = REGISTRY.register("flowing_salt_water", SaltWaterFluid.Flowing::new);

    @EventBusSubscriber(value = Dist.CLIENT)
    public static class ClientSideHandler {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(SALT_WATER.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_SALT_WATER.get(), RenderType.translucent());
        }
    }
}

