package com.renyigesai.bakery.inventory.oven;

import com.mojang.blaze3d.systems.RenderSystem;
import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.block.oven.OvenBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;

public class OvenScreen extends AbstractContainerScreen<OvenMenu> {
    private final static HashMap<String, Object> guistate = OvenMenu.guistate;
    private final static HashMap<String, String> textstate = new HashMap<>();
    private final Level world;
    private final Player entity;

    private final int x, y, z;

    public OvenScreen(OvenMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.entity = container.entity;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
    }
    private static final ResourceLocation texture = new ResourceLocation(BakeryMod.MODID,"textures/gui/oven_gui.png");

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        String A =gettag("progress",world,x,y,z);
        String C =gettag("max_progress",world,x,y,z);
        String D = A+"/"+C;
        pGuiGraphics.drawString(font, D, 140, 50, 4210752, false);

    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        pGuiGraphics.blit(texture, this.leftPos, this.topPos,this.imageWidth,this.imageHeight, 0,0,this.imageWidth, this.imageHeight, 256, 256);
        RenderSystem.disableBlend();
    }
    public static String gettag(String tagName,LevelAccessor world, double x, double y, double z) {
        return new java.text.DecimalFormat("##.##").format(new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                BlockEntity _blockEntity = world.getBlockEntity(pos);
                if (_blockEntity instanceof OvenBlockEntity ovenBlockEntity)
                    return ovenBlockEntity.getOven().getDouble(tag);
                return -1;
            }
        }.getValue(world, BlockPos.containing(x, y, z), tagName));
    }
    public static double getVaul(String tagName, LevelAccessor world, double x, double y, double z){
        return new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                BlockEntity _blockEntity = world.getBlockEntity(pos);
                if (_blockEntity instanceof OvenBlockEntity ovenBlockEntity)
                    return ovenBlockEntity.getOven().getDouble(tag);
                return -1;
            }
        }.getValue(world, BlockPos.containing(x, y, z), tagName);
    }
    @Override
    public void containerTick() {
        super.containerTick();
    }
    private ImageButton imagebutton_add;
    private ImageButton imagebutton_sub;
    @Override
    public void init() {
        super.init();

        imagebutton_add = new ImageButton(this.leftPos + 122, this.topPos + 17, 5, 6, 0, 166, 6, texture, 256, 256, e -> {
            BakeryMod.PACKET_HANDLER.sendToServer(new OvenMesseg(0, x, y, z, textstate));
            OvenMesseg.handleSlotAction(entity, 0, x, y, z, textstate);
        });
        guistate.put("button:imagebutton_add", imagebutton_add);
        this.addRenderableWidget(imagebutton_add);

        imagebutton_sub = new ImageButton(this.leftPos + 122, this.topPos + 64, 5, 6, 5, 166, 6, texture, 256, 256, e -> {
        });
        guistate.put("button:imagebutton_sub", imagebutton_sub);
        this.addRenderableWidget(imagebutton_sub);


    }
}
