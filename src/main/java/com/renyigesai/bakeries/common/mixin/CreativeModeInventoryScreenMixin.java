package com.renyigesai.bakeries.common.mixin;

import com.renyigesai.bakeries.api.items.StackingCreativeModeTab;
import com.renyigesai.bakeries.api.items.TabEntry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (screen.selectedTab instanceof StackingCreativeModeTab){
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

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen;renderTooltip(Lnet/minecraft/client/gui/GuiGraphics;II)V"))
    private void onRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen)(Object)this;
        CreativeModeInventoryScreen.ItemPickerMenu menu = screen.getMenu();
        float scrollOffs = screen.scrollOffs;
        CreativeModeTab currentTab = CreativeModeInventoryScreen.selectedTab;
        int startRow = menu.getRowIndexForScroll(scrollOffs);

        if (!(currentTab instanceof StackingCreativeModeTab)){
            return;
        }

        for (int visibleRow = 0; visibleRow < 5; visibleRow++) {
            int absoluteRow = startRow + visibleRow;
            int itemIndex = absoluteRow * 9;
            boolean allEmpty = true;
            for (int col = 0; col < 9; col++) {
                int index = itemIndex + col;
                if (index < menu.items.size() && !menu.items.get(index).isEmpty()) {
                    allEmpty = false;
                    break;
                }
            }

            if (allEmpty) {
                TabEntry entry = EMPTY_ROW_TO_TAB.get(absoluteRow);
                if (entry != null) {
                    CreativeModeTab insideTab = entry.tab.get();
                    int x = screen.getGuiLeft() + 8;
                    int y = (screen.getGuiTop() + 18 + visibleRow * 18) - 1;
                    guiGraphics.blit(entry.getTexture(), x, y, 0, 0, 162, 18, 256, 256);
                    guiGraphics.drawString(font, insideTab.getDisplayName(), x + 4, y + 5, selectedTab.getLabelColor(), false);
                }
            }
        }
    }
}