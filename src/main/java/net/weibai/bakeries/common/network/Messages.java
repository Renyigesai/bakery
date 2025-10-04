package net.weibai.bakeries.common.network;


import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.weibai.bakeries.BakeriesMod;


@EventBusSubscriber
public class Messages {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(BakeriesMod.MODID)
                .executesOn(HandlerThread.NETWORK);
//        registrar.playBidirectional(
//                packetId(),
//                MyData.STREAM_CODEC,
//                MyData::handle
//        );

    }
    public static <MSG extends CustomPacketPayload> void sendToServer(MSG message) {
        PacketDistributor.sendToServer(message);
    }
    public static <MSG extends CustomPacketPayload> void sendToPlayer(ServerPlayer pServerPlayer, MSG message) {
        PacketDistributor.sendToPlayer(pServerPlayer, message);
    }
    public static <MSG extends CustomPacketPayload> void sendToCommon(ServerPlayer pServerPlayer, MSG message) {
        PacketDistributor.sendToServer(message);
        PacketDistributor.sendToPlayer(pServerPlayer, message);
    }
    public static <MSG extends CustomPacketPayload> void sendToAllClient(MSG message) {
        PacketDistributor.sendToAllPlayers(message);
    }
    public static <MSG extends CustomPacketPayload> void sendToPlayersTrackingChunk(ServerLevel pServerLevel, ChunkPos pChunkPos, MSG message){
        PacketDistributor.sendToPlayersTrackingChunk(pServerLevel, pChunkPos, message);
    }
    public static void sendToPlayersInDimension(ServerLevel pServerLevel, CustomPacketPayload payload){
        PacketDistributor.sendToPlayersInDimension(pServerLevel, payload);
    }
    public static void sendToPlayersInDimension(ServerLevel pServerLevel, CustomPacketPayload payload, CustomPacketPayload... payloads){
        PacketDistributor.sendToPlayersInDimension(pServerLevel, payload, payloads);
    }
}