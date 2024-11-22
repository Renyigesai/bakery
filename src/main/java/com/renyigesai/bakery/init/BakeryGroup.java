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
            CreativeModeTab.builder().icon(()-> new ItemStack(BakeryBlocks.OVEN.get()))
                    .title(Component.translatable("creativetab_bakery_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        //功能方块/方块
                        output.accept(BakeryItems.OVEN.get());
                        output.accept(BakeryItems.DOUGH_CRAFTING_TABLE.get());
                        output.accept(BakeryItems.FERMENTATION_TANK.get());
                        output.accept(BakeryItems.YEAST_TANK.get());
                        output.accept(BakeryBlocks.CHEESE_TANK.get());
                        output.accept(BakeryBlocks.GLASS_CABINET_DOOR.get());
                        output.accept(BakeryItems.FLOUR_SIEVE.get());
                        //原材料/食材
                        output.accept(BakeryItems.FLOUR.get());
                        output.accept(BakeryItems.WHOLE_WHEAT_FLOUR.get());
                        output.accept(BakeryItems.SALT.get());
                        output.accept(BakeryItems.BOTTLE_YEAST.get());
                        output.accept(BakeryItems.BOTTLE_MILK.get());
                        output.accept(BakeryItems.BOTTLE_CREAM.get());
                        output.accept(BakeryItems.BOTTLE_BUTTER.get());
                        output.accept(BakeryItems.BUTTER_CUBE.get());
                        output.accept(BakeryItems.BROWN_SUGAR_CUBE.get());
                        output.accept(BakeryItems.SWEET_DOUGH.get());
                        output.accept(BakeryItems.SALTED_DOUGH.get());
                        output.accept(BakeryItems.WHOLE_WHEAT_DOUGH.get());
                        //面包/食物
                        output.accept(BakeryItems.BAGEL.get());
                        output.accept(BakeryItems.WHOLE_WHEAT_BAGEL.get());
                        output.accept(BakeryItems.ROUND_BREAD.get());
                        output.accept(BakeryItems.CINNAMON_ROLL.get());
                        output.accept(BakeryItems.PINEAPPLE_BUN.get());
                        output.accept(BakeryItems.CROISSANT.get());
                        output.accept(BakeryItems.SALT_CROISSANT.get());
                        output.accept(BakeryBlocks.TOAST.get());
                        output.accept(BakeryItems.SLICED_TOAST.get());
                        output.accept(BakeryItems.BAGUETTE.get());
                        output.accept(BakeryItems.COUNTRY_BREAD.get());
                    }))
                    .build());

    public static final RegistryObject<CreativeModeTab> BAKERY_SEMI_MANUFACTURED_PRODUCT_TAB = REGISTER.register("bakery_semi_manufactured_product_tab",() ->
            CreativeModeTab.builder().icon(()-> new ItemStack(BakeryItems.BAGEL_DOUGH.get()))
                    .title(Component.translatable("creativetab_bakery_semi_manufactured_product_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(BakeryBlocks.SWEET_DOUGH_KNEAD.get());
                        output.accept(BakeryBlocks.SALTED_DOUGH_KNEAD.get());
                        output.accept(BakeryBlocks.WHOLE_WHEAT_DOUGH_KNEAD.get());
                        output.accept(BakeryItems.BAGEL_DOUGH.get());
                        output.accept(BakeryItems.WHOLE_WHEAT_BAGEL_DOUGH.get());
                        output.accept(BakeryItems.ROUND_BREAD_DOUGH.get());
                        output.accept(BakeryItems.CINNAMON_ROLL_DOUGH.get());
                        output.accept(BakeryItems.PINEAPPLE_BUN_DOUGH.get());
                        output.accept(BakeryItems.CROISSANT_DOUGH.get());
                        output.accept(BakeryItems.SALT_CROISSANT_DOUGH.get());
                        output.accept(BakeryItems.BAGUETTE_DOUGH.get());
                        output.accept(BakeryItems.COUNTRY_BREAD_DOUGH.get());
                    }))
                    .build());

}
