package com.renyigesai.bakeries.inventory.oven;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class OvenButtonMessage {

    private final int buttonID, x, y, z;
    private HashMap<String, String> textstate;

    public OvenButtonMessage(FriendlyByteBuf buffer) {
        this.buttonID = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.textstate = readTextState(buffer);
    }

    public OvenButtonMessage(int buttonID, int x, int y, int z, HashMap<String, String> textstate) {
        this.buttonID = buttonID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.textstate = textstate;

    }

    public static void buffer(OvenButtonMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.buttonID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
        writeTextState(message.textstate, buffer);
    }

    public static void handler(OvenButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player entity = context.getSender();
            int buttonID = message.buttonID;
            int x = message.x;
            int y = message.y;
            int z = message.z;
            HashMap<String, String> textstate = message.textstate;

            handleButtonAction(entity, buttonID, x, y, z, textstate);
        });
        context.setPacketHandled(true);
    }

    public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z, HashMap<String, String> textstate) {
        Level world = entity.level();
        HashMap guistate = OvenMenu.guistate;
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
            }
        }
        if (buttonID == 1) {
            if(_blockEntity instanceof OvenBlockEntity ovenBlockEntity){
                ovenBlockEntity.subTemperature(ovenBlockEntity,1);
            }
        }
        if (buttonID == 2) {
            if(_blockEntity instanceof OvenBlockEntity ovenBlockEntity){
                int temperature = (int) (500 - ((OvenScreen.getZhen_y() - 17) / 52.0 * 500));
                OvenBlockEntity.setTemperature(ovenBlockEntity,temperature);
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        BakeriesMod.addNetworkMessage(OvenButtonMessage.class, OvenButtonMessage::buffer, OvenButtonMessage::new, OvenButtonMessage::handler);
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