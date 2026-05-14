package com.renyigesai.bakeries.common.network;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.capabilities.PlayerKeyAuxiliary;
import com.renyigesai.bakeries.block.entity.MachineBlockEntity;
import com.renyigesai.bakeries.menu.FermentationBoxMenu;
import com.renyigesai.bakeries.menu.OvenMenu;
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
        ServerPlayNetworking.registerGlobalReceiver(OVEN_BUTTON, (server, player, handler, buf, responseSender) -> {
            int buttonId = buf.readInt();
            server.execute(() -> handleOvenButton(player, buttonId));
        });
        ServerPlayNetworking.registerGlobalReceiver(FERMENTATION_BOX, (server, player, handler, buf, responseSender) -> {
            int action = buf.readInt();
            server.execute(() -> handleFermentationAction(player, action));
        });
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(BakeriesMod.MODID, path);
    }

    private static boolean readKeyDown(FriendlyByteBuf buf) {
        return buf.readBoolean();
    }

    private static void handleOvenButton(net.minecraft.server.level.ServerPlayer player, int buttonId) {
        if (!(player.containerMenu instanceof OvenMenu ovenMenu)) {
            return;
        }
        if (!(ovenMenu.getContainer() instanceof MachineBlockEntity machine)) {
            return;
        }
        if (buttonId == 0) {
            machine.resetMachineProgress();
        } else if (buttonId == 1) {
            machine.clearOutputSlot(5);
        }
    }

    private static void handleFermentationAction(net.minecraft.server.level.ServerPlayer player, int action) {
        if (!(player.containerMenu instanceof FermentationBoxMenu menu)) {
            return;
        }
        if (!(menu.getContainer() instanceof MachineBlockEntity machine)) {
            return;
        }
        if (action == 0) {
            machine.resetMachineProgress();
        }
    }
}
