package com.renyigesai.bakery.block;

import com.renyigesai.bakery.bakery;
import com.renyigesai.bakery.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, bakery.MODID);

    //Bread Block

    public static final RegistryObject<Block> BAGEL_BLOCK = registerBlock("bagel_block",() ->
        new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> BAGUETTE_BLOCK = registerBlock("baguette_block",() ->
            new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> CINNAMON_ROLL_BLOCK = registerBlock("cinnamon_roll_block",() ->
            new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> COUNTRY_BREAD_BLOCK = registerBlock("country_bread_block",() ->
            new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> CROISSANT_BLOCK = registerBlock("croissant_block",() ->
            new PileBlock(BlockBehaviour.Properties.of().strength(0.1f,0.1f).sound(SoundType.WOOL)));

    //Block
    public static final RegistryObject<Block> WOOD_COUNTER = registerBlock("wood_counter",() ->
            new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));




    private static <T extends Block> RegistryObject<T> registerBlock(String nmae, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(nmae,block);
        registryBlockItem(nmae,toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registryBlockItem (String name,RegistryObject<T>block){
        return ModItems.ITEMS.register(name,() -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
}
}
