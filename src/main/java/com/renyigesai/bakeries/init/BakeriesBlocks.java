package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.block.*;
import com.renyigesai.bakeries.block.dough_crafting_table.DoughCraftingTableBlock;
import com.renyigesai.bakeries.block.oven.OvenBlock;
import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import com.renyigesai.bakeries.fluid.SaltWaterFluidsBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
    public static final RegistryObject<Block> BERRY_BREAD;
    //common
    public static final RegistryObject<Block> OVEN;
    public static final RegistryObject<BlockEntityType<OvenBlockEntity>> OVEN_BLOCK_ENTITY;
    public static final RegistryObject<Block> FERMENTATION_TANK;
    public static final RegistryObject<Block> YEAST_TANK;
    public static final RegistryObject<Block> CHEESE_TANK;
    public static final RegistryObject<Block> Milk_TANK;
    public static final RegistryObject<Block> GLASS_CABINET_DOOR;
    public static final RegistryObject<Block> SALT_ORE;
    public static final RegistryObject<Block> DOUGH_CRAFTING_TABLE;
    public static final RegistryObject<LiquidBlock> SALT_WATER_BLOCK;
    public static final RegistryObject<Block> MOULD_TOAST;
    public static final RegistryObject<Block> RAW_SALT_BLOCK;
    public static final RegistryObject<Block> WOOD_COUNTER;


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
        BERRY_BREAD = BLOCK_REGISTRY.register("berry_bread", PileBlock::new);
        //common
        OVEN = BLOCK_REGISTRY.register("oven", OvenBlock::new);
        DOUGH_CRAFTING_TABLE = BLOCK_REGISTRY.register("dough_crafting_table", DoughCraftingTableBlock::new);
        FERMENTATION_TANK = BLOCK_REGISTRY.register("fermentation_tank", () ->
                new FermentationTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).randomTicks()));
        YEAST_TANK = BLOCK_REGISTRY.register("yeast_tank", () ->
                new YeastTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        CHEESE_TANK = BLOCK_REGISTRY.register("cheese_tank", () ->
                new CheeseTankBkock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        Milk_TANK = BLOCK_REGISTRY.register("milk_tank", () ->
                new MilkTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        GLASS_CABINET_DOOR = BLOCK_REGISTRY.register("glass_cabinet_door", () ->
                new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops(), BlockSetType.OAK));
        SALT_ORE = BLOCK_REGISTRY.register("salt_ore", () ->
                new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
//        SALT_WATER_BLOCK = BLOCK_REGISTRY.register("salt_water_block", () ->
//                new LiquidBlock(BakeriesFluids.SALT_WATER,BlockBehaviour.Properties.copy(Blocks.WATER)));
        SALT_WATER_BLOCK = BLOCK_REGISTRY.register("salt_water_block", SaltWaterFluidsBlock::new);
        MOULD_TOAST = BLOCK_REGISTRY.register("mould_toast", () ->
                new MouldBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(0.5F,0.5F),BakeriesItems.TOAST));
        RAW_SALT_BLOCK = BLOCK_REGISTRY.register("raw_salt_block", () ->
                new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
        WOOD_COUNTER = BLOCK_REGISTRY.register("wood_counter", () ->
                new WoodCounterBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

        //BlockEntity
        OVEN_BLOCK_ENTITY = BLOCK_ENTITY_REGISTRY.register("oven", () -> BlockEntityType.Builder.of(OvenBlockEntity::new, OVEN.get()).build(null));
    }

}

