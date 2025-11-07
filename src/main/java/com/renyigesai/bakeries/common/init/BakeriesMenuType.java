package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.inventory.blender.BlenderMenu;
import com.renyigesai.bakeries.common.inventory.blender.BlenderScreen;
import com.renyigesai.bakeries.common.inventory.dough_crafting_table.DoughCraftingTableMenu;
import com.renyigesai.bakeries.common.inventory.dough_crafting_table.DoughCraftingTableScreen;
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
import net.weibai.rcglib.registration.impl.DeferredMenuType;
import net.weibai.rcglib.registration.impl.MenuTypeDeferredRegister;

import java.util.function.Supplier;


@EventBusSubscriber(value = Dist.CLIENT)
public class BakeriesMenuType {
    public static final MenuTypeDeferredRegister REGISTRY = new MenuTypeDeferredRegister(BakeriesMod.MODID);
    public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(Registries.MENU,BakeriesMod.MODID);

    public static final DeferredMenuType<MenuType<OvenMenu>> OVEN_MENU =
            REGISTRY.register("oven", OvenMenu::new);

//    public static final Supplier<MenuType<BlenderMenu>> BLENDER_MENU = MENU.register("blender_menu",
//            () -> IMenuTypeExtension.create(BlenderMenu::new));

    public static final Supplier<MenuType<BlenderMenu>> BLENDER_MENU = REGISTRY.register("blender",BlenderMenu::new);
    public static final Supplier<MenuType<DoughCraftingTableMenu>> DOUGH_MENU = REGISTRY.register("dough", DoughCraftingTableMenu::new);

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(OVEN_MENU.get(), OvenScreen::new);
        event.register(BLENDER_MENU.get(), BlenderScreen::new);
        event.register(DOUGH_MENU.get(), DoughCraftingTableScreen::new);
    }
}
