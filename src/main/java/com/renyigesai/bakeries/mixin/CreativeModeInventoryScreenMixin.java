package com.renyigesai.bakeries.mixin;

import com.renyigesai.bakeries.api.creative_mode_tab.StackingCreativeModeTab;
import com.renyigesai.bakeries.api.creative_mode_tab.TabEntry;
import com.renyigesai.bakeries.mixin.accessor.CreativeModeInventoryScreenAccessor;
import com.renyigesai.bakeries.mixin.accessor.CreativeModeInventoryScreenItemPickerMenuAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.CreativeModeTabRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {

    @Shadow public static CreativeModeTab selectedTab;
    @Shadow private EditBox searchBox;
    @Shadow protected abstract void refreshSearchResults();
    @Shadow public float scrollOffs;

    private static final Map<Integer, TabEntry> EMPTY_ROW_TO_TAB = new HashMap<>();

    public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Inject(method = "renderLabels",at = @At("HEAD"), cancellable = true)
    private void onRenderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY, CallbackInfo ci){
        CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen)(Object)this;
        CreativeModeTab selectedTab = ((CreativeModeInventoryScreenAccessor) screen).invokeGetSelectedTab();
        if (selectedTab instanceof StackingCreativeModeTab){
            ci.cancel();
        }
    }

    @Inject(method = "selectTab", at = @At("HEAD"), cancellable = true)
    private void onSelectTab(CreativeModeTab tab, CallbackInfo ci) {
        if (tab instanceof StackingCreativeModeTab scmt) {
            CreativeModeTab previousTab = selectedTab;
            selectedTab = tab;
            this.quickCraftSlots.clear();
            this.menu.items.clear();
            this.clearDraggingState();
            List<ItemStack> combinedItems = new ArrayList<>();
            EMPTY_ROW_TO_TAB.clear();
            int tabIndex = 0;
            for (int i = 0; i < 9; i++) combinedItems.add(ItemStack.EMPTY);
            EMPTY_ROW_TO_TAB.put(0, scmt.tabs.get(tabIndex++));
            Collection<ItemStack> mainItems = selectedTab.getDisplayItems();
            combinedItems.addAll(mainItems);
            if (!mainItems.isEmpty()) {
                int remainder = combinedItems.size() % 9;
                if (remainder != 0) {
                    for (int j = 0; j < 9 - remainder; j++) combinedItems.add(ItemStack.EMPTY);
                }
                int rowIndex = combinedItems.size() / 9;
                for (int j = 0; j < 9; j++) combinedItems.add(ItemStack.EMPTY);
                if (tabIndex < scmt.tabs.size()) {
                    EMPTY_ROW_TO_TAB.put(rowIndex, scmt.tabs.get(tabIndex++));
                }
            }
            for (int i = 1; i < scmt.tabs.size(); i++) {
                CreativeModeTab insideTab = scmt.tabs.get(i).tab.get();
                combinedItems.addAll(insideTab.getDisplayItems());

                if (i < scmt.tabs.size() - 1) {
                    int remainder = combinedItems.size() % 9;
                    if (remainder != 0) {
                        for (int j = 0; j < 9 - remainder; j++) combinedItems.add(ItemStack.EMPTY);
                    }
                    int rowIndex = combinedItems.size() / 9;
                    for (int j = 0; j < 9; j++) combinedItems.add(ItemStack.EMPTY);
                    if (tabIndex < scmt.tabs.size()) {
                        EMPTY_ROW_TO_TAB.put(rowIndex, scmt.tabs.get(tabIndex++));
                    }
                }
            }
            this.menu.items.addAll(combinedItems);

            if (selectedTab.hasSearchBar()) {
                this.searchBox.setVisible(true);
                this.searchBox.setCanLoseFocus(false);
                this.searchBox.setFocused(true);
                if (previousTab != tab) this.searchBox.setValue("");
                this.searchBox.setWidth(selectedTab.getSearchBarWidth());
                this.searchBox.setX(this.leftPos + 171 - this.searchBox.getWidth());
                this.refreshSearchResults();
            } else {
                this.searchBox.setVisible(false);
                this.searchBox.setCanLoseFocus(true);
                this.searchBox.setFocused(false);
                this.searchBox.setValue("");
            }

            this.scrollOffs = 0.0F;
            this.menu.scrollTo(0.0F);
            ci.cancel();
        }
    }

    @Redirect(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/common/CreativeModeTabRegistry;getSortedCreativeModeTabs()Ljava/util/List;",
                    remap = false
            )
    )
    private List<CreativeModeTab> onInit() {
        return CreativeModeTabRegistry.getSortedCreativeModeTabs().stream()
                .filter(tab -> !(tab instanceof StackingCreativeModeTab sct) || !sct.isHide())
                .toList();
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen;renderTooltip(Lnet/minecraft/client/gui/GuiGraphics;II)V"))
    private void onRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen)(Object)this;
        CreativeModeInventoryScreen.ItemPickerMenu menu = screen.getMenu();
        float scrollOffs = ((CreativeModeInventoryScreenAccessor) screen).invokeGetScrollOffs();
        CreativeModeTab currentTab = ((CreativeModeInventoryScreenAccessor) screen).invokeGetSelectedTab();
        int startRow = ((CreativeModeInventoryScreenItemPickerMenuAccessor)menu).invokeGetRowIndexForScroll(scrollOffs);

        if (!(currentTab instanceof StackingCreativeModeTab)) {
            return;
        }

        long gameTime = Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : System.currentTimeMillis() / 50L;

        for (int visibleRow = 0; visibleRow < 5; visibleRow++) {
            int absoluteRow = startRow + visibleRow;
            int itemIndex = absoluteRow * 9;

            if (!isRowEmpty(menu, itemIndex)) {
                continue;
            }

            TabEntry entry = EMPTY_ROW_TO_TAB.get(absoluteRow);
            if (entry != null) {
                renderTabEntry(guiGraphics, screen, entry, visibleRow, gameTime);
            }
        }
    }

    private boolean isRowEmpty(CreativeModeInventoryScreen.ItemPickerMenu menu, int itemIndex) {
        for (int col = 0; col < 9; col++) {
            int index = itemIndex + col;
            if (index < menu.items.size() && !menu.items.get(index).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void renderTabEntry(GuiGraphics guiGraphics, CreativeModeInventoryScreen screen,
                                TabEntry entry, int visibleRow, long gameTime) {
        CreativeModeTab insideTab = entry.tab.get();
        int x = screen.getGuiLeft() + 8;
        int y = (screen.getGuiTop() + 18 + visibleRow * 18) - 1;

        renderTabTexture(guiGraphics, entry, x, y, gameTime);

        Component title = !Objects.equals(entry.title, Component.empty()) ? entry.title : insideTab.getDisplayName();
        int width = font.width(title.getString());
        int addX = !entry.leftJustifying ? 162 - width - 4 : 4;
        guiGraphics.drawString(font, title, x + addX + entry.titleX, y + 5 + entry.titleY, selectedTab.getLabelColor(), false);
    }

    private void renderTabTexture(GuiGraphics guiGraphics, TabEntry entry, int x, int y, long gameTime) {
        final int FRAME_WIDTH = 162;
        final int FRAME_HEIGHT = 18;

        int vOffset = 0;
        int totalHeight = FRAME_HEIGHT;

        if (entry.amountOfSheets > 1 && entry.duration > 0) {
            totalHeight = FRAME_HEIGHT * entry.amountOfSheets;
            int frame = (int) ((gameTime / entry.duration) % entry.amountOfSheets);
            vOffset = frame * FRAME_HEIGHT;
        }

        guiGraphics.blit(entry.getTexture(), x, y, 0, vOffset, FRAME_WIDTH, FRAME_HEIGHT, 162, totalHeight);
    }
}