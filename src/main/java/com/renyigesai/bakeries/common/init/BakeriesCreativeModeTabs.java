package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.annotation.ItemData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredItem;
import net.weibai.rcglib.registration.impl.CreativeModeTabDeferredRegister;
import net.weibai.rcglib.registration.impl.DeferredCreativeModeTab;
import net.weibai.rcglib.utils.UtilTranslatable;

import java.lang.reflect.Field;


public class BakeriesCreativeModeTabs {
    public static final CreativeModeTabDeferredRegister REGISTER = new CreativeModeTabDeferredRegister(BakeriesMod.MODID);
    public static final DeferredCreativeModeTab<CreativeModeTab> BAKERIES_TAB = REGISTER.register(
            "0_bakeries_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("item_group.bakeries.bakeries_tab"))
                    .icon(() -> BakeriesItems.OVEN.get().getDefaultInstance())
                    .displayItems((parameters, output) -> addCreativeModeTab(output,"0_bakeries_main",BakeriesItems.class,true)).build());

    public static final DeferredCreativeModeTab<CreativeModeTab> SFP_TAB = REGISTER.register(
            "1_bakeries_sfp_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("item_group.bakeries.bakeries_sfp_tab"))
                    .icon(() -> BakeriesItems.ROUND_BREAD_DOUGH.get().getDefaultInstance())
                    .displayItems((parameters, output) -> addCreativeModeTab(output,"1_bakeries_sfp",BakeriesItems.class,true)).build());

    public static final DeferredCreativeModeTab<CreativeModeTab> COMPAT_TAB = REGISTER.register(
            "2_bakeries_compat_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("item_group.bakeries.bakeries_compat_tab"))
                    .icon(() -> BakeriesItems.RICE_BREAD.get().getDefaultInstance())
                    .displayItems((parameters, output) -> addCreativeModeTab(output,"2_bakeries_compat",BakeriesItems.class,true)).build());

    private static void addCreativeModeTab(CreativeModeTab.Output output,String tab,Class<?> items,boolean conditions){
        if (!conditions){
            return;
        }
        for (Field field : items.getDeclaredFields()){
            if (field.isAnnotationPresent(ItemData.class)){
                try {
                    Object object = field.get(null);
                    if (object instanceof DeferredItem<?> deferredItem){
                        ItemData annotation = field.getAnnotation(ItemData.class);
                        if (annotation != null && annotation.group().equals(tab)) {
                            output.accept(deferredItem.get());
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
