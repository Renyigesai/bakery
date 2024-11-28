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
        this.tag(BakeriesItemTag.RAE_FOOD)
                .add(
                        BakeriesItems.BAGEL_DOUGH.get(),
                        BakeriesItems.BAGUETTE_DOUGH.get(),
                        BakeriesItems.CINNAMON_ROLL_DOUGH.get(),
                        BakeriesItems.COUNTRY_BREAD_DOUGH.get(),
                        BakeriesItems.CROISSANT_DOUGH.get(),
                        BakeriesItems.PINEAPPLE_BUN_DOUGH.get(),
                        BakeriesItems.ROUND_BREAD_DOUGH.get(),
                        BakeriesItems.SALT_CROISSANT_DOUGH.get()
                        );
        this.tag(BakeriesItemTag.FLOUR)
                .add(
                        BakeriesItems.WHOLE_WHEAT_FLOUR.get()
                );
    }

}