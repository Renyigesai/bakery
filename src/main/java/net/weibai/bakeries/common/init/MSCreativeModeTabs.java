package net.weibai.bakeries.common.init;

import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.weibai.bakeries.BakeriesMod;
import net.weibai.bakeries.common.registration.impl.CreativeModeTabDeferredRegister;
import net.weibai.bakeries.common.registration.impl.DeferredCreativeModeTab;
import net.weibai.bakeries.common.utils.UtilTranslatable;

public class MSCreativeModeTabs {
    @Getter
    private static final CreativeModeTabDeferredRegister REGISTER = new CreativeModeTabDeferredRegister(BakeriesMod.MODID);
    public static final DeferredCreativeModeTab<CreativeModeTab> MS_ITEM_TAB = REGISTER.register(
            BakeriesMod.MODID + "_item_original",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(UtilTranslatable.setCreativeModeTabs("items")))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> MSItems.MS_ICON.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {

                    }).build());

}
