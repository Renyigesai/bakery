package com.renyigesai.bakeries.screen;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.menu.FermentationBoxMenu;
import com.renyigesai.bakeries.network.Messages;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

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
        guiGraphics.drawString(this.font, Component.literal(this.menu.getMaxProgress() + " tick"), leftPos + 120, topPos + 36, 0xFFFFFF, false);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        if (mouseX >= leftPos + 121 && mouseX <= leftPos + 145 && mouseY >= topPos + 33 && mouseY <= topPos + 48) {
            double ratio = 1.0D - ((mouseY - (topPos + 33.0D)) / 15.0D);
            ratio = Math.max(0.0D, Math.min(1.0D, ratio));
            int ticks = 20 + (int) Math.round(1180.0D * ratio);
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeInt(ticks);
            ClientPlayNetworking.send(Messages.FERMENTATION_BOX, buf);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }
}
