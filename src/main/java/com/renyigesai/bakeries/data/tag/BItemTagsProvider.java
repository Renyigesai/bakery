package com.renyigesai.bakeries.data.tag;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesItemTag;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider {

    public BItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, BakeriesMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BakeriesItemTag.FLOUR)
                .add(
                        BakeriesItems.WHOLE_WHEAT_FLOUR.get()
                );
    }

}