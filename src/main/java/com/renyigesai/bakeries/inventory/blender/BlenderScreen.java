package com.renyigesai.bakeries.inventory.blender;

import com.mojang.blaze3d.systems.RenderSystem;
import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public class BlenderScreen extends AbstractContainerScreen<BlenderMenu> {
    // GUI 纹理路径
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BakeriesMod.MODID, "textures/gui/container/blender_gui.png");
    private final Player player;
    private final BlockPos pos;
    private final AtomicBoolean dragging1 = new AtomicBoolean(false);
    private final AtomicBoolean dragging2 = new AtomicBoolean(false);
    private final AtomicBoolean atomicBoolean1 = new AtomicBoolean(false);
    private int blenderChildScreenX;
    private int blenderChildScreenY;
    ImageButton imageButton1;
    ImageButton imageButton2;

    public BlenderScreen(BlenderMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.player = playerInventory.player;
        this.pos = menu.getBlockEntity().getBlockPos();
        this.imageWidth = 176; // GUI 宽度
        this.imageHeight = 166; // GUI 高度
        this.blenderChildScreenX = (this.width - 100) / 2;
        this.blenderChildScreenY = this.topPos + 3;

    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float partialTicks, int mouseX, int mouseY) {
        // 设置渲染系统
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        // 计算 GUI 的左上角位置
        // 绘制背景
        pGuiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
//        if (menu.getBlockEntity().compatibility) {
//            pGuiGraphics.blit(TEXTURE, x+7, y+17, 176, 0, 42, 52);
//        }
        pGuiGraphics.blit(TEXTURE, leftPos - 25, topPos + 12, 176, 24, 26, 26);
        pGuiGraphics.blit(TEXTURE, leftPos - 25, topPos + 40, 176, 24, 26, 26);


    }
    public boolean isHovering(double pMouseX, double pMouseY, int pX, int pY, int pWidth, int pHeight) {
        int width = pX + pWidth;
        int height = pY + pHeight;
        return pMouseX >= (double) pX && pMouseY >= (double) pY && pMouseX < (double) width && pMouseY < (double) height;
    }
    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        {
            int imageWidth = 100;
            int x = (this.width - imageWidth) / 2;
            int y = this.topPos + 3;
            if (isHovering((double) pMouseX, pMouseY, x + 6, y + 6, 7, 7) && dragging1.get()) {
                dragging1.set(false);
            }
            if (isHovering((double) pMouseX, pMouseY, x, y, 100, 7) && dragging1.get()) {
                atomicBoolean1.set(true);
                blenderChildScreenX= (int) pMouseX;
                blenderChildScreenY= (int) pMouseY;
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (atomicBoolean1.get()) {
            blenderChildScreenX= (int) pMouseX;
            blenderChildScreenY= (int) pMouseY;
        }

        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (atomicBoolean1.get()) {
            blenderChildScreenX= (int) pMouseX;
            blenderChildScreenY= (int) pMouseY;
            atomicBoolean1.set(false);
        }
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }


    @Override
    public void render(GuiGraphics poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        renderTooltip(poseStack, mouseX, mouseY);
        if(dragging1.get()){
            this.blenderChildScreen(poseStack, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }
    public void blenderChildScreen(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick){
        int imageWidth = 100;
        int imageHeight = 77;
        int x = blenderChildScreenX;
        int y = blenderChildScreenY;

        Component title = Component.translatable("screen.blender.itemset");
        int titlew = (imageWidth/2) - (font.width(title)/2);
        int titleLabelX = x + titlew;
        int titleLabelY = y + 6;
        pGuiGraphics.blit(TEXTURE, x, y, 0, 166, imageWidth, imageHeight);
        pGuiGraphics.drawString(this.font, title, titleLabelX, titleLabelY, 4210752, false);
        pGuiGraphics.blit(TEXTURE, x + 18, y + 18, 100, 166, 75, 52);
    }
    @Override
    protected void init() {
        super.init();
        imageButton1 = new ImageButton(leftPos - 20, topPos + 17,16, 16, 176, 50,TEXTURE,e->{

        }){
            @Override
            public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                this.active = !dragging1.get();
                this.setFocused(dragging1.get());
                super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            }

            @Override
            public void onClick(double pMouseX, double pMouseY) {
                dragging1.set(true);
                super.onClick(pMouseX, pMouseY);
            }

        };
        addRenderableWidget(imageButton1);
        imageButton2 = new ImageButton(leftPos - 20, topPos + 45,16, 16, 176, 98,TEXTURE,e->{
        }){
            @Override
            public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                this.active = !dragging2.get();
                this.setFocused(dragging2.get());
                super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            }
            @Override
            public void onClick(double pMouseX, double pMouseY) {
                this.setFocused(true);
                dragging2.set(true);
                super.onClick(pMouseX, pMouseY);
            }

        };
        addRenderableWidget(imageButton2);
    }

}
