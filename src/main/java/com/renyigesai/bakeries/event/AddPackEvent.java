package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.BakeriesFilePackResource;
import com.renyigesai.bakeries.config.BakeriesConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AddPackEvent {
    @SubscribeEvent
    public static void onAddPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            IModFileInfo modFileInfo = ModList.get().getModFileById(BakeriesMod.MODID);
            if (modFileInfo == null) {
                return;
            }
            IModFile modFile = modFileInfo.getFile();
            event.addRepositorySource(consumer -> {
                Pack pack1 = Pack.readMetaAndCreate(BakeriesMod.prefix("b_2d_icon").toString(),
                        Component.translatable("pack.bakeries.2d_icon"), false, id ->
                                new BakeriesFilePackResource(id, modFile, "resourcepacks/b_2d_icon"),
                        PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                if (pack1 != null) {
                    consumer.accept(pack1);
                }
            });
        }
    }
}
