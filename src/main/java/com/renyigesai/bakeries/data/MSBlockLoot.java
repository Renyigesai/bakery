package com.renyigesai.bakeries.data;


import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.weibai.rcglib.blocks.BreadBlock;

import java.util.List;
import java.util.stream.Collectors;

public class MSBlockLoot extends VanillaBlockLoot {
    public MSBlockLoot(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    protected void generate() {
//        this.dropSelf(MSBlocks.TINY_ANDESITE_BLOCK.get());
//        this.dropOther(MSBlocks.POWDER_SNOW_ACACIA_LOG_CAULDRON.get(), MSBlocks.ACACIA_LOG_CAULDRON.get());
        this.forAddAllBread(
                BakeriesBlocks.BAGEL.get(),
                BakeriesBlocks.WHOLE_WHEAT_BAGEL.get(),
                BakeriesBlocks.ROUND_BREAD.get(),
                BakeriesBlocks.BERRY_BREAD.get(),
                BakeriesBlocks.CHEESE_CREAM_BREAD.get(),
                BakeriesBlocks.BROWN_SUGAR_ROLL.get(),
                BakeriesBlocks.PINEAPPLE_BUN.get(),
                BakeriesBlocks.MEAT_FLOSS_BREAD_ROLL.get(),
                BakeriesBlocks.CROISSANT.get(),
                BakeriesBlocks.DIRTY_CHOCO_CROISSANT.get(),
                BakeriesBlocks.SALT_CROISSANT.get(),
                BakeriesBlocks.CIABATTA.get(),
                BakeriesBlocks.FOCACCIA.get(),
                BakeriesBlocks.BERRY_BAGEL.get(),
                BakeriesBlocks.BAGEL_FILLED_SAUCE.get(),
                BakeriesBlocks.BAGUETTE_WITH_FILLING.get(),
                BakeriesBlocks.TOMATO_CHEESE_CROISSANT_SANDWICH.get(),
                BakeriesBlocks.BAGUETTE.get()
        );

        this.dropSelf(BakeriesBlocks.OVEN.get());
        this.dropSelf(BakeriesBlocks.BLENDER.get());
        this.dropSelf(BakeriesBlocks.TOAST.get());//占位
    }

    private void forAddAllBread(Block... blocks) {
        List<Block> blockList = List.of(blocks);
        for (Block block : blockList) {
            this.add(block, blockIn -> this.createStateDrops(block, BreadBlock.PILE_4));
        }
    }

    protected LootTable.Builder createStateDrops(Block pBlock, IntegerProperty property) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(pBlock).apply(property.getPossibleValues(), integer ->
                        SetItemCountFunction.setCount(ConstantValue.exactly(integer))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, integer)))
                ))
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BakeriesBlocks.REGISTER.getEntries().stream().map(DeferredHolder::value).collect(Collectors.toList());
    }
}