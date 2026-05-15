package com.renyigesai.bakeries.screen;

import com.renyigesai.bakeries.BakeriesConfig;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.menu.OvenMenu;
import com.renyigesai.bakeries.network.Messages;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class OvenScreen extends BaseMachineScreen<OvenMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/oven_gui.png");

    public OvenScreen(OvenMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, TEXTURE);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        int temperature = this.menu.getOvenTemperature();
        int progressH = (int) (38 * (temperature / 500.0f));
        guiGraphics.blit(TEXTURE, leftPos + 128, topPos + (57 - progressH), 14, 166, 2, progressH, 256, 256);
        guiGraphics.blit(TEXTURE, leftPos + 128, topPos + 19, 16, 166, 2, 38, 256, 256);
        renderProgressBar(guiGraphics, leftPos, topPos, 108, 35, 44, 8, 0xE0FF9F1A);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        if (mouseX >= leftPos + 125 && mouseX <= leftPos + 132 && mouseY >= topPos + 16 && mouseY <= topPos + 62) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.translatable("container.bakeries.oven.temperature").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.literal(BakeriesConfig.formatFromCelsius(this.menu.getOvenTemperature())).withStyle(ChatFormatting.WHITE));
            tooltip.add(Component.translatable("container.bakeries.rolling").withStyle(ChatFormatting.DARK_GRAY));
            guiGraphics.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        if (mouseX >= leftPos + 125 && mouseX <= leftPos + 132 && mouseY >= topPos + 16 && mouseY <= topPos + 62) {
            int step = hasShiftDown() ? 1 : 10;
            int value = delta > 0 ? step : -step;
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeInt(value);
            ClientPlayNetworking.send(Messages.OVEN_BUTTON, buf);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }
}
