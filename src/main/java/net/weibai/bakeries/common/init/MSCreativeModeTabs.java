package net.weibai.bakeries.common.init;

import lombok.Getter;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.weibai.bakeries.BakeriesMod;
import net.weibai.bakeries.common.utils.UtilTranslatable;

public class MSCreativeModeTabs {
    @Getter
    private static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BakeriesMod.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MS_ITEM_TAB = REGISTER.register(
            BakeriesMod.MODID + "_item_original",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(UtilTranslatable.setCreativeModeTabs("items")))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> MSItems.MS_ICON.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {

                    }).build());

}
