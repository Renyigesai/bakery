package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.LazyMobEffectInstance;
import com.renyigesai.bakeries.block.CreamPumpkinPieBlock;
import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.block.*;
import com.renyigesai.bakeries.block.baysalt_frame.BaysaltFrameBlock;
import com.renyigesai.bakeries.block.baysalt_frame.BaysaltFrameBlockEntity;
import com.renyigesai.bakeries.block.blender.BlenderBlock;
import com.renyigesai.bakeries.block.blender.BlenderBlockEntity;
import com.renyigesai.bakeries.block.bread_basket.BreadBasketBlock;
import com.renyigesai.bakeries.block.bread_basket.BreadBasketBlockEntity;
import com.renyigesai.bakeries.block.cake.*;
import com.renyigesai.bakeries.block.cake_box.CakeBoxBlock;
import com.renyigesai.bakeries.block.cake_box.CakeBoxBlockEntity;
import com.renyigesai.bakeries.block.cupboard.CupboardBlock;
import com.renyigesai.bakeries.block.cupboard.CupboardBlockEntity;
import com.renyigesai.bakeries.block.dough_crafting_table.DoughCraftingTableBlock;
import com.renyigesai.bakeries.block.dough_crafting_table.DoughCraftingTableBlockEntity;
import com.renyigesai.bakeries.block.fermentation_barrel.FermentationBarrelBlock;
import com.renyigesai.bakeries.block.fermentation_barrel.FermentationBarrelBlockEntity;
import com.renyigesai.bakeries.block.FermentationTankBlock;
import com.renyigesai.bakeries.block.glass_drink_cup.GlassDrinkCupBlock;
import com.renyigesai.bakeries.block.glass_drink_cup.GlassDrinkCupBlockEntity;
import com.renyigesai.bakeries.block.menu.MenuBlock;
import com.renyigesai.bakeries.block.menu.MenuBlockEntity;
import com.renyigesai.bakeries.block.moka_pot.MokaPotBlock;
import com.renyigesai.bakeries.block.moka_pot.MokaPotBlockEntity;
import com.renyigesai.bakeries.block.oven.OvenBlock;
import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import com.renyigesai.bakeries.block.sofa.SofaBlock;
import com.renyigesai.bakeries.block.toaster.ToasterBlock;
import com.renyigesai.bakeries.block.toaster.ToasterBlockEntity;
import com.renyigesai.bakeries.block.wooden_tray.WoodenTrayBlock;
import com.renyigesai.bakeries.block.wooden_tray.WoodenTrayBlockEntity;
import com.renyigesai.bakeries.fluid.SaltWaterFluidsBlock;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.ToIntFunction;

public class BakeriesBlocks {

    public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, BakeriesMod.MODID);
//    public static final DeferredRegister<LiquidBlock> FLUID_BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS,BakeryMod.MODID)

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BakeriesMod.MODID);
    //Bread Pile Block
    public static final RegistryObject<Block> BAGEL;
    public static final RegistryObject<Block> WHOLE_WHEAT_BAGEL;
    public static final RegistryObject<Block> BAGUETTE;
    public static final RegistryObject<Block> GARLIC_FLAVORED_BAGUETTE;
    public static final RegistryObject<Block> BROWN_SUGAR_ROLL;
    public static final RegistryObject<Block> COUNTRY_BREAD;
    public static final RegistryObject<Block> CROISSANT;
    public static final RegistryObject<Block> CIABATTA;
    public static final RegistryObject<Block> PINEAPPLE_BUN;
    public static final RegistryObject<Block> ROUND_BREAD;
    public static final RegistryObject<Block> SALT_CROISSANT;
    public static final RegistryObject<Block> TOAST;
    public static final RegistryObject<Block> CHEESE_COCOA_TOAST;
    public static final RegistryObject<Block> BERRY_BREAD;
    public static final RegistryObject<Block> FOCACCIA;
    public static final RegistryObject<Block> DIRTY_CHOCO_CROISSANT;
    public static final RegistryObject<Block> PIZZA;
    public static final RegistryObject<Block> SAUSAGE_PIZZA;
    public static final RegistryObject<Block> MEAT_PASTE_PIZZA;
    public static final RegistryObject<Block> BAGUETTE_WITH_FILLING;
    public static final RegistryObject<Block> TOMATO_CHEESE_CROISSANT_SANDWICH;
    public static final RegistryObject<Block> BERRY_BAGEL;
    public static final RegistryObject<Block> CREAM_PUMPKIN_PIE;
    public static final RegistryObject<Block> CUP_CAKE;
    public static final RegistryObject<Block> CAKE_BASE;
    public static final RegistryObject<Block> CREAM_CAKE;
    public static final RegistryObject<Block> CUT_CAKE_BASE;
    public static final RegistryObject<Block> CREAM_CAKE_PROCESSING;
    public static final RegistryObject<Block> TIRAMISU;
    public static final RegistryObject<Block> SOAK_COFFEE_CUT_CAKE_BASE;
    public static final RegistryObject<Block> POUND_CAKE;
    public static final RegistryObject<Block> CARROT_CAKE;
    public static final RegistryObject<Block> BASQUE_CAKE;
    public static final RegistryObject<Block> MULTI_LAYER_CREAM_CAKE;
    public static final RegistryObject<Block> CHEESE_CREAM_BREAD;
    public static final RegistryObject<Block> BAGEL_FILLED_SAUCE;
    public static final RegistryObject<Block> RICE_BREAD;
    public static final RegistryObject<Block> EGG_TART;
    public static final RegistryObject<Block> MOULD_RED_VELVET_CAKE;
    public static final RegistryObject<Block> RED_VELVET_CAKE_BASE;
    public static final RegistryObject<Block> RED_VELVET_CAKE;

    //common
    public static final RegistryObject<Block> OVEN;
    public static final RegistryObject<BlockEntityType<OvenBlockEntity>> OVEN_BLOCK_ENTITY;
    public static final RegistryObject<Block> FERMENTATION_TANK;
    public static final RegistryObject<Block> YEAST_TANK;
    public static final RegistryObject<Block> CHEESE_TANK;
    public static final RegistryObject<Block> Milk_TANK;
    public static final RegistryObject<Block> GLASS_CABINET_DOOR;
    public static final RegistryObject<Block> GLASS_CABINET_DOOR_TWO;
    public static final RegistryObject<Block> SALT_ORE;
    public static final RegistryObject<Block> DEEPSLATE_SALT_ORE;
    public static final RegistryObject<Block> DOUGH_CRAFTING_TABLE;
    public static final RegistryObject<LiquidBlock> SALT_WATER_BLOCK;
    public static final RegistryObject<Block> BREAD_HOLDERS;
    public static final RegistryObject<Block> MOULD;
    public static final RegistryObject<Block> MOULD_TWO;
    public static final RegistryObject<Block> MOULD_TOAST;
    public static final RegistryObject<Block> MOULD_CHEESE_COCOA_TOAST;
    public static final RegistryObject<Block> RAW_SALT_BLOCK;
    public static final RegistryObject<Block> WOOD_COUNTER;
    public static final RegistryObject<Block> TOMATO;
    public static final RegistryObject<Block> BLACK_WHITE_CONCRETE ;
    public static final RegistryObject<Block> CUPBOARD;
    public static final RegistryObject<Block> FLOUR_BAG;
    public static final RegistryObject<Block> WHOLE_WHEAT_FLOUR_BAG;
    public static final RegistryObject<Block> MEAT_FLOSS_BREAD_ROLL;
    public static final RegistryObject<Block> ICED_LATTE;
    public static final RegistryObject<Block> BROWN_SUGAR_LATTE;
    public static final RegistryObject<Block> ICED_AMERICAN;
    public static final RegistryObject<Block> ORANGE_AMERICAN;
    public static final RegistryObject<Block> COFFEE_PLANT;
    public static final RegistryObject<Block> CREAM_BINGLE_COFFEE;
    public static final RegistryObject<Block> MATCHA_LATTE;
    public static final RegistryObject<Block> TRAY_SCONE;
    public static final RegistryObject<Block> PAPER_CUP;
    public static final RegistryObject<Block> MOULD_POUND_CAKE;
    public static final RegistryObject<Block> MOULD_CAKE_BASE;
    public static final RegistryObject<Block> MOULD_CARROT_CAKE;
    public static final RegistryObject<Block> MOULD_BASQUE_CAKE;
    public static final RegistryObject<Block> COFFEE_TABLE;
    public static final RegistryObject<Block> SOFA;
    public static final RegistryObject<Block> SOFA_RED;
    public static final RegistryObject<Block> SOFA_LIGHT_GRAY;
    public static final RegistryObject<Block> CASH_REGISTER_COMPUTER;
    public static final RegistryObject<Block> SILICONE_PAPER;
    public static final RegistryObject<Block> TRAY_YUNTUI_MOONCAKE;

    /*方块实体*/
    public static final RegistryObject<Block> BREAD_BASKET ;
    public static final RegistryObject<Block> TOASTER;
    public static final RegistryObject<BlockEntityType<CupboardBlockEntity>> CUPBOARD_ENTITY;
    public static final RegistryObject<BlockEntityType<BreadBasketBlockEntity>> BREAD_BASKET_BLOCK_ENTITY ;
    public static final RegistryObject<BlockEntityType<ToasterBlockEntity>> TOASTER_ENTITY;
    public static final RegistryObject<Block> BAYSALT_FRAME;
    public static final RegistryObject<Block> BLENDER;
    public static final RegistryObject<BlockEntityType<BaysaltFrameBlockEntity>> BAYSALT_FRAME_ENTITY;
    public static final RegistryObject<BlockEntityType<DoughCraftingTableBlockEntity>> DOUGH_CRAFTING_TABLE_ENTITY;
    public static final RegistryObject<BlockEntityType<BlenderBlockEntity>> BLENDER_ENTITY;
    public static final RegistryObject<Block> DRINK_CUP;
    public static final RegistryObject<BlockEntityType<GlassDrinkCupBlockEntity>> DRINK_CUP_ENTITY;
    public static final RegistryObject<Block> MOKA_POT;
    public static final RegistryObject<BlockEntityType<MokaPotBlockEntity>> MOKA_POT_ENTITY;
    public static final RegistryObject<Block> MENU_BLOCK;
    public static final RegistryObject<BlockEntityType<MenuBlockEntity>> MENU_ENTITY;
    public static final RegistryObject<Block> WOOD_TRAY;
    public static final RegistryObject<BlockEntityType<WoodenTrayBlockEntity>> WOOD_TRAY_ENTITY;
    public static final RegistryObject<Block> FERMENTATION_BARREL;
    public static final RegistryObject<BlockEntityType<FermentationBarrelBlockEntity>> FERMENTATION_BARREL_ENTITY;
    public static RegistryObject<Block> CAKE_BOX;
    public static final RegistryObject<BlockEntityType<CakeBoxBlockEntity>> CAKE_BOX_ENTITY;
    public static RegistryObject<Block> CAKE_ROLL_PROCESSING;
    public static final RegistryObject<BlockEntityType<CakeRollProcessingBlockEntity>> CAKE_ROLL_PROCESSING_ENTITY;

    static {
        /*面包方块*/
        BAGEL = BLOCK_REGISTRY.register("bagel", PileBlock::new);
        WHOLE_WHEAT_BAGEL = BLOCK_REGISTRY.register("whole_wheat_bagel", PileBlock::new);
        BAGUETTE = BLOCK_REGISTRY.register("baguette", PileBlock::new);
        GARLIC_FLAVORED_BAGUETTE = BLOCK_REGISTRY.register("garlic_flavored_baguette", PileBlock::new);
        BROWN_SUGAR_ROLL = BLOCK_REGISTRY.register("brown_sugar_roll", PileBlock::new);
        COUNTRY_BREAD = BLOCK_REGISTRY.register("country_bread", () ->
                new CountryBreadBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F,0.5F)));
        CROISSANT = BLOCK_REGISTRY.register("croissant", PileBlock::new);
        CIABATTA = BLOCK_REGISTRY.register("ciabatta",PileBlock::new);
        PINEAPPLE_BUN = BLOCK_REGISTRY.register("pineapple_bun", PileBlock::new);
        ROUND_BREAD = BLOCK_REGISTRY.register("round_bread", PileBlock::new);
        SALT_CROISSANT = BLOCK_REGISTRY.register("salt_croissant", PileBlock::new);
        TOAST = BLOCK_REGISTRY.register("toast", () ->
                new ToastBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F,0.5F), BakeriesItems.SLICED_TOAST));
        CHEESE_COCOA_TOAST = BLOCK_REGISTRY.register("cheese_cocoa_toast", () ->
                new ToastBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F,0.5F), BakeriesItems.SLICED_CHEESE_COCOA_TOAST));
        BERRY_BREAD = BLOCK_REGISTRY.register("berry_bread", PileBlock::new);
        MEAT_FLOSS_BREAD_ROLL = BLOCK_REGISTRY.register("meat_floss_bread_roll", PileBlock::new);
        PIZZA = BLOCK_REGISTRY.register("pizza",()->
                new PizzaBlock(BlockBehaviour.Properties.copy(Blocks.CAKE),2,0.1F));
        SAUSAGE_PIZZA = BLOCK_REGISTRY.register("sausage_pizza",()->
                new PizzaBlock(BlockBehaviour.Properties.copy(Blocks.CAKE),2,0.1F));
        MEAT_PASTE_PIZZA = BLOCK_REGISTRY.register("meat_paste_pizza",()->
                new PizzaBlock(BlockBehaviour.Properties.copy(Blocks.CAKE),2,0.1F));
        FOCACCIA = BLOCK_REGISTRY.register("focaccia",PileBlock::new);
        DIRTY_CHOCO_CROISSANT = BLOCK_REGISTRY.register("dirty_choco_croissant",PileBlock::new);
        BAGUETTE_WITH_FILLING = BLOCK_REGISTRY.register("baguette_with_filling",PileBlock::new);
        TOMATO_CHEESE_CROISSANT_SANDWICH = BLOCK_REGISTRY.register("tomato_cheese_croissant_sandwich",PileBlock::new);
        BERRY_BAGEL = BLOCK_REGISTRY.register("berry_bagel",PileBlock::new);
        CREAM_PUMPKIN_PIE = BLOCK_REGISTRY.register("cream_pumpkin_pie", CreamPumpkinPieBlock::new);
        BAGEL_FILLED_SAUCE = BLOCK_REGISTRY.register("bagel_filled_sauce",PileBlock::new);
        RICE_BREAD = BLOCK_REGISTRY.register("rice_bread",PileBlock::new);
        EGG_TART = BLOCK_REGISTRY.register("egg_tart",PileBlock::new);
        CHEESE_CREAM_BREAD = BLOCK_REGISTRY.register("cheese_cream_bread",PileBlock::new);

        /*蛋糕方块*/
        CUP_CAKE = BLOCK_REGISTRY.register("cup_cake",PileBlock::new);

        CAKE_BASE = BLOCK_REGISTRY.register("cake_base", CakeBaseBlock::new);

        CREAM_CAKE = BLOCK_REGISTRY.register("cream_cake",()-> new CreamCakeBlock(ItemUtil.addEffects(
                new LazyMobEffectInstance(()-> MobEffects.REGENERATION,1200),
                new LazyMobEffectInstance(BakeriesMobEffects.SOFT,1200)),10,0.25f));

        CUT_CAKE_BASE = BLOCK_REGISTRY.register("cut_cake_base", CakeProcessingInitialBlock::new);

        CREAM_CAKE_PROCESSING = BLOCK_REGISTRY.register("cream_cake_processing",()-> new CakeProcessingBlock(BakeriesItems.CUT_CAKE_BASE,BakeriesItems.FOAMED_CREAM,()-> Items.SWEET_BERRIES,()-> Items.SWEET_BERRIES,CREAM_CAKE,2));

        TIRAMISU = BLOCK_REGISTRY.register("tiramisu", ()-> new InstanceCakeBlock(ItemUtil.addEffects(
                new LazyMobEffectInstance(BakeriesMobEffects.CHEESE_POWER,1200),
                new LazyMobEffectInstance(BakeriesMobEffects.COCOA_MANIA,1200),
                new LazyMobEffectInstance(()-> MobEffects.NIGHT_VISION,1200)),4,0.5f));

        SOAK_COFFEE_CUT_CAKE_BASE = BLOCK_REGISTRY.register("soak_coffee_cut_cake_base", ()-> new CakeProcessingBlock(BakeriesItems.CHEESE_CREAM,BakeriesItems.SOAK_COFFEE_CUT_CAKE_BASE,BakeriesItems.CHEESE_CREAM,BakeriesItems.COCOA_POWDER,TIRAMISU));

        POUND_CAKE = BLOCK_REGISTRY.register("pound_cake",()-> new ToastBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F,0.5F),BakeriesItems.SLICED_POUND_CAKE));

        CARROT_CAKE = BLOCK_REGISTRY.register("carrot_cake",()-> new InstanceCakeBlock(ItemUtil.addEffects(
                new LazyMobEffectInstance(()-> MobEffects.NIGHT_VISION,1200)),10,0.25f));

        BASQUE_CAKE = BLOCK_REGISTRY.register("basque_cake", ()-> new InstanceCakeBlock(ItemUtil.addEffects(
                new LazyMobEffectInstance(BakeriesMobEffects.CHEESE_POWER,1200)),6,0.5f));

        MULTI_LAYER_CREAM_CAKE = BLOCK_REGISTRY.register("multi_layer_cream_cake",MultiLayerCreamCakeBlock::new);

        RED_VELVET_CAKE_BASE = BLOCK_REGISTRY.register("red_velvet_cake_base", RedVelvetCakeBaseBlock::new);

        RED_VELVET_CAKE = BLOCK_REGISTRY.register("red_velvet_cake", ()-> new InstanceCakeBlock(5,0.25f));

        /*普通方块*/
        FERMENTATION_TANK = BLOCK_REGISTRY.register("fermentation_tank", () ->
                new FermentationTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).randomTicks()));
        YEAST_TANK = BLOCK_REGISTRY.register("yeast_tank", () ->
                new YeastTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        CHEESE_TANK = BLOCK_REGISTRY.register("cheese_tank", () ->
                new CheeseTankBkock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        Milk_TANK = BLOCK_REGISTRY.register("milk_tank", () ->
                new MilkTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).randomTicks()));
        GLASS_CABINET_DOOR = BLOCK_REGISTRY.register("glass_cabinet_door", () ->
                new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops(), BlockSetType.OAK));
        GLASS_CABINET_DOOR_TWO = BLOCK_REGISTRY.register("glass_cabinet_door_two", () ->
                new GlassCabinetDoorTwoBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops()));
        SALT_ORE = BLOCK_REGISTRY.register("salt_ore", () ->
                new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
        DEEPSLATE_SALT_ORE = BLOCK_REGISTRY.register("deepslate_salt_ore", () ->
                new DropExperienceBlock(BlockBehaviour.Properties.copy(SALT_ORE.get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
        SALT_WATER_BLOCK = BLOCK_REGISTRY.register("salt_water_block", SaltWaterFluidsBlock::new);
        MOULD = BLOCK_REGISTRY.register("mould", () -> new MouldBlock(BakeriesItems.MOULD));
        MOULD_TWO = BLOCK_REGISTRY.register("mould_two", () -> new MouldBlock(BakeriesItems.MOULD_TWO));
        MOULD_TOAST = BLOCK_REGISTRY.register("mould_toast", () ->
                new MouldToastBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(0.5F,0.5F),BakeriesItems.TOAST));
        MOULD_CHEESE_COCOA_TOAST = BLOCK_REGISTRY.register("mould_cheese_cocoa_toast", () ->
                new MouldToastBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(0.5F,0.5F),BakeriesItems.CHEESE_COCOA_TOAST));
        RAW_SALT_BLOCK = BLOCK_REGISTRY.register("raw_salt_block", () ->
                new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
        WOOD_COUNTER = BLOCK_REGISTRY.register("wood_counter", () ->
                new WoodCounterBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        TOMATO = BLOCK_REGISTRY.register("tomato",() ->
                new TomatoBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
        BLACK_WHITE_CONCRETE = BLOCK_REGISTRY.register("black_white_concrete", () ->
                new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_CONCRETE)));
        BREAD_HOLDERS = BLOCK_REGISTRY.register("bread_holders",()->new BreadHoldersBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        FLOUR_BAG = BLOCK_REGISTRY.register("flour_bag",()->
                new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)));
        WHOLE_WHEAT_FLOUR_BAG = BLOCK_REGISTRY.register("whole_wheat_flour_bag",()->
                new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)));
        ICED_LATTE = coldDrinkBlock("iced_latte");
        BROWN_SUGAR_LATTE = coldDrinkBlock("brown_sugar_latte");
        ICED_AMERICAN = coldDrinkBlock("iced_american");
        ORANGE_AMERICAN = coldDrinkBlock("orange_american");
        CREAM_BINGLE_COFFEE = coldDrinkBlock("cream_bingle_coffee");
        MATCHA_LATTE = coldDrinkBlock("matcha_latte");
        COFFEE_PLANT = BLOCK_REGISTRY.register("coffee_plant",()->
                new CoffeePlantBlock(BlockBehaviour.Properties.copy(Blocks.AZALEA)));
        TRAY_SCONE = BLOCK_REGISTRY.register("tray_scone",()->
                new TraySconeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        PAPER_CUP = BLOCK_REGISTRY.register("paper_cup",()->
                new PaperCupBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)));
        MOULD_POUND_CAKE = BLOCK_REGISTRY.register("mould_pound_cake", () ->
                new MouldToastBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(0.5F,0.5F),BakeriesItems.POUND_CAKE));
        MOULD_CAKE_BASE = BLOCK_REGISTRY.register("mould_cake_base",()->new MouldCakeBlock(BakeriesItems.CAKE_BASE));
        MOULD_CARROT_CAKE = BLOCK_REGISTRY.register("mould_carrot_cake",()->new MouldCakeBlock(BakeriesItems.CARROT_CAKE));
        MOULD_BASQUE_CAKE = BLOCK_REGISTRY.register("mould_basque_cake",()->new MouldCakeBlock(BakeriesItems.BASQUE_CAKE));
        MOULD_RED_VELVET_CAKE = BLOCK_REGISTRY.register("mould_red_velvet_cake", ()->new MouldCakeBlock(BakeriesItems.RED_VELVET_CAKE_BASE));
        COFFEE_TABLE = BLOCK_REGISTRY.register("coffee_table",()-> new CoffeeTableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        SOFA = BLOCK_REGISTRY.register("sofa",()-> new SofaBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        SOFA_RED = BLOCK_REGISTRY.register("sofa_red",()-> new SofaBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        SOFA_LIGHT_GRAY = BLOCK_REGISTRY.register("sofa_light_gray",()-> new SofaBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        CASH_REGISTER_COMPUTER = BLOCK_REGISTRY.register("cash_register_computer",()->new CashRegisterComputerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(1.5F, 6.0F).lightLevel(litBlockEmission(10))));
        SILICONE_PAPER = BLOCK_REGISTRY.register("silicone_paper",()-> new SiliconePaperBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).strength(0.1f)));
        TRAY_YUNTUI_MOONCAKE = BLOCK_REGISTRY.register("tray_yuntui_mooncake",()-> new TraySconeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        /*
        方块实体BlockEntity
        */
        OVEN = BLOCK_REGISTRY.register("oven", ()->new OvenBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5F, 3.5F).requiresCorrectToolForDrops()
                .sound(SoundType.METAL).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
        OVEN_BLOCK_ENTITY = BLOCK_ENTITY_REGISTRY.register("oven", () -> BlockEntityType.Builder.of(OvenBlockEntity::new, OVEN.get()).build(null));

        CUPBOARD = BLOCK_REGISTRY.register("cupboard",() ->
                new CupboardBlock(BlockBehaviour.Properties.of().strength(2.0F,3.0F).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_GRAY).sound(SoundType.CHISELED_BOOKSHELF)));
        CUPBOARD_ENTITY = BLOCK_ENTITY_REGISTRY.register("cupboard", () ->BlockEntityType.Builder.of(CupboardBlockEntity::new,CUPBOARD.get()).build(null));

        BREAD_BASKET = BLOCK_REGISTRY.register("bread_basket", () ->
                new BreadBasketBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS).strength(0.0F,0.0F)));
        BREAD_BASKET_BLOCK_ENTITY = BLOCK_ENTITY_REGISTRY.register("bread_basket", () -> BlockEntityType.Builder.of(BreadBasketBlockEntity::new, BREAD_BASKET.get()).build(null));

        TOASTER = BLOCK_REGISTRY.register("toaster", () ->
                new ToasterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        TOASTER_ENTITY = BLOCK_ENTITY_REGISTRY.register("toaster", () -> BlockEntityType.Builder.of(ToasterBlockEntity::new, TOASTER.get()).build(null));

        BAYSALT_FRAME = BLOCK_REGISTRY.register("baysalt_frame", BaysaltFrameBlock::new);
        BAYSALT_FRAME_ENTITY = BLOCK_ENTITY_REGISTRY.register("baysalt_frame", () -> BlockEntityType.Builder.of(BaysaltFrameBlockEntity::new, BAYSALT_FRAME.get()).build(null));

        DOUGH_CRAFTING_TABLE = BLOCK_REGISTRY.register("dough_crafting_table", DoughCraftingTableBlock::new);
        DOUGH_CRAFTING_TABLE_ENTITY = BLOCK_ENTITY_REGISTRY.register("dough_crafting_table", () -> BlockEntityType.Builder.of(DoughCraftingTableBlockEntity::new, DOUGH_CRAFTING_TABLE.get()).build(null));

        BLENDER = BLOCK_REGISTRY.register("blender", ()->new BlenderBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5F, 3.5F).requiresCorrectToolForDrops()
                .sound(SoundType.METAL).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
        BLENDER_ENTITY = BLOCK_ENTITY_REGISTRY.register("blender", () -> BlockEntityType.Builder.of(BlenderBlockEntity::new, BLENDER.get()).build(null));

        DRINK_CUP = BLOCK_REGISTRY.register("drink_cup",()-> new GlassDrinkCupBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(0.1F,0.1F)));
        DRINK_CUP_ENTITY = BLOCK_ENTITY_REGISTRY.register("drink_cup", () -> BlockEntityType.Builder.of(GlassDrinkCupBlockEntity::new, DRINK_CUP.get()).build(null));

        MOKA_POT = BLOCK_REGISTRY.register("moka_pot",()-> new MokaPotBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        MOKA_POT_ENTITY = BLOCK_ENTITY_REGISTRY.register("moka_pot", () -> BlockEntityType.Builder.of(MokaPotBlockEntity::new, MOKA_POT.get()).build(null));

        MENU_BLOCK = BLOCK_REGISTRY.register("menu_block",()->new MenuBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        MENU_ENTITY = BLOCK_ENTITY_REGISTRY.register("menu", () -> BlockEntityType.Builder.of(MenuBlockEntity::new, MENU_BLOCK.get()).build(null));

        WOOD_TRAY = BLOCK_REGISTRY.register("wood_tray",()->new WoodenTrayBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        WOOD_TRAY_ENTITY = BLOCK_ENTITY_REGISTRY.register("wood_tray", () -> BlockEntityType.Builder.of(WoodenTrayBlockEntity::new, WOOD_TRAY.get()).build(null));

        FERMENTATION_BARREL = BLOCK_REGISTRY.register("fermentation_barrel", FermentationBarrelBlock::new);
        FERMENTATION_BARREL_ENTITY = BLOCK_ENTITY_REGISTRY.register("fermentation_barrel",() -> BlockEntityType.Builder.of(FermentationBarrelBlockEntity::new, FERMENTATION_BARREL.get()).build(null));

        CAKE_BOX = BLOCK_REGISTRY.register("cake_box", CakeBoxBlock::new);
        CAKE_BOX_ENTITY = BLOCK_ENTITY_REGISTRY.register("cake_box",() -> BlockEntityType.Builder.of(CakeBoxBlockEntity::new, CAKE_BOX.get()).build(null));

        CAKE_ROLL_PROCESSING = BLOCK_REGISTRY.register("cake_roll_processing", CakeRollProcessingBlock::new);
        CAKE_ROLL_PROCESSING_ENTITY = BLOCK_ENTITY_REGISTRY.register("cake_roll_processing",() -> BlockEntityType.Builder.of(CakeRollProcessingBlockEntity::new, CAKE_ROLL_PROCESSING.get()).build(null));
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    private static RegistryObject<Block> coldDrinkBlock(String name){
        return BLOCK_REGISTRY.register(name,()->
                new ColdDrinkBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(0.1F,0.1F)));
    }

}

