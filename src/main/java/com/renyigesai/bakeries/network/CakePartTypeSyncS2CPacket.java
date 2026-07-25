package com.renyigesai.bakeries.network;

import com.renyigesai.bakeries.util.measurer.CakePartMeasurer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CakePartTypeSyncS2CPacket {
    public final Map<ResourceLocation,String> map = new HashMap<>();

    public CakePartTypeSyncS2CPacket(Map<ResourceLocation,String> map) {
        this.map.putAll(map);
    }

    public CakePartTypeSyncS2CPacket(FriendlyByteBuf buffer) {
        int size = buffer.readVarInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = buffer.readResourceLocation();
            String value = buffer.readUtf();
            this.map.put(key, value);
        }
    }

    public static void toBytes(CakePartTypeSyncS2CPacket message, FriendlyByteBuf buffer) {
        buffer.writeVarInt(message.map.size());
        for (Map.Entry<ResourceLocation, String> entry : message.map.entrySet()) {
            buffer.writeResourceLocation(entry.getKey());
            buffer.writeUtf(entry.getValue());
        }
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            CakePartMeasurer.getClientPartsType().clear();
            CakePartMeasurer.getClientPartsType().putAll(map);
        });
        context.setPacketHandled(true);
    }
}
