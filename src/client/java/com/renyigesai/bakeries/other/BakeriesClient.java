package com.renyigesai.bakeries.other;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BakeriesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModContainer modContainer = FabricLoader.getInstance()
                .getModContainer("bakeries")
                .orElseThrow(() -> new IllegalStateException("Missing mod container: bakeries"));

        ResourceManagerHelper.registerBuiltinResourcePack(
                new ResourceLocation("bakeries", "b_16x"),
                modContainer,
                Component.literal("Bakeries Dark"),
                ResourcePackActivationType.NORMAL
        );
    }
}
