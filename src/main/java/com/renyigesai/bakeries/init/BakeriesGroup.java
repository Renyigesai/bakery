package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.compat.init.BakeriesCompatItems;
import com.renyigesai.bakeries.item.RepeatEatItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;

public class BakeriesGroup {

    public static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BakeriesMod.MODID);

    public static final RegistryObject<CreativeModeTab> BAKERY_TAB = REGISTER.register("bakeries_tab",() ->
            CreativeModeTab.builder().icon(()-> new ItemStack(BakeriesBlocks.OVEN.get()))
                    .title(Component.translatable("creativetab_bakeries_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        //功能方块/方块/物品
                        output.accept(BakeriesItems.OVEN.get());//烤箱
                        output.accept(BakeriesItems.STONE_KILN.get());//石窑炉
                        output.accept(BakeriesItems.BLENDER.get());//搅拌机
                        output.accept(BakeriesItems.MOKA_POT.get());//摩卡壶
                        output.accept(BakeriesItems.MOKA_POT_FILL.get());//装有咖啡的摩卡壶
                        output.accept(BakeriesItems.DOUGH_CRAFTING_TABLE.get());//面胚制作台
                        output.accept(BakeriesItems.CUPBOARD.get());//厨台
                        output.accept(BakeriesItems.FERMENTATION_TANK.get());//发酵罐
                        output.accept(BakeriesItems.YEAST_TANK.get());//满装酵母罐
                        output.accept(BakeriesItems.CHEESE_TANK.get());//满装奶酪罐
                        output.accept(BakeriesItems.FERMENTATION_BARREL.get());//满装奶酪罐
                        output.accept(BakeriesItems.DRINK_CUP.get());//玻璃饮料杯
                        output.accept(BakeriesItems.WHOLE_WHEAT_FLOUR_BAG.get());//全麦面粉袋
                        output.accept(BakeriesItems.FLOUR_BAG.get());//面粉袋
                        output.accept(BakeriesItems.MENU.get());//菜单
                        output.accept(BakeriesItems.WOOD_TRAY.get());//木盘
                        output.accept(BakeriesItems.WOOD_COUNTER.get());//木质柜台
                        output.accept(BakeriesItems.COFFEE_TABLE.get());//咖啡桌
                        output.accept(BakeriesItems.SOFA.get());//沙发
                        output.accept(BakeriesItems.SOFA_LIGHT_GRAY.get());//淡灰色沙发
                        output.accept(BakeriesItems.SOFA_RED.get());//紅色沙发
                        output.accept(BakeriesItems.CASH_REGISTER_COMPUTER.get());//收银电脑
                        output.accept(BakeriesItems.BREAD_HOLDERS.get());//面包夹架
                        output.accept(BakeriesItems.BREAD_BASKET.get());//面包筐
                        output.accept(BakeriesItems.CAKE_BOX.get());//蛋糕盒
                        output.accept(BakeriesItems.GLASS_CABINET_DOOR.get());//玻璃柜门
                        output.accept(BakeriesItems.GLASS_CABINET_DOOR_TWO.get());//玻璃柜门二号
                        output.accept(BakeriesItems.BLACK_WHITE_CONCRETE.get());//黑白混凝土
                        output.accept(BakeriesItems.FLOUR_SIEVE.get());//面粉筛
                        output.accept(BakeriesItems.BREAD_KNIFE.get());//面包刀
                        output.accept(BakeriesItems.STONE_KILN_SHOVEL.get());//炉铲
                        output.accept(BakeriesItems.MOULD.get());//模具
                        output.accept(BakeriesItems.MOULD_TWO.get());//模具
                        output.accept(BakeriesItems.PAPER_CUP.get());//纸杯
                        output.accept(BakeriesItems.SILICONE_PAPER.get());//油纸
                        //原材料/食材
                        output.accept(BakeriesItems.WHOLE_WHEAT_FLOUR.get());//全麦面粉
                        output.accept(BakeriesItems.FLOUR.get());//面粉
                        output.accept(BakeriesItems.COCOA_POWDER.get());//可可粉
                        output.accept(BakeriesItems.MATCHA_POWDER.get());//抹茶粉
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
                        output.accept(BakeriesItems.FOAMED_CREAM.get());//奶油
                        output.accept(BakeriesItems.CHEESE_CREAM.get());//奶酪奶油
                        output.accept(BakeriesItems.BUTTER_FLOUR_SAND.get());//黄油面砂
                        output.accept(BakeriesItems.HONEY_BUTTER.get());//蜂蜜黄油
                        output.accept(BakeriesItems.WHOLE_EGG.get());//全蛋
                        output.accept(BakeriesItems.RAW_PROTEIN.get());//生蛋白
                        output.accept(BakeriesItems.RAW_EGG_YOLK.get());//生蛋黄
                        output.accept(BakeriesItems.CHEESE_CUBE.get());//干酪块
                        output.accept(BakeriesItems.FRESH_CHEESE_CUBE.get());//鲜奶酪块
                        output.accept(BakeriesItems.BROWN_SUGAR_CUBE.get());//红糖块
                        output.accept(BakeriesItems.TOMATO.get());//番茄
                        output.accept(BakeriesItems.OLIVE.get());//橄榄
                        output.accept(BakeriesItems.RAW_COFFEE_BEAN.get());//生咖啡豆
                        output.accept(BakeriesItems.COFFEE_BEAN.get());//咖啡豆
                        output.accept(BakeriesItems.GROUND_COFFEE.get());//咖啡粉
                        output.accept(BakeriesItems.OLIVE_OIL.get());//橄榄油
                        output.accept(BakeriesItems.BEARNAISE.get());//蛋黄酱
                        output.accept(BakeriesItems.MEAT_FLOSS.get());//肉松
                        //面包/食物
                        output.accept(BakeriesItems.SCONE.get());//司康
                        output.accept(BakeriesItems.TRAY_SCONE.get());//盘装司康
                        output.accept(BakeriesItems.BAGEL.get());//贝果
                        output.accept(BakeriesItems.WHOLE_WHEAT_BAGEL.get());//全麦贝果
                        output.accept(BakeriesItems.ROUND_BREAD.get());//圆面包
                        output.accept(BakeriesItems.BERRY_BREAD.get());//莓果面包
                        output.accept(BakeriesItems.CHEESE_CREAM_BREAD.get());//莓果面包
                        output.accept(BakeriesItems.BROWN_SUGAR_ROLL.get());//红糖卷
                        output.accept(BakeriesItems.PINEAPPLE_BUN.get());//菠萝包
                        output.accept(BakeriesItems.PINEAPPLE_OIL.get());//菠萝包
                        output.accept(BakeriesItems.MEAT_FLOSS_BREAD_ROLL.get());//肉松面包卷
                        output.accept(BakeriesItems.CROISSANT.get());//可颂
                        output.accept(BakeriesItems.DIRTY_CHOCO_CROISSANT.get());//脏脏包
                        output.accept(BakeriesItems.SALT_CROISSANT.get());//盐可颂
                        output.accept(BakeriesItems.TOAST.get());//吐司
                        output.accept(BakeriesItems.SLICED_TOAST.get());//切片吐司
                        output.accept(BakeriesItems.HONEY_BUTTER_SPREAD_TOAST.get());//蜂蜜黄油抹吐司
                        output.accept(BakeriesItems.CHEESE_COCOA_TOAST.get());//奶酪可可吐司
                        output.accept(BakeriesItems.SLICED_CHEESE_COCOA_TOAST.get());//切片奶酪可可吐司
                        output.accept(BakeriesItems.BAGUETTE.get());//法棍
                        output.accept(BakeriesItems.CIABATTA.get());//恰巴塔
                        output.accept(BakeriesItems.FOCACCIA.get());//佛卡夏
                        output.accept(BakeriesItems.COUNTRY_BREAD.get());//乡村面包
                        output.accept(BakeriesItems.COUNTRY_BREAD_SLICE.get());//乡村面包切片
                        output.accept(BakeriesItems.HONEY_BUTTER_SPREAD_COUNTRY_BREAD.get());//乡村面包切片
                        output.accept(BakeriesItems.BERRY_BAGEL.get());//浆果贝果
                        output.accept(BakeriesItems.BAGEL_FILLED_SAUCE.get());//填酱贝果
                        output.accept(BakeriesItems.BAGUETTE_WITH_FILLING.get());//填馅法棍
                        output.accept(BakeriesItems.TOMATO_CHEESE_CROISSANT_SANDWICH.get());//番茄奶酪可颂三明治
                        output.accept(BakeriesItems.CREAM_PUMPKIN_PIE.get());//奶油南瓜派
                        output.accept(BakeriesItems.EGG_TART.get());//蛋挞
                        output.accept(BakeriesItems.CAKE_BASE.get());//蛋糕胚
                        output.accept(BakeriesItems.CUT_CAKE_BASE.get());//蛋糕胚切片
                        output.accept(BakeriesItems.RED_VELVET_CAKE_BASE.get());//红丝绒蛋糕胚
                        output.accept(BakeriesItems.SOAK_COFFEE_CUT_CAKE_BASE.get());//咖啡蛋糕胚切片
                        output.accept(BakeriesItems.CUP_CAKE.get());//纸杯蛋糕
                        output.accept(BakeriesItems.CAKE_ROLL.get());//蛋糕卷
                        output.accept(BakeriesItems.POUND_CAKE.get());//磅蛋糕
                        output.accept(BakeriesItems.SLICED_POUND_CAKE.get());//磅蛋糕切片
                        output.accept(BakeriesItems.CREAM_CAKE.get());//奶油蛋糕
                        output.accept(BakeriesItems.CREAM_CAKE_CUBE.get());//奶油蛋糕切块
                        output.accept(BakeriesItems.TIRAMISU.get());//提拉米苏
                        output.accept(BakeriesItems.CARROT_CAKE.get());//胡萝卜蛋糕
                        output.accept(BakeriesItems.RED_VELVET_CAKE.get());//红丝绒蛋糕
                        output.accept(BakeriesItems.PIZZA.get());//披萨
                        output.accept(BakeriesItems.CUSTOM_PIZZA.get());//自定义披萨
//                        output.accept(BakeriesItems.BASQUE_CAKE.get());//巴斯克蛋糕
                        /*
                        饮料
                        */
                        output.accept(BakeriesItems.ICED_AMERICAN.get());//冰美式
                        output.accept(BakeriesItems.ICED_LATTE.get());//冰拿铁
                        output.accept(BakeriesItems.BROWN_SUGAR_LATTE.get());//黑铁拿铁
                        output.accept(BakeriesItems.CREAM_BINGLE_COFFEE.get());//奶油冰冰乐
                        output.accept(BakeriesItems.MATCHA_LATTE.get());//抹茶拿铁
                        output.accept(BakeriesItems.MATCHA_PARFAIT.get());//抹茶巴菲
                        /*
                        唱片
                        */
                        output.accept(BakeriesItems.MUSIC_DISC_BAKING_IN_PROGRESS.get());
                    }))
                    .build());

    public static final RegistryObject<CreativeModeTab> BAKERY_SEMI_MANUFACTURED_PRODUCT_TAB = REGISTER.register("bakery_semi_manufactured_product_tab",() ->
            CreativeModeTab.builder().icon(()-> new ItemStack(BakeriesItems.BAGEL_DOUGH.get()))
                    .title(Component.translatable("creativetab_bakeries_semi_manufactured_product_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(BakeriesItems.CAKE_PASTE_BUCKET.get());
                        output.accept(BakeriesItems.EGG_YOLK_PASTE_BUCKET.get());
                        output.accept(BakeriesItems.FOAMED_PROTEIN_BUCKET.get());
                        output.accept(BakeriesItems.SWEET_DOUGH.get());
                        output.accept(BakeriesItems.COCOA_DOUGH.get());
                        output.accept(BakeriesItems.SALTED_DOUGH.get());
                        output.accept(BakeriesItems.WHOLE_WHEAT_DOUGH.get());
                        output.accept(BakeriesItems.CRISPY_DOUGH.get());
                        output.accept(BakeriesItems.PASTRY.get());
                        output.accept(BakeriesItems.EGG_TART_SHELL.get());
                        output.accept(BakeriesItems.RAW_EGG_TART.get());
                        output.accept(BakeriesItems.SCONE_DOUGH.get());
                        output.accept(BakeriesItems.MOULD_CAKE_PASTE.get());
                        output.accept(BakeriesItems.MOULD_CARROT_CAKE_PASTE.get());
                        output.accept(BakeriesItems.MOULD_BASQUE_CAKE_PASTE.get());
                        output.accept(BakeriesItems.MOULD_RED_VELVET_CAKE_PASTE.get());
                        output.accept(BakeriesItems.PAPER_CUP_CAKE_PASTE.get());
                        output.accept(BakeriesItems.MOULD_POUND_CAKE_PASTE.get());
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
                        output.accept(BakeriesItems.FOCACCIA_DOUGH.get());
                        output.accept(BakeriesItems.COUNTRY_BREAD_DOUGH.get());
                        output.accept(BakeriesItems.CREAM_PUMPKIN_PIE_DOUGH.get());
                        output.accept(BakeriesItems.PIZZA_FLATBREAD.get());
                        output.accept(BakeriesItems.RAW_PIZZA.get());
                        output.accept(BakeriesItems.RAW_CUSTOM_PIZZA.get());
                    }))
                    .build());

    public static final RegistryObject<CreativeModeTab> BAKERY_COMPAT_TAB = REGISTER.register("bakery_compat_tab",() ->
            CreativeModeTab.builder().icon(()-> new ItemStack(BakeriesCompatItems.GARLIC_FLAVORED_BAGUETTE.get()))
                    .title(Component.translatable("creativetab_bakery_compat_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(BakeriesCompatItems.RICE_BREAD.get());//米面包
                        output.accept(BakeriesItems.RICE_BREAD_DOUGH.get());//米面包面胚
                        output.accept(BakeriesCompatItems.SALMON_SANDWICH.get());//鲑鱼三明治
                        output.accept(BakeriesCompatItems.GARLIC_FLAVORED_BAGUETTE.get());//蒜香法棍
                        output.accept(BakeriesCompatItems.YUNTUI_MOONCAKE.get());//云腿月饼
                        output.accept(BakeriesItems.RAW_YUNTUI_MOONCAKE.get());//生云腿月饼
                        output.accept(BakeriesCompatItems.TRAY_YUNTUI_MOONCAKE.get());//盘装云腿月饼
                        output.accept(BakeriesCompatItems.ORANGE_AMERICAN.get());//橙C美式
                    }))
                    .build());
}
