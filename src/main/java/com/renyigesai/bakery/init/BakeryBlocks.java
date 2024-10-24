package com.renyigesai.bakery.init;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.block.PileBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BakeryBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BakeryMod.MODID);

    //Bread Block

    public static final RegistryObject<Block> BAGEL_BLOCK = registerBlock("bagel",() ->
        new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> BAGUETTE_BLOCK = registerBlock("baguette",() ->
            new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> CINNAMON_ROLL_BLOCK = registerBlock("cinnamon_roll",() ->
            new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> COUNTRY_BREAD_BLOCK = registerBlock("country_bread",() ->
            new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> CROISSANT_BLOCK = registerBlock("croissant",() ->
            new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> PINEAPPLE_BUN_BLOCK = registerBlock("pineapple_bun",() ->
            new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> ROUND_BREAD_BLOCK = registerBlock("round_bread",() ->
            new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> SALT_CROISSANTt_BLOCK = registerBlock("salt_croissant",() ->
            new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    //Block
    public static final RegistryObject<Block> WOOD_COUNTER = registerBlock("wood_counter",() ->
            new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    private static <T extends Block> RegistryObject<T> registerBlock(String nmae, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(nmae,block);
        return toReturn;
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
}
}
