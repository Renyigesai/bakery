package com.renyigesai.bakeries.network;

import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import com.renyigesai.bakeries.inventory.oven.OvenMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class OvenButtonMessage {

    private final int buttonID, x, y, z,zhen_y;
    private final HashMap<String, String> textstate;

    public OvenButtonMessage(FriendlyByteBuf buffer) {
        this.buttonID = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.zhen_y = buffer.readInt();
        this.textstate = readTextState(buffer);
    }

    public OvenButtonMessage(int buttonID, int x, int y, int z, int zhen_y, HashMap<String, String> textstate) {
        this.buttonID = buttonID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.zhen_y = zhen_y;
        this.textstate = textstate;

    }

    public static void toBytes(OvenButtonMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.buttonID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
        buffer.writeInt(message.zhen_y);
        writeTextState(message.textstate, buffer);
    }

    public static void handle(OvenButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player entity = context.getSender();
            int buttonID = message.buttonID;
            int x = message.x;
            int y = message.y;
            int z = message.z;
            int zhen_y = message.zhen_y;
            HashMap<String, String> textstate = message.textstate;

            handleButtonAction(entity, buttonID, x, y, z, zhen_y, textstate);
        });
        context.setPacketHandled(true);
    }

    public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z, int zhen_y, HashMap<String, String> textstate) {
        Level world = entity.level();
        HashMap<String, Object> guistate = OvenMenu.guistate;
        for (Map.Entry<String, String> entry : textstate.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            guistate.put(key, value);
        }

        // security measure to prevent arbitrary chunk generation
        if (!world.hasChunkAt(new BlockPos(x, y, z)))
            return;
        BlockPos _bp = BlockPos.containing(x, y, z);
        BlockEntity _blockEntity = world.getBlockEntity(_bp);
        BlockState _bs = world.getBlockState(_bp);


        if (buttonID == 0) {
            if(_blockEntity instanceof OvenBlockEntity ovenBlockEntity){
                ovenBlockEntity.addTemperature(ovenBlockEntity,1);
//                if (Log.INFO) {
//                    BakeriesMod.LOGGER.info("temperature:" + ovenBlockEntity.getTemperature(ovenBlockEntity));
//                }
            }
        }
        if (buttonID == 1) {
            if(_blockEntity instanceof OvenBlockEntity ovenBlockEntity){
                ovenBlockEntity.subTemperature(ovenBlockEntity,1);
//                if(Log.INFO){
//                    BakeriesMod.LOGGER.info("temperature:"+ovenBlockEntity.getTemperature(ovenBlockEntity));
//                }
            }
        }
        if (buttonID == 2) {
            if(_blockEntity instanceof OvenBlockEntity ovenBlockEntity){
                int temperature = (int) (500 - ((zhen_y - 17) / 52.0 * 500));
                ovenBlockEntity.setTemperature(ovenBlockEntity,temperature);
//                if(Log.INFO){
//                    BakeriesMod.LOGGER.info("temperature:"+ovenBlockEntity.getTemperature(ovenBlockEntity));
//                }
            }
        }
    }


    public static void writeTextState(HashMap<String, String> map, FriendlyByteBuf buffer) {
        buffer.writeInt(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            buffer.writeUtf(entry.getKey());
            buffer.writeUtf(entry.getValue());
        }
    }

    public static HashMap<String, String> readTextState(FriendlyByteBuf buffer) {
        int size = buffer.readInt();
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String key = buffer.readUtf();
            String value = buffer.readUtf();
            map.put(key, value);
        }
        return map;
    }

}