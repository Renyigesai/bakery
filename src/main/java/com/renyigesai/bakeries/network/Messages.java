package com.renyigesai.bakeries.network;


import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messages {

    public static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(BakeriesMod.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = net;
        net.messageBuilder(OvenButtonMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OvenButtonMessage::new)
                .encoder(OvenButtonMessage::toBytes)
                .consumerMainThread(OvenButtonMessage::handle)
                .add();
        net.messageBuilder(SwitchButtonMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SwitchButtonMessage::new)
                .encoder(SwitchButtonMessage::toBytes)
                .consumerMainThread(SwitchButtonMessage::handle)
                .add();
        net.messageBuilder(KeyAuxiliaryMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(KeyAuxiliaryMessage::new)
                .encoder(KeyAuxiliaryMessage::toBytes)
                .consumerMainThread(KeyAuxiliaryMessage::handle)
                .add();
        net.messageBuilder(FermentationBoxMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FermentationBoxMessage::new)
                .encoder(FermentationBoxMessage::toBytes)
                .consumerMainThread(FermentationBoxMessage::handle)
                .add();
        net.messageBuilder(FluidSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FluidSyncS2CPacket::new)
                .encoder(FluidSyncS2CPacket::toBytes)
                .consumerMainThread(FluidSyncS2CPacket::handle)
                .add();
        net.messageBuilder(CakePartTypeSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CakePartTypeSyncS2CPacket::new)
                .encoder(CakePartTypeSyncS2CPacket::toBytes)
                .consumerMainThread(CakePartTypeSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);

    }
    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static <MSG> void sendToPlayersTrackingEntity(MSG message, Entity entity) {
        sendToPlayersTrackingEntity(message, entity, false);
    }

    public static <MSG> void sendToPlayersTrackingEntity(MSG message, Entity entity, boolean sendToSource) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);
        if (sendToSource && entity instanceof ServerPlayer serverPlayer)
            sendToPlayer(message, serverPlayer);
    }
}