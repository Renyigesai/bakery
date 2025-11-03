package com.renyigesai.bakeries.common.init;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.annotation.Group;
import com.renyigesai.bakeries.api.annotation.ItemData;
import com.renyigesai.bakeries.api.annotation.ItemType;
import com.renyigesai.bakeries.api.items.RawItem;
import com.renyigesai.bakeries.common.items.BreadKnifeItem;
import com.renyigesai.bakeries.common.items.FlourSieveItem;
import com.renyigesai.bakeries.common.items.ShakeItem;
import com.renyigesai.bakeries.common.items.WholeEggItem;
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

public class BakeriesItems {

    public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(BakeriesMod.MODID);


    private BakeriesItems() {
    }

    @ItemData(zhCn = "面粉", enUs = "Flour", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> FLOUR;

    @ItemData(zhCn = "全麦面粉", enUs = "Whole Wheat Flour", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> WHOLE_WHEAT_FLOUR;

    @ItemData(zhCn = "可可粉", enUs = "Cocoa Powder", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> COCOA_POWDER;

    @ItemData(zhCn = "抹茶粉", enUs = "Matcha Powder", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> MATCHA_POWDER;

    @ItemData(zhCn = "盐", enUs = "Salt", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> SALT;

    @ItemData(zhCn = "黄油块", enUs = "Butter Cube", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BUTTER_CUBE;

    @ItemData(zhCn = "打发奶油", enUs = "Foamed Cream", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> FOAMED_CREAM;

    @ItemData(zhCn = "奶酪奶油", enUs = "Cheese Cream", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> CHEESE_CREAM;

    @ItemData(zhCn = "全蛋", enUs = "Whole Egg", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> WHOLE_EGG;

    @ItemData(zhCn = "生蛋白", enUs = "Raw Protein", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> RAW_PROTEIN;

    @ItemData(zhCn = "生蛋黄", enUs = "Raw Egg Yolk", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> RAW_EGG_YOLK;

    @ItemData(zhCn = "干酪块", enUs = "Cheese Cube", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> CHEESE_CUBE;

    @ItemData(zhCn = "鲜奶酪块", enUs = "Fresh Cheese Cube", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> FRESH_CHEESE_CUBE;

    @ItemData(zhCn = "红糖块", enUs = "Brown Sugar Cube", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BROWN_SUGAR_CUBE;

    @ItemData(zhCn = "瓶装酵母", enUs = "Bottle of Yeast", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BOTTLE_YEAST;

    @ItemData(zhCn = "瓶装奶", enUs = "Bottle of Milk", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BOTTLE_MILK;

    @ItemData(zhCn = "瓶装奶油", enUs = "Bottle of Cream", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BOTTLE_CREAM;

    @ItemData(zhCn = "瓶装黄油", enUs = "Bottle of Butter", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BOTTLE_BUTTER;

    @ItemData(zhCn = "甜面团", enUs = "Sweet Dough", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> SWEET_DOUGH;

    @ItemData(zhCn = "咸面团", enUs = "Salted Dough", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> SALTED_DOUGH;

    @ItemData(zhCn = "全麦面团", enUs = "Whole Wheat Dough", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> WHOLE_WHEAT_DOUGH;

    /*面包方块*/
    @ItemData(zhCn = "贝果", enUs = "Bagel", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BAGEL;

    @ItemData(zhCn = "全麦贝果", enUs = "Whole Wheat Bagel", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> WHOLE_WHEAT_BAGEL;

    @ItemData(zhCn = "圆面包", enUs = "Round Bread", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> ROUND_BREAD;

    @ItemData(zhCn = "莓果面包", enUs = "Berry Bread", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BERRY_BREAD;

    @ItemData(zhCn = "乳酪面包", enUs = "Cheese Cream Bread", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> CHEESE_CREAM_BREAD;

    @ItemData(zhCn = "红糖卷", enUs = "Brown Sugar Roll", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BROWN_SUGAR_ROLL;

    @ItemData(zhCn = "菠萝包", enUs = "Pineapple Bun", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> PINEAPPLE_BUN;

    @ItemData(zhCn = "肉松面包卷", enUs = "Meat Floss Bread Roll", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> MEAT_FLOSS_BREAD_ROLL;

    @ItemData(zhCn = "可颂", enUs = "Croissant", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> CROISSANT;

    @ItemData(zhCn = "脏脏包", enUs = "Dirty Choco Croissant", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> DIRTY_CHOCO_CROISSANT;

    @ItemData(zhCn = "盐可颂", enUs = "Salt Croissant", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> SALT_CROISSANT;

    @ItemData(zhCn = "恰巴塔面包", enUs = "Ciabatta", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> CIABATTA;

    @ItemData(zhCn = "佛卡夏面包", enUs = "Focaccia", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> FOCACCIA;

    @ItemData(zhCn = "浆果贝果", enUs = "Berry Bagel", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BERRY_BAGEL;

    @ItemData(zhCn = "填酱贝果", enUs = "Bagel Filled with Sauce", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BAGEL_FILLED_SAUCE;

    @ItemData(zhCn = "填馅法棍", enUs = "Baguette with Filling", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BAGUETTE_WITH_FILLING;

    @ItemData(zhCn = "番茄奶酪可颂三明治", enUs = "Tomato Cheese Croissant Sandwich", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> TOMATO_CHEESE_CROISSANT_SANDWICH;

    @ItemData(zhCn = "法棍", enUs = "Baguette", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BAGUETTE;

    /*功能物品*/
    @ItemData(zhCn = "面包刀", enUs = "Bread Knife", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> BREAD_KNIFE;
    @ItemData(zhCn = "面粉筛", enUs = "Flour Sieve", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> FLOUR_SIEVE;


    @ItemData(zhCn = "搅拌机", enUs = "Blender", itemType = ItemType.BLOCK)
    public static final DeferredItem<Item> BLENDER;

    @ItemData(zhCn = "烤箱", enUs = "Oven", itemType = ItemType.BLOCK)
    public static final DeferredItem<Item> OVEN;

    @ItemData(zhCn = "圆面包面胚", enUs = "Round Bread Dough", itemType = ItemType.ITEM,groups = Group.SFP)
    public static final DeferredItem<Item> ROUND_BREAD_DOUGH;

    @ItemData(zhCn = "冰块", enUs = "Ice Cubes", itemType = ItemType.ITEM)
    public static final DeferredItem<Item> ICE_CUBES;

    static {

        /*一般物品 食材 食物*/
        WHOLE_WHEAT_FLOUR = item("whole_wheat_flour");
        FLOUR = item("flour");
        SALT = item("salt");
        COCOA_POWDER = item("cocoa_powder");
        MATCHA_POWDER = item("matcha_powder");

        BUTTER_CUBE = item("butter_cube");
        FOAMED_CREAM = foodItem("foamed_cream", BakeriesFoodProperties.FOAMED_CREAM);
        CHEESE_CREAM = foodItem("cheese_cream", BakeriesFoodProperties.FOAMED_CREAM);
        WHOLE_EGG = REGISTER.register("whole_egg", WholeEggItem::new);
        RAW_PROTEIN = item("raw_protein");
        RAW_EGG_YOLK = item("raw_egg_yolk");

        CHEESE_CUBE = foodItem("cheese_cube", BakeriesFoodProperties.CHEESE_CUBE);
        FRESH_CHEESE_CUBE = foodItem("fresh_cheese_cube", BakeriesFoodProperties.CHEESE_CUBE);
        BROWN_SUGAR_CUBE = item("brown_sugar_cube");

        BOTTLE_YEAST = REGISTER.register("bottle_yeast", () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));
        BOTTLE_MILK = REGISTER.register("bottle_milk", () -> new ShakeItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE), BakeriesItems.BOTTLE_CREAM));
        BOTTLE_CREAM = REGISTER.register("bottle_cream", () -> new ShakeItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE), BakeriesItems.BOTTLE_BUTTER));
        BOTTLE_BUTTER = REGISTER.register("bottle_butter", () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));

        SWEET_DOUGH = item("sweet_dough");
        SALTED_DOUGH = item("salted_dough");
        WHOLE_WHEAT_DOUGH = item("whole_wheat_dough");

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
        BREAD_KNIFE = REGISTER.register("bread_knife", () -> new BreadKnifeItem(Tiers.IRON, new Item.Properties()));
        FLOUR_SIEVE = REGISTER.register("flour_sieve", () -> new FlourSieveItem(new Item.Properties().stacksTo(1).durability(250)));


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

    private static DeferredItem<Item> itemRarity(String name, Rarity rarity) {
        return REGISTER.register(name, () -> new Item(new Item.Properties().rarity(rarity)));
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
        return registerItem(name, () -> new BlockItem(block.get(), properties));
    }

    private static DeferredItem<Item> registerItem(String name, Supplier<? extends Item> item) {
        return REGISTER.register(name, item);
    }

}