package com.renyigesai.bakeries.compat.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.api.item.PileItem;
import com.renyigesai.bakeries.compat.CompatMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesFoodProperties;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.item.ColdDrinkItem;
import com.renyigesai.bakeries.item.GarlicFlavoredBaguetteItem;
import com.renyigesai.bakeries.util.ItemUtils;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeriesCompatItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, BakeriesMod.MODID);
    public static final RegistryObject<Item> RICE_BREAD;
    public static final RegistryObject<Item> SALMON_SANDWICH;
    public static final RegistryObject<Item> GARLIC_FLAVORED_BAGUETTE;
    public static final RegistryObject<Item> ORANGE_AMERICAN;
    public static final RegistryObject<Item> YUNTUI_MOONCAKE;
    public static final RegistryObject<Item> TRAY_YUNTUI_MOONCAKE;

    static {
        RICE_BREAD = foodBlockItem(BakeriesBlocks.RICE_BREAD, conditionReturn(CompatMod.FARMER_S_DELIGHT,BakeriesFoodProperties.RICE_BREAD_FARMERSDELIGHT,BakeriesFoodProperties.RICE_BREAD),true,false);
        SALMON_SANDWICH = foodBlockItem(BakeriesBlocks.SALMON_SANDWICH,BakeriesFoodProperties.SALMON_SANDWICH,true,false, ItemUtils.ADVANCED);
        ORANGE_AMERICAN = coldDrinkItem(BakeriesBlocks.ORANGE_AMERICAN,BakeriesFoodProperties.ORANGE_AMERICAN,true,3,3,true,4);
        GARLIC_FLAVORED_BAGUETTE = REGISTER.register("garlic_flavored_baguette",()->new GarlicFlavoredBaguetteItem(BakeriesBlocks.GARLIC_FLAVORED_BAGUETTE.get(),PileBlock.integerProperty,new Item.Properties().durability(4).food(BakeriesFoodProperties.GARLIC_FLAVORED_BAGUETTE).rarity(ItemUtils.ADVANCED),true,false));
        YUNTUI_MOONCAKE = REGISTER.register("yuntui_mooncake",()-> new Item(new Item.Properties().food(BakeriesFoodProperties.YUNTUI_MOONCAKE)));
        TRAY_YUNTUI_MOONCAKE = block(BakeriesBlocks.TRAY_YUNTUI_MOONCAKE);
    }

    /*根据加载模组动态赋值食物属性*/
    private static FoodProperties conditionReturn(boolean condition,FoodProperties a,FoodProperties b){
        if (condition){
            return a;
        }
        return b;
    }

    private static RegistryObject<Item> foodBlockItem(RegistryObject<Block> block, FoodProperties foodProperties) {
        return REGISTER.register(block.getId().getPath(), () -> new PileItem(block.get(), PileBlock.integerProperty,new Item.Properties().food(foodProperties)));
    }

    private static RegistryObject<Item> foodBlockItem(RegistryObject<Block> block, FoodProperties foodProperties, Rarity rarity) {
        return REGISTER.register(block.getId().getPath(), () -> new PileItem(block.get(), PileBlock.integerProperty,new Item.Properties().food(foodProperties).rarity(rarity)));
    }

    private static RegistryObject<Item> foodBlockItem(RegistryObject<Block> block, FoodProperties foodProperties,boolean effectTooltip,boolean customField) {
        return REGISTER.register(block.getId().getPath(), () -> new PileItem(block.get(), PileBlock.integerProperty, new Item.Properties().food(foodProperties),effectTooltip,customField));
    }

    private static RegistryObject<Item> foodBlockItem(RegistryObject<Block> block, FoodProperties foodProperties,boolean effectTooltip,boolean customField,Rarity rarity) {
        return REGISTER.register(block.getId().getPath(), () -> new PileItem(block.get(), PileBlock.integerProperty, new Item.Properties().food(foodProperties).rarity(rarity),effectTooltip,customField));
    }

    private static RegistryObject<Item> coldDrinkItem(RegistryObject<Block> block,FoodProperties foodProperties,boolean is_thirst,int thirst,int quenched,boolean toolTips,int upEffect) {
        return REGISTER.register(block.getId().getPath(), () -> new ColdDrinkItem(block.get(),new Item.Properties().durability(6).craftRemainder(BakeriesItems.DRINK_CUP.get()).food(foodProperties),is_thirst,thirst,quenched,toolTips,false,quenched));
    }

    private static RegistryObject<Item> block(RegistryObject<Block> block) {
        return REGISTER.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

}
