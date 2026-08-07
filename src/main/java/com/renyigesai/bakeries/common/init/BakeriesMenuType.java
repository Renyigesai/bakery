package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.inventory.blender.BlenderMenu;
import com.renyigesai.bakeries.common.inventory.blender.BlenderScreen;
import com.renyigesai.bakeries.common.inventory.dough_crafting_table.DoughCraftingTableMenu;
import com.renyigesai.bakeries.common.inventory.dough_crafting_table.DoughCraftingTableScreen;
import com.renyigesai.bakeries.common.inventory.fermentation_box.FermentationBoxMenu;
import com.renyigesai.bakeries.common.inventory.fermentation_box.FermentationBoxScreen;
import com.renyigesai.bakeries.common.inventory.oven.OvenMenu;
import com.renyigesai.bakeries.common.inventory.oven.OvenScreen;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


@EventBusSubscriber(value = Dist.CLIENT)
public class BakeriesMenuType {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU,BakeriesMod.MODID);
    public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(Registries.MENU,BakeriesMod.MODID);

    public static final Supplier<MenuType<OvenMenu>> OVEN_MENU = REGISTRY.register("oven", () -> IMenuTypeExtension.create(OvenMenu::new));

    public static final Supplier<MenuType<BlenderMenu>> BLENDER_MENU = REGISTRY.register("blender",()-> IMenuTypeExtension.create(BlenderMenu::new));

    public static final Supplier<MenuType<DoughCraftingTableMenu>> DOUGH_MENU = REGISTRY.register("dough", ()-> IMenuTypeExtension.create(DoughCraftingTableMenu::new));

    public static final Supplier<MenuType<FermentationBoxMenu>> FERMENTATION_BOX_MENU = MENU.register("fermentation_box", () -> IMenuTypeExtension.create(FermentationBoxMenu::new));

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(OVEN_MENU.get(), OvenScreen::new);
        event.register(BLENDER_MENU.get(), BlenderScreen::new);
        event.register(DOUGH_MENU.get(), DoughCraftingTableScreen::new);
        event.register(FERMENTATION_BOX_MENU.get(), FermentationBoxScreen::new);
    }
}
