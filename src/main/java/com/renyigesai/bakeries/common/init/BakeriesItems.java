package com.renyigesai.bakeries.common.init;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.annotation.CustomData;
import com.renyigesai.bakeries.api.annotation.ItemType;
import com.renyigesai.bakeries.api.items.RawItem;
import com.renyigesai.bakeries.common.items.BreadKnifeItem;
import com.renyigesai.bakeries.common.items.FlourSieveItem;
import com.renyigesai.bakeries.common.items.ShakeItem;
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
    public static final String SFP_TAB = BakeriesMod.MODID + "_sfp_tab";

    public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(BakeriesMod.MODID);
    private BakeriesItems(){}

    /*杂物*/
    @ItemType(zhCn = "面粉", group = BAKERIES_TAB)
    public static final DeferredItem<Item> FLOUR;
    @ItemType(zhCn = "全麦面粉", group = BAKERIES_TAB)
    public static final DeferredItem<Item> WHOLE_WHEAT_FLOUR;
    @ItemType(zhCn = "可可粉", group = BAKERIES_TAB)
    public static final DeferredItem<Item> COCOA_POWDER;
    @ItemType(zhCn = "抹茶粉", group = BAKERIES_TAB)
    public static final DeferredItem<Item> MATCHA_POWDER;
    @ItemType(zhCn = "盐", group = BAKERIES_TAB)
    public static final DeferredItem<Item> SALT;
    @ItemType(zhCn = "黄油块", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BUTTER_CUBE;
    @ItemType(zhCn = "打发奶油", group = BAKERIES_TAB)
    public static final DeferredItem<Item> FOAMED_CREAM;
    @ItemType(zhCn = "奶酪奶油", group = BAKERIES_TAB)
    public static final DeferredItem<Item> CHEESE_CREAM;
    @ItemType(zhCn = "黄油面砂", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BUTTER_FLOUR_SAND;
    @ItemType(zhCn = "蜂蜜黄油", group = BAKERIES_TAB)
    public static final DeferredItem<Item> HONEY_BUTTER;
    @ItemType(zhCn = "全蛋", group = BAKERIES_TAB)
    public static final DeferredItem<Item> WHOLE_EGG;
    @ItemType(zhCn = "生蛋白", group = BAKERIES_TAB)
    public static final DeferredItem<Item> RAW_PROTEIN;
    @ItemType(zhCn = "生蛋黄", group = BAKERIES_TAB)
    public static final DeferredItem<Item> RAW_EGG_YOLK;
    @ItemType(zhCn = "干酪块", group = BAKERIES_TAB)
    public static final DeferredItem<Item> CHEESE_CUBE;
    @ItemType(zhCn = "鲜奶酪块", group = BAKERIES_TAB)
    public static final DeferredItem<Item> FRESH_CHEESE_CUBE;
    @ItemType(zhCn = "红糖块", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BROWN_SUGAR_CUBE;
    @ItemType(zhCn = "生咖啡豆", group = BAKERIES_TAB)
    public static final DeferredItem<Item> RAW_COFFEE_BEAN;
    @ItemType(zhCn = "咖啡豆", group = BAKERIES_TAB)
    public static final DeferredItem<Item> COFFEE_BEAN;
    @ItemType(zhCn = "咖啡粉", group = BAKERIES_TAB)
    public static final DeferredItem<Item> GROUND_COFFEE;
    @ItemType(zhCn = "蛋黄酱", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BEARNAISE;
    @ItemType(zhCn = "橄榄油", group = BAKERIES_TAB)
    public static final DeferredItem<Item> OLIVE_OIL;
    @ItemType(zhCn = "肉松", group = BAKERIES_TAB)
    public static final DeferredItem<Item> MEAT_FLOSS;
    @ItemType(zhCn = "司康饼", group = BAKERIES_TAB)
    public static final DeferredItem<Item> SCONE;
    @ItemType(zhCn = "番茄", group = BAKERIES_TAB)
    public static final DeferredItem<Item> TOMATO;
    @ItemType(zhCn = "橄榄", group = BAKERIES_TAB)
    public static final DeferredItem<Item> OLIVE;
    @ItemType(zhCn = "吐司片", group = BAKERIES_TAB)
    public static final DeferredItem<Item> SLICED_TOAST;
    @ItemType(zhCn = "蜂蜜黄油吐司", group = BAKERIES_TAB)
    public static final DeferredItem<Item> HONEY_BUTTER_SPREAD_TOAST;
    @ItemType(zhCn = "奶酪可可吐司", group = BAKERIES_TAB)
    public static final DeferredItem<Item> SLICED_CHEESE_COCOA_TOAST;
    @ItemType(zhCn = "乡村面包片", group = BAKERIES_TAB)
    public static final DeferredItem<Item> COUNTRY_BREAD_SLICE;
    @ItemType(zhCn = "蜂蜜黄油乡村面包片", group = BAKERIES_TAB)
    public static final DeferredItem<Item> HONEY_BUTTER_SPREAD_COUNTRY_BREAD;
    @ItemType(zhCn = "瓶装酵母", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BOTTLE_YEAST;
    @ItemType(zhCn = "瓶装奶", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BOTTLE_MILK;
    @ItemType(zhCn = "瓶装奶油", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BOTTLE_CREAM;
    @ItemType(zhCn = "瓶装黄油", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BOTTLE_BUTTER;
    @ItemType(zhCn = "甜面团", group = BAKERIES_TAB)
    public static final DeferredItem<Item> SWEET_DOUGH;
    @ItemType(zhCn = "咸面团", group = BAKERIES_TAB)
    public static final DeferredItem<Item> SALTED_DOUGH;
    @ItemType(zhCn = "全麦面团", group = BAKERIES_TAB)
    public static final DeferredItem<Item> WHOLE_WHEAT_DOUGH;

    /*面包方块*/
    @ItemType(zhCn = "贝果", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BAGEL;
    @ItemType(zhCn = "全麦贝果", group = BAKERIES_TAB)
    public static final DeferredItem<Item> WHOLE_WHEAT_BAGEL;
    @ItemType(zhCn = "圆面包", group = BAKERIES_TAB)
    public static final DeferredItem<Item> ROUND_BREAD;
    @ItemType(zhCn = "莓果面包", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BERRY_BREAD;
    @ItemType(zhCn = "乳酪面包", group = BAKERIES_TAB)
    public static final DeferredItem<Item> CHEESE_CREAM_BREAD;
    @ItemType(zhCn = "红糖卷", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BROWN_SUGAR_ROLL;
    @ItemType(zhCn = "菠萝包", group = BAKERIES_TAB)
    public static final DeferredItem<Item> PINEAPPLE_BUN;
    @ItemType(zhCn = "肉松面包卷", group = BAKERIES_TAB)
    public static final DeferredItem<Item> MEAT_FLOSS_BREAD_ROLL;
    @ItemType(zhCn = "可颂", group = BAKERIES_TAB)
    public static final DeferredItem<Item> CROISSANT;
    @ItemType(zhCn = "脏脏包", group = BAKERIES_TAB)
    public static final DeferredItem<Item> DIRTY_CHOCO_CROISSANT;
    @ItemType(zhCn = "盐可颂", group = BAKERIES_TAB)
    public static final DeferredItem<Item> SALT_CROISSANT;
    @ItemType(zhCn = "恰巴塔面包", group = BAKERIES_TAB)
    public static final DeferredItem<Item> CIABATTA;
    @ItemType(zhCn = "佛卡夏面包", group = BAKERIES_TAB)
    public static final DeferredItem<Item> FOCACCIA;
    @ItemType(zhCn = "浆果贝果", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BERRY_BAGEL;
    @ItemType(zhCn = "填酱贝果", enUs = "Bagel Filled with Sauce", itemClass = ItemType.Class.CUSTOM_ITEM, group = BAKERIES_TAB)
    public static final DeferredItem<Item> BAGEL_FILLED_SAUCE;
    @ItemType(zhCn = "填馅法棍", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BAGUETTE_WITH_FILLING;
    @ItemType(zhCn = "番茄奶酪可颂三明治", group = BAKERIES_TAB)
    public static final DeferredItem<Item> TOMATO_CHEESE_CROISSANT_SANDWICH;
    @ItemType(zhCn = "法棍", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BAGUETTE;

    /*功能物品*/
    @ItemType(zhCn = "面包刀", group = BAKERIES_TAB)
    public static final DeferredItem<Item> BREAD_KNIFE;
    @ItemType(zhCn = "面粉筛", group = BAKERIES_TAB)
    public static final DeferredItem<Item> FLOUR_SIEVE;
    @ItemType(zhCn = "搅拌机", itemClass = ItemType.Class.BLOCK, group = BAKERIES_TAB)
    public static final DeferredItem<Item> BLENDER;
    @ItemType(zhCn = "烤箱", itemClass = ItemType.Class.BLOCK, group = BAKERIES_TAB)
    public static final DeferredItem<Item> OVEN;
    @ItemType(zhCn = "酥皮", group = SFP_TAB)
    public static final DeferredItem<Item> PASTRY;

    @ItemType(zhCn = "贝果面胚", group = SFP_TAB)
    public static final DeferredItem<Item> BAGEL_DOUGH;

    @ItemType(zhCn = "全麦贝果面胚", group = SFP_TAB)
    public static final DeferredItem<Item> WHOLE_WHEAT_BAGEL_DOUGH;

    @ItemType(zhCn = "圆面包面胚", group = SFP_TAB)
    public static final DeferredItem<Item> ROUND_BREAD_DOUGH;
    @ItemType(zhCn = "红糖卷面胚", group = SFP_TAB)
    public static final DeferredItem<Item> BROWN_SUGAR_ROLL_DOUGH;

    @ItemType(zhCn = "菠萝包面胚", group = SFP_TAB)
    public static final DeferredItem<Item> PINEAPPLE_BUN_DOUGH;

    @ItemType(zhCn = "可颂面胚", group = SFP_TAB)
    public static final DeferredItem<Item> CROISSANT_DOUGH;

    @ItemType(zhCn = "盐可颂面胚", group = SFP_TAB)
    public static final DeferredItem<Item> SALT_CROISSANT_DOUGH;

    @ItemType(zhCn = "法棍面胚", group = SFP_TAB)
    public static final DeferredItem<Item> BAGUETTE_DOUGH;

    @ItemType(zhCn = "恰巴塔面胚", group = SFP_TAB)
    public static final DeferredItem<Item> CIABATTA_DOUGH;

    @ItemType(zhCn = "佛卡夏面胚", group = SFP_TAB)
    public static final DeferredItem<Item> FOCACCIA_DOUGH;

    @ItemType(zhCn = "乡村面包面胚", group = SFP_TAB)
    public static final DeferredItem<Item> COUNTRY_BREAD_DOUGH;


    @ItemType(zhCn = "冰块", group = BAKERIES_TAB)
    public static final DeferredItem<Item> ICE_CUBES;
    static {
        FLOUR = item("flour");
        WHOLE_WHEAT_FLOUR = item("whole_wheat_flour");
        COCOA_POWDER = item("cocoa_powder");
        MATCHA_POWDER = item("matcha_powder");
        SALT = item("salt");
        BUTTER_CUBE = item("butter_cube");
        FOAMED_CREAM = foodItem("foamed_cream",BakeriesFoodProperties.FOAMED_CREAM);
        CHEESE_CREAM = foodItem("cheese_cream",BakeriesFoodProperties.FOAMED_CREAM);
        BUTTER_FLOUR_SAND = item("butter_flour_sand");
        HONEY_BUTTER = item("honey_butter");
        WHOLE_EGG = item("whole_egg");
        RAW_PROTEIN = item("raw_protein");
        RAW_EGG_YOLK = item("raw_egg_yolk");
        CHEESE_CUBE = foodItem("cheese_cube",BakeriesFoodProperties.CHEESE_CUBE);
        FRESH_CHEESE_CUBE = foodItem("fresh_cheese_cube",BakeriesFoodProperties.CHEESE_CUBE);
        BROWN_SUGAR_CUBE = item("brown_sugar_cube");
        RAW_COFFEE_BEAN = item("raw_coffee_bean");
        COFFEE_BEAN = item("coffee_bean");
        GROUND_COFFEE = item("ground_coffee");
        BEARNAISE = registerItem("bearnaise",()->new Item(new Item.Properties().stacksTo(16).craftRemainder(Items.BOWL)));
        OLIVE_OIL = registerItem("olive_oil",()-> new Item(new Item.Properties().durability(6)));
        MEAT_FLOSS = foodItem("meat_floss",BakeriesFoodProperties.MEAT_FLOSS);
        SCONE = foodItem("scone",BakeriesFoodProperties.SCONE);
        TOMATO = registerItem("tomato",()->new Item(new Item.Properties().food(BakeriesFoodProperties.TOMATO)));
        OLIVE = foodItem("olive",BakeriesFoodProperties.OLIVE);
        SLICED_TOAST = foodItem("sliced_toast",BakeriesFoodProperties.SLICED_TOAST);
        HONEY_BUTTER_SPREAD_TOAST = registerItem("honey_butter_spread_toast",()-> new Item(new Item.Properties().food(BakeriesFoodProperties.HONEY_BUTTER_SPREAD_TOAST)));
        SLICED_CHEESE_COCOA_TOAST = foodItem("sliced_cheese_cocoa_toast",BakeriesFoodProperties.SLICED_CHEESE_COCOA_TOAST);
        COUNTRY_BREAD_SLICE = foodItem("country_bread_slice",BakeriesFoodProperties.COUNTRY_BREAD_SLICE);
        HONEY_BUTTER_SPREAD_COUNTRY_BREAD = registerItem("honey_butter_spread_country_bread",()-> new Item(new Item.Properties().food(BakeriesFoodProperties.HONEY_BUTTER_SPREAD_COUNTRY_BREAD)));


        BOTTLE_YEAST = REGISTER.register("bottle_yeast",()-> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));
        BOTTLE_MILK = REGISTER.register("bottle_milk",()-> new ShakeItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE),BakeriesItems.BOTTLE_CREAM));
        BOTTLE_CREAM = REGISTER.register("bottle_cream",()-> new ShakeItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE),BakeriesItems.BOTTLE_BUTTER));
        BOTTLE_BUTTER = REGISTER.register("bottle_butter",()-> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));

        SWEET_DOUGH = item("sweet_dough");
        SALTED_DOUGH = item("salted_dough");
        WHOLE_WHEAT_DOUGH = item("whole_wheat_dough");

        PASTRY = item("pastry");
        BAGEL_DOUGH = rawItem("bagel_dough",200);
        WHOLE_WHEAT_BAGEL_DOUGH = rawItem("whole_wheat_bagel_dough",200);
        BROWN_SUGAR_ROLL_DOUGH = rawItem("brown_sugar_roll_dough",155);
        PINEAPPLE_BUN_DOUGH = rawItem("pineapple_bun_dough",170);
        CROISSANT_DOUGH = rawItem("croissant_dough",175);
        SALT_CROISSANT_DOUGH = rawItem("salt_croissant_dough",180);
        BAGUETTE_DOUGH = rawItem("baguette_dough",230);
        CIABATTA_DOUGH = rawItem("ciabatta_dough",210);
        FOCACCIA_DOUGH = rawItem("focaccia_dough",230);
        COUNTRY_BREAD_DOUGH = rawItem("country_bread_dough",225);


        BAGEL = foodBreadBlock(BakeriesBlocks.BAGEL, BakeriesFoodProperties.BAGEL);
        WHOLE_WHEAT_BAGEL = foodBreadBlock(BakeriesBlocks.WHOLE_WHEAT_BAGEL, BakeriesFoodProperties.WHOLE_WHEAT_BAGEL);
        ROUND_BREAD = foodBreadBlock(BakeriesBlocks.ROUND_BREAD, BakeriesFoodProperties.ROUND_BREAD);
        BERRY_BREAD = foodBreadBlock(BakeriesBlocks.BERRY_BREAD, BakeriesFoodProperties.BERRY_BREAD);
        CHEESE_CREAM_BREAD = foodBreadBlock(BakeriesBlocks.CHEESE_CREAM_BREAD, ItemRarity.advanced(), BakeriesFoodProperties.CHEESE_CREAM_BREAD);
        BROWN_SUGAR_ROLL = foodBreadBlock(BakeriesBlocks.BROWN_SUGAR_ROLL, BakeriesFoodProperties.BROWN_SUGAR_ROLL);
        PINEAPPLE_BUN = foodBreadBlock(BakeriesBlocks.PINEAPPLE_BUN, BakeriesFoodProperties.PINEAPPLE_BUN);
        MEAT_FLOSS_BREAD_ROLL = foodBreadBlock(BakeriesBlocks.MEAT_FLOSS_BREAD_ROLL, ItemRarity.advanced(), BakeriesFoodProperties.MEAT_FLOSS_BREAD);
        CROISSANT = foodBreadBlock(BakeriesBlocks.CROISSANT, BakeriesFoodProperties.CROISSANT);
        DIRTY_CHOCO_CROISSANT = foodBreadBlock(BakeriesBlocks.DIRTY_CHOCO_CROISSANT, BakeriesFoodProperties.DIRTY_CHOCO_CROISSANT);
        SALT_CROISSANT = foodBreadBlock(BakeriesBlocks.SALT_CROISSANT, BakeriesFoodProperties.SALT_CROISSANT);
        CIABATTA = foodBreadBlock(BakeriesBlocks.CIABATTA, BakeriesFoodProperties.CIABATTA);
        FOCACCIA = foodBreadBlock(BakeriesBlocks.FOCACCIA, BakeriesFoodProperties.FOCACCIA);
        BERRY_BAGEL = foodBreadBlock(BakeriesBlocks.BERRY_BAGEL, ItemRarity.advanced(), BakeriesFoodProperties.BERRY_BAGEL);
        BAGEL_FILLED_SAUCE = foodBreadBlock(BakeriesBlocks.BAGEL_FILLED_SAUCE, ItemRarity.advanced(), BakeriesFoodProperties.BAGEL_FILLED_SAUCE);
        BAGUETTE_WITH_FILLING = foodBreadBlock(BakeriesBlocks.BAGUETTE_WITH_FILLING, ItemRarity.advanced(), BakeriesFoodProperties.BAGUETTE_WITH_FILLING);
        TOMATO_CHEESE_CROISSANT_SANDWICH = foodBreadBlock(BakeriesBlocks.TOMATO_CHEESE_CROISSANT_SANDWICH, ItemRarity.advanced(), BakeriesFoodProperties.TOMATO_CHEESE_CROISSANT_SANDWICH);
        BAGUETTE = foodWeaponBreadBlock(BakeriesBlocks.BAGUETTE, 4, ItemRarity.common(), BakeriesFoodProperties.BAGUETTE,
                ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.withDefaultNamespace("base_attack_damage"), 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLocation.withDefaultNamespace("base_attack_speed"), -3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build());


        /*功能物品*/
        BREAD_KNIFE = REGISTER.register("bread_knife",()-> new BreadKnifeItem(Tiers.IRON,new Item.Properties()));
        FLOUR_SIEVE = REGISTER.register("flour_sieve",()-> new FlourSieveItem(new Item.Properties().stacksTo(1).durability(250)));


        BLENDER = registerBlock(BakeriesBlocks.BLENDER);
        OVEN = registerBlock(BakeriesBlocks.OVEN);

        ROUND_BREAD_DOUGH = rawItem("round_bread_dough", 155);

        ICE_CUBES = itemRarity("ice_cubes", ItemRarity.common());
    }

    private static DeferredItem<Item> foodBreadBlock(Holder<Block> block, Rarity rarity, FoodProperties foodProperties) {
        return registerItem(block.unwrapKey().orElseThrow().location().getPath(), () -> new BreadItem(block.value(), new Item.Properties().rarity(rarity).food(foodProperties)) {
            @Override
            public DataComponentType<Boolean> perfectComponent() {
                return BakeriesDataComponents.PERFECT.get();
            }
        });
    }

    private static DeferredItem<Item> foodBreadBlock(Holder<Block> block, FoodProperties foodProperties) {
        return registerItem(block.unwrapKey().orElseThrow().location().getPath(), () -> new BreadItem(block.value(), new Item.Properties().food(foodProperties)) {
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

    private static DeferredItem<Item> registerBlock(Holder<Block> block) {
        return registerBlock(block, new Item.Properties());
    }

    private static DeferredItem<Item> registerBlock(String name, Supplier<BlockItem> blockItem) {
        return registerItem(name, blockItem);
    }

    public static DeferredItem<Item> registerBlock(Holder<Block> block, final Supplier<? extends Block>... others) {
        return registerBlock(block.unwrapKey().orElseThrow().location().getPath(), () -> new BlockItem(block.value(), new Item.Properties()) {
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
    private static DeferredItem<Item> registerBlock(Holder<Block> block, Item.Properties properties) {
        return registerBlock(block.unwrapKey().orElseThrow().location().getPath(), block::value, properties);
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

    private static DeferredItem<Item> registerBlock(String name, Supplier<? extends Block> block, Item.Properties properties) {
        return registerItem(name, ()-> new BlockItem(block.get(), properties));
    }
    private static DeferredItem<Item> registerItem(String name, Supplier<? extends Item> item){
        return REGISTER.register(name, item);
    }

}