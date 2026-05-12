package com.renyigesai.bakeries.common.network.to_server;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.blocks.fermentation_box.FermentationBoxBlockEntity;
import com.renyigesai.bakeries.common.blocks.oven.OvenBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public record FermentationBoxMessage(String buttonID, BlockPos pos, int value) implements CustomPacketPayload {
    public static final Type<FermentationBoxMessage> TYPE =  new Type<>(BakeriesMod.rl("fermentation_box_button"));
    public static final String ADD = "add";
    public static final String SUB = "sub";
    @Override
    public Type<FermentationBoxMessage> type() {
        return TYPE;
    }
    public static final StreamCodec<FriendlyByteBuf, FermentationBoxMessage> STREAM_CODEC = StreamCodec.of((buf, msg) -> {
        buf.writeUtf(msg.buttonID);
        buf.writeBlockPos(msg.pos);
        buf.writeInt(msg.value);
    }, buf -> {
        try {
            String buttonID = buf.readUtf(32767); // 限制长度防止异常
            BlockPos pos = buf.readBlockPos();
            int value = buf.readInt();
            return new FermentationBoxMessage(buttonID, pos, value);
        } catch (Exception e) {
            // 添加异常处理
            BakeriesMod.LOGGER.error("Failed to decode FermentationBoxMessage", e);
            return null;
        }
    });

    public static void handle(FermentationBoxMessage message, IPayloadContext context) {
        if (message == null) return;

        context.enqueueWork(() -> {
            try {
                // 应该只在服务端处理温度变化逻辑
                if (!context.player().level().isClientSide()) {
                    String buttonID = message.buttonID;
                    BlockPos pos = message.pos;
                    int v = message.value;
                    if (context.player().level().getBlockEntity(pos) instanceof FermentationBoxBlockEntity box) {
                        switch (buttonID) {
                            case ADD -> box.addFermentationMaxTime(box,v);
                            case SUB -> box.subFermentationMaxTime(box,v);
                        }
                    }
                }
            } catch (Exception e) {
                BakeriesMod.LOGGER.error("Error handling FermentationBoxMessage", e);
            }
        });
    }

}