package com.renyigesai.bakeries.data.loot;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.init.BakeriesBlocks;
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
        this.add(BakeriesBlocks.BAGEL.get(), this::createPileDrops);
        this.add(BakeriesBlocks.BAGUETTE.get(), this::createPileDrops);
        this.add(BakeriesBlocks.BROWN_SUGAR_ROLL.get(), this::createPileDrops);
        this.add(BakeriesBlocks.COUNTRY_BREAD.get(), this::createPileDrops);
        this.add(BakeriesBlocks.CROISSANT.get(), this::createPileDrops);
        this.dropSelf(BakeriesBlocks.OVEN.get());
        this.dropSelf(BakeriesBlocks.FERMENTATION_TANK.get());
        this.dropSelf(BakeriesBlocks.YEAST_TANK.get());
        this.dropSelf(BakeriesBlocks.CHEESE_TANK.get());
        this.dropSelf(BakeriesBlocks.GLASS_CABINET_DOOR.get());
        this.dropSelf(BakeriesBlocks.PINEAPPLE_BUN.get());
        this.dropSelf(BakeriesBlocks.ROUND_BREAD.get());
        this.dropSelf(BakeriesBlocks.SALT_CROISSANT.get());
        this.dropSelf(BakeriesBlocks.SALT_ORE.get());
    }
    protected LootTable.Builder createPileDrops(Block pPileBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(pPileBlock, LootItem.lootTableItem(pPileBlock).apply(List.of(2, 3, 4), (p_249985_) -> {
            return SetItemCountFunction.setCount(ConstantValue.exactly((float)p_249985_.intValue())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pPileBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PileBlock.PILE, p_249985_)));
        }))));
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(BakeriesMod.MODID)).collect(Collectors.toList());
    }

}
