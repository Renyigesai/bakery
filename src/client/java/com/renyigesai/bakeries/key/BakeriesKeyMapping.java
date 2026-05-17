package com.renyigesai.bakeries.key;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.network.Messages;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.client.KeyMapping;
import io.netty.buffer.Unpooled;
import org.lwjgl.glfw.GLFW;

public final class BakeriesKeyMapping {
    public static final KeyMapping AUX_KEY = new KeyMapping(
            "key.bakeries.aux",
            GLFW.GLFW_KEY_V,
            "key.categories." + BakeriesMod.MODID
    );
    private static boolean lastSentDown;

    private BakeriesKeyMapping() {
    }

    public static void init() {
        KeyBindingHelper.registerKeyBinding(AUX_KEY);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                lastSentDown = false;
                return;
            }
            boolean down = AUX_KEY.isDown();
            if (down == lastSentDown) {
                return;
            }
            lastSentDown = down;
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeBoolean(down);
            ClientPlayNetworking.send(Messages.KEY_DOWN, buf);
        });
    }

    public static boolean isAuxDown() {
        return AUX_KEY.isDown();
    }
}
