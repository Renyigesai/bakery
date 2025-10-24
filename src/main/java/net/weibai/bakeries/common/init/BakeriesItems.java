package net.weibai.bakeries.common.init;


import lombok.Getter;
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
import net.weibai.bakeries.BakeriesMod;
import net.weibai.bakeries.api.items.RawItem;
import net.weibai.rcglib.items.BreadItem;
import net.weibai.rcglib.items.FoodWeaponItem;
import net.weibai.rcglib.items.ItemRarity;
import net.weibai.rcglib.items.RepeatEatItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BakeriesItems {
    @Getter
    private static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(BakeriesMod.MODID);
    private BakeriesItems(){}
    /*面包方块*/
    /**贝果*/
    public static final DeferredItem<Item> BAGEL;
    /**全麦贝果*/
    public static final DeferredItem<Item> WHOLE_WHEAT_BAGEL;
    /**圆面包*/
    public static final DeferredItem<Item> ROUND_BREAD;
    /**莓果面包*/
    public static final DeferredItem<Item> BERRY_BREAD;
    /**乳酪面包*/
    public static final DeferredItem<Item> CHEESE_CREAM_BREAD;
    /**红糖卷*/
    public static final DeferredItem<Item> BROWN_SUGAR_ROLL;
    /**菠萝包*/
    public static final DeferredItem<Item> PINEAPPLE_BUN;
    /**肉松面包卷*/
    public static final DeferredItem<Item> MEAT_FLOSS_BREAD_ROLL;
    /**可颂*/
    public static final DeferredItem<Item> CROISSANT;
    /**脏脏包*/
    public static final DeferredItem<Item> DIRTY_CHOCO_CROISSANT;
    /**盐可颂*/
    public static final DeferredItem<Item> SALT_CROISSANT;
    /**恰巴塔面包*/
    public static final DeferredItem<Item> CIABATTA;
    /**佛卡夏面包*/
    public static final DeferredItem<Item> FOCACCIA;
    /**浆果贝果*/
    public static final DeferredItem<Item> BERRY_BAGEL;
    /**填酱贝果*/
    public static final DeferredItem<Item> BAGEL_FILLED_SAUCE;
    /**填馅法棍*/
    public static final DeferredItem<Item> BAGUETTE_WITH_FILLING;
    /**番茄奶酪可颂三明治*/
    public static final DeferredItem<Item> TOMATO_CHEESE_CROISSANT_SANDWICH;
    /**法棍*/
    public static final DeferredItem<Item> BAGUETTE;



    /**烤箱*/
    public static final DeferredItem<Item> OVEN;
    /**生圆面包*/
    public static final DeferredItem<Item> ROUND_BREAD_DOUGH;

    /**冰块*/
    public static final DeferredItem<Item> ICE_CUBES;
    static {
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




        OVEN = registerBlock(BakeriesBlocks.OVEN);

        ROUND_BREAD_DOUGH = rawItem("round_bread_dough", 155);

        ICE_CUBES = itemRarity("ice_cubes", ItemRarity.common());
    }
    private static DeferredItem<Item> foodBreadBlock(Holder<Block> block, Rarity rarity, FoodProperties foodProperties) {
        return registerItem(block.unwrapKey().orElseThrow().location().getPath(), ()-> new BreadItem(block.value(), new Item.Properties().rarity(rarity).food(foodProperties)){
            @Override
            public DataComponentType<Boolean> perfectComponent(){
                return BakeriesDataComponents.PERFECT.get();
            }
        });
    }
    private static DeferredItem<Item> foodBreadBlock(Holder<Block> block, FoodProperties foodProperties) {
        return registerItem(block.unwrapKey().orElseThrow().location().getPath(), ()-> new BreadItem(block.value(), new Item.Properties().food(foodProperties)){
            @Override
            public DataComponentType<Boolean> perfectComponent(){
                return BakeriesDataComponents.PERFECT.get();
            }
        });
    }
    private static DeferredItem<Item> repeatEatfoodBreadBlock(Holder<Block> block, int maxEat, Rarity rarity, FoodProperties foodProperties) {
        return registerItem(block.unwrapKey().orElseThrow().location().getPath(), ()-> new RepeatEatItem(block.value(), maxEat, rarity, foodProperties) {
            @Override
            public DataComponentType<Boolean> perfectComponent() {
                return BakeriesDataComponents.PERFECT.get();
            }
        });
    }
    private static DeferredItem<Item> foodWeaponBreadBlock(Holder<Block> block, int maxEat, Rarity rarity, FoodProperties foodProperties, ItemAttributeModifiers itemAttributeModifiers) {
        return registerItem(block.unwrapKey().orElseThrow().location().getPath(), ()-> new FoodWeaponItem(block.value(), maxEat, rarity, itemAttributeModifiers, foodProperties) {
            @Override
            public DataComponentType<Boolean> perfectComponent() {
                return BakeriesDataComponents.PERFECT.get();
            }
        });
    }
    private static DeferredItem<Item> registerBlock(Holder<Block> block) {
        return registerBlock(block, new Item.Properties());
    }
    private static DeferredItem<Item> registerBlock(String name ,Supplier<BlockItem> blockItem) {
        return registerItem(name, blockItem);
    }
    public static DeferredItem<Item>  registerBlock(Holder<Block> block, final Supplier<? extends Block>... others) {
        return registerBlock(block.unwrapKey().orElseThrow().location().getPath(), ()->new BlockItem(block.value(), new Item.Properties()) {
            public void registerBlocks(Map<Block, Item> map, Item self) {
                super.registerBlocks(map, self);

                for(Supplier<? extends Block> b : others) {
                    map.put(b.get(), self);
                }

            }

            @SuppressWarnings("removal")
            @Override
            public void removeFromBlockToItemMap(Map<Block, Item> map, Item self) {
                super.removeFromBlockToItemMap(map, self);

                for(Supplier<? extends Block> b : others) {
                    map.remove(b.get());
                }

            }
        });
    }
    private static DeferredItem<Item> rawItem(String pName, int temperature) {
        return REGISTER.register(pName, () -> new RawItem(new Item.Properties(),temperature));
    }
    private static DeferredItem<Item> itemRarity(String name, Rarity rarity){
        return REGISTER.register(name, ()-> new Item(new Item.Properties().rarity(rarity)));
    }
    private static DeferredItem<Item> registerBlock(Holder<Block> block, Item.Properties properties) {
        return registerBlock(block.unwrapKey().orElseThrow().location().getPath(), block::value, properties);
    }
    private static DeferredItem<Item> registerItemNameBlockItem(String name, Supplier<? extends Block> block) {
        return registerItem(name, ()-> new ItemNameBlockItem(block.get(), new Item.Properties()));
    }
    private static DeferredItem<Item> registerItemNameBlockItemHasAppendHoverText(String name, Supplier<? extends Block> block, Supplier<List<Component>> tooltip) {
        return registerItem(name, ()-> new ItemNameBlockItem(block.get(), new Item.Properties()){
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