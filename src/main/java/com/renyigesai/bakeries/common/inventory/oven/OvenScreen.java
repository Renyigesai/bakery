package com.renyigesai.bakeries.common.inventory.oven;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.blocks.oven.OvenBlockEntity;
import com.renyigesai.bakeries.common.network.to_server.OvenButtonMessage;
import lombok.Getter;
import net.createmod.catnip.platform.NeoForgeNetworkHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class OvenScreen extends AbstractContainerScreen<OvenMenu>{
    private final OvenBlockEntity ovenBlockEntity;
    @Getter
    public int zhenY = 69; // 初始位置
    public OvenScreen(OvenMenu ovenMenu, Inventory inventory, Component text) {
        super(ovenMenu, inventory, text);
        this.ovenBlockEntity = ovenMenu.getBlockEntity();
        this.imageWidth = 176;//设置GUI宽度
        this.imageHeight = 166;//设置GUI高度
        this.titleLabelY = 4;//设置GUI标题高度

    }
    private static final ResourceLocation TEXTURE = BakeriesMod.rl("textures/gui/oven_gui.png");

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        int x = this.leftPos;
        int y = this.topPos;
        // ★ 悬停区域：16→20，62→66
        if (pMouseX >= x + 125 && pMouseX <= x + 132 && pMouseY >= y + 20 && pMouseY <= y + 66){
            renderTemperatureTooltip(pGuiGraphics, pMouseX, pMouseY);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int p_282681_, int p_283686_) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.pose().pushPose();
        if(ovenBlockEntity != null){
            pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos,this.imageWidth,this.imageHeight, 0,0,this.imageWidth, this.imageHeight, 256, 256);

            int progressH = (int) (38 * ((float) this.menu.data.get(0) / 500.0f));
            // ★ 进度条：Y 57→61，UV 14→20
            pGuiGraphics.blit(TEXTURE, this.leftPos + 128, this.topPos + (61 - progressH), 20, 166, 2, progressH, 256, 256);

            // ★ 背景条：Y 19→23，UV 16→22
            pGuiGraphics.blit(TEXTURE, this.leftPos + 128, this.topPos + 23, 22, 166, 2, 38, 256, 256);

            // ★ 上排三个指示器：Y 36→40，UV 0,166 → 0,181
            pGuiGraphics.blit(TEXTURE, this.leftPos + 53, this.topPos + 40, 0, 181, this.menu.data.get(1), 2, 256, 256);

            pGuiGraphics.blit(TEXTURE, this.leftPos + 71, this.topPos + 40, 0, 181, this.menu.data.get(2), 2, 256, 256);

            pGuiGraphics.blit(TEXTURE, this.leftPos + 89, this.topPos + 40, 0, 181, this.menu.data.get(3), 2, 256, 256);

            // ★ 下排三个指示器：Y 66→70，UV 0,166 → 0,181
            pGuiGraphics.blit(TEXTURE, this.leftPos + 53, this.topPos + 70, 0, 181, this.menu.data.get(4), 2, 256, 256);

            pGuiGraphics.blit(TEXTURE, this.leftPos + 71, this.topPos + 70, 0, 181, this.menu.data.get(5), 2, 256, 256);

            pGuiGraphics.blit(TEXTURE, this.leftPos + 89, this.topPos + 70, 0, 181, this.menu.data.get(6), 2, 256, 256);
        }
        pGuiGraphics.pose().popPose();
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int x = this.leftPos;
        int y = this.topPos;
        // ★ 滚轮检测区域：16→20，62→66
        if (mouseX >= x + 125 && mouseX <= x + 132 && mouseY >= y + 20 && mouseY <= y + 66){
            if (ovenBlockEntity != null) {
                int count = Screen.hasShiftDown() ? 1 : 10;
                if(scrollY > 0){
                    PacketDistributor.sendToServer(new OvenButtonMessage(OvenButtonMessage.ADD, ovenBlockEntity.getBlockPos(), (int) (scrollY * count)));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_HAT, 2.0F));
                }else if(scrollY < 0){
                    PacketDistributor.sendToServer(new OvenButtonMessage(OvenButtonMessage.SUB, ovenBlockEntity.getBlockPos(),  (int) ((0-scrollY) * count)));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_HAT, 2.0F));
                }
            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX,scrollY);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        if (ovenBlockEntity != null) {
            this.zhenY = (int) ((500 - ovenBlockEntity.getTemperature()) / (500 / 52.0) + 17);
        }
    }
    @Override
    public void init() {
        super.init();
        if (ovenBlockEntity != null) {
            // ★ 添加按钮：Y 65→69
            ImageButton addButton = new ImageButton(this.leftPos + 123, this.topPos + 69, 5, 6, new WidgetSprites(ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "add/1add_0"), ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "add/1add_1")),e ->{
                PacketDistributor.sendToServer(new OvenButtonMessage(OvenButtonMessage.ADD, ovenBlockEntity.getBlockPos(), 1));
            });
            this.addRenderableWidget(addButton);


            // ★ 减少按钮：Y 65→69
            ImageButton subButton = new ImageButton(this.leftPos + 130, this.topPos + 69, 5, 6,
                    new WidgetSprites(ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "sub/1sub_0"), ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "sub/1sub_1")),
                    e -> {
                        PacketDistributor.sendToServer(new OvenButtonMessage(OvenButtonMessage.SUB, ovenBlockEntity.getBlockPos(), 1));
                    });
            this.addRenderableWidget(subButton);
        }
    }

    protected void renderTemperatureTooltip(GuiGraphics gui, int mouseX, int mouseY) {
        if(ovenBlockEntity != null){
            if (this.minecraft != null && this.minecraft.player != null && this.menu.getCarried().isEmpty()) {
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(Component.translatable("container.bakeries.oven.temperature").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.literal(this.getMenu().data.get(0) + "°C").withStyle(ChatFormatting.WHITE));
                tooltip.add(Component.translatable("container.bakeries.rolling").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC));
                gui.renderComponentTooltip(font, tooltip, mouseX, mouseY);
            }
        }
    }

}