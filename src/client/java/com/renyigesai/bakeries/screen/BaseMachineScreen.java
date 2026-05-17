package com.renyigesai.bakeries.screen;

import com.renyigesai.bakeries.menu.AbstractMachineMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class BaseMachineScreen<T extends AbstractMachineMenu> extends AbstractContainerScreen<T> {
    private final ResourceLocation texture;

    protected BaseMachineScreen(T menu, Inventory playerInventory, Component title, ResourceLocation texture) {
        super(menu, playerInventory, title);
        this.texture = texture;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(texture, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    protected void renderProgressBar(GuiGraphics guiGraphics, int leftPos, int topPos, int x, int width, int color) {
        if (!this.menu.isCrafting()) {
            return;
        }
        int max = this.menu.getMaxProgress();
        if (max <= 0) {
            return;
        }
        int filled = Math.max(0, Math.min(width, this.menu.getProgress() * width / max));
        if (filled > 0) {
            guiGraphics.fill(leftPos + x, topPos + 35, leftPos + x + filled, topPos + 35 + 8, color);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
