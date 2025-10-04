package net.weibai.bakeries.common.init;

import lombok.Getter;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.weibai.mechanical_soar.client.inventory.fluid_pipe.FluidPipeMenu;
import net.weibai.mechanical_soar.client.inventory.fluid_pipe.FluidPipeScreen;
import net.weibai.mechanical_soar.common.MechanicalSoarMod;
import net.weibai.mechanical_soar.common.registration.impl.DeferredMenuType;
import net.weibai.mechanical_soar.common.registration.impl.MenuTypeDeferredRegister;

@EventBusSubscriber(value = Dist.CLIENT)
public class MSMenuType {
    @Getter
    private static final MenuTypeDeferredRegister REGISTRY = new MenuTypeDeferredRegister(MechanicalSoarMod.MODID);

    public static final DeferredMenuType<MenuType<FluidPipeMenu>> OVEN_MENU =
            REGISTRY.register("fluid_pipe_menu", FluidPipeMenu::new);

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(OVEN_MENU.get(), FluidPipeScreen::new);
    }
}
