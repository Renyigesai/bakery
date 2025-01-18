package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BakeriesGroup {

    public static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BakeriesMod.MODID);

    public static final RegistryObject<CreativeModeTab> BAKERY_TAB = REGISTER.register("bakeries_tab",() ->
            CreativeModeTab.builder().icon(()-> new ItemStack(BakeriesBlocks.OVEN.get()))
                    .title(Component.translatable("creativetab_bakeries_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        //功能方块/方块/物品
                        output.accept(BakeriesItems.OVEN.get());
                        output.accept(BakeriesItems.DOUGH_CRAFTING_TABLE.get());
                        output.accept(BakeriesItems.CUPBOARD.get());
                        output.accept(BakeriesItems.BAYSALT_FRAME.get());
                        output.accept(BakeriesItems.FERMENTATION_TANK.get());
                        output.accept(BakeriesItems.YEAST_TANK.get());
                        output.accept(BakeriesItems.CHEESE_TANK.get());
                        output.accept(BakeriesItems.WOOD_COUNTER.get());
                        output.accept(BakeriesItems.BREAD_BASKET.get());
                        output.accept(BakeriesItems.GLASS_CABINET_DOOR.get());
                        output.accept(BakeriesItems.BLACK_WHITE_CONCRETE.get());
                        output.accept(BakeriesItems.FLOUR_SIEVE.get());
                        output.accept(BakeriesItems.MOULD.get());
                        //原材料/食材
                        output.accept(BakeriesItems.FLOUR.get());
                        output.accept(BakeriesItems.WHOLE_WHEAT_FLOUR.get());
                        output.accept(BakeriesItems.SALT_WATER_BUCKET.get());
                        output.accept(BakeriesItems.RAW_SALT_BLOCK.get());
                        output.accept(BakeriesItems.SALT.get());
                        output.accept(BakeriesItems.BOTTLE_YEAST.get());
                        output.accept(BakeriesItems.BOTTLE_MILK.get());
                        output.accept(BakeriesItems.BOTTLE_CREAM.get());
                        output.accept(BakeriesItems.BOTTLE_BUTTER.get());
                        output.accept(BakeriesItems.BUTTER_CUBE.get());
                        output.accept(BakeriesItems.BROWN_SUGAR_CUBE.get());
                        output.accept(BakeriesItems.TOMATO.get());
                        output.accept(BakeriesItems.OLIVE.get());
                        //面包/食物
                        output.accept(BakeriesItems.BAGEL.get());
                        output.accept(BakeriesItems.WHOLE_WHEAT_BAGEL.get());
                        output.accept(BakeriesItems.ROUND_BREAD.get());
                        output.accept(BakeriesItems.BERRY_BREAD.get());
                        output.accept(BakeriesItems.BROWN_SUGAR_ROLL.get());
                        output.accept(BakeriesItems.PINEAPPLE_BUN.get());
                        output.accept(BakeriesItems.CROISSANT.get());
                        output.accept(BakeriesItems.SALT_CROISSANT.get());
                        output.accept(BakeriesItems.TOAST.get());
                        output.accept(BakeriesItems.SLICED_TOAST.get());
                        output.accept(BakeriesItems.BAGUETTE.get());
                        output.accept(BakeriesItems.CIABATTA.get());
                        output.accept(BakeriesItems.COUNTRY_BREAD.get());
                        output.accept(BakeriesItems.COUNTRY_BREAD_SLICE.get());
//                        output.accept(BakeriesItems.PIZZA.get());
//                        output.accept(BakeriesItems.SAUSAGE_PIZZA.get());
//                        output.accept(BakeriesItems.MEAT_PASTE_PIZZA.get());
                    }))
                    .build());

    public static final RegistryObject<CreativeModeTab> BAKERY_SEMI_MANUFACTURED_PRODUCT_TAB = REGISTER.register("bakery_semi_manufactured_product_tab",() ->
            CreativeModeTab.builder().icon(()-> new ItemStack(BakeriesItems.BAGEL_DOUGH.get()))
                    .title(Component.translatable("creativetab_bakeries_semi_manufactured_product_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(BakeriesItems.SWEET_DOUGH.get());
                        output.accept(BakeriesItems.SALTED_DOUGH.get());
                        output.accept(BakeriesItems.WHOLE_WHEAT_DOUGH.get());
                        output.accept(BakeriesItems.PASTRY.get());
                        output.accept(BakeriesItems.BAGEL_DOUGH.get());
                        output.accept(BakeriesItems.WHOLE_WHEAT_BAGEL_DOUGH.get());
                        output.accept(BakeriesItems.ROUND_BREAD_DOUGH.get());
                        output.accept(BakeriesItems.BERRY_BREAD_DOUGH.get());
                        output.accept(BakeriesItems.BROWN_SUGAR_ROLL_DOUGH.get());
                        output.accept(BakeriesItems.PINEAPPLE_BUN_DOUGH.get());
                        output.accept(BakeriesItems.CROISSANT_DOUGH.get());
                        output.accept(BakeriesItems.SALT_CROISSANT_DOUGH.get());
                        output.accept(BakeriesItems.MOULD_TOAST_DOUGH.get());
                        output.accept(BakeriesItems.BAGUETTE_DOUGH.get());
                        output.accept(BakeriesItems.CIABATTA_DOUGH.get());
                        output.accept(BakeriesItems.COUNTRY_BREAD_DOUGH.get());
                    }))
                    .build());

}
