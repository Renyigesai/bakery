package com.renyigesai.bakeries.screen;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.network.Messages;
import com.renyigesai.bakeries.menu.OvenMenu;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class OvenScreen extends BaseMachineScreen<OvenMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/oven_gui.png");

    public OvenScreen(OvenMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, TEXTURE);
    }

    @Override
    protected void init() {
        super.init();
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;

        this.addRenderableWidget(Button.builder(Component.literal("Reset"), btn -> sendOvenButton(0))
                .pos(leftPos + 112, topPos + 12)
                .size(52, 16)
                .build());

        this.addRenderableWidget(Button.builder(Component.literal("Clear"), btn -> sendOvenButton(1))
                .pos(leftPos + 112, topPos + 32)
                .size(52, 16)
                .build());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        renderProgressBar(guiGraphics, leftPos, topPos, 108, 35, 44, 8, 0xE0FF9F1A);
    }

    private static void sendOvenButton(int buttonId) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(buttonId);
        ClientPlayNetworking.send(Messages.OVEN_BUTTON, buf);
    }
}
