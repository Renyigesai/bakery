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
                        output.accept(BakeriesItems.OVEN.get());//烤箱
                        output.accept(BakeriesItems.DOUGH_CRAFTING_TABLE.get());//面胚制作台
                        output.accept(BakeriesItems.CUPBOARD.get());//厨台
                        output.accept(BakeriesItems.BAYSALT_FRAME.get());//晒盐框
                        output.accept(BakeriesItems.FERMENTATION_TANK.get());//发酵罐
                        output.accept(BakeriesItems.YEAST_TANK.get());//满装酵母罐
                        output.accept(BakeriesItems.CHEESE_TANK.get());//满装奶酪罐
                        output.accept(BakeriesItems.WOOD_COUNTER.get());//木质柜台
                        output.accept(BakeriesItems.BREAD_HOLDERS.get());//面包夹架
                        output.accept(BakeriesItems.BREAD_BASKET.get());//面包筐
                        output.accept(BakeriesItems.GLASS_CABINET_DOOR.get());//玻璃柜门
                        output.accept(BakeriesItems.BLACK_WHITE_CONCRETE.get());//黑白混凝土
                        output.accept(BakeriesItems.FLOUR_SIEVE.get());//面粉筛
                        output.accept(BakeriesItems.BREAD_KNIFE.get());//面包刀
                        output.accept(BakeriesItems.MOULD.get());//模具
                        output.accept(BakeriesItems.SALT_SCRAPER_RAKE.get());//盐耙
                        //原材料/食材
                        output.accept(BakeriesItems.WHOLE_WHEAT_FLOUR.get());//全麦面粉
                        output.accept(BakeriesItems.FLOUR.get());//面粉
                        output.accept(BakeriesItems.COCOA_POWDER.get());//可可粉
                        output.accept(BakeriesItems.SALT_ORE.get());//盐矿石
                        output.accept(BakeriesItems.DEEPSLATE_SALT_ORE.get());//深层盐矿石
                        output.accept(BakeriesItems.SALT_WATER_BUCKET.get());//盐水桶
                        output.accept(BakeriesItems.RAW_SALT_BLOCK.get());//粗盐块
                        output.accept(BakeriesItems.SALT.get());//盐
                        output.accept(BakeriesItems.BOTTLE_YEAST.get());//瓶装鲜酵母
                        output.accept(BakeriesItems.BOTTLE_MILK.get());//瓶装牛奶
                        output.accept(BakeriesItems.BOTTLE_CREAM.get());//瓶装奶油
                        output.accept(BakeriesItems.BOTTLE_BUTTER.get());//瓶装黄油
                        output.accept(BakeriesItems.BUTTER_CUBE.get());//黄油块
                        output.accept(BakeriesItems.CHEESE_CUBE.get());//奶酪块
                        output.accept(BakeriesItems.BROWN_SUGAR_CUBE.get());//红糖块
                        output.accept(BakeriesItems.TOMATO.get());//番茄
                        output.accept(BakeriesItems.OLIVE.get());//橄榄
                        //面包/食物
                        output.accept(BakeriesItems.BAGEL.get());//
                        output.accept(BakeriesItems.WHOLE_WHEAT_BAGEL.get());//
                        output.accept(BakeriesItems.ROUND_BREAD.get());//
                        output.accept(BakeriesItems.BERRY_BREAD.get());//
                        output.accept(BakeriesItems.BROWN_SUGAR_ROLL.get());//
                        output.accept(BakeriesItems.PINEAPPLE_BUN.get());//
                        output.accept(BakeriesItems.CROISSANT.get());//
                        output.accept(BakeriesItems.SALT_CROISSANT.get());//
                        output.accept(BakeriesItems.TOAST.get());//
                        output.accept(BakeriesItems.SLICED_TOAST.get());//
                        output.accept(BakeriesItems.CHEESE_COCOA_TOAST.get());//
                        output.accept(BakeriesItems.SLICED_CHEESE_COCOA_TOAST.get());//
                        output.accept(BakeriesItems.BAGUETTE.get());//
                        output.accept(BakeriesItems.CIABATTA.get());//
                        output.accept(BakeriesItems.COUNTRY_BREAD.get());//
                        output.accept(BakeriesItems.COUNTRY_BREAD_SLICE.get());//
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
                        output.accept(BakeriesItems.COCOA_DOUGH.get());
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
                        output.accept(BakeriesItems.MOULD_CHEESE_COCOA_TOAST_DOUGH.get());
                        output.accept(BakeriesItems.BAGUETTE_DOUGH.get());
                        output.accept(BakeriesItems.CIABATTA_DOUGH.get());
                        output.accept(BakeriesItems.COUNTRY_BREAD_DOUGH.get());
                    }))
                    .build());

}
