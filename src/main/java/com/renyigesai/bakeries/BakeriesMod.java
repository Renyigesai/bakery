package com.renyigesai.bakeries;

import com.renyigesai.bakeries.block.toaster.ToasterBlockEntityRender;
import com.renyigesai.bakeries.config.BakeriesConfig;
import com.renyigesai.bakeries.fluid.BakeriesFluidTypes;
import com.renyigesai.bakeries.fluid.BakeriesFluids;
import com.renyigesai.bakeries.init.*;
import com.renyigesai.bakeries.key.BakeriesKeyMapping;
import com.renyigesai.bakeries.villager.BakeriesVillagers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod(BakeriesMod.MODID)
public class BakeriesMod {

    public static final String MODID = "bakeries";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID = 0;
    @SuppressWarnings("removal")
    public BakeriesMod() {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BakeriesItems.REGISTER.register(bus);
        BakeriesBlocks.BLOCK_REGISTRY.register(bus);
        BakeriesBlocks.BLOCK_ENTITY_REGISTRY.register(bus);
        BakeriesGroup.REGISTER.register(bus);
        BakeriesMenuType.REGISTRY.register(bus);
        BakeriesSounds.REGISTRY.register(bus);
        BakeriesFluids.REGISTRY.register(bus);
        BakeriesFluidTypes.REGISTRY.register(bus);
        BakeriesVillagers.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, BakeriesConfig.SPEC, "bakeries-server.toml");
    }

    private void clientSetup(FMLClientSetupEvent event) {
        BakeriesKeyMapping.register(event);
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

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
        @SubscribeEvent
        public static void onRegisterRender(EntityRenderersEvent.RegisterRenderers event){
//            event.registerBlockEntityRenderer(BakeriesBlocks.TOASTER_ENTITY.get(), ToasterBlockEntityRender::new);
        }
    }
}
