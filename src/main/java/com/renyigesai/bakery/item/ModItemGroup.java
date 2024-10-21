package com.renyigesai.bakery.item;

import com.renyigesai.bakery.bakery;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModItemGroup {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, bakery.MODID);

    public static final RegistryObject<CreativeModeTab> BAKERY_TAB = CREATIVE_MODE_TAB.register("bakery_tab",() ->
            CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.BUTTER_CUBE.get()))
                    .title(Component.translatable("creativetab_bakery_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModItems.FLOUR.get());
                        output.accept(ModItems.FLOUR_RYE.get());
                        output.accept(ModItems.SALT.get());
                        output.accept(ModItems.BROWN_SUGAR_CUBE.get());
                        output.accept(ModItems.BUTTER_CUBE.get());
                        output.accept(ModItems.BAGEL.get());
                        output.accept(ModItems.BAGUETTE.get());
                        output.accept(ModItems.CINNAMON_ROLL.get());
                        output.accept(ModItems.COUNTRY_BREAD.get());
                        output.accept(ModItems.CROISSANT.get());
                        output.accept(ModItems.PINEAPPLE_BUN.get());
                        output.accept(ModItems.ROUND_BREAD.get());
                        output.accept(ModItems.SALT_CROISSANT.get());
                    }))
                    .build());

    public static final RegistryObject<CreativeModeTab> BAKERY_SEMI_MANUFACTURED_PRODUCT_TAB = CREATIVE_MODE_TAB.register("bakery_semi_manufactured_product_tab",() ->
            CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.BUTTER_CUBE.get()))
                    .title(Component.translatable("creativetab_bakery_semi_manufactured_product_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModItems.BAGEL_DOUGH.get());
                        output.accept(ModItems.BAGUETTE_DOUGH.get());
                        output.accept(ModItems.CINNAMON_ROLL_DOUGH.get());
                        output.accept(ModItems.PINEAPPLE_BUN_DOUGH.get());
                        output.accept(ModItems.CROISSANT_DOUGH.get());
                        output.accept(ModItems.SALT_CROISSANT_DOUGH.get());
                        output.accept(ModItems.RAW_EGG_TART.get());
                        output.accept(ModItems.RAW_PUMPKIN_PIE.get());
                        output.accept(ModItems.RAW_TARE_CRUST.get());
                        output.accept(ModItems.ROUND_BREAD_DOUGH.get());
                        output.accept(ModItems.COUNTRY_BREAD_DOUGH.get());
                        output.accept(ModItems.TART_SHELL.get());
                    }))
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
