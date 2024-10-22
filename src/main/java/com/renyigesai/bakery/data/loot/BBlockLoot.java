package com.renyigesai.bakery.data.loot;


import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.init.BakeryBlocks;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

public class BBlockLoot extends VanillaBlockLoot {

    @Override
    protected void generate() {
        this.dropSelf(BakeryBlocks.BAGEL_BLOCK.get());
        this.dropSelf(BakeryBlocks.BAGUETTE_BLOCK.get());
        this.dropSelf(BakeryBlocks.CINNAMON_ROLL_BLOCK.get());
        this.dropSelf(BakeryBlocks.COUNTRY_BREAD_BLOCK.get());
        this.dropSelf(BakeryBlocks.CROISSANT_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(BakeryMod.MODID)).collect(Collectors.toList());
    }

}
