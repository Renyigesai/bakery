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
import net.minecraft.world.level.block.entity.BlockEntity;

public class BlenderScreen extends AbstractContainerScreen<BlenderMenu> {
    // GUI 纹理路径
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BakeriesMod.MODID, "textures/gui/blender_gui.png");
    public final BlockEntity blockEntity;
    public int x,y,z;

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
        // 设置渲染系统
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        // 计算 GUI 的左上角位置
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        // 绘制背景
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        pGuiGraphics.blit(TEXTURE, x+7, y+17, 176, 0, 42, 52);
    }

    @Override
    public void render(GuiGraphics poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
        super.renderLabels(p_281635_, p_282681_, p_283686_);
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
    }
}
