package net.weibai.bakeries.common.network;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.weibai.bakeries.BakeriesMod;
import net.weibai.bakeries.common.network.to_server.OvenButtonMessage;


@EventBusSubscriber
public class Messages {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(BakeriesMod.MODID)
                .executesOn(HandlerThread.NETWORK);
        registrar.playBidirectional(
                OvenButtonMessage.TYPE,
                OvenButtonMessage.STREAM_CODEC,
                OvenButtonMessage::handle
        );

    }

}