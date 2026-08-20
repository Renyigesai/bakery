package com.renyigesai.bakeries.data;


import com.renyigesai.bakeries.api.block.AbstractPileBlock;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockLootTables extends VanillaBlockLoot {

    private final Set<Block> generatedLootTables = new HashSet<>();
    public BlockLootTables(HolderLookup.Provider registries) {
        super(registries);
    }
    @Override
    protected void generate() {
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
                BakeriesBlocks.BAGUETTE.get(),
                BakeriesBlocks.EGG_TART.get(),
                BakeriesBlocks.RICE_BREAD.get(),
                BakeriesBlocks.PINEAPPLE_OIL.get(),
                BakeriesBlocks.ICED_AMERICAN.get(),
                BakeriesBlocks.ICED_LATTE.get(),
                BakeriesBlocks.BROWN_SUGAR_LATTE.get(),
                BakeriesBlocks.CREAM_BINGLE_COFFEE.get(),
                BakeriesBlocks.MATCHA_LATTE.get(),
                BakeriesBlocks.MATCHA_PARFAIT.get(),
                BakeriesBlocks.FLAT_CROISSANT.get(),
                BakeriesBlocks.TARO_SALT_YOLK_BREAD.get(),
                BakeriesBlocks.TARO_MILK.get(),
                BakeriesBlocks.SALMON_SANDWICH.get()
        );
        this.dropSelf(BakeriesBlocks.SALT_ORE.get());
        this.dropSelf(BakeriesBlocks.DEEPSLATE_SALT_ORE.get());
        this.dropSelf(BakeriesBlocks.RAW_SALT_BLOCK.get());
        this.dropSelf(BakeriesBlocks.CUPBOARD.get());
        this.dropSelf(BakeriesBlocks.DOUGH_CRAFTING_TABLE.get());
        this.dropSelf(BakeriesBlocks.OVEN.get());
        this.dropSelf(BakeriesBlocks.BLENDER.get());
        this.dropSelf(BakeriesBlocks.MOULD.get());
        this.dropSelf(BakeriesBlocks.MOULD_TWO.get());
        this.dropSelf(BakeriesBlocks.WOOD_COUNTER.get());
        this.dropSelf(BakeriesBlocks.WHOLE_WHEAT_FLOUR_BAG.get());
        this.dropSelf(BakeriesBlocks.FLOUR_BAG.get());
        this.dropSelf(BakeriesBlocks.BREAD_BASKET.get());
        this.dropSelf(BakeriesBlocks.GLASS_CABINET_DOOR.get());
        this.dropSelf(BakeriesBlocks.SOFA_WHITE.get());
        this.dropSelf(BakeriesBlocks.SOFA_RED.get());
        this.dropSelf(BakeriesBlocks.SOFA_LIGHT_GRAY.get());
        this.dropSelf(BakeriesBlocks.COFFEE_TABLE.get());
        this.dropSelf(BakeriesBlocks.CASH_REGISTER_COMPUTER.get());
        this.dropSelf(BakeriesBlocks.MOKA_POT.get());
        this.dropSelf(BakeriesBlocks.DRINK_CUP.get());
        this.dropSelf(BakeriesBlocks.MENU.get());
        this.dropSelf(BakeriesBlocks.WOOD_TRAY.get());
        this.dropSelf(BakeriesBlocks.BREAD_RACK.get());
        this.dropSelf(BakeriesBlocks.FERMENTATION_BOX.get());

        this.dropSelf(BakeriesBlocks.GLASS_BREAD_RACK.get());
        this.dropSelf(BakeriesBlocks.LUMINOUS_LIGHT_SIGN.get());
        this.dropSelf(BakeriesBlocks.BLACK_WHITE_CONCRETE.get());
        this.dropSelf(BakeriesBlocks.BREAD_HOLDERS.get());
    }
    private void forAddAllBread(Block... blocks){
        List<Block> blockList = List.of(blocks);
        for (Block block : blockList) {
            if (block instanceof AbstractPileBlock abstractPileBlock) {
                this.add(block, blockIn -> this.createStateDrops(block, abstractPileBlock.getPileProperty()));
            }
        }
    }

    protected LootTable.Builder createStateDrops(Block pBlock, Property<Integer> property) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(pBlock).apply(property.getPossibleValues(), integer ->
                        SetItemCountFunction.setCount(ConstantValue.exactly(integer))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, integer)))
                ))
        );
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        this.generatedLootTables.add(block);
        this.map.put(block.getLootTable(), builder);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return generatedLootTables;
    }
}