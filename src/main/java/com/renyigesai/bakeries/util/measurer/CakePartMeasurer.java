package com.renyigesai.bakeries.util.measurer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.custom_cake.CakePartData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CakePartMeasurer {
    public final Gson GSON;
    public static final String CAKE_BASE = "bakeries:cake_base";
    public static final String CAKE_FILLING = "bakeries:cake_filling";
    public static final String CAKE_CREAM = "bakeries:cake_cream";
    public static final String CAKE_TOPPING = "bakeries:cake_topping";
    private static final Map<String,CakePartData> SERVER_PARTS = new HashMap<>();

    private CakePartMeasurer() {
        GSON = new GsonBuilder().registerTypeAdapter(CakePartData.class, new CakePartData.Deserializer()).create();;
    }

    public static void cakePartMeasurerServerBuilder(){
        new CakePartMeasurer().loadServerAllParts();
    }

    private void loadServerAllParts() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            BakeriesMod.LOGGER.warn("Cannot load cake parts: server not available");
            return;
        }
        ResourceManager manager = server.getResourceManager();
        SERVER_PARTS.clear();

        Map<ResourceLocation, Resource> resources = manager.listResources("cake_parts",
                rl -> rl.getPath().endsWith(".json"));

        if (resources.isEmpty()) {
            BakeriesMod.LOGGER.warn("No cake parts found via listResources. " +
                    "Custom directory may not be indexed. Consider using an index file.");
            return;
        }

        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            ResourceLocation fullPath = entry.getKey();
            String path = fullPath.getPath();
            String namespace = fullPath.getNamespace();
            String prefix = "cake_parts/";
            if (!path.startsWith(prefix) || !path.endsWith(".json")){
                continue;
            }
            String relative = path.substring(prefix.length(), path.length() - 5);
            ResourceLocation partId = new ResourceLocation(namespace, relative);

            try (InputStream stream = entry.getValue().open();
                 Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                 CakePartData data = GSON.fromJson(reader, CakePartData.class);
                 if ("".equals(data.getLoadId()) || ModList.get().isLoaded(data.getLoadId())){
                     data.setId(partId.toString());
                     SERVER_PARTS.put(partId.toString(), data);
                 }else {

                 }
            } catch (Exception e) {
                 BakeriesMod.LOGGER.error("Failed to parse cake part: {}", partId, e);
            }
        }
        BakeriesMod.LOGGER.info("Loaded {} cake parts via listResources", SERVER_PARTS.size());
    }

    public static Map<String, CakePartData> getParts() {
        return SERVER_PARTS;
    }

    public static List<CakePartData> getAllParts() {
        return SERVER_PARTS.values().stream().toList();
    }

    public static Optional<CakePartData> isIngredient(ItemStack stack){
        List<CakePartData> allParts = getAllParts();
        for (CakePartData cakePartData : allParts) {
            if (cakePartData.getIngredient().test(stack)){
                return Optional.of(cakePartData);
            }
        }
        return Optional.empty();
    }
}
