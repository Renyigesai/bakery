package com.renyigesai.bakeries;

import com.renyigesai.bakeries.screen.BlenderScreen;
import com.renyigesai.bakeries.screen.DoughCraftingTableScreen;
import com.renyigesai.bakeries.screen.FermentationBoxScreen;
import com.renyigesai.bakeries.screen.OvenScreen;
import com.renyigesai.bakeries.init.BakeriesMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public final class BakeriesClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(BakeriesMenuTypes.OVEN, OvenScreen::new);
        MenuScreens.register(BakeriesMenuTypes.BLENDER, BlenderScreen::new);
        MenuScreens.register(BakeriesMenuTypes.FERMENTATION_BOX, FermentationBoxScreen::new);
        MenuScreens.register(BakeriesMenuTypes.DOUGH_CRAFTING_TABLE, DoughCraftingTableScreen::new);
    }
}
