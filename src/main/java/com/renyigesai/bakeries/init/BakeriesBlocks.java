package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.block.*;
import com.renyigesai.bakeries.block.baysalt_frame.BaysaltFrameBlock;
import com.renyigesai.bakeries.block.baysalt_frame.BaysaltFrameBlockEntity;
import com.renyigesai.bakeries.block.bread_basket.BreadBasketBlock;
import com.renyigesai.bakeries.block.bread_basket.BreadBasketBlockEntity;
import com.renyigesai.bakeries.block.cupboard.CupboardBlock;
import com.renyigesai.bakeries.block.cupboard.CupboardBlockEntity;
import com.renyigesai.bakeries.block.dough_crafting_table.DoughCraftingTableBlock;
import com.renyigesai.bakeries.block.dough_crafting_table.DoughCraftingTableBlockEntity;
import com.renyigesai.bakeries.block.oven.OvenBlock;
import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import com.renyigesai.bakeries.block.toaster.ToasterBlock;
import com.renyigesai.bakeries.block.toaster.ToasterBlockEntity;
import com.renyigesai.bakeries.fluid.SaltWaterFluidsBlock;
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

import java.util.function.ToIntFunction;

public class BakeriesBlocks {

    public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, BakeriesMod.MODID);
//    public static final DeferredRegister<LiquidBlock> FLUID_BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS,BakeryMod.MODID)

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BakeriesMod.MODID);
    //Bread Pile Block
    public static final RegistryObject<Block> BAGEL;
    public static final RegistryObject<Block> WHOLE_WHEAT_BAGEL;
    public static final RegistryObject<Block> BAGUETTE;
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
    public static final RegistryObject<Block> PIZZA;
    public static final RegistryObject<Block> SAUSAGE_PIZZA;
    public static final RegistryObject<Block> MEAT_PASTE_PIZZA;
    //common
    public static final RegistryObject<Block> OVEN;
    public static final RegistryObject<BlockEntityType<OvenBlockEntity>> OVEN_BLOCK_ENTITY;
    public static final RegistryObject<Block> FERMENTATION_TANK;
    public static final RegistryObject<Block> YEAST_TANK;
    public static final RegistryObject<Block> CHEESE_TANK;
    public static final RegistryObject<Block> Milk_TANK;
    public static final RegistryObject<Block> GLASS_CABINET_DOOR;
    public static final RegistryObject<Block> SALT_ORE;
    public static final RegistryObject<Block> DEEPSLATE_SALT_ORE;
    public static final RegistryObject<Block> DOUGH_CRAFTING_TABLE;
    public static final RegistryObject<LiquidBlock> SALT_WATER_BLOCK;
    public static final RegistryObject<Block> BREAD_HOLDERS;
    public static final RegistryObject<Block> MOULD;
    public static final RegistryObject<Block> MOULD_TOAST;
    public static final RegistryObject<Block> MOULD_CHEESE_COCOA_TOAST;
    public static final RegistryObject<Block> RAW_SALT_BLOCK;
    public static final RegistryObject<Block> WOOD_COUNTER;
    public static final RegistryObject<Block> TOMATO;
    public static final RegistryObject<Block> BLACK_WHITE_CONCRETE ;
    public static final RegistryObject<Block> CUPBOARD;

    public static final RegistryObject<Block> BREAD_BASKET ;
    public static final RegistryObject<Block> TOASTER;
//    public static final RegistryObject<Block> PIZZA_DOUGH;
    public static final RegistryObject<BlockEntityType<CupboardBlockEntity>> CUPBOARD_ENTITY;
    public static final RegistryObject<BlockEntityType<BreadBasketBlockEntity>> BREAD_BASKET_BLOCK_ENTITY ;
    public static final RegistryObject<BlockEntityType<ToasterBlockEntity>> TOASTER_ENTITY;
//    public static final RegistryObject<BlockEntityType<PizzaDoughBlockEntity>> PIZZA_DOUGH_ENTITY;
    public static final RegistryObject<Block> BAYSALT_FRAME;
    public static final RegistryObject<BlockEntityType<BaysaltFrameBlockEntity>> BAYSALT_FRAME_ENTITY;
    public static final RegistryObject<BlockEntityType<DoughCraftingTableBlockEntity>> DOUGH_CRAFTING_TABLE_ENTITY;

    static {
        //Bread Block
        BAGEL = BLOCK_REGISTRY.register("bagel", PileBlock::new);
        WHOLE_WHEAT_BAGEL = BLOCK_REGISTRY.register("whole_wheat_bagel", PileBlock::new);
        BAGUETTE = BLOCK_REGISTRY.register("baguette", PileBlock::new);
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
        PIZZA = BLOCK_REGISTRY.register("pizza",()->
                new PizzaBlock(BlockBehaviour.Properties.copy(Blocks.CAKE),2,0.1F));
        SAUSAGE_PIZZA = BLOCK_REGISTRY.register("sausage_pizza",()->
                new PizzaBlock(BlockBehaviour.Properties.copy(Blocks.CAKE),2,0.1F));
        MEAT_PASTE_PIZZA = BLOCK_REGISTRY.register("meat_paste_pizza",()->
                new PizzaBlock(BlockBehaviour.Properties.copy(Blocks.CAKE),2,0.1F));
        //common
        OVEN = BLOCK_REGISTRY.register("oven", ()->new OvenBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5F, 3.5F).requiresCorrectToolForDrops()
                .sound(SoundType.METAL).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
        DOUGH_CRAFTING_TABLE = BLOCK_REGISTRY.register("dough_crafting_table", DoughCraftingTableBlock::new);
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
        SALT_ORE = BLOCK_REGISTRY.register("salt_ore", () ->
                new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
        DEEPSLATE_SALT_ORE = BLOCK_REGISTRY.register("deepslate_salt_ore", () ->
                new DropExperienceBlock(BlockBehaviour.Properties.copy(SALT_ORE.get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
        SALT_WATER_BLOCK = BLOCK_REGISTRY.register("salt_water_block", SaltWaterFluidsBlock::new);
        MOULD = BLOCK_REGISTRY.register("mould", () ->
                new MouldBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(0.5F,0.5F)));
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
        CUPBOARD = BLOCK_REGISTRY.register("cupboard",() ->
                new CupboardBlock(BlockBehaviour.Properties.of().strength(2.0F,3.0F).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_GRAY).sound(SoundType.CHISELED_BOOKSHELF)));
        BREAD_BASKET = BLOCK_REGISTRY.register("bread_basket", () ->
                new BreadBasketBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS).strength(0.0F,0.0F)));
        TOASTER = BLOCK_REGISTRY.register("toaster", () ->
                new ToasterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
//        PIZZA_DOUGH = BLOCK_REGISTRY.register("pizza_dough", () ->
//                new PizzaDoughBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)));
        BAYSALT_FRAME = BLOCK_REGISTRY.register("baysalt_frame", BaysaltFrameBlock::new);
        BREAD_HOLDERS = BLOCK_REGISTRY.register("bread_holders",()->new BreadHoldersBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

        //BlockEntity
        OVEN_BLOCK_ENTITY = BLOCK_ENTITY_REGISTRY.register("oven", () -> BlockEntityType.Builder.of(OvenBlockEntity::new, OVEN.get()).build(null));
        CUPBOARD_ENTITY = BLOCK_ENTITY_REGISTRY.register("cupboard", () ->BlockEntityType.Builder.of(CupboardBlockEntity::new,CUPBOARD.get()).build(null));
        BREAD_BASKET_BLOCK_ENTITY = BLOCK_ENTITY_REGISTRY.register("bread_basket", () -> BlockEntityType.Builder.of(BreadBasketBlockEntity::new, BREAD_BASKET.get()).build(null));
        TOASTER_ENTITY = BLOCK_ENTITY_REGISTRY.register("toaster", () -> BlockEntityType.Builder.of(ToasterBlockEntity::new, TOASTER.get()).build(null));
//        PIZZA_DOUGH_ENTITY = BLOCK_ENTITY_REGISTRY.register("pizza_dough", () -> BlockEntityType.Builder.of(PizzaDoughBlockEntity::new, PIZZA_DOUGH.get()).build(null));
        BAYSALT_FRAME_ENTITY = BLOCK_ENTITY_REGISTRY.register("baysalt_frame", () -> BlockEntityType.Builder.of(BaysaltFrameBlockEntity::new, BAYSALT_FRAME.get()).build(null));
        DOUGH_CRAFTING_TABLE_ENTITY = BLOCK_ENTITY_REGISTRY.register("dough_crafting_table", () -> BlockEntityType.Builder.of(DoughCraftingTableBlockEntity::new, BAYSALT_FRAME.get()).build(null));
    }

}

