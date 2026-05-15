package com.renyigesai.bakeries;

import com.renyigesai.bakeries.screen.BlenderScreen;
import com.renyigesai.bakeries.screen.DoughCraftingTableScreen;
import com.renyigesai.bakeries.screen.FermentationBoxScreen;
import com.renyigesai.bakeries.screen.OvenScreen;
import com.renyigesai.bakeries.key.BakeriesKeyMapping;
import com.renyigesai.bakeries.overlay.ToasterOverlay;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesMenuTypes;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;

@SuppressWarnings("unused")
public final class BakeriesClientMod implements ClientModInitializer {
    private final ToasterOverlay toasterOverlay = new ToasterOverlay();

    @Override
    public void onInitializeClient() {
        registerRenderLayers();
        MenuScreens.register(BakeriesMenuTypes.OVEN, OvenScreen::new);
        MenuScreens.register(BakeriesMenuTypes.BLENDER, BlenderScreen::new);
        MenuScreens.register(BakeriesMenuTypes.FERMENTATION_BOX, FermentationBoxScreen::new);
        MenuScreens.register(BakeriesMenuTypes.DOUGH_CRAFTING_TABLE, DoughCraftingTableScreen::new);
        BakeriesKeyMapping.init();
        HudRenderCallback.EVENT.register((graphics, tickDelta) -> {
            int width = net.minecraft.client.Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int height = net.minecraft.client.Minecraft.getInstance().getWindow().getGuiScaledHeight();
            toasterOverlay.render(graphics, width, height);
        });
    }

    private static void registerRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderType.cutout(),
                BakeriesBlocks.TOASTER,
                BakeriesBlocks.FERMENTATION_TANK,
                BakeriesBlocks.WOOD_COUNTER,
                BakeriesBlocks.COFFEE_TABLE,
                BakeriesBlocks.CHEESE_TANK,
                BakeriesBlocks.MILK_TANK,
                BakeriesBlocks.YEAST_TANK,
                BakeriesBlocks.BREAD_RACK,
                BakeriesBlocks.GLASS_BREAD_RACK,
                BakeriesBlocks.BREAD_BASKET,
                BakeriesBlocks.GLASS_CABINET_DOOR,
                BakeriesBlocks.MOULD,
                BakeriesBlocks.DRINK_CUP,
                BakeriesBlocks.TOAST,
                BakeriesBlocks.CHEESE_COCOA_TOAST,
                BakeriesBlocks.BAGEL,
                BakeriesBlocks.BAGUETTE,
                BakeriesBlocks.CROISSANT,
                BakeriesBlocks.ROUND_BREAD,
                BakeriesBlocks.RICE_BREAD,
                BakeriesBlocks.WHOLE_WHEAT_BAGEL,
                BakeriesBlocks.PINEAPPLE_BUN,
                BakeriesBlocks.FOCACCIA,
                BakeriesBlocks.CIABATTA,
                BakeriesBlocks.EGG_TART,
                BakeriesBlocks.SALT_CROISSANT,
                BakeriesBlocks.COUNTRY_BREAD,
                BakeriesBlocks.CREAM_BINGLE_COFFEE,
                BakeriesBlocks.MATCHA_PARFAIT,
                BakeriesBlocks.COFFEE_PLANT,
                BakeriesBlocks.TARO,
                BakeriesBlocks.TOMATO
        );
    }
}
