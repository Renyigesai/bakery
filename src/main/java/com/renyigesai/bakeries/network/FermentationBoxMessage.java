package com.renyigesai.bakeries.network;

import com.renyigesai.bakeries.block.fermentation_box.FermentationBoxBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FermentationBoxMessage {
    public final int x,y,z;
    public final int type;
    public final int amount;

    public FermentationBoxMessage(int x, int y, int z, int type, int amount) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.amount = amount;
    }

    public FermentationBoxMessage(FriendlyByteBuf buffer){
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.type = buffer.readInt();
        this.amount = buffer.readInt();
    }

    public static void toBytes(FermentationBoxMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
        buffer.writeInt(message.type);
        buffer.writeInt(message.amount);
    }

    public static void handle(FermentationBoxMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        Player entity = context.getSender();
        int x = message.x;
        int y = message.y;
        int z = message.z;
        int type = message.type;
        int amount = message.amount;
        if (entity != null) {
            context.enqueueWork(() -> handle(entity, x,y,z,type,amount));
        }
        context.setPacketHandled(true);
    }

    public static void handle(Player entity, int x,int y,int z,int type,int amount) {
        BlockPos containing = BlockPos.containing(x, y, z);
        BlockEntity blockEntity = entity.level().getBlockEntity(containing);
        if (blockEntity instanceof FermentationBoxBlockEntity box){
            if (type == 0){
                box.addFermentationMaxTime(box,amount);
            }else {
                box.subFermentationMaxTime(box,amount);
            }
        }
    }
}
