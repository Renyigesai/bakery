package com.renyigesai.bakeries.common.network.to_server;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesAttributes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Objects;


public class KeyDownMessage implements CustomPacketPayload {

    public static final Type<KeyDownMessage> TYPE =  new Type<>(BakeriesMod.rl("down"));
    private final int type;

    public KeyDownMessage(int type){
        this.type = type;
    }

    @Override
    public Type<KeyDownMessage> type() {
        return TYPE;
    }
    public static final StreamCodec<FriendlyByteBuf, KeyDownMessage> STREAM_CODEC = StreamCodec.of((buf, msg) -> {
        buf.writeInt(msg.type);
    }, buf -> {
        try {
            int value = buf.readInt();
            return new KeyDownMessage(value);
        } catch (Exception e) {
            BakeriesMod.LOGGER.error("Failed to decode KeyDownMessage", e);
            return null;
        }
    });

    public static void handle(KeyDownMessage message, IPayloadContext context) {
        if (message == null) return;

        context.enqueueWork(() -> {
            try {
                if (!context.player().level().isClientSide()) {
                    Player player = context.player();
                    AttributeMap attributes = player.getAttributes();
                    int v = message.type == 1 ? 0 : 1;
                    AttributeModifier attributeModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("bakeries","base_down"), v, AttributeModifier.Operation.ADD_VALUE);
                    Objects.requireNonNull(attributes.getInstance(BakeriesAttributes.DOWN)).addOrUpdateTransientModifier(attributeModifier);
                }
            } catch (NullPointerException _null) {
                BakeriesMod.LOGGER.error("Error handling KeyDownMessage", _null);
            }
        });
    }

}