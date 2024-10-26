package com.renyigesai.bakery.init;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.api.block.PileBlock;
import com.renyigesai.bakery.block.oven.OvenBlock;
import com.renyigesai.bakery.block.oven.OvenBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeryBlocks {

    public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, BakeryMod.MODID);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BakeryMod.MODID);

    public static final RegistryObject<Block> BAGEL_BLOCK;
    public static final RegistryObject<Block> BAGUETTE_BLOCK;
    public static final RegistryObject<Block> CINNAMON_ROLL_BLOCK;
    public static final RegistryObject<Block> COUNTRY_BREAD_BLOCK;
    public static final RegistryObject<Block> CROISSANT_BLOCK;
    public static final RegistryObject<Block> OVEN;
    public static final RegistryObject<BlockEntityType<OvenBlockEntity>> OVEN_BLOCK_ENTITY;
    static {
        BAGEL_BLOCK = BLOCK_REGISTRY.register("bagel", PileBlock::new);
        BAGUETTE_BLOCK = BLOCK_REGISTRY.register("baguette", PileBlock::new);
        CINNAMON_ROLL_BLOCK = BLOCK_REGISTRY.register("cinnamon_roll", PileBlock::new);
        COUNTRY_BREAD_BLOCK = BLOCK_REGISTRY.register("country_bread", PileBlock::new);
        CROISSANT_BLOCK = BLOCK_REGISTRY.register("croissant", PileBlock::new);
        OVEN = BLOCK_REGISTRY.register("oven", () -> new OvenBlock(BlockBehaviour.Properties.of()));
        OVEN_BLOCK_ENTITY = BLOCK_ENTITY_REGISTRY.register("oven", () -> BlockEntityType.Builder.of(OvenBlockEntity::new, OVEN.get()).build(null));
    }
}
