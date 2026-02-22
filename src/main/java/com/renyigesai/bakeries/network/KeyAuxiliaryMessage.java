package com.renyigesai.bakeries.network;

import com.renyigesai.bakeries.init.BakeriesCapabilities;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class KeyAuxiliaryMessage {
    private final int type;

    public KeyAuxiliaryMessage(int buttonID) {
        this.type = buttonID;
    }

    public KeyAuxiliaryMessage(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
    }

    public static void toBytes(KeyAuxiliaryMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
    }

    public static void handle(KeyAuxiliaryMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        Player entity = context.getSender();
        int type = message.type;
        if (entity != null) {
            context.enqueueWork(() -> handle(entity, type));
        }
        context.setPacketHandled(true);
    }

    public static void handle(Player entity, int type) {
        entity.getCapability(BakeriesCapabilities.PLAYER_KEY_AUXILIARY).ifPresent((cap)-> cap.setKey(type != 1));
    }
}
