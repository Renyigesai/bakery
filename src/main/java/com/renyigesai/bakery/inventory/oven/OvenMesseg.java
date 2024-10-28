package com.renyigesai.bakery.inventory.oven;

import com.renyigesai.bakery.BakeryMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class OvenMesseg {

    private final int slotID, x, y, z, changeType, meta;
    private HashMap<String, String> textstate;

    public OvenMesseg(int slotID, int x, int y, int z, int changeType, int meta, HashMap<String, String> textstate) {
        this.slotID = slotID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.changeType = changeType;
        this.meta = meta;
        this.textstate = textstate;
    }

    public OvenMesseg(FriendlyByteBuf buffer) {
        this.slotID = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.changeType = buffer.readInt();
        this.meta = buffer.readInt();
        this.textstate = readTextState(buffer);
    }

    public static void buffer(OvenMesseg message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.slotID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
        buffer.writeInt(message.changeType);
        buffer.writeInt(message.meta);
        writeTextState(message.textstate, buffer);

    }

    public static void handler(OvenMesseg message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player entity = context.getSender();
            int slotID = message.slotID;
            int changeType = message.changeType;
            int meta = message.meta;
            int x = message.x;
            int y = message.y;
            int z = message.z;
            HashMap<String, String> textstate = message.textstate;

            handleSlotAction(entity, slotID, changeType, meta, x, y, z, textstate);
        });
        context.setPacketHandled(true);
    }

    public static void handleSlotAction(Player entity, int slot, int changeType, int meta, int x, int y, int z, HashMap<String, String> textstate) {
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
//
//        if (slot == 0 && changeType == 0) {
//            OvenBlockEntity.slot0(world, x, y, z, entity);
//        }
//        if (slot == 1 && changeType == 0) {
//            OvenBlockEntity.slot1(world, x, y, z, entity);
//        }
//        if (slot == 2 && changeType == 0) {
//            OvenBlockEntity.slot2(world, x, y, z, entity);
//        }
//        if (slot == 3 && changeType == 0) {
//            OvenBlockEntity.slot3(world, x, y, z, entity);
//        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        BakeryMod.addNetworkMessage(OvenMesseg.class, OvenMesseg::buffer, OvenMesseg::new, OvenMesseg::handler);
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