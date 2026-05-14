package com.renyigesai.bakeries.common.network;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.capabilities.PlayerKeyAuxiliary;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class Messages {
    public static final ResourceLocation KEY_DOWN = id("key_down");
    public static final ResourceLocation OVEN_BUTTON = id("oven_button");
    public static final ResourceLocation FERMENTATION_BOX = id("fermentation_box");

    private Messages() {
    }

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(KEY_DOWN, (server, player, handler, buf, responseSender) -> {
            boolean down = readKeyDown(buf);
            server.execute(() -> PlayerKeyAuxiliary.of(player.getUUID()).setKeyDown(down));
        });
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(BakeriesMod.MODID, path);
    }

    private static boolean readKeyDown(FriendlyByteBuf buf) {
        return buf.readBoolean();
    }
}
