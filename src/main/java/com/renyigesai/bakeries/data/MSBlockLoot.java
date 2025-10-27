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
        this.dropSelf(BakeriesBlocks.TOAST.get());//占位
    }
    private void forAddAllBread(Block... blocks){
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
//        this.add(MSBlocks.METEORITE_IRON_ORE.get(), block -> this.createOreDrop(block, MSItems.RAW_METEORITE_IRON.get()));
//        this.add(MSBlocks.DEEPSLATE_METEORITE_IRON_ORE.get(), block -> this.createOreDrop(block, MSItems.RAW_METEORITE_IRON.get()));
//        this.dropSelf(MSBlocks.RAW_METEORITE_IRON_BLOCK.get());
//
//        this.add(MSBlocks.MAGNETIC_IRON_ORE.get(), block -> this.createOreDrop(block, MSItems.RAW_MAGNETIC_IRON.get()));
//        this.add(MSBlocks.DEEPSLATE_MAGNETIC_IRON_ORE.get(), block -> this.createOreDrop(block, MSItems.RAW_MAGNETIC_IRON.get()));
//        this.dropSelf(MSBlocks.RAW_MAGNETIC_IRON_BLOCK.get());
//
//        this.add(MSBlocks.HEMATITE_IRON_ORE.get(), block -> this.createOreDrop(block, MSItems.RAW_HEMATITE_IRON.get()));
//        this.add(MSBlocks.DEEPSLATE_HEMATITE_IRON_ORE.get(), block -> this.createOreDrop(block, MSItems.RAW_HEMATITE_IRON.get()));
//        this.dropSelf(MSBlocks.RAW_HEMATITE_IRON_BLOCK.get());
//
//        this.add(MSBlocks.LIGNITE_ORE.get(), block -> this.createOreDrop(block, MSItems.LIGNITE.get()));
//        this.add(MSBlocks.DEEPSLATE_LIGNITE_ORE.get(), block -> this.createOreDrop(block, MSItems.LIGNITE.get()));
//        this.dropSelf(MSBlocks.LIGNITE_BLOCK.get());
//
//        this.add(MSBlocks.BITUMINOUS_COAL_ORE.get(), block -> this.createOreDrop(block, MSItems.BITUMINOUS_COAL.get()));
//        this.add(MSBlocks.DEEPSLATE_BITUMINOUS_COAL_ORE.get(), block -> this.createOreDrop(block, MSItems.BITUMINOUS_COAL.get()));
//        this.dropSelf(MSBlocks.BITUMINOUS_COAL_BLOCK.get());

//        this.add(MSBlocks.ITEM_BLOCK.get(), noDrop());
//        this.add(MSBlocks.OAK_SOIL_BURNING_BLOCK.get(), block -> createStateDrops(block, SoilBurningBlock.VALUE));
//        this.add(MSBlocks.BIRCH_SOIL_BURNING_BLOCK.get(),block -> createStateDrops(block, SoilBurningBlock.VALUE));
//        this.add(MSBlocks.SPRUCE_SOIL_BURNING_BLOCK.get(), block -> createStateDrops(block, SoilBurningBlock.VALUE));
//        this.add(MSBlocks.JUNGLE_SOIL_BURNING_BLOCK.get(), block -> createStateDrops(block, SoilBurningBlock.VALUE));
//        this.add(MSBlocks.ACACIA_SOIL_BURNING_BLOCK.get(), block -> createStateDrops(block, SoilBurningBlock.VALUE));
//        this.add(MSBlocks.DARK_OAK_SOIL_BURNING_BLOCK.get(), block -> createStateDrops(block, SoilBurningBlock.VALUE));
//        this.add(MSBlocks.BAMBOO_SOIL_BURNING_BLOCK.get(), block -> createStateDrops(block, SoilBurningBlock.VALUE));
//        this.add(MSBlocks.CHERRY_SOIL_BURNING_BLOCK.get(), block -> createStateDrops(block, SoilBurningBlock.VALUE));
//        this.add(MSBlocks.MANGROVE_SOIL_BURNING_BLOCK.get(), block -> createStateDrops(block, SoilBurningBlock.VALUE));
//        this.add(MSBlocks.CRIMSON_STEM_SOIL_BURNING_BLOCK.get(), block -> createStateDrops(block, SoilBurningBlock.VALUE));
//        this.add(MSBlocks.WARPED_STEM_SOIL_BURNING_BLOCK.get(), block -> createStateDrops(block, SoilBurningBlock.VALUE));
//        LootItemCondition.Builder flaxBlockLootCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(MSBlocks.FLAX_BLOCK.get())
//                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FlaxPlantBlock.AGE, 5));
//
//        this.add(MSBlocks.FLAX_BLOCK.get(), this.createCropDrops(MSBlocks.FLAX_BLOCK.get(), MSItems.FLAX.get(), MSItems.FLAX_SEEDS.get(), flaxBlockLootCondition));
//
//        this.add(MSBlocks.OAK_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.BIRCH_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.SPRUCE_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.JUNGLE_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.ACACIA_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.DARK_OAK_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.BAMBOO_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.CHERRY_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.MANGROVE_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.CRIMSON_STEM_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.WARPED_STEM_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.SOUL_OAK_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))));
//        this.add(MSBlocks.SOUL_BIRCH_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.SOUL_SPRUCE_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.SOUL_JUNGLE_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.SOUL_ACACIA_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.SOUL_DARK_OAK_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.SOUL_BAMBOO_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.SOUL_CHERRY_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.SOUL_MANGROVE_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.SOUL_CRIMSON_STEM_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
//        this.add(MSBlocks.SOUL_WARPED_STEM_CAMPFIRE.get(), block -> this.createSilkTouchDispatchTable(block,
//                this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL)
//                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));