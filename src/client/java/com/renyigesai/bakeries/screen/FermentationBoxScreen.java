package com.renyigesai.bakeries.screen;

import com.renyigesai.bakeries.BakeriesConfig;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.menu.FermentationBoxMenu;
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

public class FermentationBoxScreen extends BaseMachineScreen<FermentationBoxMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/fermentation_box_gui.png");

    public FermentationBoxScreen(FermentationBoxMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, TEXTURE);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        renderProgressBar(guiGraphics, leftPos, topPos, 53, 14, 0xE0A8FF90);
        guiGraphics.drawString(this.font, Component.literal(BakeriesConfig.formatTicks(this.menu.getMaxProgress())), leftPos + 120, topPos + 36, 0xFFFFFF, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        if (mouseX >= leftPos + 121 && mouseX <= leftPos + 145 && mouseY >= topPos + 33 && mouseY <= topPos + 48) {
            List<Component> tooltip = new ArrayList<>();
            int perfectTime = this.menu.getPerfectTime();
            if (perfectTime <= 0) {
                tooltip.add(Component.translatable("container.bakeries.temperature_loading").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.translatable("container.bakeries.rolling").withStyle(ChatFormatting.DARK_GRAY));
                guiGraphics.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
                return;
            }
            tooltip.add(Component.translatable("container.bakeries.today_temperature", this.menu.getTemperature() + "\u00B0c").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.literal(BakeriesConfig.formatTicks(this.menu.getMaxProgress())).withStyle(ChatFormatting.WHITE));
            int maxPerfectTime = perfectTime + 100;
            if (maxPerfectTime < 430) {
                tooltip.add(Component.translatable("container.bakeries.temperature_too_low").withStyle(ChatFormatting.DARK_GRAY));
            } else {
                int minTime = Math.max(perfectTime - 100, 430);
                tooltip.add(Component.translatable("container.bakeries.suggested_time",
                        BakeriesConfig.formatTicks(minTime),
                        BakeriesConfig.formatTicks(maxPerfectTime)).withStyle(ChatFormatting.DARK_GRAY));
            }
            tooltip.add(Component.translatable("container.bakeries.rolling").withStyle(ChatFormatting.DARK_GRAY));
            guiGraphics.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        if (mouseX >= leftPos + 121 && mouseX <= leftPos + 145 && mouseY >= topPos + 33 && mouseY <= topPos + 48) {
            int step = hasShiftDown() ? 1 : 10;
            int value = delta > 0 ? step : -step;
            int ticks = Math.max(0, Math.min(1200, this.menu.getMaxProgress() + value));
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeInt(ticks);
            ClientPlayNetworking.send(Messages.FERMENTATION_BOX, buf);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }
}
