package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.annotation.ItemType;
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
            BakeriesMod.MODID + "_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(UtilTranslatable.setCreativeModeTabs(BakeriesMod.MODID ,BakeriesMod.MODID + "_tab")))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> BakeriesItems.OVEN.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        for (Field field : BakeriesItems.class.getDeclaredFields()){
                            if (field.isAnnotationPresent(ItemType.class)){
                                try {
                                    Object object = field.get(null);
                                    if (object instanceof DeferredItem<?> deferredItem){
                                        ItemType annotation = field.getAnnotation(ItemType.class);
                                        if (annotation != null && annotation.group().equals(BakeriesItems.BAKERIES_TAB)) {
                                            output.accept(deferredItem.get());
                                        }
                                    }
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
//                        BakeriesItems.REGISTER.getEntries().forEach(( item)->
//                                output.accept(item.get()));
                    }).build());

    public static final DeferredCreativeModeTab<CreativeModeTab> SFP_TAB = REGISTER.register(
            BakeriesMod.MODID + "_sfp_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(UtilTranslatable.setCreativeModeTabs(BakeriesMod.MODID ,BakeriesMod.MODID + "_sfp_tab")))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> BakeriesItems.ROUND_BREAD_DOUGH.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        for (Field field : BakeriesItems.class.getDeclaredFields()){
                            if (field.isAnnotationPresent(ItemType.class)){
                                try {
                                    Object object = field.get(null);
                                    if (object instanceof DeferredItem<?> deferredItem){
                                        ItemType annotation = field.getAnnotation(ItemType.class);
                                        if (annotation != null && annotation.group().equals(BakeriesItems.SFP_TAB)) {
                                            output.accept(deferredItem.get());
                                        }
                                    }
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
//                        BakeriesItems.REGISTER.getEntries().forEach(( item)->
//                                output.accept(item.get()));
                    }).build());


}
