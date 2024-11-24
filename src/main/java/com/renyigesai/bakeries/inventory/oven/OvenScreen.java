package com.renyigesai.bakeries.inventory.oven;

import com.mojang.blaze3d.systems.RenderSystem;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@OnlyIn(Dist.CLIENT)
public class OvenScreen extends AbstractContainerScreen<OvenMenu> {
    private final static HashMap<String, Object> guistate = OvenMenu.guistate;
    private final static HashMap<String, String> textstate = new HashMap<>();
    private final Level world;
    private final Player entity;
    private final BlockEntity boundBlockEntity;
    private final int x, y, z;
    private AtomicBoolean dragging = new AtomicBoolean(false);
    private int mousey;

    @Getter
    public static int zhen_y = 69; // 初始位置
    public OvenScreen(OvenMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.entity = container.entity;
        this.boundBlockEntity = container.boundBlockEntity;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;

    }
    private static final ResourceLocation texture = new ResourceLocation(BakeriesMod.MODID,"textures/gui/oven_gui.png");

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.mousey = pMouseY;
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        if(boundBlockEntity instanceof OvenBlockEntity ovenBlockEntity) {
            String A = new DecimalFormat("##.##").format(this.getMenu().data.get(0));
            pGuiGraphics.drawString(font, A, 140, 50, 4210752, false);
        }
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        pGuiGraphics.blit(texture, this.leftPos, this.topPos,this.imageWidth,this.imageHeight, 0,0,this.imageWidth, this.imageHeight, 256, 256);
        pGuiGraphics.blit(texture, this.leftPos + 104, this.topPos + zhen_y, 0, 178, 20, 3, 256, 256);

        RenderSystem.disableBlend();
    }
     @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (insideScrollbar(pMouseX, pMouseY)) {
            dragging.set(true);
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (dragging.get()) {
            updateProgress();
            if (Math.random() < 0.2) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_HAT, 1.0F));
            }
        }

        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }
    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if(dragging.get()){
            updateProgress();
            dragging.set(false);
        }
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }
    @Override
    public void containerTick() {
        super.containerTick();
        int mouseY = this.mousey - this.topPos;
        if(zhen_y> 69){
            zhen_y = 69;
        }else if(zhen_y < 17){
            zhen_y = 17;
        }
        if (dragging.get()) {
            zhen_y = Math.min(Math.max(mouseY, 17), 69);
        } else {
            zhen_y = (int) (52 - ((this.menu.data.get(0)/500.0)*52.0)) + 17;
        }
    }
    private void updateProgress() {
        // 根据滑动指针的位置更新进度
        if(boundBlockEntity instanceof OvenBlockEntity ovenBlockEntity){
            BakeriesMod.PACKET_HANDLER.sendToServer(new OvenButtonMessage(2, x, y, z, textstate));
            OvenButtonMessage.handleButtonAction(entity, 2, x, y, z, textstate);
        }
    }
    protected boolean insideScrollbar(double pMouseX, double pMouseY) {
        int k = this.leftPos + 105;
        int l = this.topPos + zhen_y;
        int i1 = this.leftPos + 124;
        int j1 = l + 3;
        return pMouseX >= (double) k && pMouseY >= (double) l && pMouseX < (double) i1 && pMouseY < (double) j1;
    }
    private ImageButton imagebutton_add;
    private ImageButton imagebutton_sub;
//    private ImageButton imagebutton_zhen;
    @Override
    public void init() {
        super.init();

        imagebutton_add = new ImageButton(this.leftPos + 125, this.topPos + 17, 5, 6, 0, 166, 6, texture, 256, 256, e -> {
            if(boundBlockEntity instanceof OvenBlockEntity ovenBlockEntity){
                zhen_y = (int) ((500 - ovenBlockEntity.getTemperature(ovenBlockEntity)) / (500/52.0) + 17);
                BakeriesMod.PACKET_HANDLER.sendToServer(new OvenButtonMessage(0, x, y, z, textstate));
                OvenButtonMessage.handleButtonAction(entity, 0, x, y, z, textstate);

            }
        });
        guistate.put("button:imagebutton_add", imagebutton_add);
        this.addRenderableWidget(imagebutton_add);

        imagebutton_sub = new ImageButton(this.leftPos + 125, this.topPos + 64, 5, 6, 5, 166, 6, texture, 256, 256, e -> {
            if(boundBlockEntity instanceof OvenBlockEntity ovenBlockEntity){
                zhen_y = (int) ((500 - ovenBlockEntity.getTemperature(ovenBlockEntity)) / (500/52.0) + 17);
                BakeriesMod.PACKET_HANDLER.sendToServer(new OvenButtonMessage(1, x, y, z, textstate));
                OvenButtonMessage.handleButtonAction(entity, 1, x, y, z, textstate);
            }
        });
        guistate.put("button:imagebutton_sub", imagebutton_sub);
        this.addRenderableWidget(imagebutton_sub);

    }
}
