package com.renyigesai.bakeries.common.network;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.network.to_server.FermentationBoxMessage;
import com.renyigesai.bakeries.common.network.to_server.KeyDownMessage;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import com.renyigesai.bakeries.common.network.to_server.OvenButtonMessage;


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
        registrar.playBidirectional(
                FermentationBoxMessage.TYPE,
                FermentationBoxMessage.STREAM_CODEC,
                FermentationBoxMessage::handle
        );

        registrar.playBidirectional(KeyDownMessage.TYPE, KeyDownMessage.STREAM_CODEC, KeyDownMessage::handle);

    }

}