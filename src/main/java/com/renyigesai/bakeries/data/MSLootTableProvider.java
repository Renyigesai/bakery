package com.renyigesai.bakeries.data;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MSLootTableProvider extends LootTableProvider {

    public MSLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output,Collections.emptySet(), List.of(new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK)), provider);
    }


}