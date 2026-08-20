package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.LazyMobEffectInstance;
import com.renyigesai.bakeries.common.blocks.*;
import com.renyigesai.bakeries.common.blocks.blander.BlenderBlock;
import com.renyigesai.bakeries.common.blocks.blander.BlenderBlockEntity;
import com.renyigesai.bakeries.common.blocks.bread_basket.BreadBasketBlock;
import com.renyigesai.bakeries.common.blocks.bread_basket.BreadBasketBlockEntity;
import com.renyigesai.bakeries.common.blocks.bread_rack.BreadRackBlock;
import com.renyigesai.bakeries.common.blocks.bread_rack.BreadRackBlockEntity;
import com.renyigesai.bakeries.common.blocks.bread_rack.GlassBreadRackBlock;
import com.renyigesai.bakeries.common.blocks.cake.CakeBaseBlock;
import com.renyigesai.bakeries.common.blocks.cake.CreamCakeBlock;
import com.renyigesai.bakeries.common.blocks.cupboard.CupboardBlock;
import com.renyigesai.bakeries.common.blocks.cupboard.CupboardBlockEntity;
import com.renyigesai.bakeries.common.blocks.custom_cake.CustomCakeBlock;
import com.renyigesai.bakeries.common.blocks.custom_cake.CustomCakeBlockEntity;
import com.renyigesai.bakeries.common.blocks.dough_crafting_table.DoughCraftingTableBlock;
import com.renyigesai.bakeries.common.blocks.dough_crafting_table.DoughCraftingTableBlockEntity;
import com.renyigesai.bakeries.common.blocks.fermentation_box.FermentationBoxBlock;
import com.renyigesai.bakeries.common.blocks.fermentation_box.FermentationBoxBlockEntity;
import com.renyigesai.bakeries.common.blocks.fluid.SaltWaterFluidsBlock;
import com.renyigesai.bakeries.common.blocks.glass_drink_cup.GlassDrinkCupBlock;
import com.renyigesai.bakeries.common.blocks.glass_drink_cup.GlassDrinkCupBlockEntity;
import com.renyigesai.bakeries.common.blocks.luminous_light_sign.LuminousLightSignBlock;
import com.renyigesai.bakeries.common.blocks.luminous_light_sign.LuminousLightSignBlockEntity;
import com.renyigesai.bakeries.common.blocks.menu.MenuBlock;
import com.renyigesai.bakeries.common.blocks.menu.MenuBlockEntity;
import com.renyigesai.bakeries.common.blocks.mix_block.MixBlock;
import com.renyigesai.bakeries.common.blocks.mix_block.MixBlockEntity;
import com.renyigesai.bakeries.common.blocks.moka_pot.MokaPotBlock;
import com.renyigesai.bakeries.common.blocks.moka_pot.MokaPotBlockEntity;
import com.renyigesai.bakeries.common.blocks.mould_cake.MouldCakeBlock;
import com.renyigesai.bakeries.common.blocks.mould_cake.MouldCakeBlockEntity;
import com.renyigesai.bakeries.common.blocks.oven.OvenBlock;
import com.renyigesai.bakeries.common.blocks.oven.OvenBlockEntity;
import com.renyigesai.bakeries.common.blocks.sofa.SofaBlock;
import com.renyigesai.bakeries.common.blocks.toaster.ToasterBlock;
import com.renyigesai.bakeries.common.blocks.toaster.ToasterBlockEntity;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class BakeriesBlocks {
    public static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(BakeriesMod.MODID);

    /*面包方块*/

    /**贝果*/
    public static final DeferredBlock<Block> BAGEL;
    /**全麦贝果*/
    public static final DeferredBlock<Block> WHOLE_WHEAT_BAGEL;
    /**圆面包*/
    public static final DeferredBlock<Block> ROUND_BREAD;
    /**莓果面包*/
    public static final DeferredBlock<Block> BERRY_BREAD;
    /**乳酪面包*/
    public static final DeferredBlock<Block> CHEESE_CREAM_BREAD;
    /**红糖卷*/
    public static final DeferredBlock<Block> BROWN_SUGAR_ROLL;
    /**菠萝包*/
    public static final DeferredBlock<Block> PINEAPPLE_BUN;
    /**菠萝包*/
    public static final DeferredBlock<Block> PINEAPPLE_OIL;
    /**肉松面包卷*/
    public static final DeferredBlock<Block> MEAT_FLOSS_BREAD_ROLL;
    /**可颂*/
    public static final DeferredBlock<Block> CROISSANT;
    /**脏脏包*/
    public static final DeferredBlock<Block> DIRTY_CHOCO_CROISSANT;
    /**盐可颂*/
    public static final DeferredBlock<Block> SALT_CROISSANT;
    /**恰巴塔面包*/
    public static final DeferredBlock<Block> CIABATTA;
    /**佛卡夏面包*/
    public static final DeferredBlock<Block> FOCACCIA;
    /**浆果贝果*/
    public static final DeferredBlock<Block> BERRY_BAGEL;
    /**填酱贝果*/
    public static final DeferredBlock<Block> BAGEL_FILLED_SAUCE;
    /**填馅法棍*/
    public static final DeferredBlock<Block> BAGUETTE_WITH_FILLING;
    /**番茄奶酪可颂三明治*/
    public static final DeferredBlock<Block> TOMATO_CHEESE_CROISSANT_SANDWICH;
    /**法棍*/
    public static final DeferredBlock<Block> BAGUETTE;
    /**乡村面包*/
    public static final DeferredBlock<Block> COUNTRY_BREAD;
    /**扁可颂*/
    public static final DeferredBlock<Block> FLAT_CROISSANT;
    /**吐司*/
    public static final DeferredBlock<Block> TOAST;
    public static final DeferredBlock<Block> MOULD_TOAST;
    public static final DeferredBlock<Block> CHEESE_COCOA_TOAST;
    public static final DeferredBlock<Block> MOULD_CHEESE_COCOA_TOAST;

    /**蛋挞*/
    public static final DeferredBlock<Block> EGG_TART;
    /**芋泥咸蛋黄面包*/
    public static final DeferredBlock<Block> TARO_SALT_YOLK_BREAD;

    /**自定义蛋糕*/
    public static final DeferredBlock<Block> CUSTOM_CAKE;
    public static final DeferredBlock<Block> CAKE_BASE;
    public static final DeferredBlock<Block> RED_VELVET_CAKE_BASE;
    public static final DeferredBlock<Block> MATCHA_CAKE;
    public static final DeferredBlock<Block> CREAM_CAKE;
    public static final DeferredBlock<Block> RED_VELVET_CAKE;
    public static final DeferredBlock<Block> TIRAMISU;
    public static final DeferredBlock<Block> CARROT_CAKE;
    public static final DeferredBlock<Block> BASQUE_CAKE;

    /**番茄*/
    public static final DeferredBlock<Block> TOMATO;
    /**咖啡丛*/
    public static final DeferredBlock<Block> COFFEE_PLANT;
    /**芋头*/
    public static final DeferredBlock<Block> TARO;

    /**盐矿石*/
    public static final DeferredBlock<Block> SALT_ORE;
    public static final DeferredBlock<Block> DEEPSLATE_SALT_ORE;

    /**发酵罐*/
    public static final DeferredBlock<Block> FERMENTATION_TANK;
    public static final DeferredBlock<Block> YEAST_TANK;
    public static final DeferredBlock<Block> MILk_TANK;
    public static final DeferredBlock<Block> CHEESE_TANK;

    /**模具*/
    public static final DeferredBlock<Block> MOULD;
    public static final DeferredBlock<Block> MOULD_TWO;

    /**面粉袋*/
    public static final DeferredBlock<Block> WHOLE_WHEAT_FLOUR_BAG;
    public static final DeferredBlock<Block> FLOUR_BAG;

    /**菜单*/
    public static final DeferredBlock<Block> MENU;

    /**面包架*/
    public static final DeferredBlock<Block> BREAD_RACK;

    /**玻璃面包架*/
    public static final DeferredBlock<Block> GLASS_BREAD_RACK;

    /**黑白混凝土*/
    public static final DeferredBlock<Block> BLACK_WHITE_CONCRETE;

    /**木制柜台*/
    public static final DeferredBlock<Block> WOOD_COUNTER;
    /**咖啡桌*/
    public static final DeferredBlock<Block> COFFEE_TABLE;

    public static final DeferredBlock<Block> WOOD_TRAY;

    public static final DeferredBlock<Block> GLASS_CABINET_DOOR;

    public static final DeferredBlock<Block> RAW_SALT_BLOCK;

    public static final DeferredBlock<SofaBlock> SOFA_WHITE;
    public static final DeferredBlock<SofaBlock> SOFA_RED;
    public static final DeferredBlock<SofaBlock> SOFA_LIGHT_GRAY;

    public static final DeferredBlock<Block> CASH_REGISTER_COMPUTER;

    public static DeferredBlock<Block> FERMENTATION_BOX;
    public static DeferredBlock<Block> LUMINOUS_LIGHT_SIGN;
    public static DeferredBlock<Block> BREAD_HOLDERS;
    public static DeferredBlock<Block> SILICONE_PAPER;


    /**饮料方块*/
    public static final DeferredBlock<Block> ICED_AMERICAN;
    public static final DeferredBlock<Block> ICED_LATTE;
    public static final DeferredBlock<Block> BROWN_SUGAR_LATTE;
    public static final DeferredBlock<Block> CREAM_BINGLE_COFFEE;
    public static final DeferredBlock<Block> MATCHA_LATTE;
    public static final DeferredBlock<Block> MATCHA_PARFAIT;
    public static final DeferredBlock<Block> TARO_MILK;

    /**蛋黄酱酱*/
    public static final DeferredBlock<Block> OLIVE_OIL;
    public static final DeferredBlock<Block> BEARNAISE;

    public static final DeferredBlock<Block> BLENDER;
    public static final DeferredBlock<OvenBlock> OVEN;
    public static final DeferredBlock<DoughCraftingTableBlock> DOUGH_CRAFTING_TABLE;
    public static final DeferredBlock<CupboardBlock> CUPBOARD;
    public static final DeferredBlock<BreadBasketBlock> BREAD_BASKET;
    public static final DeferredBlock<MokaPotBlock> MOKA_POT;
    public static final DeferredBlock<MixBlock> MIX_BLOCK;
    public static final DeferredBlock<GlassDrinkCupBlock> DRINK_CUP;
    public static final DeferredBlock<ToasterBlock> TOASTER;
    public static final DeferredBlock<Block> MOULD_CAKE;

    /**盐水*/
    public static final DeferredBlock<LiquidBlock> SALT_WATER_BLOCK;

    /**联动方块*/
    public static final DeferredBlock<Block> RICE_BREAD;
    public static final DeferredBlock<Block> SALMON_SANDWICH;

    static {
        /*面包方块*/
        BAGEL = REGISTER.register("bagel", com.renyigesai.bakeries.common.blocks.BreadBlock::new);
        WHOLE_WHEAT_BAGEL = REGISTER.register("whole_wheat_bagel", com.renyigesai.bakeries.common.blocks.BreadBlock::new);
        ROUND_BREAD = register("round_bread", com.renyigesai.bakeries.common.blocks.BreadBlock::new);
        BERRY_BREAD = register("berry_bread", com.renyigesai.bakeries.common.blocks.BreadBlock::new);
        CHEESE_CREAM_BREAD = register("cheese_cream_bread", com.renyigesai.bakeries.common.blocks.BreadBlock::new);
        BROWN_SUGAR_ROLL = register("brown_sugar_roll", com.renyigesai.bakeries.common.blocks.BreadBlock::new);
        PINEAPPLE_BUN = register("pineapple_bun", com.renyigesai.bakeries.common.blocks.BreadBlock::new);
        PINEAPPLE_OIL = register("pineapple_oil",com.renyigesai.bakeries.common.blocks.BreadBlock::new);
        MEAT_FLOSS_BREAD_ROLL = register("meat_floss_bread_roll", com.renyigesai.bakeries.common.blocks.BreadBlock::new);
        CROISSANT = register("croissant", com.renyigesai.bakeries.common.blocks.BreadBlock::new);
        DIRTY_CHOCO_CROISSANT = register("dirty_choco_croissant", com.renyigesai.bakeries.common.blocks.BreadBlock::new);
        SALT_CROISSANT = register("salt_croissant", com.renyigesai.bakeries.common.blocks.BreadBlock::new);
        CIABATTA = register("ciabatta",BreadBlock::new);
        FOCACCIA = register("focaccia",BreadBlock::new);
        BERRY_BAGEL = register("berry_bagel",BreadBlock::new);
        BAGEL_FILLED_SAUCE = register("bagel_filled_sauce",BreadBlock::new);
        BAGUETTE_WITH_FILLING = register("baguette_with_filling",BreadBlock::new);
        TOMATO_CHEESE_CROISSANT_SANDWICH = register("tomato_cheese_croissant_sandwich",BreadBlock::new);
        BAGUETTE = register("baguette", BreadBlock::new);
        COUNTRY_BREAD = register("country_bread",()-> new CountryBreadBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F,0.5F)));
        FLAT_CROISSANT = register("flat_croissant", BreadBlock::new);
        TOAST = register("toast", () -> new ToastBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F,0.5F), BakeriesItems.SLICED_TOAST));
        MOULD_TOAST = REGISTER.register("mould_toast", () ->
                new MouldToastBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(0.5F,0.5F),BakeriesItems.TOAST));
        CHEESE_COCOA_TOAST = register("cheese_cocoa_toast", () -> new ToastBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F,0.5F), BakeriesItems.SLICED_CHEESE_COCOA_TOAST));
        MOULD_CHEESE_COCOA_TOAST = REGISTER.register("mould_cheese_cocoa_toast", () ->
                new MouldToastBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(0.5F,0.5F),BakeriesItems.CHEESE_COCOA_TOAST));
        EGG_TART = register("egg_tart",BreadBlock::new);
        TARO_SALT_YOLK_BREAD = register("taro_salt_yolk_bread",BreadBlock::new);

        /*蛋糕*/
        CUSTOM_CAKE = REGISTER.register("custom_cake",()-> new CustomCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));

        CAKE_BASE = REGISTER.register("cake_base",()-> new CakeBaseBlock(5,0.4f));
        RED_VELVET_CAKE_BASE = REGISTER.register("red_velvet_cake_base",()-> new CakeBaseBlock(5,0.4f));
        MATCHA_CAKE = REGISTER.register("matcha_cake",()-> new CakeBaseBlock(5,0.4f));

        CREAM_CAKE = REGISTER.register("cream_cake",()-> new CreamCakeBlock(ItemUtils.addEffects(
                new LazyMobEffectInstance(MobEffects.REGENERATION,1200),
                new LazyMobEffectInstance(BakeriesMobEffects.SOFT,1200)),10,0.25f));

        TIRAMISU = REGISTER.register("tiramisu",()-> new CakeBaseBlock(
                ItemUtils.addEffects(
                        new LazyMobEffectInstance(BakeriesMobEffects.CHEESE_POWER,1200),
                        new LazyMobEffectInstance(BakeriesMobEffects.COCOA_MANIA,1200),
                        new LazyMobEffectInstance(MobEffects.NIGHT_VISION,1200)),
                5,0.4f));

        CARROT_CAKE = REGISTER.register("carrot_cake",()-> new CakeBaseBlock(
                ItemUtils.addEffects(
                        new LazyMobEffectInstance(MobEffects.NIGHT_VISION,1200)),
                10,0.4f));

        BASQUE_CAKE = REGISTER.register("basque_cake",()-> new CakeBaseBlock(ItemUtils.addEffects(
                new LazyMobEffectInstance(BakeriesMobEffects.CHEESE_POWER,1200)),
                6,0.4f));

        RED_VELVET_CAKE = REGISTER.register("red_velvet_cake",()-> new CakeBaseBlock(6,0.4f));

        /*饮料方块*/
        ICED_AMERICAN = drinkBlock("iced_american");
        ICED_LATTE = drinkBlock("iced_latte");
        BROWN_SUGAR_LATTE = drinkBlock("brown_sugar_latte");
        CREAM_BINGLE_COFFEE = drinkBlock("cream_bingle_coffee");
        MATCHA_LATTE = drinkBlock("matcha_latte");
        MATCHA_PARFAIT = REGISTER.register("matcha_parfait",MatchaParfaitBlock::new);
        TARO_MILK = drinkBlock("taro_milk");

        OLIVE_OIL = register("olive_oil",TanPieBlock::new);
        BEARNAISE = register("bearnaise",TanPieBlock::new);


        /*作物*/
        TOMATO = REGISTER.register("tomato",() ->
                new TomatoBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
        COFFEE_PLANT = REGISTER.register("coffee_plant",()->
                new CoffeePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
        TARO = REGISTER.register("taro",()->
                new TaroBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));

        SALT_ORE = REGISTER.register("salt_ore", () ->
                new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
        DEEPSLATE_SALT_ORE = REGISTER.register("deepslate_salt_ore", () ->
                new Block(BlockBehaviour.Properties.ofFullCopy(SALT_ORE.get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));

        /*发酵罐*/
        FERMENTATION_TANK = REGISTER.register("fermentation_tank",()->
                new FermentationTankBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).randomTicks()));
        YEAST_TANK = REGISTER.register("yeast_tank",()->
                new YeastTankBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));
        MILk_TANK = REGISTER.register("milk_tank",()->
                new MilkTankBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).randomTicks()));
        CHEESE_TANK = REGISTER.register("cheese_tank",()->
                new CheeseTankBkock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));

        /*面粉袋*/
        WHOLE_WHEAT_FLOUR_BAG = REGISTER.register("whole_wheat_flour_bag",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
        FLOUR_BAG = REGISTER.register("flour_bag",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
        MENU = REGISTER.register("menu",()-> new MenuBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

        BREAD_RACK = REGISTER.register("bread_rack",()-> new BreadRackBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
        GLASS_BREAD_RACK = REGISTER.register("glass_bread_rack",()-> new GlassBreadRackBlock(BlockBehaviour.Properties.ofFullCopy(BREAD_RACK.get())));
        BLACK_WHITE_CONCRETE = REGISTER.register("black_white_concrete",() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE)));

        WOOD_COUNTER = REGISTER.register("wood_counter",()-> new WoodCounterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
        COFFEE_TABLE = REGISTER.register("coffee_table",()-> new CoffeeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
        WOOD_TRAY = REGISTER.register("wood_tray",()-> new WoodenTrayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
        GLASS_CABINET_DOOR = REGISTER.register("glass_cabinet_door", () ->
                new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));

        /*沙发*/
        SOFA_WHITE = REGISTER.register("sofa_white",()-> new SofaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), SofaBlock.Color.WHITE));
        SOFA_RED = REGISTER.register("sofa_red",()-> new SofaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), SofaBlock.Color.RED));
        SOFA_LIGHT_GRAY = REGISTER.register("sofa_light_gray",()-> new SofaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), SofaBlock.Color.LIGHT_GRAY));


        CASH_REGISTER_COMPUTER = REGISTER.register("cash_register_computer",()-> new CashRegisterComputerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(1.5F, 6.0F).lightLevel(litBlockEmission(10))));

        MOULD = register("mould", () -> new MouldBlock(BakeriesItems.MOULD));
        MOULD_TWO = register("mould_two", () -> new MouldBlock(BakeriesItems.MOULD_TWO));
        RAW_SALT_BLOCK = REGISTER.register("raw_salt_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
        BLENDER = REGISTER.register("blender",()-> new BlenderBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5F, 3.5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
        OVEN = registerBlock("oven", OvenBlock::new, BlockBehaviour.Properties.of().strength(3.5F));
        CUPBOARD = REGISTER.register("cupboard", ()-> new CupboardBlock(BlockBehaviour.Properties.of().strength(2.0F,3.0F).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_GRAY).sound(SoundType.CHISELED_BOOKSHELF)));
        DOUGH_CRAFTING_TABLE = REGISTER.register("dough_crafting_table", DoughCraftingTableBlock::new);
        BREAD_BASKET = REGISTER.register("bread_basket",()-> new BreadBasketBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(0.0F,0.0F)));
        MOKA_POT = REGISTER.register("moka_pot",()-> new MokaPotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
        MIX_BLOCK = REGISTER.register("mix_block",()->  new MixBlock());
        DRINK_CUP = REGISTER.register("drink_cup",()-> new GlassDrinkCupBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.1F,0.1F)));
        TOASTER = REGISTER.register("toaster",()-> new ToasterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
        MOULD_CAKE = REGISTER.register("mould_cake",()-> new MouldCakeBlock(BlockBehaviour.Properties.ofFullCopy(MOULD_TWO.get())));

        SALT_WATER_BLOCK = REGISTER.register("salt_water_block", SaltWaterFluidsBlock::new);

        FERMENTATION_BOX = REGISTER.register("fermentation_box",()-> new FermentationBoxBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5F, 3.5F).requiresCorrectToolForDrops()
                .sound(SoundType.NETHERITE_BLOCK).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
        LUMINOUS_LIGHT_SIGN = REGISTER.register("luminous_light_sign", LuminousLightSignBlock::new);
        BREAD_HOLDERS = REGISTER.register("bread_holders",()->new BreadHoldersBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

        SILICONE_PAPER = REGISTER.register("silicone_paper",()-> new SiliconePaperBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).strength(0.1f)));


        /**联动方块*/
        RICE_BREAD = register("rice_bread", BreadBlock::new);
        SALMON_SANDWICH = register("salmon_sandwich", BreadBlock::new);
    }


    private static<B extends Block> DeferredBlock<B> registerBlock(String name, Function<BlockBehaviour.Properties, B> func, BlockBehaviour.Properties props) {
        return REGISTER.registerBlock(name, func, props);
    }
    private static DeferredBlock<Block> drinkBlock(String name){
        return REGISTER.register(name,()->
                new DrinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.1F,0.1F)));
    }
    private static<B extends Block> DeferredBlock<B> registerBlock(String name, Function<BlockBehaviour.Properties, B> func) {
        return REGISTER.registerBlock(name, func);
    }
    private static<B extends Block> DeferredBlock<B> register(String name, Supplier<B> block) {
        return REGISTER.register(name, block);
    }
    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return state -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    public static class Entities {
        public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BakeriesMod.MODID);
        public static final Supplier<BlockEntityType<OvenBlockEntity>> OVEN_BLOCK_ENTITY = REGISTER.register("oven", () -> BlockEntityType.Builder.of(OvenBlockEntity::new, OVEN.get()).build(null));
        public static final Supplier<BlockEntityType<BlenderBlockEntity>> BLENDER_ENTITY = REGISTER.register("blender", () -> BlockEntityType.Builder.of(BlenderBlockEntity::new, BLENDER.get()).build(null));
        public static final Supplier<BlockEntityType<CupboardBlockEntity>> CUPBOARD_ENTITY = REGISTER.register("cupboard", () -> BlockEntityType.Builder.of(CupboardBlockEntity::new, CUPBOARD.get()).build(null));
        public static final Supplier<BlockEntityType<DoughCraftingTableBlockEntity>> DOUGH_CRAFTING_TABLE_ENTITY = REGISTER.register("dough_crafting_table", () -> BlockEntityType.Builder.of(DoughCraftingTableBlockEntity::new, DOUGH_CRAFTING_TABLE.get()).build(null));
        public static final Supplier<BlockEntityType<BreadBasketBlockEntity>> BREAD_BASKET_ENTITY = REGISTER.register("bread_basket", () -> BlockEntityType.Builder.of(BreadBasketBlockEntity::new, BREAD_BASKET.get()).build(null));
        public static final Supplier<BlockEntityType<MokaPotBlockEntity>> MOKA_POT_ENTITY = REGISTER.register("moka_pot", () -> BlockEntityType.Builder.of(MokaPotBlockEntity::new, MOKA_POT.get()).build(null));
        public static final Supplier<BlockEntityType<MixBlockEntity>> MIX_BLOCK_ENTITY = REGISTER.register("mix_block", () -> BlockEntityType.Builder.of(MixBlockEntity::new, MIX_BLOCK.get()).build(null));
        public static final Supplier<BlockEntityType<GlassDrinkCupBlockEntity>> DRINK_CUP_ENTITY = REGISTER.register("drink_cup", () -> BlockEntityType.Builder.of(GlassDrinkCupBlockEntity::new, DRINK_CUP.get()).build(null));
        public static final Supplier<BlockEntityType<ToasterBlockEntity>> TOASTER_ENTITY = REGISTER.register("toaster", () -> BlockEntityType.Builder.of(ToasterBlockEntity::new, TOASTER.get()).build(null));
        public static final Supplier<BlockEntityType<MenuBlockEntity>> MENU_ENTITY = REGISTER.register("menu", () -> BlockEntityType.Builder.of(MenuBlockEntity::new, MENU.get()).build(null));
        public static final Supplier<BlockEntityType<BreadRackBlockEntity>> BREAD_RACK_ENTITY = REGISTER.register("bread_rack", () -> BlockEntityType.Builder.of(BreadRackBlockEntity::new, BREAD_RACK.get(),GLASS_BREAD_RACK.get()).build(null));
        public static final Supplier<BlockEntityType<FermentationBoxBlockEntity>> FERMENTATION_BOX_ENTITY = REGISTER.register("fermentation_box", () -> BlockEntityType.Builder.of(FermentationBoxBlockEntity::new, FERMENTATION_BOX.get()).build(null));
        public static Supplier<BlockEntityType<LuminousLightSignBlockEntity>> LUMINOUS_LIGHT_SIGN_ENTITY = REGISTER.register("luminous_light_sign_", () -> BlockEntityType.Builder.of(LuminousLightSignBlockEntity::new, LUMINOUS_LIGHT_SIGN.get()).build(null));;
        public static Supplier<BlockEntityType<CustomCakeBlockEntity>> CUSTOM_CAKE_ENTITY = REGISTER.register("custom_cake", () -> BlockEntityType.Builder.of(CustomCakeBlockEntity::new, CUSTOM_CAKE.get()).build(null));;
        public static Supplier<BlockEntityType<MouldCakeBlockEntity>> MOULD_CAKE_ENTITY = REGISTER.register("mould_cake", () -> BlockEntityType.Builder.of(MouldCakeBlockEntity::new, MOULD_CAKE.get()).build(null));;
    }
}
