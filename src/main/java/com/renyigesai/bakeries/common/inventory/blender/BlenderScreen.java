package com.renyigesai.bakeries.common.inventory.blender;

import com.mojang.blaze3d.systems.RenderSystem;
import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class BlenderScreen extends AbstractContainerScreen<BlenderMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "textures/gui/blender/blender_gui.png");

    private static final ResourceLocation FLOAT_PANEL = ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "textures/gui/blender/blender_float_panel.png");

    public boolean isDragged;
    public int pressTime = 0;
    public boolean isFiltration = false;
    public boolean isMove = false;
    private int dragOffsetX, dragOffsetY;
    private int filtrationX = 0;
    private int filtrationY = 0;
    private static final int FILTRATION_WIDTH = 63;
    private static final int FILTRATION_HEIGHT = 90;

    public BlenderScreen(BlenderMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.titleLabelY = 4;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        pGuiGraphics.blit(TEXTURE, x + 7, y + 17, 176, 0, 42, 52);
    }

    @Override
    public void render(GuiGraphics poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack, mouseX, mouseY, partialTicks);
        updateDisplay(mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        if (isFiltration) {
            poseStack.blit(FLOAT_PANEL, filtrationX, filtrationY, 0, 0, 63, 90);
        }
        // ★ 悬停区域与第一个一致：leftPos+10 ~ leftPos+35，topPos+33 ~ topPos+58
        if (mouseX >= this.leftPos + 10 && mouseX <= this.leftPos + 35
                && mouseY >= this.topPos + 33 && mouseY <= this.topPos + 58) {
            // ★ 绘制位置与第一个一致：leftPos+10, topPos+35
            poseStack.blit(TEXTURE, this.leftPos + 10, this.topPos + 35, 0, 190, 24, 24, 256, 256);
        }
        renderTooltip(poseStack, mouseX, mouseY);
    }


    @Override
    protected void init() {
        super.init();
        // ★ 按钮位置与第一个一致：leftPos+10, topPos+35
        ImageButton filtration = new ImageButton(
                this.leftPos + 10, this.topPos + 35, 24, 24,
                new WidgetSprites(
                        ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "blender/blender_filtration_button_0"),
                        ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "blender/blender_filtration_button_1")
                ),
                e -> this.isFiltration = !this.isFiltration
        );
        this.addRenderableWidget(filtration);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isFiltration && isMouseOverFiltrationPanel(mouseX, mouseY)) {
            this.isDragged = true;
            this.dragOffsetX = (int) (mouseX - filtrationX);
            this.dragOffsetY = (int) (mouseY - filtrationY);
        }
        int x = filtrationX + 63 - 6;
        int y = filtrationY;
        if (mouseX >= x && mouseX <= filtrationX + 63 && mouseY >= y && mouseY <= y + 6) {
            this.isFiltration = false;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.isDragged) {
            filtrationX = (int) (mouseX - dragOffsetX);
            filtrationY = (int) (mouseY - dragOffsetY);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        this.pressTime = 0;
        this.isDragged = false;
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    private boolean isMouseOverFiltrationPanel(double mouseX, double mouseY) {
        return mouseX >= filtrationX && mouseX <= filtrationX + FILTRATION_WIDTH
                && mouseY >= filtrationY && mouseY <= filtrationY + FILTRATION_HEIGHT;
    }

    private void updateDisplay(int mouseX, int mouseY) {
        if (isDragged) {
            isMove = true;
            filtrationX = mouseX - dragOffsetX;
            filtrationY = mouseY - dragOffsetY;
            filtrationX = Math.max(0, Math.min(filtrationX, width - 63));
            filtrationY = Math.max(0, Math.min(filtrationY, height - 90));
        } else if (!isMove) {
            filtrationX = (width - imageWidth) / 2 - 63;
            filtrationY = (height - imageHeight) / 2;
        }
        for (int i = 0; i < 10; i++) {
            Slot slot = menu.getSlot(i + 11);
            if (slot instanceof BlenderMenu.FiltrationSlot filtrationSlot) {
                filtrationSlot.setActive(isFiltration);
            }
        }
        if (!isFiltration) {
            return;
        }
        int slotBaseX = filtrationX - this.leftPos;
        int slotBaseY = filtrationY - this.topPos;
        int[][] slotOffsets = {{5, 8}, {23, 8}, {41, 8}, {5, 26}, {23, 26}, {41, 26}, {5, 44}, {23, 44}, {41, 44}, {23, 67}};

        for (int i = 0; i < 10; i++) {
            int slotIndex = 11 + i;
            if (slotIndex < this.menu.slots.size()) {
                int slotX = slotBaseX + slotOffsets[i][0];
                int slotY = slotBaseY + slotOffsets[i][1];
                this.menu.slots.get(slotIndex).x = slotX;
                this.menu.slots.get(slotIndex).y = slotY;
            }
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int p_282681_, int p_283686_) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }
}