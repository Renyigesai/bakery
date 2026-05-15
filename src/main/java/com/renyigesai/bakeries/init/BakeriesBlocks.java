package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.blocks.menu.MenuBlock;
import com.renyigesai.bakeries.init.blocks.FacingBlock;
import com.renyigesai.bakeries.init.blocks.MachineBlocks;
import com.renyigesai.bakeries.init.blocks.StateBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class BakeriesBlocks {
    public static final Block OVEN, TOASTER, BLENDER, FERMENTATION_BOX, FERMENTATION_TANK, DOUGH_CRAFTING_TABLE, CUPBOARD, WOOD_COUNTER, COFFEE_TABLE, CHEESE_TANK, MILK_TANK, YEAST_TANK, MOKA_POT, BREAD_RACK, GLASS_BREAD_RACK, BREAD_BASKET, GLASS_CABINET_DOOR, MENU, MOULD, DRINK_CUP, TOAST, CHEESE_COCOA_TOAST, BAGEL, BAGUETTE, CROISSANT, ROUND_BREAD, RICE_BREAD, WHOLE_WHEAT_BAGEL, PINEAPPLE_BUN, FOCACCIA, CIABATTA, EGG_TART, SALT_CROISSANT, COUNTRY_BREAD, CREAM_BINGLE_COFFEE, MATCHA_PARFAIT, COFFEE_PLANT, TARO, TOMATO, MIX_BLOCK,BAGEL_FILLED_SAUCE,BAGUETTE_WITH_FILLING,BERRY_BAGEL;
    public static final Block CASH_REGISTER_COMPUTER;
    public static final Block CHEESE_CREAM_BREAD;
    public static final Block RAW_SALT_BLOCK;
    public static final Block SALT_ORE;
    public static final Block DIRTY_CHOCO_CROISSANT;
    public static final Block FLOUR_BAG;
    public static final Block MEAT_FLOSS_BREAD_ROLL;
    public static final Block SOFA_LIGHT_GRAY;
    public static final Block SOFA_RED;
    public static final Block SOFA_WHITE;
    public static final Block TARO_SALT_YOLK_BREAD;
    public static final Block TOMATO_CHEESE_CROISSANT_SANDWICH;
    public static final Block WHOLE_WHEAT_FLOUR_BAG;
    public static final Block WOOD_TRAY;
    public static final Block PINEAPPLE_OIL;
    static {
        PINEAPPLE_OIL = register("pineapple_oil", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        SOFA_LIGHT_GRAY = register("sofa_light_gray", new StateBlocks.RackBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        SOFA_RED = register("sofa_red", new StateBlocks.RackBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        SOFA_WHITE = register("sofa_white", new StateBlocks.RackBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        TARO_SALT_YOLK_BREAD = register("taro_salt_yolk_bread", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion(), 4));
        TOMATO_CHEESE_CROISSANT_SANDWICH = register("tomato_cheese_croissant_sandwich", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion(), 4));
        WHOLE_WHEAT_FLOUR_BAG = register("whole_wheat_flour_bag", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        WOOD_TRAY = register("wood_tray", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        MEAT_FLOSS_BREAD_ROLL = register("meat_floss_bread_roll", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        FLOUR_BAG = register("flour_bag", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        DIRTY_CHOCO_CROISSANT = register("dirty_choco_croissant", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        CHEESE_CREAM_BREAD = register("cheese_cream_bread", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion(), 4));
        CASH_REGISTER_COMPUTER = register("cash_register_computer", new StateBlocks.LitFacingBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        RAW_SALT_BLOCK = register("raw_salt_block", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        SALT_ORE = register("salt_ore", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        OVEN = register("oven", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));
        TOASTER = register("toaster", new StateBlocks.ToasterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
        BLENDER = register("blender", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5F, 3.5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().isRedstoneConductor((state, level, pos) -> false)));
        FERMENTATION_BOX = register("fermentation_box", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5F, 3.5F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK).noOcclusion().isRedstoneConductor((state, level, pos) -> false)));
        FERMENTATION_TANK = register("fermentation_tank", new StateBlocks.FermentationTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).randomTicks()));
        DOUGH_CRAFTING_TABLE = register("dough_crafting_table", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));
        CUPBOARD = register("cupboard", new MachineBlocks.FacingMachineBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_GRAY).sound(SoundType.CHISELED_BOOKSHELF)));
        WOOD_COUNTER = register("wood_counter", new StateBlocks.WoodCounterBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
        COFFEE_TABLE = register("coffee_table", new StateBlocks.RackBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
        CHEESE_TANK = register("cheese_tank", new StateBlocks.CheeseTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        MILK_TANK = register("milk_tank", new StateBlocks.MilkTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).randomTicks()));
        YEAST_TANK = register("yeast_tank", new StateBlocks.YeastTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        MOKA_POT = register("moka_pot", new MachineBlocks.MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
        BREAD_RACK = register("bread_rack", new StateBlocks.RackBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
        GLASS_BREAD_RACK = register("glass_bread_rack", new StateBlocks.RackBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()));
        BREAD_BASKET = register("bread_basket", new StateBlocks.BreadBasketBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS).strength(0.0F, 0.0F).noOcclusion()));
        GLASS_CABINET_DOOR = register("glass_cabinet_door", new StateBlocks.GlassCabinetDoorBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        MENU = register("menu", new MenuBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
        MOULD = register("mould", new StateBlocks.MouldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(0.5F, 0.5F).sound(SoundType.METAL).noOcclusion()));
        DRINK_CUP = register("drink_cup", new StateBlocks.DrinkCupBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(0.1F, 0.1F).noOcclusion()));
        TOAST = register("toast", new StateBlocks.ToastBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F)));
        CHEESE_COCOA_TOAST = register("cheese_cocoa_toast", new StateBlocks.ToastBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F).noOcclusion()));
        BAGEL = register("bagel", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F).noOcclusion(), 4));
        BAGUETTE = register("baguette", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F).noOcclusion(), 4));
        CROISSANT = register("croissant", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F).noOcclusion(), 4));
        ROUND_BREAD = register("round_bread", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F).noOcclusion(), 4));
        RICE_BREAD = register("rice_bread", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F).noOcclusion(), 4));
        WHOLE_WHEAT_BAGEL = register("whole_wheat_bagel", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F).noOcclusion(), 4));
        PINEAPPLE_BUN = register("pineapple_bun", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F).noOcclusion(), 4));
        FOCACCIA = register("focaccia", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F).noOcclusion(), 4));
        CIABATTA = register("ciabatta", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F).noOcclusion(), 4));
        EGG_TART = register("egg_tart", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F).noOcclusion(), 4));
        SALT_CROISSANT = register("salt_croissant", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F).noOcclusion(), 4));
        COUNTRY_BREAD = register("country_bread", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F, 0.5F), 2));
        CREAM_BINGLE_COFFEE = register("cream_bingle_coffee", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(0.1F, 0.1F), 2));
        MATCHA_PARFAIT = register("matcha_parfait", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(0.1F, 0.1F), 2));
        COFFEE_PLANT = register("coffee_plant", new StateBlocks.CropLikeBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT), 2));
        TARO = register("taro", new StateBlocks.CropLikeBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT), 3));
        TOMATO = register("tomato", new StateBlocks.CropLikeBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT), 7));
        MIX_BLOCK = register("mix_block", new MachineBlocks.MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
        BAGEL_FILLED_SAUCE = register("bagel_filled_sauce", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).strength(0.1F, 0.1F), 2));
        BAGUETTE_WITH_FILLING = register("baguette_with_filling", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).strength(0.1F, 0.1F), 2));
        BERRY_BAGEL = register("berry_bagel", new StateBlocks.FacingPileBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).strength(0.1F, 0.1F), 2));

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
