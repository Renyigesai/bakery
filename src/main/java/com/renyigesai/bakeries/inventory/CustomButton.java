package com.renyigesai.bakeries.inventory;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.concurrent.atomic.AtomicBoolean;

@OnlyIn(Dist.CLIENT)
public abstract class CustomButton {
    public ResourceLocation resourceLocation = new ResourceLocation("");
    public String name = "";
    public int pX, pX2, pY, pY2, pBlitOffset, pWidth, pHeight, pUWidth, pVHeight, pTextureWidth, pTextureHeight;
    public float u0, u1, v0, v1, pUOffset, pVOffset, pRed, pGreen, pBlue, pAlpha;
    public CustomButton(){
    }
    /**
     * 将指定纹理图集精灵的一部分绘制到屏幕上的给定坐标处。
     * @param pX 绘制位置的x坐标。
     * @param pY 绘制位置的y坐标。
     * @param pBlitOffset 渲染顺序的z轴偏移量。
     * @param pWidth 绘制部分的宽度。
     * @param pHeight 绘制部分的高度。
     * @param pSprite 要绘制的纹理图集精灵。
     */
    public CustomButton(GuiGraphics pGuiGraphics, int pX, int pY, int pBlitOffset, int pWidth, int pHeight, TextureAtlasSprite pSprite) {
        this.resourceLocation = pSprite.atlasLocation();
        this.pX = pX;
        this.pX2 = pX + pWidth;
        this.pY = pY;
        this.pY2 = pY + pHeight;
        this.pBlitOffset = pBlitOffset;
        this.pWidth = pWidth;
        this.pHeight = pHeight;
        this.u0 = pSprite.getU0();
        this.u1 = pSprite.getU1();
        this.v0 = pSprite.getV0();
        this.v1 = pSprite.getV1();
        this.pTextureWidth = pWidth;
        this.pTextureHeight = pHeight;
        pGuiGraphics.blit(pX, pY, pBlitOffset, pWidth, pHeight, pSprite);
    }
    /**
     * 将指定纹理图集精灵的一部分绘制到屏幕上的给定坐标处，并应用颜色叠加效果。
     * @param pX 绘制位置的x坐标。
     * @param pY 绘制位置的y坐标。
     * @param pBlitOffset 渲染顺序的z轴偏移量。
     * @param pWidth 绘制部分的宽度。
     * @param pHeight 绘制部分的高度。
     * @param pSprite 要绘制的纹理图集精灵。
     * @param pRed 颜色叠加效果的红色分量。
     * @param pGreen 颜色叠加效果的绿色分量。
     * @param pBlue 颜色叠加效果的蓝色分量。
     * @param pAlpha 颜色叠加效果的透明度分量。
     */
    public CustomButton(GuiGraphics pGuiGraphics, int pX, int pY, int pBlitOffset, int pWidth, int pHeight, TextureAtlasSprite pSprite, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.resourceLocation = pSprite.atlasLocation();
        this.pX = pX;
        this.pX2 = pX + pWidth;
        this.pY = pY;
        this.pY2 = pY + pHeight;
        this.pBlitOffset = pBlitOffset;
        this.pWidth = pWidth;
        this.pHeight = pHeight;
        this.u0 = pSprite.getU0();
        this.u1 = pSprite.getU1();
        this.v0 = pSprite.getV0();
        this.v1 = pSprite.getV1();
        this.pTextureWidth = pWidth;
        this.pTextureHeight = pHeight;
        this.pRed = pRed;
        this.pGreen = pGreen;
        this.pBlue = pBlue;
        this.pAlpha = pAlpha;
        pGuiGraphics.blit(pX, pY, pBlitOffset, pWidth, pHeight, pSprite, pRed, pGreen, pBlue, pAlpha);
    }
    /**
     * 将由图集位置指定的纹理的一部分绘制到屏幕上的给定坐标处。
     * @param pAtlasLocation 纹理图集的位置。
     * @param pX 绘制位置的x坐标。
     * @param pY 绘制位置的y坐标。
     * @param pUOffset 水平纹理坐标偏移量。
     * @param pVOffset 垂直纹理坐标偏移量。
     * @param pUWidth 纹理坐标中绘制部分的宽度。
     * @param pVHeight 纹理坐标中绘制部分的高度。
     */
    public CustomButton(GuiGraphics pGuiGraphics, ResourceLocation pAtlasLocation, int pX, int pY, int pUOffset, int pVOffset, int pUWidth, int pVHeight) {
        this.resourceLocation = pAtlasLocation;
        this.pX = pX;
        this.pY = pY;
        this.pBlitOffset = 0;
        this.pUOffset = pUOffset;
        this.pVOffset = pVOffset;
        this.pUWidth = pUWidth;
        this.pVHeight = pVHeight;
        this.pTextureWidth = 256;
        this.pTextureHeight = 256;
        pGuiGraphics.blit(pAtlasLocation, pX, pY, pUOffset, pVOffset, pUWidth, pVHeight);
    }
    /**
     * 将由图集位置指定的纹理的一部分绘制到屏幕上的给定坐标处，并使用绘制偏移量和纹理坐标。
     * @param pAtlasLocation 纹理图集的位置。
     * @param pX 绘制位置的x坐标。
     * @param pY 绘制位置的y坐标。
     * @param pBlitOffset 渲染顺序的z轴偏移量。
     * @param pUOffset 水平纹理坐标偏移量。
     * @param pVOffset 垂直纹理坐标偏移量。
     * @param pUWidth 纹理坐标中绘制部分的宽度。
     * @param pVHeight 纹理坐标中绘制部分的高度。
     * @param pTextureWidth 纹理的宽度。
     * @param pTextureHeight 纹理的高度。
     */
    public CustomButton(GuiGraphics pGuiGraphics, ResourceLocation pAtlasLocation, int pX, int pY, int pBlitOffset, float pUOffset, float pVOffset, int pUWidth, int pVHeight, int pTextureWidth, int pTextureHeight) {
        this.resourceLocation = pAtlasLocation;
        this.pX = pX;
        this.pY = pY;
        this.pBlitOffset = pBlitOffset;
        this.pUOffset = pUOffset;
        this.pVOffset = pVOffset;
        this.pUWidth = pUWidth;
        this.pVHeight = pVHeight;
        this.pTextureWidth = pTextureWidth;
        this.pTextureHeight = pTextureHeight;
        pGuiGraphics.blit(pAtlasLocation, pX, pY, pBlitOffset, pUOffset, pVOffset, pUWidth, pVHeight, pTextureWidth, pTextureHeight);
    }
    /**
     * 将由图集位置指定的纹理的一部分绘制到屏幕上的给定位置和尺寸，并使用纹理坐标。
     * @param pAtlasLocation 纹理图集的位置。
     * @param pX 绘制位置左上角的x坐标。
     * @param pY 绘制位置左上角的y坐标。
     * @param pWidth 绘制部分的宽度。
     * @param pHeight 绘制部分的高度。
     * @param pUOffset 水平纹理坐标偏移量。
     * @param pVOffset 垂直纹理坐标偏移量。
     * @param pUWidth 纹理坐标中绘制部分的宽度。
     * @param pVHeight 纹理坐标中绘制部分的高度。
     * @param pTextureWidth 纹理的宽度。
     * @param pTextureHeight 纹理的高度。
     */
    public CustomButton(GuiGraphics pGuiGraphics, ResourceLocation pAtlasLocation, int pX, int pY, int pWidth, int pHeight, float pUOffset, float pVOffset, int pUWidth, int pVHeight, int pTextureWidth, int pTextureHeight) {
        this.resourceLocation = pAtlasLocation;
        this.pX = pX;
        this.pY = pY;
        this.pWidth = pWidth;
        this.pHeight = pHeight;
        this.pBlitOffset = 0;
        this.pUOffset = pUOffset;
        this.pVOffset = pVOffset;
        this.pUWidth = pUWidth;
        this.pVHeight = pVHeight;
        this.pTextureWidth = pTextureWidth;
        this.pTextureHeight = pTextureHeight;
        pGuiGraphics.blit(pAtlasLocation, pX, pY, pWidth, pHeight, pUOffset, pVOffset, pUWidth, pVHeight, pTextureWidth, pTextureHeight);
    }
    /**
     * 将由图集位置指定的纹理的一部分绘制到屏幕上的给定位置和尺寸，并使用纹理坐标。
     * @param pAtlasLocation 纹理图集的位置。
     * @param pX 绘制位置左上角的x坐标。
     * @param pY 绘制位置左上角的y坐标。
     * @param pUOffset 水平纹理坐标偏移量。
     * @param pVOffset 垂直纹理坐标偏移量。
     * @param pWidth 绘制部分的宽度。
     * @param pHeight 绘制部分的高度。
     * @param pTextureWidth 纹理的宽度。
     * @param pTextureHeight 纹理的高度。
     */
    public CustomButton(GuiGraphics pGuiGraphics, ResourceLocation pAtlasLocation, int pX, int pY, float pUOffset, float pVOffset, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
        this.resourceLocation = pAtlasLocation;
        this.pX = pX;
        this.pY = pY;
        this.pWidth = pWidth;
        this.pHeight = pHeight;
        this.pUOffset = pUOffset;
        this.pVOffset = pVOffset;
        this.pTextureWidth = pTextureWidth;
        this.pTextureHeight = pTextureHeight;
        pGuiGraphics.blit(pAtlasLocation, pX, pY, pWidth, pHeight, pUOffset, pVOffset, pWidth, pHeight, pTextureWidth, pTextureHeight);
    }
    private boolean insideScrollbar(double pMouseX, double pMouseY) {
        int x = this.pX;
        int y = this.pY;
        int pWidth = this.pWidth != 0 ? this.pWidth : 0;
        int pHeight = this.pHeight != 0 ? this.pHeight : 0;
        int w = pWidth != 0 ? pWidth : this.pUWidth;
        int h = pHeight != 0 ? pHeight : this.pVHeight;
        int width = this.pX + w;
        int height = this.pY + h;
        return pMouseX >= (double) x && pMouseY >= (double) y && pMouseX < (double) width && pMouseY < (double) height;
    }
    public void mouseClicked(AtomicBoolean isClicked, double pMouseX, double pMouseY, int pButton) {
        if (insideScrollbar(pMouseX, pMouseY)) {
            isClicked.set(true);
        }
    }
    public void mouseDragged(AtomicBoolean isClicked, double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (isClicked.get()) {
            updateProgress();
        }
    }
    public void mouseReleased(AtomicBoolean isClicked, double pMouseX, double pMouseY, int pButton) {
        if(isClicked.get()){
            updateProgress();
            isClicked.set(false);
        }
    }
    protected abstract void updateProgress();
    public void renderName(GuiGraphics pGuiGraphics, Font font, String name, int pColor, boolean pDropShadow){
        this.name = name;
        int pWidth = this.pWidth != 0 ? this.pWidth : 0;
        int pHeight = this.pHeight != 0 ? this.pHeight : 0;
        int w = pWidth != 0 ? pWidth : this.pUWidth;
        int h = pHeight != 0 ? pHeight : this.pVHeight;
        int width = (this.pX + w)/2;
        int height = (this.pX + h)/2;
        int txtX = width - font.width(name)/2;
        int txtY = height - font.lineHeight/2;
        pGuiGraphics.drawString(font, name, txtX, txtY, pColor, pDropShadow);
    }
}
