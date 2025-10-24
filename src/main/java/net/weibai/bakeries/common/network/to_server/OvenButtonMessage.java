package net.weibai.bakeries.common.network.to_server;


import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.weibai.bakeries.BakeriesMod;
import net.weibai.bakeries.common.blocks.oven.OvenBlockEntity;


public record OvenButtonMessage(String buttonID, BlockPos pos, int value) implements CustomPacketPayload {
    public static final Type<OvenButtonMessage> TYPE =  new Type<>(BakeriesMod.rl("oven_button"));
    public static final String ADD = "add";
    public static final String SUB = "sub";
    @Override
    public Type<OvenButtonMessage> type() {
        return TYPE;
    }
    public static final StreamCodec<FriendlyByteBuf, OvenButtonMessage> STREAM_CODEC = StreamCodec.of((buf, msg) -> {
        buf.writeUtf(msg.buttonID);
        buf.writeBlockPos(msg.pos);
        buf.writeInt(msg.value);
    }, buf -> {
        try {
            String buttonID = buf.readUtf(32767); // 限制长度防止异常
            BlockPos pos = buf.readBlockPos();
            int value = buf.readInt();
            return new OvenButtonMessage(buttonID, pos, value);
        } catch (Exception e) {
            // 添加异常处理
            BakeriesMod.LOGGER.error("Failed to decode OvenButtonMessage", e);
            return null;
        }
    });

    public static void handle(OvenButtonMessage message, IPayloadContext context) {
        if (message == null) return;

        context.enqueueWork(() -> {
            try {
                // 应该只在服务端处理温度变化逻辑
                if (!context.player().level().isClientSide()) {
                    String buttonID = message.buttonID;
                    BlockPos pos = message.pos;
                    int v = message.value;
                    if (context.player().level().getBlockEntity(pos) instanceof OvenBlockEntity oven) {
                        switch (buttonID) {
                            case ADD -> oven.addTemperature(v);
                            case SUB -> oven.subTemperature(v);
                        }
                    }
                }
            } catch (Exception e) {
                BakeriesMod.LOGGER.error("Error handling OvenButtonMessage", e);
            }
        });
    }

}