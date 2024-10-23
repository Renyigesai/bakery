package com.renyigesai.bakery.init;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.api.block.PileBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeryBlocks {

    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, BakeryMod.MODID);
    public static final RegistryObject<Block> BAGEL_BLOCK;
    public static final RegistryObject<Block> BAGUETTE_BLOCK;
    public static final RegistryObject<Block> CINNAMON_ROLL_BLOCK;
    public static final RegistryObject<Block> COUNTRY_BREAD_BLOCK;
    public static final RegistryObject<Block> CROISSANT_BLOCK;
    static {
        BAGEL_BLOCK = REGISTER.register("bagel", PileBlock::new);
        BAGUETTE_BLOCK = REGISTER.register("baguette", PileBlock::new);
        CINNAMON_ROLL_BLOCK = REGISTER.register("cinnamon_roll", PileBlock::new);
        COUNTRY_BREAD_BLOCK = REGISTER.register("country_bread", PileBlock::new);
        CROISSANT_BLOCK = REGISTER.register("croissant", PileBlock::new);
    }
}
