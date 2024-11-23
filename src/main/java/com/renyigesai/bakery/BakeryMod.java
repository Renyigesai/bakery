package com.renyigesai.bakery;

import com.mojang.logging.LogUtils;
import com.renyigesai.bakery.config.BakeryConfig;
import com.renyigesai.bakery.fluid.BakeryFluidTypes;
import com.renyigesai.bakery.fluid.BakeryFluids;
import com.renyigesai.bakery.init.*;
import com.renyigesai.bakery.villager.BakeryVillagers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod(BakeryMod.MODID)
public class BakeryMod {

    public static final String MODID = "bakery";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID = 0;
    public BakeryMod() {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BakeryItems.REGISTER.register(bus);
        BakeryBlocks.BLOCK_REGISTRY.register(bus);
        BakeryBlocks.BLOCK_ENTITY_REGISTRY.register(bus);
        BakeryGroup.REGISTER.register(bus);
        BakeryMenuType.REGISTRY.register(bus);
        BakerySounds.REGISTRY.register(bus);
        BakeryFluids.REGISTRY.register(bus);
        BakeryFluidTypes.REGISTRY.register(bus);
        BakeryVillagers.register(bus);
        bus.addListener(this::commonSetup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::modConfig);

    }

    private void modConfig(FMLConstructModEvent event) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, BakeryConfig.SPEC, "bakery-server.toml");
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }
    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    private void commonSetup(final FMLClientSetupEvent event){
        event.enqueueWork(()->{
        });
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
