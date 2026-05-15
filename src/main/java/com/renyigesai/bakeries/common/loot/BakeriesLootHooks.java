package com.renyigesai.bakeries.common.loot;

import com.renyigesai.bakeries.init.BakeriesItems;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class BakeriesLootHooks {
    private static final ResourceLocation ICE_DROP_SOURCES = new ResourceLocation("bakeries", "loot_sources/ice_drop_sources.json");

    private BakeriesLootHooks() {
    }

    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootDataManager, id, tableBuilder, source) -> {
            if (!source.isBuiltin()) {
                return;
            }
            Map<ResourceLocation, ResourceLocation> tableToBlockId = loadTargetTableToBlockId(resourceManager);
            Set<ResourceLocation> targetLootTables = loadTargetLootTables(resourceManager);
            if (targetLootTables.isEmpty() || !targetLootTables.contains(id)) {
                return;
            }
            LootItemCondition.Builder noSilkTouch = InvertedLootItemCondition.invert(
                    MatchTool.toolMatches(
                            ItemPredicate.Builder.item()
                                    .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))
                    )
            );
            tableBuilder.pool(
                    LootPool.lootPool()
                            .when(noSilkTouch)
                            .add(
                                    LootItem.lootTableItem(BakeriesItems.ICE_CUBES)
                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 9.0F)))
                                            .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                            ).build()
            );

            ResourceLocation targetBlockId = tableToBlockId.get(id);
            if (new ResourceLocation("minecraft", "frosted_ice").equals(targetBlockId)) {
                LootItemCondition.Builder silkTouch = MatchTool.toolMatches(
                        ItemPredicate.Builder.item()
                                .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))
                );
                Item dropItem = BuiltInRegistries.ITEM.containsKey(targetBlockId)
                        ? BuiltInRegistries.ITEM.get(targetBlockId)
                        : Items.ICE;
                tableBuilder.pool(
                        LootPool.lootPool()
                                .when(silkTouch)
                                .add(LootItem.lootTableItem(dropItem))
                                .build()
                );
            }
        });
    }

    private static Set<ResourceLocation> loadTargetLootTables(net.minecraft.server.packs.resources.ResourceManager resourceManager) {
        Set<ResourceLocation> tables = new HashSet<>();
        try {
            Optional<Resource> resource = resourceManager.getResource(ICE_DROP_SOURCES);
            if (resource.isEmpty()) {
                return tables;
            }
            try (var reader = resource.get().openAsReader()) {
                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
                JsonArray blocks = root.getAsJsonArray("blocks");
                if (blocks == null) {
                    return tables;
                }
                for (var element : blocks) {
                    if (!element.isJsonPrimitive()) {
                        continue;
                    }
                    ResourceLocation blockId = new ResourceLocation(element.getAsString());
                    if (!BuiltInRegistries.BLOCK.containsKey(blockId)) {
                        continue;
                    }
                    tables.add(BuiltInRegistries.BLOCK.get(blockId).getLootTable());
                }
            }
        } catch (Exception ignored) {
        }
        return tables;
    }

    private static Map<ResourceLocation, ResourceLocation> loadTargetTableToBlockId(net.minecraft.server.packs.resources.ResourceManager resourceManager) {
        Map<ResourceLocation, ResourceLocation> result = new HashMap<>();
        try {
            Optional<Resource> resource = resourceManager.getResource(ICE_DROP_SOURCES);
            if (resource.isEmpty()) {
                return result;
            }
            try (var reader = resource.get().openAsReader()) {
                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
                JsonArray blocks = root.getAsJsonArray("blocks");
                if (blocks == null) {
                    return result;
                }
                for (var element : blocks) {
                    if (!element.isJsonPrimitive()) {
                        continue;
                    }
                    ResourceLocation blockId = new ResourceLocation(element.getAsString());
                    if (!BuiltInRegistries.BLOCK.containsKey(blockId)) {
                        continue;
                    }
                    result.put(BuiltInRegistries.BLOCK.get(blockId).getLootTable(), blockId);
                }
            }
        } catch (Exception ignored) {
        }
        return result;
    }
}
