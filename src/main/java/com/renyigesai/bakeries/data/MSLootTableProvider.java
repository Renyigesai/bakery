package com.renyigesai.bakeries.data;


import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MSLootTableProvider extends LootTableProvider {

    public MSLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output,Collections.emptySet(), List.of(new SubProviderEntry(MSBlockLoot::new, LootContextParamSets.BLOCK)), provider);
    }


}