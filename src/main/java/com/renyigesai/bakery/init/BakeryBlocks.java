package com.renyigesai.bakery.init;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.api.block.PileBlock;
import com.renyigesai.bakery.block.*;
import com.renyigesai.bakery.block.dough_crafting_table.DoughCraftingTableBlockEntity;
import com.renyigesai.bakery.block.oven.OvenBlock;
import com.renyigesai.bakery.block.oven.OvenBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeryBlocks {

    public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, BakeryMod.MODID);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BakeryMod.MODID);
    //Bread Pile Block
    public static final RegistryObject<Block> BAGEL;
    public static final RegistryObject<Block> WHOLE_WHEAT_BAGEL;
    public static final RegistryObject<Block> BAGUETTE;
    public static final RegistryObject<Block> CINNAMON_ROLL;
    public static final RegistryObject<Block> COUNTRY_BREAD;
    public static final RegistryObject<Block> CROISSANT;
    public static final RegistryObject<Block> PINEAPPLE_BUN;
    public static final RegistryObject<Block> ROUND_BREAD;
    public static final RegistryObject<Block> SALT_CROISSANT;
    public static final RegistryObject<Block> TOAST;
    //common
    public static final RegistryObject<Block> OVEN;
    public static final RegistryObject<BlockEntityType<OvenBlockEntity>> OVEN_BLOCK_ENTITY;
    public static final RegistryObject<Block> FERMENTATION_TANK;
    public static final RegistryObject<Block> YEAST_TANK;
    public static final RegistryObject<Block> CHEESE_TANK;
    public static final RegistryObject<Block> GLASS_CABINET_DOOR;
    public static final RegistryObject<Block> SALT_ORE;
    public static final RegistryObject<Block> DOUGH_CRAFTING_TABLE;
    public static final RegistryObject<BlockEntityType<DoughCraftingTableBlockEntity>> DOUGH_CRAFTING_TABLE_BLOCK_ENTITY;
    public static final RegistryObject<Block> SWEET_DOUGH_KNEAD;
    public static final RegistryObject<Block> WHOLE_WHEAT_DOUGH_KNEAD;
    public static final RegistryObject<Block> SALTED_DOUGH_KNEAD;

    static {
        //Bread Block
        BAGEL = BLOCK_REGISTRY.register("bagel", PileBlock::new);
        WHOLE_WHEAT_BAGEL = BLOCK_REGISTRY.register("whole_wheat_bagel", PileBlock::new);
        BAGUETTE = BLOCK_REGISTRY.register("baguette", PileBlock::new);
        CINNAMON_ROLL = BLOCK_REGISTRY.register("cinnamon_roll", PileBlock::new);
        COUNTRY_BREAD = BLOCK_REGISTRY.register("country_bread", PileBlock::new);
        CROISSANT = BLOCK_REGISTRY.register("croissant", PileBlock::new);
        PINEAPPLE_BUN = BLOCK_REGISTRY.register("pineapple_bun", PileBlock::new);
        ROUND_BREAD = BLOCK_REGISTRY.register("round_bread", PileBlock::new);
        SALT_CROISSANT = BLOCK_REGISTRY.register("salt_croissant", PileBlock::new);
        TOAST = BLOCK_REGISTRY.register("toast", () ->
                new ToastBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F,0.5F),BakeryItems.SLICED_TOAST));
        //common
        OVEN = BLOCK_REGISTRY.register("oven", OvenBlock::new);
        DOUGH_CRAFTING_TABLE = BLOCK_REGISTRY.register("dough_crafting_table", () ->
                new Block(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));
        FERMENTATION_TANK = BLOCK_REGISTRY.register("fermentation_tank", () ->
                new FermentationTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).randomTicks()));
        YEAST_TANK = BLOCK_REGISTRY.register("yeast_tank", () ->
                new YeastTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        CHEESE_TANK = BLOCK_REGISTRY.register("cheese_tank", () ->
                new CheeseTank(BlockBehaviour.Properties.copy(Blocks.GLASS)));
        GLASS_CABINET_DOOR = BLOCK_REGISTRY.register("glass_cabinet_door", () ->
                new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.GLASS), BlockSetType.OAK));
        SALT_ORE = BLOCK_REGISTRY.register("salt_ore", () ->
                new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
        SWEET_DOUGH_KNEAD = BLOCK_REGISTRY.register("sweet_dough_knead", () ->
                new DoughBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F,0.5F),BakeryItems.SWEET_DOUGH));
        WHOLE_WHEAT_DOUGH_KNEAD = BLOCK_REGISTRY.register("whole_wheat_dough_knead", () ->
                new DoughBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F,0.5F),BakeryItems.WHOLE_WHEAT_DOUGH));
        SALTED_DOUGH_KNEAD = BLOCK_REGISTRY.register("salted_dough_knead", () ->
                new SaltedDough(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.5F,0.5F)));

        //BlockEntity
        OVEN_BLOCK_ENTITY = BLOCK_ENTITY_REGISTRY.register("oven", () -> BlockEntityType.Builder.of(OvenBlockEntity::new, OVEN.get()).build(null));
        DOUGH_CRAFTING_TABLE_BLOCK_ENTITY = BLOCK_ENTITY_REGISTRY.register("dough_crafting_table", () -> BlockEntityType.Builder.of(DoughCraftingTableBlockEntity::new, DOUGH_CRAFTING_TABLE.get()).build(null));
    }

}

