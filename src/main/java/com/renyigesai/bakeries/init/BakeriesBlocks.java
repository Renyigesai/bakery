package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.blocks.FacingBlock;
import com.renyigesai.bakeries.init.blocks.StateBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class BakeriesBlocks {
    public static final Block OVEN, TOASTER, BLENDER, FERMENTATION_BOX, FERMENTATION_TANK, DOUGH_CRAFTING_TABLE, CUPBOARD, WOOD_COUNTER, COFFEE_TABLE, CHEESE_TANK, MILK_TANK, YEAST_TANK, MOKA_POT, BREAD_RACK, GLASS_BREAD_RACK, BREAD_BASKET, GLASS_CABINET_DOOR, MENU, MOULD, DRINK_CUP, TOAST, CHEESE_COCOA_TOAST, BAGEL, BAGUETTE, CROISSANT, ROUND_BREAD, RICE_BREAD, WHOLE_WHEAT_BAGEL, PINEAPPLE_BUN, FOCACCIA, CIABATTA, EGG_TART, SALT_CROISSANT, COUNTRY_BREAD, CREAM_BINGLE_COFFEE, MATCHA_PARFAIT, COFFEE_PLANT, TARO, TOMATO, MIX_BLOCK;

    static {
        OVEN = register("oven", BlockBehaviour.Properties.copy(Blocks.STONE));
        TOASTER = register("toaster", new StateBlocks.ToasterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        BLENDER = register("blender", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
        FERMENTATION_BOX = register("fermentation_box", BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
        FERMENTATION_TANK = register("fermentation_tank", new StateBlocks.FermentationTankBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        DOUGH_CRAFTING_TABLE = register("dough_crafting_table", new FacingBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));
        CUPBOARD = register("cupboard", new FacingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        WOOD_COUNTER = register("wood_counter", new StateBlocks.WoodCounterBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        COFFEE_TABLE = register("coffee_table", new StateBlocks.RackBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        CHEESE_TANK = register("cheese_tank", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
        MILK_TANK = register("milk_tank", new StateBlocks.MilkTankBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        YEAST_TANK = register("yeast_tank", new StateBlocks.YeastTankBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        MOKA_POT = register("moka_pot", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
        BREAD_RACK = register("bread_rack", new StateBlocks.RackBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        GLASS_BREAD_RACK = register("glass_bread_rack", new StateBlocks.RackBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        BREAD_BASKET = register("bread_basket", new StateBlocks.BreadBasketBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        GLASS_CABINET_DOOR = register("glass_cabinet_door", new StateBlocks.GlassCabinetDoorBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        MENU = register("menu", new FacingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        MOULD = register("mould", new FacingBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        DRINK_CUP = register("drink_cup", new FacingBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        TOAST = register("toast", new StateBlocks.ToastBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)));
        CHEESE_COCOA_TOAST = register("cheese_cocoa_toast", BlockBehaviour.Properties.copy(Blocks.CAKE));
        BAGEL = register("bagel", BlockBehaviour.Properties.copy(Blocks.CAKE));
        BAGUETTE = register("baguette", BlockBehaviour.Properties.copy(Blocks.CAKE));
        CROISSANT = register("croissant", BlockBehaviour.Properties.copy(Blocks.CAKE));
        ROUND_BREAD = register("round_bread", BlockBehaviour.Properties.copy(Blocks.CAKE));
        RICE_BREAD = register("rice_bread", BlockBehaviour.Properties.copy(Blocks.CAKE));
        WHOLE_WHEAT_BAGEL = register("whole_wheat_bagel", BlockBehaviour.Properties.copy(Blocks.CAKE));
        PINEAPPLE_BUN = register("pineapple_bun", BlockBehaviour.Properties.copy(Blocks.CAKE));
        FOCACCIA = register("focaccia", BlockBehaviour.Properties.copy(Blocks.CAKE));
        CIABATTA = register("ciabatta", BlockBehaviour.Properties.copy(Blocks.CAKE));
        EGG_TART = register("egg_tart", BlockBehaviour.Properties.copy(Blocks.CAKE));
        SALT_CROISSANT = register("salt_croissant", BlockBehaviour.Properties.copy(Blocks.CAKE));
        COUNTRY_BREAD = register("country_bread", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2));
        CREAM_BINGLE_COFFEE = register("cream_bingle_coffee", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2));
        MATCHA_PARFAIT = register("matcha_parfait", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2));
        COFFEE_PLANT = register("coffee_plant", new StateBlocks.CropLikeBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT), 2));
        TARO = register("taro", new StateBlocks.CropLikeBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT), 3));
        TOMATO = register("tomato", new StateBlocks.CropLikeBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT), 7));
        MIX_BLOCK = register("mix_block", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    }
    private BakeriesBlocks() {
    }

    public static void init() {
        BakeriesMod.LOGGER.info("Registered Bakeries base blocks.");
    }

    private static Block register(String id, BlockBehaviour.Properties properties) {
        ResourceLocation key = new ResourceLocation(BakeriesMod.MODID, id);
        return Registry.register(BuiltInRegistries.BLOCK, key, new Block(properties));
    }

    private static Block register(String id, Block block) {
        ResourceLocation key = new ResourceLocation(BakeriesMod.MODID, id);
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }
}
