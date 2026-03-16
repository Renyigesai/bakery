package com.renyigesai.bakeries.inventory.oven;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import com.renyigesai.bakeries.inventory.CustomButton;
import com.renyigesai.bakeries.network.SwitchButtonMessage;
import com.renyigesai.bakeries.network.Messages;
import com.renyigesai.bakeries.network.OvenButtonMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@OnlyIn(Dist.CLIENT)
public class OvenScreen extends AbstractContainerScreen<OvenMenu> {
    private final static HashMap<String, Object> guistate = OvenMenu.guistate;
    private final static HashMap<String, String> textstate = new HashMap<>();
    private final BlockEntity boundBlockEntity;
    private final int x, y, z;
    private final AtomicBoolean dragging = new AtomicBoolean(false);
    private int mousey;
    private final Player player;
    private CustomButton zhi_zheng;

    public static int zhen_y = 69; // 初始位置
    public OvenScreen(OvenMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        Level world = container.world;
        this.player = container.entity;
        this.boundBlockEntity = container.boundBlockEntity;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.imageWidth = 176;//设置GUI宽度
        this.imageHeight = 166;//设置GUI高度
        this.titleLabelY = 4;//设置GUI标题高度

    }
    private static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID,"textures/gui/oven_gui.png");

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.mousey = pMouseY;
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        int x = this.leftPos;
        int y = this.topPos;
        if (pMouseX >= x + 125 && pMouseX <= x + 132 && pMouseY >= y + 16 && pMouseY <= y + 62){
            if (boundBlockEntity instanceof OvenBlockEntity oven) {
                renderTemperatureTooltip(pGuiGraphics, pMouseX, pMouseY,oven);
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos,this.imageWidth,this.imageHeight, 0,0,this.imageWidth, this.imageHeight, 256, 256);

        int progressH = (int) (38 * ((float)this.menu.data.get(0) / 500.0f));
        pGuiGraphics.blit(TEXTURE,this.leftPos + 128, this.topPos + (57 - progressH),20, 166,2,progressH, 256, 256);
        pGuiGraphics.blit(TEXTURE,this.leftPos + 128, this.topPos + 19,22, 166,2,38, 256, 256);

        pGuiGraphics.blit(TEXTURE, this.leftPos + 53, this.topPos + 36, 0, 181, this.menu.data.get(1), 2, 256, 256);

        pGuiGraphics.blit(TEXTURE, this.leftPos + 71, this.topPos + 36, 0, 181, this.menu.data.get(2), 2, 256, 256);

        pGuiGraphics.blit(TEXTURE, this.leftPos + 89, this.topPos + 36, 0, 181, this.menu.data.get(3), 2, 256, 256);

        pGuiGraphics.blit(TEXTURE, this.leftPos + 53, this.topPos + 66, 0, 181, this.menu.data.get(4), 2, 256, 256);

        pGuiGraphics.blit(TEXTURE, this.leftPos + 71, this.topPos + 66, 0, 181, this.menu.data.get(5), 2, 256, 256);

        pGuiGraphics.blit(TEXTURE, this.leftPos + 89, this.topPos + 66, 0, 181, this.menu.data.get(6), 2, 256, 256);
        pGuiGraphics.pose().popPose();
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        int x = this.leftPos;
        int y = this.topPos;
        if (pMouseX >= x + 125 && pMouseX <= x + 132 && pMouseY >= y + 16 && pMouseY <= y + 62){
            boolean flag = pDelta == 1.0;
            Messages.sendToServer(new OvenButtonMessage(flag ? OvenButtonMessage.ADD : OvenButtonMessage.SUBTRACT, this.x, this.y, this.z, Screen.hasShiftDown() ? 1 : 10, textstate));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_HAT, 2.0F));
        }
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

//    @Override
//    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
//         zhi_zheng.mouseClicked(dragging, pMouseX, pMouseY, pButton);
//        return super.mouseClicked(pMouseX, pMouseY, pButton);
//    }
//
//    @Override
//    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
//        zhi_zheng.mouseDragged(dragging, pMouseX, pMouseY, pButton, pDragX, pDragY);
//        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
//    }
//    @Override
//    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
//        zhi_zheng.mouseReleased(dragging, pMouseX, pMouseY, pButton);
//        return super.mouseReleased(pMouseX, pMouseY, pButton);
//    }

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
    @Override
    public void init() {

        super.init();

        ImageButton imagebutton_add = new ImageButton(this.leftPos + 123, this.topPos + 65, 5, 6, 0, 166, 6, TEXTURE, 256, 256, e -> {
            if (boundBlockEntity instanceof OvenBlockEntity ovenBlockEntity) {
                zhen_y = (int) ((500 - ovenBlockEntity.getTemperature(ovenBlockEntity)) / (500 / 52.0) + 17);
                Messages.sendToServer(new OvenButtonMessage(OvenButtonMessage.ADD, x, y, z, 1, textstate));
            }
        });
        guistate.put("button:imagebutton_add", imagebutton_add);
        this.addRenderableWidget(imagebutton_add);

        ImageButton imagebutton_sub = new ImageButton(this.leftPos + 130, this.topPos + 65, 5, 6, 5, 166, 6, TEXTURE, 256, 256, e -> {
            if (boundBlockEntity instanceof OvenBlockEntity ovenBlockEntity) {
                zhen_y = (int) ((500 - ovenBlockEntity.getTemperature(ovenBlockEntity)) / (500 / 52.0) + 17);
                Messages.sendToServer(new OvenButtonMessage(OvenButtonMessage.SUBTRACT, x, y, z,1, textstate));
            }
        });
        guistate.put("button:imagebutton_sub", imagebutton_sub);
        this.addRenderableWidget(imagebutton_sub);

        for (int i = 0; i < 3; i++) {
            int finalI = i;
            ImageButton button = new ImageButton(this.leftPos + i * 17, this.topPos - 17, 18, 18, 0, 0, 1, new ResourceLocation(BakeriesMod.MODID, "textures/gui/switch_button_" + (i + 1) + ".png"), 18, 18, e -> {
                if (!(boundBlockEntity instanceof OvenBlockEntity && finalI == 2)) {
                    Messages.sendToServer(new SwitchButtonMessage(finalI,x,y,z));
                }
            });
            this.addRenderableWidget(button);
        }



    }

    protected void renderTemperatureTooltip(GuiGraphics gui, int mouseX, int mouseY,OvenBlockEntity oven) {
        if (this.minecraft != null && this.minecraft.player != null && this.menu.getCarried().isEmpty()) {
            List<Component> tooltip = new ArrayList<>();
            String string = Component.translatable("gui.bakeries.oven.temperature").getString();
            tooltip.add(Component.translatable(string).withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.literal(this.getMenu().data.get(0) + "°C").withStyle(ChatFormatting.WHITE));
            tooltip.add(Component.translatable("gui.bakeries.oven.rolling").withStyle(ChatFormatting.DARK_GRAY));
            gui.renderComponentTooltip(font, tooltip, mouseX,mouseY);
        }
    }
}
