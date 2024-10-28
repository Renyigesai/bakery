package com.renyigesai.bakery.data.tag;


import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.init.BakeryItemTag;
import com.renyigesai.bakery.init.BakeryItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider {

    public BItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, BakeryMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BakeryItemTag.RAE_FOOD)
                .add(
                        BakeryItems.BAGEL_DOUGH.get(),
                        BakeryItems.BAGUETTE_DOUGH.get(),
                        BakeryItems.CINNAMON_ROLL_DOUGH.get(),
                        BakeryItems.COUNTRY_BREAD_DOUGH.get(),
                        BakeryItems.CROISSANT_DOUGH.get(),
                        BakeryItems.PINEAPPLE_BUN_DOUGH.get(),
                        BakeryItems.ROUND_BREAD_DOUGH.get(),
                        BakeryItems.SALT_CROISSANT_DOUGH.get()
                        );
    }

}