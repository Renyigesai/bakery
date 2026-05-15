package com.renyigesai.bakeries.screen;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.menu.DoughCraftingTableMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;

import java.util.List;
import java.util.Objects;

public class DoughCraftingTableScreen extends net.minecraft.client.gui.screens.inventory.AbstractContainerScreen<DoughCraftingTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/container/dough_crafting_table_gui.png");
    private float scrollOffs;
    private boolean scrolling;
    private int startIndex;
    private boolean displayRecipes;

    public DoughCraftingTableScreen(DoughCraftingTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        menu.registerUpdateListener(this::containerChanged);
        this.containerChanged();
        --this.titleLabelY;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int k = (int)(41.0F * this.scrollOffs);
        guiGraphics.blit(TEXTURE, i + 119, j + 15 + k, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, 15);
        int rx = this.leftPos + 52;
        int ry = this.topPos + 14;
        int last = this.startIndex + 12;
        this.renderButtons(guiGraphics, mouseX, mouseY, rx, ry, last);
        this.renderRecipes(guiGraphics, rx, ry, last);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);
        if (!this.displayRecipes) {
            return;
        }
        int rx = this.leftPos + 52;
        int ry = this.topPos + 14;
        int last = this.startIndex + 12;
        List<SimpleMachineRecipe> list = this.menu.getRecipes();
        for (int i = this.startIndex; i < last && i < this.menu.getNumRecipes(); ++i) {
            int idx = i - this.startIndex;
            int bx = rx + idx % 4 * 16;
            int by = ry + idx / 4 * 18 + 2;
            if (x >= bx && x < bx + 16 && y >= by && y < by + 18) {
                guiGraphics.renderTooltip(this.font, list.get(i).getResultItem(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).level).registryAccess()), x, y);
            }
        }
    }

    private void renderButtons(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, int last) {
        for (int i = this.startIndex; i < last && i < this.menu.getNumRecipes(); ++i) {
            int idx = i - this.startIndex;
            int bx = x + idx % 4 * 16;
            int by = y + idx / 4 * 18 + 2;
            int v = this.imageHeight;
            if (i == this.menu.getSelectedRecipeIndex()) {
                v += 18;
            } else if (mouseX >= bx && mouseY >= by && mouseX < bx + 16 && mouseY < by + 18) {
                v += 36;
            }
            guiGraphics.blit(TEXTURE, bx, by - 1, 0, v, 16, 18);
        }
    }

    private void renderRecipes(GuiGraphics guiGraphics, int x, int y, int last) {
        List<SimpleMachineRecipe> list = this.menu.getRecipes();
        for (int i = this.startIndex; i < last && i < this.menu.getNumRecipes(); ++i) {
            int idx = i - this.startIndex;
            int bx = x + idx % 4 * 16;
            int by = y + idx / 4 * 18 + 2;
            guiGraphics.renderItem(list.get(i).getResultItem(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).level).registryAccess()), bx, by);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        if (this.displayRecipes) {
            int x = this.leftPos + 52;
            int y = this.topPos + 14;
            int last = this.startIndex + 12;
            for (int i = this.startIndex; i < last; ++i) {
                int idx = i - this.startIndex;
                double dx = mouseX - (double) (x + idx % 4 * 16);
                double dy = mouseY - (double) (y + idx / 4 * 18);
                if (dx >= 0.0D && dy >= 0.0D && dx < 16.0D && dy < 18.0D && this.menu.clickMenuButton(Objects.requireNonNull(this.minecraft).player, i)) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    Objects.requireNonNull(this.minecraft.gameMode).handleInventoryButtonClick(this.menu.containerId, i);
                    return true;
                }
            }
            x = this.leftPos + 119;
            y = this.topPos + 9;
            if (mouseX >= (double)x && mouseX < (double)(x + 12) && mouseY >= (double)y && mouseY < (double)(y + 54)) {
                this.scrolling = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.scrolling && this.isScrollBarActive()) {
            int i = this.topPos + 14;
            int j = i + 54;
            this.scrollOffs = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)this.getOffscreenRows()) + 0.5D) * 4;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (this.isScrollBarActive()) {
            int rows = this.getOffscreenRows();
            float step = (float)delta / (float)rows;
            this.scrollOffs = Mth.clamp(this.scrollOffs - step, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)rows) + 0.5D) * 4;
        }
        return true;
    }

    private boolean isScrollBarActive() {
        return this.displayRecipes && this.menu.getNumRecipes() > 12;
    }

    private int getOffscreenRows() {
        return (this.menu.getNumRecipes() + 4 - 1) / 4 - 3;
    }

    private void containerChanged() {
        this.displayRecipes = this.menu.hasInputItem();
        if (!this.displayRecipes) {
            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }
    }
}
