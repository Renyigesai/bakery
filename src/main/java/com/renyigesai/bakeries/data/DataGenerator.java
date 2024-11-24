package com.renyigesai.bakeries.data;


import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.data.loot.BLootTableProvider;
import com.renyigesai.bakeries.data.provider.BBlockStateProvider;
import com.renyigesai.bakeries.data.provider.BItemModelProvider;
import com.renyigesai.bakeries.data.provider.BLanguageProvider;
import com.renyigesai.bakeries.data.provider.BRecipeProvider;
import com.renyigesai.bakeries.data.tag.BBlockTagProvider;
import com.renyigesai.bakeries.data.tag.BItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BakeriesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerator {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();



        BBlockTagProvider blockTagsProvider = new BBlockTagProvider(output, provider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new BItemTagsProvider(
                output, provider, blockTagsProvider.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeClient(),  new RegistryDataGenerator(output, provider));
        generator.addProvider(event.includeServer(), new BBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new BItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new BLootTableProvider(output));
        generator.addProvider(event.includeServer(), new BRecipeProvider(output));
        generator.addProvider(event.includeClient(), new BLanguageProvider(output, "en_us"));
        generator.addProvider(event.includeClient(), new BLanguageProvider(output, "zh_cn"));
    }

}