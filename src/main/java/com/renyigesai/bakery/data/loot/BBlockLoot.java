package com.renyigesai.bakery.data.loot;


import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.block.PileBlock;
import com.renyigesai.bakery.init.BakeryBlocks;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

public class BBlockLoot extends VanillaBlockLoot {

    @Override
    protected void generate() {
        this.add(BakeryBlocks.BAGEL_BLOCK.get(), this::createPileDrops);
        this.add(BakeryBlocks.BAGUETTE_BLOCK.get(), this::createPileDrops);
        this.add(BakeryBlocks.CINNAMON_ROLL_BLOCK.get(), this::createPileDrops);
        this.add(BakeryBlocks.COUNTRY_BREAD_BLOCK.get(), this::createPileDrops);
        this.add(BakeryBlocks.CROISSANT_BLOCK.get(), this::createPileDrops);
    }
    protected LootTable.Builder createPileDrops(Block pPileBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(pPileBlock, LootItem.lootTableItem(pPileBlock).apply(List.of(2, 3, 4), (p_249985_) -> {
            return SetItemCountFunction.setCount(ConstantValue.exactly((float)p_249985_.intValue())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pPileBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PileBlock.PILE, p_249985_)));
        }))));
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(BakeryMod.MODID)).collect(Collectors.toList());
    }

}
