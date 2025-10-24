package net.weibai.bakeries.common.init;

import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.weibai.bakeries.BakeriesMod;
import net.weibai.rcglib.registration.impl.CreativeModeTabDeferredRegister;
import net.weibai.rcglib.registration.impl.DeferredCreativeModeTab;
import net.weibai.rcglib.utils.UtilTranslatable;


public class BakeriesCreativeModeTabs {
    @Getter
    private static final CreativeModeTabDeferredRegister REGISTER = new CreativeModeTabDeferredRegister(BakeriesMod.MODID);
    public static final DeferredCreativeModeTab<CreativeModeTab> BAKERIES_TAB = REGISTER.register(
            BakeriesMod.MODID + "_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(UtilTranslatable.setCreativeModeTabs(BakeriesMod.MODID ,BakeriesMod.MODID + "_tab")))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> BakeriesItems.OVEN.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
//                        output.accept(BakeriesBlocks.OVEN.get());
//                        output.accept(BakeriesItems.ROUND_BREAD_DOUGH.get());
//                        output.accept(BakeriesItems.ROUND_BREAD.get());
//                        output.accept(BakeriesItems.ICE_CUBES.get());
                        BakeriesItems.getREGISTER().getEntries().forEach(( item)->
                                output.accept(item.get()));
                    }).build());

}
