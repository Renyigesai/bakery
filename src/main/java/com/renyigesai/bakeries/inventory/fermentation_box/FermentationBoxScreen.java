package com.renyigesai.bakeries.inventory.fermentation_box;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.fermentation_box.FermentationBoxBlockEntity;
import com.renyigesai.bakeries.network.FermentationBoxMessage;
import com.renyigesai.bakeries.network.Messages;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FermentationBoxScreen extends AbstractContainerScreen<FermentationBoxMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BakeriesMod.MODID, "textures/gui/fermentation_box_gui.png");
    public final BlockEntity blockEntity;
    public int x,y,z;
    public FermentationBoxScreen(FermentationBoxMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176; // GUI 宽度
        this.imageHeight = 166; // GUI 高度
        this.titleLabelY = 4;// GUI标题高度
        this.blockEntity = menu.getBlockEntity();
        this.x = pMenu.x;
        this.y = pMenu.y;
        this.z = pMenu.z;
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        int x = this.leftPos;
        int y = this.topPos;
        if (pMouseX >= x + 121 && pMouseX <= x + 145 && pMouseY >= y + 33 && pMouseY <= y + 48){
            if (blockEntity instanceof FermentationBoxBlockEntity box) {
                renderTemperatureTooltip(pGuiGraphics, pMouseX, pMouseY,box);
            }
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        int x = this.leftPos;
        int y = this.topPos;
        if (pMouseX >= x + 121 && pMouseX <= x + 145 && pMouseY >= y + 33 && pMouseY <= y + 48){
            boolean flag = pDelta == 1.0;
            Messages.sendToServer(new FermentationBoxMessage(this.x,this.y,this.z,flag ? 0 : 1, Screen.hasShiftDown() ? 1 : 10));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_HAT, 2.0F));
        }
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        FermentationBoxBlockEntity boxBlock = menu.getBlockEntity();
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos,this.imageWidth,this.imageHeight, 0,0,this.imageWidth, this.imageHeight, 256, 256);
        pGuiGraphics.blit(TEXTURE,this.leftPos + 128, this.topPos + 19,22, 166,2,38, 256, 256);
        pGuiGraphics.blit(TEXTURE, this.leftPos + 53, this.topPos + 36, 0, 166, getProgressbarW(boxBlock,0), 2, 256, 256);
        pGuiGraphics.blit(TEXTURE, this.leftPos + 71, this.topPos + 36, 0, 166, getProgressbarW(boxBlock,1), 2, 256, 256);
        pGuiGraphics.blit(TEXTURE, this.leftPos + 89, this.topPos + 36, 0, 166, getProgressbarW(boxBlock,2), 2, 256, 256);
        pGuiGraphics.blit(TEXTURE, this.leftPos + 53, this.topPos + 66, 0, 166, getProgressbarW(boxBlock,3), 2, 256, 256);
        pGuiGraphics.blit(TEXTURE, this.leftPos + 71, this.topPos + 66, 0, 166, getProgressbarW(boxBlock,4), 2, 256, 256);
        pGuiGraphics.blit(TEXTURE, this.leftPos + 89, this.topPos + 66, 0, 166, getProgressbarW(boxBlock,5), 2, 256, 256);
        pGuiGraphics.pose().popPose();
    }

    public int getProgressbarW(FermentationBoxBlockEntity boxBlock, int slot) {
        int max = boxBlock.getFermentationMaxTime();
        if (max == 0){
            return 0;
        }
        int time = boxBlock.getFermentationTime()[slot];
        return (int) (14.0 * time / max);
    }

    protected void renderTemperatureTooltip(GuiGraphics gui, int mouseX, int mouseY, FermentationBoxBlockEntity box) {
        if (this.minecraft != null && this.minecraft.player != null && this.menu.getCarried().isEmpty()) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.translatable("gui.bakeries.today_temperature",box.getTemperature() + "°c").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.literal(box.getFermentationMaxTime() + "tick").withStyle(ChatFormatting.WHITE));
            int min = Math.max(box.getPerfectTime() - 200, 430);
            tooltip.add(Component.translatable("gui.bakeries.suggested_time",min,box.getPerfectTime() + 200).withStyle(ChatFormatting.DARK_GRAY));
            tooltip.add(Component.translatable("gui.bakeries.rolling").withStyle(ChatFormatting.DARK_GRAY));
            gui.renderComponentTooltip(font, tooltip, mouseX,mouseY);
        }
    }
}
