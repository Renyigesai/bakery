package net.weibai.bakeries.common.init;

import lombok.Getter;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.weibai.bakeries.BakeriesMod;
import net.weibai.bakeries.common.registration.impl.MenuTypeDeferredRegister;


@EventBusSubscriber(value = Dist.CLIENT)
public class MSMenuType {
    @Getter
    private static final MenuTypeDeferredRegister REGISTRY = new MenuTypeDeferredRegister(BakeriesMod.MODID);

//    public static final DeferredMenuType<MenuType<FluidPipeMenu>> OVEN_MENU =
//            REGISTRY.register("fluid_pipe_menu", FluidPipeMenu::new);

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
//        event.register(OVEN_MENU.get(), FluidPipeScreen::new);
    }
}
