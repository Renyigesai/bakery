package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.annotation.ItemData;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.lang.reflect.Field;
import java.util.function.Supplier;


public class BakeriesCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,BakeriesMod.MODID);
    public static final Supplier<CreativeModeTab> BAKERIES_TAB = REGISTER.register(
            "0_bakeries_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("item_group.bakeries.bakeries_tab"))
                    .icon(() -> BakeriesItems.OVEN.get().getDefaultInstance())
                    .displayItems((parameters, output) -> addCreativeModeTab(output,"0_bakeries_main",BakeriesItems.class,true)).build());

    public static final Supplier<CreativeModeTab> SFP_TAB = REGISTER.register(
            "1_bakeries_sfp_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("item_group.bakeries.bakeries_sfp_tab"))
                    .icon(() -> BakeriesItems.ROUND_BREAD_DOUGH.get().getDefaultInstance())
                    .displayItems((parameters, output) -> addCreativeModeTab(output,"1_bakeries_sfp",BakeriesItems.class,true)).build());

    public static final Supplier<CreativeModeTab> COMPAT_TAB = REGISTER.register(
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
