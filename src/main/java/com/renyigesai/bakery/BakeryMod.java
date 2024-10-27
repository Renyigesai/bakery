package com.renyigesai.bakery;

import com.mojang.logging.LogUtils;
import com.renyigesai.bakery.init.BakeryBlocks;
import com.renyigesai.bakery.config.BakeryConfig;
import com.renyigesai.bakery.init.BakeryGroup;
import com.renyigesai.bakery.init.BakeryItems;
import com.renyigesai.bakery.init.BakeryMenuType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Locale;

@Mod(BakeryMod.MODID)
public class BakeryMod {

    public static final String MODID = "bakery";
    private static final Logger LOGGER = LogUtils.getLogger();

    public BakeryMod() {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BakeryItems.REGISTER.register(bus);
        BakeryBlocks.BLOCK_REGISTRY.register(bus);
        BakeryBlocks.BLOCK_ENTITY_REGISTRY.register(bus);
        BakeryGroup.REGISTER.register(bus);
        BakeryMenuType.REGISTRY.register(bus);


        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BakeryConfig.SPEC);
    }
    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
