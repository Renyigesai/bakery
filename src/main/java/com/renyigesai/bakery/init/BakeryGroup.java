package com.renyigesai.bakery.init;

import com.renyigesai.bakery.BakeryMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BakeryGroup {

    public static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BakeryMod.MODID);

    public static final RegistryObject<CreativeModeTab> BAKERY_TAB = REGISTER.register("bakery_tab",() ->
            CreativeModeTab.builder().icon(()-> new ItemStack(BakeryItems.BUTTER_CUBE.get()))
                    .title(Component.translatable("creativetab_bakery_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(BakeryItems.FLOUR.get());
                        output.accept(BakeryItems.FLOUR_RYE.get());
                        output.accept(BakeryItems.SALT.get());
                        output.accept(BakeryItems.BROWN_SUGAR_CUBE.get());
                        output.accept(BakeryItems.BUTTER_CUBE.get());
                        output.accept(BakeryItems.BAGEL_BLOCK.get());
                        output.accept(BakeryItems.BAGUETTE_BLOCK.get());
                        output.accept(BakeryItems.CINNAMON_ROLL_BLOCK.get());
                        output.accept(BakeryItems.COUNTRY_BREAD_BLOCK.get());
                        output.accept(BakeryItems.CROISSANT_BLOCK.get());
                        output.accept(BakeryItems.PINEAPPLE_BUN.get());
                        output.accept(BakeryItems.ROUND_BREAD.get());
                        output.accept(BakeryItems.SALT_CROISSANT.get());
                        output.accept(BakeryBlocks.FERMENTATION_TANK.get());
                        output.accept(BakeryBlocks.YEAST_TANK.get());
                        output.accept(BakeryItems.BOTTLE_YEAST.get());
                        output.accept(BakeryBlocks.GLASS_CABINET_DOOR.get());
                    }))
                    .build());

    public static final RegistryObject<CreativeModeTab> BAKERY_SEMI_MANUFACTURED_PRODUCT_TAB = REGISTER.register("bakery_semi_manufactured_product_tab",() ->
            CreativeModeTab.builder().icon(()-> new ItemStack(BakeryItems.BUTTER_CUBE.get()))
                    .title(Component.translatable("creativetab_bakery_semi_manufactured_product_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(BakeryItems.BAGEL_DOUGH.get());
                        output.accept(BakeryItems.BAGUETTE_DOUGH.get());
                        output.accept(BakeryItems.CINNAMON_ROLL_DOUGH.get());
                        output.accept(BakeryItems.PINEAPPLE_BUN_DOUGH.get());
                        output.accept(BakeryItems.CROISSANT_DOUGH.get());
                        output.accept(BakeryItems.SALT_CROISSANT_DOUGH.get());
                        output.accept(BakeryItems.RAW_EGG_TART.get());
                        output.accept(BakeryItems.RAW_PUMPKIN_PIE.get());
                        output.accept(BakeryItems.RAW_TARE_CRUST.get());
                        output.accept(BakeryItems.ROUND_BREAD_DOUGH.get());
                        output.accept(BakeryItems.COUNTRY_BREAD_DOUGH.get());
                        output.accept(BakeryItems.TART_SHELL.get());
                    }))
                    .build());

}
