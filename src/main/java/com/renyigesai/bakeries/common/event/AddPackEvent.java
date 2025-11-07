package com.renyigesai.bakeries.common.event;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.BakeriesFilePackResource;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.locating.IModFile;

import java.util.Optional;

//@EventBusSubscriber(modid = BakeriesMod.MODID)
public class AddPackEvent {
//    @SubscribeEvent
    public static void onAddPack(AddPackFindersEvent event) {
//        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
//            IModFileInfo modFileInfo = ModList.get().getModFileById(BakeriesMod.MODID);
//            if (modFileInfo == null) {
//                return;
//            }
//            IModFile modFile = modFileInfo.getFile();
//
//            PackLocationInfo location = new PackLocationInfo(BakeriesMod.prefix("b_16x").getPath(),
//                    Component.literal("Bakeries 16x Texture"),
//                    PackSource.BUILT_IN,
//                    Optional.empty());
//
//            event.addRepositorySource(consumer -> {
//                Pack pack = Pack.readMetaAndCreate(location, new BakeriesFilePackResource(location, modFile, "resourcepacks/b_16x"),PackType.CLIENT_RESOURCES,Pack.Position.TOP);
//                if (pack != null) {
//                    consumer.accept(pack);
//                }
//            });
//        }
    }
}
