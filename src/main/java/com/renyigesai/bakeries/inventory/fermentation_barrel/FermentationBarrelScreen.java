package com.renyigesai.bakeries.inventory.fermentation_barrel;

import com.mojang.blaze3d.systems.RenderSystem;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.FermentationBarrelBlockEntity;
import com.renyigesai.bakeries.util.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class FermentationBarrelScreen extends AbstractContainerScreen<FermentationBarrelMenu> {
    // GUI 纹理路径
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BakeriesMod.MODID, "textures/gui/fermentor_gui.png");

    public FermentationBarrelScreen(FermentationBarrelMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 180; // GUI 宽度
        this.imageHeight = 170; // GUI 高度
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
    }

    @Override
    public void render(GuiGraphics poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
//        renderTooltip(poseStack, mouseX, mouseY);
        renderCTooltip(poseStack,mouseX,mouseY);
    }

    protected void renderCTooltip(GuiGraphics gui, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null && this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            System.out.println(this.hoveredSlot.index);
            if (this.hoveredSlot.index == 6) {
                List<Component> tooltip = new ArrayList<>();

                ItemStack mealStack = this.hoveredSlot.getItem();
                tooltip.add(((MutableComponent) mealStack.getItem().getDescription()).withStyle(ChatFormatting.BLUE));
                IItemHandler inventory = this.menu.blockEntity.getInventory();
                ItemStack stackInSlot = inventory.getStackInSlot(6);
                String container = !stackInSlot.isEmpty() ? stackInSlot.getItem().getDescription().getString() : "";
                String string = Component.translatable("container.fermentation_barre.served_on").getString();
                tooltip.add(Component.translatable(string + container).withStyle(ChatFormatting.GRAY));
                gui.renderComponentTooltip(font, tooltip, mouseX, mouseY);
            }else {
                gui.renderTooltip(font, this.hoveredSlot.getItem(), mouseX, mouseY);
            }
        }
    }
}
