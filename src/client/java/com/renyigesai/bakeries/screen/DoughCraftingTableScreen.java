package com.renyigesai.bakeries.screen;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.menu.DoughCraftingTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DoughCraftingTableScreen extends BaseMachineScreen<DoughCraftingTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/container/dough_crafting_table_gui.png");

    public DoughCraftingTableScreen(DoughCraftingTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, TEXTURE);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        renderProgressBar(guiGraphics, leftPos, topPos, 48, 36, 84, 6, 0xE0A7F070);
    }
}
