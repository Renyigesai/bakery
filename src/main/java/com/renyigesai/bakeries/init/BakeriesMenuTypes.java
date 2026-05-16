package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.menu.BlenderMenu;
import com.renyigesai.bakeries.menu.CupboardMenu;
import com.renyigesai.bakeries.menu.DoughCraftingTableMenu;
import com.renyigesai.bakeries.menu.FermentationBoxMenu;
import com.renyigesai.bakeries.menu.OvenMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public final class BakeriesMenuTypes {
    public static final MenuType<OvenMenu> OVEN = Registry.register(
            BuiltInRegistries.MENU,
            new ResourceLocation(BakeriesMod.MODID, "oven"),
            new MenuType<>(OvenMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );
    public static final MenuType<BlenderMenu> BLENDER = Registry.register(
            BuiltInRegistries.MENU,
            new ResourceLocation(BakeriesMod.MODID, "blender"),
            new MenuType<>(BlenderMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );
    public static final MenuType<FermentationBoxMenu> FERMENTATION_BOX = Registry.register(
            BuiltInRegistries.MENU,
            new ResourceLocation(BakeriesMod.MODID, "fermentation_box"),
            new MenuType<>(FermentationBoxMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );
    public static final MenuType<DoughCraftingTableMenu> DOUGH_CRAFTING_TABLE = Registry.register(
            BuiltInRegistries.MENU,
            new ResourceLocation(BakeriesMod.MODID, "dough_crafting_table"),
            new MenuType<>(DoughCraftingTableMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );
    public static final MenuType<CupboardMenu> CUPBOARD = Registry.register(
            BuiltInRegistries.MENU,
            new ResourceLocation(BakeriesMod.MODID, "cupboard"),
            new MenuType<>(CupboardMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );

    private BakeriesMenuTypes() {
    }

    public static void init() {
        BakeriesMod.LOGGER.info("Registered Bakeries menu types.");
    }
}
