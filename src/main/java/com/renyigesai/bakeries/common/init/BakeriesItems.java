package com.renyigesai.bakeries.common.init;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.annotation.CustomData;
import com.renyigesai.bakeries.api.annotation.ItemData;
import com.renyigesai.bakeries.api.items.BBreadItem;
import com.renyigesai.bakeries.api.items.RawItem;
import com.renyigesai.bakeries.common.items.*;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.weibai.rcglib.items.BreadItem;
import net.weibai.rcglib.items.FoodWeaponItem;
import net.weibai.rcglib.items.ItemRarity;
import net.weibai.rcglib.items.RepeatEatItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
@CustomData
public class BakeriesItems {
    public static final String BAKERIES_TAB = BakeriesMod.MODID + "_tab";
    public static final String SFP_TAB = BakeriesMod.MODID + "_sfp";
    public static final String NOT = BakeriesMod.MODID + "_not";

    public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(BakeriesMod.MODID);
    private BakeriesItems(){}

    @ItemData(zhCn = "搅拌机", itemType = ItemData.ItemType.BLOCK, model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> BLENDER;

    @ItemData(zhCn = "烤炉", itemType = ItemData.ItemType.BLOCK, model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> OVEN;

    @ItemData(zhCn = "面胚制作台", itemType = ItemData.ItemType.BLOCK, model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> DOUGH_CRAFTING_TABLE;

    @ItemData(zhCn = "厨台", itemType = ItemData.ItemType.BLOCK, model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> CUPBOARD;

    @ItemData(zhCn = "发酵罐",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> FERMENTATION_TANK;

    @ItemData(zhCn = "满装酵母罐",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> YEAST_TANK;

    @ItemData(zhCn = "满装牛奶罐",group = NOT,itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> MILK_TANK;

    @ItemData(zhCn = "满装奶酪罐",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> CHEESE_TANK;

    @ItemData(zhCn = "全麦面粉袋",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> WHOLE_WHEAT_FLOUR_BAG;
    @ItemData(zhCn = "面粉袋",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> FLOUR_BAG;

    @ItemData(zhCn = "木制柜台",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> WOOD_COUNTER;

    @ItemData(zhCn = "玻璃橱柜门",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> GLASS_CABINET_DOOR;

    @ItemData(zhCn = "面包筐",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> BREAD_BASKET;

    @ItemData(zhCn = "面包刀",model = ItemData.ModelType.TOOL)
    public static final DeferredItem<Item> BREAD_KNIFE;

    @ItemData(zhCn = "面粉筛",model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> FLOUR_SIEVE;

    @ItemData(zhCn = "模具",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> MOULD;

    @ItemData(zhCn = "面粉")
    public static final DeferredItem<Item> FLOUR;

    @ItemData(zhCn = "全麦面粉")
    public static final DeferredItem<Item> WHOLE_WHEAT_FLOUR;

    @ItemData(zhCn = "可可粉")
    public static final DeferredItem<Item> COCOA_POWDER;

    @ItemData(zhCn = "抹茶粉")
    public static final DeferredItem<Item> MATCHA_POWDER;

    @ItemData(zhCn = "盐矿石",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> SALT_ORE;

    @ItemData(zhCn = "盐矿石",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> DEEPSLATE_SALT_ORE;

    @ItemData(zhCn = "盐")
    public static final DeferredItem<Item> SALT;

    @ItemData(zhCn = "盐块",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> RAW_SALT_BLOCK;

    @ItemData(zhCn = "瓶装酵母")
    public static final DeferredItem<Item> BOTTLE_YEAST;

    @ItemData(zhCn = "瓶装奶")
    public static final DeferredItem<Item> BOTTLE_MILK;

    @ItemData(zhCn = "瓶装奶油")
    public static final DeferredItem<Item> BOTTLE_CREAM;

    @ItemData(zhCn = "瓶装黄油")
    public static final DeferredItem<Item> BOTTLE_BUTTER;

    @ItemData(zhCn = "黄油块")
    public static final DeferredItem<Item> BUTTER_CUBE;

    @ItemData(zhCn = "打发奶油")
    public static final DeferredItem<Item> FOAMED_CREAM;

    @ItemData(zhCn = "奶酪奶油")
    public static final DeferredItem<Item> CHEESE_CREAM;

    @ItemData(zhCn = "黄油面砂")
    public static final DeferredItem<Item> BUTTER_FLOUR_SAND;

    @ItemData(zhCn = "蜂蜜黄油")
    public static final DeferredItem<Item> HONEY_BUTTER;

    @ItemData(zhCn = "全蛋")
    public static final DeferredItem<Item> WHOLE_EGG;

    @ItemData(zhCn = "生蛋白")
    public static final DeferredItem<Item> RAW_PROTEIN;

    @ItemData(zhCn = "生蛋黄")
    public static final DeferredItem<Item> RAW_EGG_YOLK;

    @ItemData(zhCn = "干酪块")
    public static final DeferredItem<Item> CHEESE_CUBE;

    @ItemData(zhCn = "鲜奶酪块")
    public static final DeferredItem<Item> FRESH_CHEESE_CUBE;

    @ItemData(zhCn = "红糖块")
    public static final DeferredItem<Item> BROWN_SUGAR_CUBE;

    @ItemData(zhCn = "生咖啡豆",itemType = ItemData.ItemType.BLOCK)
    public static final DeferredItem<Item> RAW_COFFEE_BEAN;

    @ItemData(zhCn = "咖啡豆")
    public static final DeferredItem<Item> COFFEE_BEAN;

    @ItemData(zhCn = "咖啡粉")
    public static final DeferredItem<Item> GROUND_COFFEE;

    @ItemData(zhCn = "蛋黄酱")
    public static final DeferredItem<Item> BEARNAISE;

    @ItemData(zhCn = "橄榄油")
    public static final DeferredItem<Item> OLIVE_OIL;

    @ItemData(zhCn = "肉松")
    public static final DeferredItem<Item> MEAT_FLOSS;

    @ItemData(zhCn = "司康饼")
    public static final DeferredItem<Item> SCONE;

    @ItemData(zhCn = "番茄",itemType = ItemData.ItemType.BLOCK)
    public static final DeferredItem<Item> TOMATO;

    @ItemData(zhCn = "橄榄")
    public static final DeferredItem<Item> OLIVE;

    @ItemData(zhCn = "吐司",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> TOAST;

    @ItemData(zhCn = "吐司片")
    public static final DeferredItem<Item> SLICED_TOAST;

    @ItemData(zhCn = "蜂蜜黄油抹吐司片")
    public static final DeferredItem<Item> HONEY_BUTTER_SPREAD_TOAST;

    @ItemData(zhCn = "奶酪可可吐司",itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> CHEESE_COCOA_TOAST;

    @ItemData(zhCn = "奶酪可可吐司片")
    public static final DeferredItem<Item> SLICED_CHEESE_COCOA_TOAST;

    @ItemData(zhCn = "乡村面包片")
    public static final DeferredItem<Item> COUNTRY_BREAD_SLICE;

    @ItemData(zhCn = "蜂蜜黄油抹乡村面包片")
    public static final DeferredItem<Item> HONEY_BUTTER_SPREAD_COUNTRY_BREAD;

    @ItemData(zhCn = "冰块")
    public static final DeferredItem<Item> ICE_CUBES;

    @ItemData(zhCn = "甜面团", group = SFP_TAB)
    public static final DeferredItem<Item> SWEET_DOUGH;

    @ItemData(zhCn = "咸面团", group = SFP_TAB)
    public static final DeferredItem<Item> SALTED_DOUGH;

    @ItemData(zhCn = "全麦面团", group = SFP_TAB)
    public static final DeferredItem<Item> WHOLE_WHEAT_DOUGH;

    /*面包方块*/
    @ItemData(zhCn = "贝果",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> BAGEL;

    @ItemData(zhCn = "全麦贝果",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> WHOLE_WHEAT_BAGEL;

    @ItemData(zhCn = "圆面包",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> ROUND_BREAD;

    @ItemData(zhCn = "莓果面包",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> BERRY_BREAD;

    @ItemData(zhCn = "乳酪面包",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> CHEESE_CREAM_BREAD;

    @ItemData(zhCn = "红糖卷",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> BROWN_SUGAR_ROLL;

    @ItemData(zhCn = "菠萝包",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> PINEAPPLE_BUN;

    @ItemData(zhCn = "肉松面包卷",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> MEAT_FLOSS_BREAD_ROLL;

    @ItemData(zhCn = "可颂",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> CROISSANT;

    @ItemData(zhCn = "脏脏包",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> DIRTY_CHOCO_CROISSANT;

    @ItemData(zhCn = "盐可颂",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> SALT_CROISSANT;

    @ItemData(zhCn = "法棍",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> BAGUETTE;

    @ItemData(zhCn = "乡村面包",model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> COUNTRY_BREAD;

    @ItemData(zhCn = "恰巴塔面包",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> CIABATTA;

    @ItemData(zhCn = "佛卡夏面包",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> FOCACCIA;

    @ItemData(zhCn = "浆果贝果",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> BERRY_BAGEL;

    @ItemData(zhCn = "填酱贝果",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> BAGEL_FILLED_SAUCE;

    @ItemData(zhCn = "填馅法棍",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> BAGUETTE_WITH_FILLING;

    @ItemData(zhCn = "番茄奶酪可颂三明治",model = ItemData.ModelType.BREAD)
    public static final DeferredItem<Item> TOMATO_CHEESE_CROISSANT_SANDWICH;

    /*功能物品*/
    @ItemData(zhCn = "酥皮", group = SFP_TAB)
    public static final DeferredItem<Item> PASTRY;

    @ItemData(zhCn = "贝果面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> BAGEL_DOUGH;

    @ItemData(zhCn = "全麦贝果面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> WHOLE_WHEAT_BAGEL_DOUGH;

    @ItemData(zhCn = "圆面包面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> ROUND_BREAD_DOUGH;
    @ItemData(zhCn = "红糖卷面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> BROWN_SUGAR_ROLL_DOUGH;

    @ItemData(zhCn = "菠萝包面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> PINEAPPLE_BUN_DOUGH;

    @ItemData(zhCn = "可颂面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> CROISSANT_DOUGH;

    @ItemData(zhCn = "盐可颂面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> SALT_CROISSANT_DOUGH;

    @ItemData(zhCn = "法棍面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> BAGUETTE_DOUGH;

    @ItemData(zhCn = "恰巴塔面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> CIABATTA_DOUGH;

    @ItemData(zhCn = "佛卡夏面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> FOCACCIA_DOUGH;

    @ItemData(zhCn = "乡村面包面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> COUNTRY_BREAD_DOUGH;

    @ItemData(zhCn = "吐司面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> MOULD_TOAST_DOUGH;

    @ItemData(zhCn = "吐司",group = NOT,itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> MOULD_TOAST;

    @ItemData(zhCn = "奶酪可可吐司面胚", group = SFP_TAB,model = ItemData.ModelType.CUSTOM)
    public static final DeferredItem<Item> MOULD_CHEESE_COCOA_TOAST_DOUGH;

    @ItemData(zhCn = "奶酪可可吐司",group = NOT,itemType = ItemData.ItemType.BLOCK,model = ItemData.ModelType.BLOCK)
    public static final DeferredItem<Item> MOULD_CHEESE_COCOA_TOAST;




    static {
        OVEN = block(BakeriesBlocks.OVEN);
        BLENDER = block(BakeriesBlocks.BLENDER);
        DOUGH_CRAFTING_TABLE = block(BakeriesBlocks.DOUGH_CRAFTING_TABLE);
        CUPBOARD = block(BakeriesBlocks.CUPBOARD);
        FERMENTATION_TANK = block(BakeriesBlocks.FERMENTATION_TANK);
        YEAST_TANK = block(BakeriesBlocks.YEAST_TANK);
        MILK_TANK = block(BakeriesBlocks.MILk_TANK);
        CHEESE_TANK = block(BakeriesBlocks.CHEESE_TANK);
        WOOD_COUNTER = block(BakeriesBlocks.WOOD_COUNTER);
        GLASS_CABINET_DOOR = block(BakeriesBlocks.GLASS_CABINET_DOOR);
        BREAD_BASKET = block(BakeriesBlocks.BREAD_BASKET);
        WHOLE_WHEAT_FLOUR_BAG = block(BakeriesBlocks.WHOLE_WHEAT_FLOUR_BAG);
        FLOUR_BAG = block(BakeriesBlocks.FLOUR_BAG);
        BREAD_KNIFE = REGISTER.register("bread_knife",()-> new BreadKnifeItem(Tiers.IRON,new Item.Properties()));
        FLOUR_SIEVE = REGISTER.register("flour_sieve",()-> new FlourSieveItem(new Item.Properties().stacksTo(1).durability(250)));
        MOULD = block(BakeriesBlocks.MOULD);
        FLOUR = item("flour");
        WHOLE_WHEAT_FLOUR = item("whole_wheat_flour");
        COCOA_POWDER = item("cocoa_powder");
        MATCHA_POWDER = item("matcha_powder");
        SALT_ORE = block(BakeriesBlocks.SALT_ORE);
        DEEPSLATE_SALT_ORE = block(BakeriesBlocks.DEEPSLATE_SALT_ORE);
        SALT = item("salt");
        RAW_SALT_BLOCK = block(BakeriesBlocks.RAW_SALT_BLOCK);
        BOTTLE_YEAST = REGISTER.register("bottle_yeast",()-> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));
        BOTTLE_MILK = REGISTER.register("bottle_milk",()-> new ShakeItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE),BakeriesItems.BOTTLE_CREAM));
        BOTTLE_CREAM = REGISTER.register("bottle_cream",()-> new ShakeItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE),BakeriesItems.BOTTLE_BUTTER));
        BOTTLE_BUTTER = REGISTER.register("bottle_butter",()-> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));
        BUTTER_CUBE = item("butter_cube");
        FOAMED_CREAM = foodItem("foamed_cream",BakeriesFoodProperties.FOAMED_CREAM);
        CHEESE_CREAM = foodItem("cheese_cream",BakeriesFoodProperties.FOAMED_CREAM);
        BUTTER_FLOUR_SAND = item("butter_flour_sand");
        HONEY_BUTTER = item("honey_butter");
        WHOLE_EGG = REGISTER.register("whole_egg", WholeEggItem::new);
        RAW_PROTEIN = item("raw_protein");
        RAW_EGG_YOLK = item("raw_egg_yolk");
        CHEESE_CUBE = foodItem("cheese_cube",BakeriesFoodProperties.CHEESE_CUBE);
        FRESH_CHEESE_CUBE = foodItem("fresh_cheese_cube",BakeriesFoodProperties.CHEESE_CUBE);
        BROWN_SUGAR_CUBE = item("brown_sugar_cube");
        RAW_COFFEE_BEAN = REGISTER.register("raw_coffee_bean",()-> new BlockItem(BakeriesBlocks.COFFEE_PLANT.get(),new Item.Properties()));
        COFFEE_BEAN = item("coffee_bean");
        GROUND_COFFEE = item("ground_coffee");
        BEARNAISE = registerItem("bearnaise",()->new Item(new Item.Properties().stacksTo(16).craftRemainder(Items.BOWL)));
        OLIVE_OIL = registerItem("olive_oil",()-> new OliveOilItem(new Item.Properties().durability(6)));
        MEAT_FLOSS = foodItem("meat_floss",BakeriesFoodProperties.MEAT_FLOSS);
        ICE_CUBES = item("ice_cubes");
        SCONE = foodItem("scone",BakeriesFoodProperties.SCONE);
        TOMATO = REGISTER.register("tomato",()-> new BlockItem(BakeriesBlocks.TOMATO.get(),new Item.Properties().food(BakeriesFoodProperties.TOMATO)));
        OLIVE = foodItem("olive",BakeriesFoodProperties.OLIVE);
        TOAST = block(BakeriesBlocks.TOAST);
        SLICED_TOAST = foodItem("sliced_toast",BakeriesFoodProperties.SLICED_TOAST);
        HONEY_BUTTER_SPREAD_TOAST = registerItem("honey_butter_spread_toast",()-> new Item(new Item.Properties().food(BakeriesFoodProperties.HONEY_BUTTER_SPREAD_TOAST)));
        CHEESE_COCOA_TOAST = block(BakeriesBlocks.CHEESE_COCOA_TOAST);
        SLICED_CHEESE_COCOA_TOAST = foodItem("sliced_cheese_cocoa_toast",BakeriesFoodProperties.SLICED_CHEESE_COCOA_TOAST);
        COUNTRY_BREAD_SLICE = foodItem("country_bread_slice",BakeriesFoodProperties.COUNTRY_BREAD_SLICE);
        HONEY_BUTTER_SPREAD_COUNTRY_BREAD = registerItem("honey_butter_spread_country_bread",()-> new Item(new Item.Properties().food(BakeriesFoodProperties.HONEY_BUTTER_SPREAD_COUNTRY_BREAD)));

        BAGEL = foodBreadBlock(BakeriesBlocks.BAGEL, BakeriesFoodProperties.BAGEL);
        WHOLE_WHEAT_BAGEL = foodBreadBlock(BakeriesBlocks.WHOLE_WHEAT_BAGEL, BakeriesFoodProperties.WHOLE_WHEAT_BAGEL);
        ROUND_BREAD = foodBreadBlock(BakeriesBlocks.ROUND_BREAD, BakeriesFoodProperties.ROUND_BREAD);
        BERRY_BREAD = foodBreadBlock(BakeriesBlocks.BERRY_BREAD, BakeriesFoodProperties.BERRY_BREAD);
        CHEESE_CREAM_BREAD = foodBreadBlock(BakeriesBlocks.CHEESE_CREAM_BREAD, ItemRarity.advanced(), BakeriesFoodProperties.CHEESE_CREAM_BREAD,true);
        BROWN_SUGAR_ROLL = foodBreadBlock(BakeriesBlocks.BROWN_SUGAR_ROLL, BakeriesFoodProperties.BROWN_SUGAR_ROLL,true);
        PINEAPPLE_BUN = foodBreadBlock(BakeriesBlocks.PINEAPPLE_BUN, BakeriesFoodProperties.PINEAPPLE_BUN,true);
        MEAT_FLOSS_BREAD_ROLL = foodBreadBlock(BakeriesBlocks.MEAT_FLOSS_BREAD_ROLL, ItemRarity.advanced(), BakeriesFoodProperties.MEAT_FLOSS_BREAD);
        CROISSANT = foodBreadBlock(BakeriesBlocks.CROISSANT, BakeriesFoodProperties.CROISSANT,true);
        DIRTY_CHOCO_CROISSANT = foodBreadBlock(BakeriesBlocks.DIRTY_CHOCO_CROISSANT, BakeriesFoodProperties.DIRTY_CHOCO_CROISSANT,true);
        SALT_CROISSANT = foodBreadBlock(BakeriesBlocks.SALT_CROISSANT, BakeriesFoodProperties.SALT_CROISSANT,true);
        CIABATTA = foodBreadBlock(BakeriesBlocks.CIABATTA, BakeriesFoodProperties.CIABATTA);
        FOCACCIA = foodBreadBlock(BakeriesBlocks.FOCACCIA, BakeriesFoodProperties.FOCACCIA,true);
        BERRY_BAGEL = foodBreadBlock(BakeriesBlocks.BERRY_BAGEL, ItemRarity.advanced(), BakeriesFoodProperties.BERRY_BAGEL,true);
        BAGEL_FILLED_SAUCE = foodBreadBlock(BakeriesBlocks.BAGEL_FILLED_SAUCE, ItemRarity.advanced(), BakeriesFoodProperties.BAGEL_FILLED_SAUCE);
        BAGUETTE_WITH_FILLING = foodBreadBlock(BakeriesBlocks.BAGUETTE_WITH_FILLING, ItemRarity.advanced(), BakeriesFoodProperties.BAGUETTE_WITH_FILLING);
        TOMATO_CHEESE_CROISSANT_SANDWICH = foodBreadBlock(BakeriesBlocks.TOMATO_CHEESE_CROISSANT_SANDWICH, ItemRarity.advanced(), BakeriesFoodProperties.TOMATO_CHEESE_CROISSANT_SANDWICH,true);
        BAGUETTE = foodWeaponBreadBlock(BakeriesBlocks.BAGUETTE, 4, ItemRarity.common(), BakeriesFoodProperties.BAGUETTE,
                ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.withDefaultNamespace("base_attack_damage"), 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLocation.withDefaultNamespace("base_attack_speed"), -3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build());
        COUNTRY_BREAD = block(BakeriesBlocks.COUNTRY_BREAD);
        MOULD_TOAST = block(BakeriesBlocks.MOULD_TOAST);
        MOULD_CHEESE_COCOA_TOAST = block(BakeriesBlocks.MOULD_CHEESE_COCOA_TOAST);

        SWEET_DOUGH = item("sweet_dough");
        SALTED_DOUGH = item("salted_dough");
        WHOLE_WHEAT_DOUGH = item("whole_wheat_dough");
        PASTRY = item("pastry");
        BAGEL_DOUGH = rawItem("bagel_dough",200);
        WHOLE_WHEAT_BAGEL_DOUGH = rawItem("whole_wheat_bagel_dough",200);
        ROUND_BREAD_DOUGH = rawItem("round_bread_dough", 155);
        BROWN_SUGAR_ROLL_DOUGH = rawItem("brown_sugar_roll_dough",155);
        PINEAPPLE_BUN_DOUGH = rawItem("pineapple_bun_dough",170);
        CROISSANT_DOUGH = rawItem("croissant_dough",175);
        SALT_CROISSANT_DOUGH = rawItem("salt_croissant_dough",180);
        BAGUETTE_DOUGH = rawItem("baguette_dough",230);
        CIABATTA_DOUGH = rawItem("ciabatta_dough",210);
        FOCACCIA_DOUGH = rawItem("focaccia_dough",230);
        COUNTRY_BREAD_DOUGH = rawItem("country_bread_dough",225);
        MOULD_TOAST_DOUGH = rawItem("mould_toast_dough",135);
        MOULD_CHEESE_COCOA_TOAST_DOUGH = rawItem("mould_cheese_cocoa_toast_dough",135);
        /*功能物品*/
    }

    private static DeferredItem<Item> foodBreadBlock(Holder<Block> block, Rarity rarity, FoodProperties foodProperties) {
        return registerItem(block.unwrapKey().orElseThrow().location().getPath(), () -> new BBreadItem(block.value(), new Item.Properties().rarity(rarity).food(foodProperties)) {
            @Override
            public DataComponentType<Boolean> perfectComponent() {
                return BakeriesDataComponents.PERFECT.get();
            }
        });
    }

    private static DeferredItem<Item> foodBreadBlock(Holder<Block> block, Rarity rarity, FoodProperties foodProperties,boolean effectTool) {
        return registerItem(block.unwrapKey().orElseThrow().location().getPath(), () -> new BBreadItem(block.value(), new Item.Properties().rarity(rarity).food(foodProperties),effectTool) {
            @Override
            public DataComponentType<Boolean> perfectComponent() {
                return BakeriesDataComponents.PERFECT.get();
            }
        });
    }

    private static DeferredItem<Item> foodBreadBlock(Holder<Block> block, FoodProperties foodProperties,boolean effectTool) {
        return registerItem(block.unwrapKey().orElseThrow().location().getPath(), () -> new BBreadItem(block.value(), new Item.Properties().food(foodProperties),effectTool) {
            @Override
            public DataComponentType<Boolean> perfectComponent() {
                return BakeriesDataComponents.PERFECT.get();
            }
        });
    }

    private static DeferredItem<Item> foodBreadBlock(Holder<Block> block, FoodProperties foodProperties) {
        return registerItem(block.unwrapKey().orElseThrow().location().getPath(), () -> new BBreadItem(block.value(), new Item.Properties().food(foodProperties)) {
            @Override
            public DataComponentType<Boolean> perfectComponent() {
                return BakeriesDataComponents.PERFECT.get();
            }
        });
    }

    private static DeferredItem<Item> repeatEatfoodBreadBlock(Holder<Block> block, int maxEat, Rarity rarity, FoodProperties foodProperties) {
        return registerItem(block.unwrapKey().orElseThrow().location().getPath(), () -> new RepeatEatItem(block.value(), maxEat, rarity, foodProperties) {
            @Override
            public DataComponentType<Boolean> perfectComponent() {
                return BakeriesDataComponents.PERFECT.get();
            }
        });
    }

    private static DeferredItem<Item> foodWeaponBreadBlock(Holder<Block> block, int maxEat, Rarity rarity, FoodProperties foodProperties, ItemAttributeModifiers itemAttributeModifiers) {
        return registerItem(block.unwrapKey().orElseThrow().location().getPath(), () -> new FoodWeaponItem(block.value(), maxEat, rarity, itemAttributeModifiers, foodProperties) {
            @Override
            public DataComponentType<Boolean> perfectComponent() {
                return BakeriesDataComponents.PERFECT.get();
            }
        });
    }

    private static DeferredItem<Item> item(String name) {
        return REGISTER.register(name, () -> new Item(new Item.Properties()));
    }

    private static DeferredItem<Item> foodItem(String name, FoodProperties foodProperties) {
        return REGISTER.register(name, () -> new Item(new Item.Properties().food(foodProperties)));
    }

    private static DeferredItem<Item> block(Holder<Block> block) {
        return block(block, new Item.Properties());
    }

    private static DeferredItem<Item> block(String name, Supplier<BlockItem> blockItem) {
        return registerItem(name, blockItem);
    }

    public static DeferredItem<Item> block(Holder<Block> block, final Supplier<? extends Block>... others) {
        return block(block.unwrapKey().orElseThrow().location().getPath(), () -> new BlockItem(block.value(), new Item.Properties()) {
            public void registerBlocks(Map<Block, Item> map, Item self) {
                super.registerBlocks(map, self);

                for (Supplier<? extends Block> b : others) {
                    map.put(b.get(), self);
                }

            }

            @SuppressWarnings("removal")
            @Override
            public void removeFromBlockToItemMap(Map<Block, Item> map, Item self) {
                super.removeFromBlockToItemMap(map, self);

                for (Supplier<? extends Block> b : others) {
                    map.remove(b.get());
                }

            }
        });
    }
    private static DeferredItem<Item> rawItem(String pName, int temperature) {
        return REGISTER.register(pName, () -> new RawItem(new Item.Properties(), temperature));
    }
    private static DeferredItem<Item> itemRarity(String name, Rarity rarity){
        return REGISTER.register(name, ()-> new Item(new Item.Properties().rarity(rarity)));
    }
    private static DeferredItem<Item> block(Holder<Block> block, Item.Properties properties) {
        return block(block.unwrapKey().orElseThrow().location().getPath(), block::value, properties);
    }
    private static DeferredItem<Item> registerItemNameBlockItem(String name, Supplier<? extends Block> block) {
        return registerItem(name, () -> new ItemNameBlockItem(block.get(), new Item.Properties()));
    }
    private static DeferredItem<Item> registerItemNameBlockItemHasAppendHoverText(String name, Supplier<? extends Block> block, Supplier<List<Component>> tooltip) {
        return registerItem(name, () -> new ItemNameBlockItem(block.get(), new Item.Properties()) {
            @Override
            public void appendHoverText(@NotNull ItemStack pStack, @NotNull Item.@NotNull TooltipContext pContext, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
                super.appendHoverText(pStack, pContext, pTooltip, pFlag);
                pTooltip.addAll(tooltip.get());
            }
        });
    }

    private static DeferredItem<Item> block(String name, Supplier<? extends Block> block, Item.Properties properties) {
        return registerItem(name, ()-> new BlockItem(block.get(), properties));
    }
    private static DeferredItem<Item> registerItem(String name, Supplier<? extends Item> item){
        return REGISTER.register(name, item);
    }

}