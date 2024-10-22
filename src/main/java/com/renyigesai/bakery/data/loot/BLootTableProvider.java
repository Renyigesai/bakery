package com.renyigesai.bakery.data.loot;


import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class BLootTableProvider extends net.minecraft.data.loot.LootTableProvider {

    public BLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(BBlockLoot::new, LootContextParamSets.BLOCK)));
    }

}