package com.renyigesai.bakeries.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LootTable extends LootTableProvider {
    public LootTable(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Collections.emptySet(), List.of(new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK)), provider);
    }
}
