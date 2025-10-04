package net.weibai.bakeries.data;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.weibai.bakeries.BakeriesMod;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BakeriesMod.MODID)
public class DataGenerator {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

//        MSTagsProvider.Blocks blockTagsProvider = new MSTagsProvider.Blocks(output, provider, existingFileHelper);
//        generator.addProvider(events.includeServer(), new MSTagsProvider.Blocks(output, provider, existingFileHelper));
//        generator.addProvider(events.includeServer(), new MSTagsProvider.Items(
//                output, provider, blockTagsProvider.contentsGetter(), existingFileHelper));


        generator.addProvider(event.includeClient(),  new RegistryDataGenerator(output, provider));
        generator.addProvider(event.includeClient(), new MSSoundsProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new MSBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new MSItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new MSLootTableProvider(output, provider));
        generator.addProvider(event.includeServer(), new MSRecipeProvider(output, provider));
        generator.addProvider(event.includeClient(), new MSLanguageProvider(output, "en_us"));
        generator.addProvider(event.includeClient(), new MSLanguageProvider(output, "zh_cn"));


        generator.addProvider(event.includeServer(), new MSTagsProvider(output, provider, existingFileHelper));
    }

}