package com.renyigesai.bakeries.inventory.blender;

import com.mojang.blaze3d.systems.RenderSystem;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.blender.BlenderBlockEntity;
import com.renyigesai.bakeries.network.SwitchButtonMessage;
import com.renyigesai.bakeries.network.Messages;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BlenderScreen extends AbstractContainerScreen<BlenderMenu> {
    // GUI 纹理路径
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BakeriesMod.MODID, "textures/gui/blender/blender_gui.png");
    private static final ResourceLocation FLOAT_PANEL =
            new ResourceLocation(BakeriesMod.MODID, "textures/gui/blender/blender_float_panel.png");
    public final BlockEntity blockEntity;
    public int x,y,z;
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
        this.imageWidth = 176; // GUI 宽度
        this.imageHeight = 166; // GUI 高度
        this.titleLabelY = 4;// GUI标题高度
        this.blockEntity = menu.getBlockEntity();
        this.x = menu.x;
        this.y = menu.y;
        this.z = menu.z;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        pGuiGraphics.blit(TEXTURE, x+7, y+17, 176, 0, 42, 52);
    }

    @Override
    public void render(GuiGraphics poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        updateDisplay(mouseX,mouseY);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        if (isFiltration) {
            poseStack.blit(FLOAT_PANEL, filtrationX, filtrationY, 0, 0, 63, 90);
        }
        if (mouseX >= this.leftPos + 15 && mouseX <= this.leftPos + 38 && mouseY >= this.topPos + 30 && mouseY <= this.topPos + 53){
            poseStack.blit(TEXTURE, this.leftPos + 15, this.topPos + 30, 0, 190, 24, 24, 256, 256);
        }
        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isFiltration && isMouseOverFiltrationPanel(mouseX, mouseY)) {
            this.isDragged = true;
            this.dragOffsetX = (int)(mouseX - filtrationX);
            this.dragOffsetY = (int)(mouseY - filtrationY);
        }
        int x = filtrationX + 63 - 6;
        int y = filtrationY;
        if (mouseX >= x && mouseX <= filtrationX + 63 && mouseY >= y && mouseY <= y + 6){
            this.isFiltration = false;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.isDragged) {
            filtrationX = (int)(mouseX - dragOffsetX);
            filtrationY = (int)(mouseY - dragOffsetY);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }


    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        this.pressTime = 0;
        this.isDragged = false;
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    protected void init() {
        super.init();
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            ImageButton button = new ImageButton(this.leftPos + i * 17, this.topPos - 17, 18, 18, 0, 0, 1, new ResourceLocation(BakeriesMod.MODID, "textures/gui/switch_button_" + (i + 1) + ".png"), 18, 18, e -> {
                if (!(blockEntity instanceof BlenderBlockEntity && finalI == 0)) {
                    Messages.sendToServer(new SwitchButtonMessage(finalI,x,y,z));
                }
            });
            this.addRenderableWidget(button);
        }
        ImageButton filtration = new ImageButton(this.leftPos + 15, this.topPos + 30, 24, 24, 0, 166, 0, TEXTURE, 256, 256, e -> {
            this.isFiltration = !this.isFiltration;
        });
        this.addRenderableWidget(filtration);
    }

    private boolean isMouseOverFiltrationPanel(double mouseX, double mouseY) {
        return mouseX >= filtrationX && mouseX <= filtrationX + FILTRATION_WIDTH &&
                mouseY >= filtrationY && mouseY <= filtrationY + FILTRATION_HEIGHT;
    }

    private void updateDisplay(int mouseX, int mouseY) {
        if (isDragged) {
            isMove = true;
            filtrationX = mouseX - dragOffsetX;
            filtrationY = mouseY - dragOffsetY;
            filtrationX = Math.max(0, Math.min(filtrationX, width - 63));
            filtrationY = Math.max(0, Math.min(filtrationY, height - 90));
        }else if (!isMove){
            filtrationX = (width - imageWidth) / 2 - 63;
            filtrationY = (height - imageHeight) / 2;
        }
        for (int i = 0; i < 10; i++) {
            Slot slot = menu.getSlot(i + 11);
            if (slot instanceof BlenderMenu.FiltrationSlot filtrationSlot){
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
}
