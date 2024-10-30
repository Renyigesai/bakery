package com.renyigesai.bakery.data.loot;


import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.api.block.PileBlock;
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
        this.add(BakeryBlocks.BAGEL.get(), this::createPileDrops);
        this.add(BakeryBlocks.BAGUETTE.get(), this::createPileDrops);
        this.add(BakeryBlocks.CINNAMON_ROLL.get(), this::createPileDrops);
        this.add(BakeryBlocks.COUNTRY_BREAD.get(), this::createPileDrops);
        this.add(BakeryBlocks.CROISSANT.get(), this::createPileDrops);
        this.dropSelf(BakeryBlocks.OVEN.get());
        this.dropSelf(BakeryBlocks.FERMENTATION_TANK.get());
        this.dropSelf(BakeryBlocks.YEAST_TANK.get());
        this.dropSelf(BakeryBlocks.CHEESE_TANK.get());
        this.dropSelf(BakeryBlocks.GLASS_CABINET_DOOR.get());
        this.dropSelf(BakeryBlocks.PINEAPPLE_BUN.get());
        this.dropSelf(BakeryBlocks.ROUND_BREAD.get());
        this.dropSelf(BakeryBlocks.SALT_CROISSANT.get());
        this.dropSelf(BakeryBlocks.SALT_ORE.get());
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
