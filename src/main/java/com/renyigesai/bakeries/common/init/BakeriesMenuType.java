package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.inventory.oven.OvenMenu;
import com.renyigesai.bakeries.common.inventory.oven.OvenScreen;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.weibai.rcglib.registration.impl.DeferredMenuType;
import net.weibai.rcglib.registration.impl.MenuTypeDeferredRegister;


@EventBusSubscriber(value = Dist.CLIENT)
public class BakeriesMenuType {
    public static final MenuTypeDeferredRegister REGISTRY = new MenuTypeDeferredRegister(BakeriesMod.MODID);

    public static final DeferredMenuType<MenuType<OvenMenu>> OVEN_MENU =
            REGISTRY.register("oven", OvenMenu::new);

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(OVEN_MENU.get(), OvenScreen::new);
    }

}
