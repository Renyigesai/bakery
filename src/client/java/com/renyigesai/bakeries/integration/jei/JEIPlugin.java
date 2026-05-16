package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.recipe.BlenderRecipe;
import com.renyigesai.bakeries.recipe.CoffeeRecipe;
import com.renyigesai.bakeries.recipe.MultiOutputSingleItemRecipe;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import com.renyigesai.bakeries.screen.BlenderScreen;
import com.renyigesai.bakeries.screen.DoughCraftingTableScreen;
import com.renyigesai.bakeries.screen.FermentationBoxScreen;
import com.renyigesai.bakeries.screen.OvenScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.recipe.advanced.ISimpleRecipeManagerPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IAdvancedRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.util.GsonHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
@SuppressWarnings("unused")
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "jei_plugin");
    private static final ResourceLocation ICE_DROP_SOURCES = new ResourceLocation(BakeriesMod.MODID, "loot_sources/ice_drop_sources.json");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        BakeriesMod.LOGGER.info("[JEI] registerCategories called");
        registration.addRecipeCategories(
                new OvenRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new BlenderRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new DoughCraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new FermentationBoxRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new BreadKnifeRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new FlourSieveRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new DrinkRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new ToasterRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        BakeriesMod.LOGGER.info("[JEI] registerRecipes called");
        Minecraft minecraft = Minecraft.getInstance();
        RecipeManager manager = null;
        if (minecraft.getConnection() != null) {
            manager = minecraft.getConnection().getRecipeManager();
        } else if (minecraft.level != null) {
            manager = minecraft.level.getRecipeManager();
        }

        List<SimpleMachineRecipe> ovenRecipes;
        List<SimpleMachineRecipe> blenderRecipes;
        List<SimpleMachineRecipe> doughRecipes;
        List<SimpleMachineRecipe> breadKnifeRecipes;
        List<SimpleMachineRecipe> flourSieveRecipes;
        List<CoffeeRecipe> drinkRecipes;
        List<SimpleMachineRecipe> fermentationRecipes;
        List<CampfireCookingRecipe> toasterRecipes;

        if (manager != null) {
            ovenRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.OVEN);
            blenderRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.BLENDER);
            doughRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.DOUGH_CRAFTING);
            breadKnifeRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.BREAD_KNIFE);
            flourSieveRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.FLOUR_SIEVE);
            drinkRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.COFFEE).stream()
                    .filter(JEIPlugin::isDisplayableCoffeeRecipe)
                    .toList();
            fermentationRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.FERMENTATION_BOX);
            toasterRecipes = manager.getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType.CAMPFIRE_COOKING);
        } else {
            minecraft.getResourceManager();
            ovenRecipes = loadSimpleRecipesFromResources(minecraft.getResourceManager(), "oven");
            blenderRecipes = loadSimpleRecipesFromResources(minecraft.getResourceManager(), "blender");
            doughRecipes = loadSimpleRecipesFromResources(minecraft.getResourceManager(), "dough_crafting");
            breadKnifeRecipes = loadSimpleRecipesFromResources(minecraft.getResourceManager(), "bread_knife");
            flourSieveRecipes = loadSimpleRecipesFromResources(minecraft.getResourceManager(), "flour_sieve");
            drinkRecipes = loadCoffeeRecipesFromResources(minecraft.getResourceManager());
            fermentationRecipes = loadSimpleRecipesFromResources(minecraft.getResourceManager(), "fermentation_box");
            toasterRecipes = List.of();
        }
        registration.addRecipes(OvenRecipeCategory.TYPE, ovenRecipes.stream().filter(SimpleMachineRecipe::isValid).toList());
        registration.addRecipes(BlenderRecipeCategory.TYPE, blenderRecipes.stream().filter(SimpleMachineRecipe::isValid).toList());
        registration.addRecipes(DoughCraftingRecipeCategory.TYPE, doughRecipes.stream().filter(SimpleMachineRecipe::isValid).toList());
        registration.addRecipes(BreadKnifeRecipeCategory.TYPE, breadKnifeRecipes.stream().filter(SimpleMachineRecipe::isValid).toList());
        registration.addRecipes(FlourSieveRecipeCategory.TYPE, flourSieveRecipes.stream().filter(SimpleMachineRecipe::isValid).toList());
        registration.addRecipes(DrinkRecipeCategory.TYPE, drinkRecipes);
        registration.addRecipes(FermentationBoxRecipeCategory.TYPE, fermentationRecipes.stream().filter(SimpleMachineRecipe::isValid).toList());
        registration.addRecipes(ToasterRecipeCategory.TYPE, toasterRecipes);
        registration.addItemStackInfo(new ItemStack(BakeriesItems.BOTTLE_YEAST),
                Component.translatable("bakeries.bottle_yeast.description"));
        registration.addItemStackInfo(new ItemStack(BakeriesItems.CHEESE_CUBE),
                Component.translatable("bakeries.cheese_cube.description"));
        registration.addItemStackInfo(new ItemStack(BakeriesItems.OLIVE),
                Component.translatable("bakeries.olive.description"));
        registration.addItemStackInfo(new ItemStack(BakeriesItems.RAW_COFFEE_BEAN),
                Component.translatable("bakeries.raw_coffee_bean.description"));
        registration.addItemStackInfo(new ItemStack(BakeriesItems.ICE_CUBES),
                Component.translatable("bakeries.ice.description", buildIceSourceNames(minecraft.getResourceManager())));
        BakeriesMod.LOGGER.info("[JEI] recipes registered: oven={}, blender={}, dough={}, bread_knife={}, flour_sieve={}, coffee={}",
                ovenRecipes.size(), blenderRecipes.size(), doughRecipes.size(), breadKnifeRecipes.size(), flourSieveRecipes.size(), drinkRecipes.size());
    }

    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {
        registration.addTypedRecipeManagerPlugin(OvenRecipeCategory.TYPE, new DynamicSimpleMachineRecipePlugin(BakeriesRecipeTypes.OVEN));
        registration.addTypedRecipeManagerPlugin(BlenderRecipeCategory.TYPE, new DynamicSimpleMachineRecipePlugin(BakeriesRecipeTypes.BLENDER));
        registration.addTypedRecipeManagerPlugin(DoughCraftingRecipeCategory.TYPE, new DynamicSimpleMachineRecipePlugin(BakeriesRecipeTypes.DOUGH_CRAFTING));
        registration.addTypedRecipeManagerPlugin(BreadKnifeRecipeCategory.TYPE, new DynamicSimpleMachineRecipePlugin(BakeriesRecipeTypes.BREAD_KNIFE));
        registration.addTypedRecipeManagerPlugin(FlourSieveRecipeCategory.TYPE, new DynamicSimpleMachineRecipePlugin(BakeriesRecipeTypes.FLOUR_SIEVE));
        registration.addTypedRecipeManagerPlugin(DrinkRecipeCategory.TYPE, new DynamicCoffeeRecipePlugin());
        registration.addTypedRecipeManagerPlugin(FermentationBoxRecipeCategory.TYPE, new DynamicSimpleMachineRecipePlugin(BakeriesRecipeTypes.FERMENTATION_BOX));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        BakeriesMod.LOGGER.info("[JEI] registerRecipeCatalysts called");
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.OVEN), OvenRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.BLENDER), BlenderRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.DOUGH_CRAFTING_TABLE), DoughCraftingRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.BREAD_KNIFE), BreadKnifeRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.FLOUR_SIEVE), FlourSieveRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.DRINK_CUP), DrinkRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.FERMENTATION_BOX), FermentationBoxRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BakeriesItems.TOASTER), ToasterRecipeCategory.TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(OvenScreen.class, 110, 16, 8, 54, OvenRecipeCategory.TYPE);
        registration.addRecipeClickArea(BlenderScreen.class, 136, 38, 20, 20, BlenderRecipeCategory.TYPE);
        registration.addRecipeClickArea(DoughCraftingTableScreen.class, 118, 30, 22, 18, DoughCraftingRecipeCategory.TYPE);
        registration.addRecipeClickArea(FermentationBoxScreen.class, 121, 33, 24, 15, FermentationBoxRecipeCategory.TYPE);
    }

    private record DynamicSimpleMachineRecipePlugin(
            RecipeType<SimpleMachineRecipe> type) implements ISimpleRecipeManagerPlugin<SimpleMachineRecipe> {

        @Override
            public boolean isHandledInput(ITypedIngredient<?> input) {
                return input.getIngredient() instanceof ItemStack;
            }

            @Override
            public boolean isHandledOutput(ITypedIngredient<?> output) {
                return output.getIngredient() instanceof ItemStack;
            }

            @Override
            public @NotNull List<SimpleMachineRecipe> getRecipesForInput(ITypedIngredient<?> input) {
                if (!(input.getIngredient() instanceof ItemStack stack)) {
                    return List.of();
                }
                List<SimpleMachineRecipe> result = new ArrayList<>();
                for (SimpleMachineRecipe recipe : getAllRecipes()) {
                    if (recipeUsesInput(recipe, stack)) {
                        result.add(recipe);
                    }
                }
                return result;
            }

            @Override
            public @NotNull List<SimpleMachineRecipe> getRecipesForOutput(ITypedIngredient<?> output) {
                if (!(output.getIngredient() instanceof ItemStack stack)) {
                    return List.of();
                }
                List<SimpleMachineRecipe> result = new ArrayList<>();
                for (SimpleMachineRecipe recipe : getAllRecipes()) {
                    if (recipeHasOutput(recipe, stack)) {
                        result.add(recipe);
                    }
                }
                return result;
            }

            @Override
            public @NotNull List<SimpleMachineRecipe> getAllRecipes() {
                Minecraft minecraft = Minecraft.getInstance();
                RecipeManager manager = null;
                if (minecraft.getConnection() != null) {
                    manager = minecraft.getConnection().getRecipeManager();
                } else if (minecraft.level != null) {
                    manager = minecraft.level.getRecipeManager();
                }
                if (manager == null) {
                    return List.of();
                }
                return manager.getAllRecipesFor(type).stream()
                        .filter(SimpleMachineRecipe::isValid)
                        .toList();
            }

            private static boolean recipeUsesInput(SimpleMachineRecipe recipe, ItemStack stack) {
                if (recipe instanceof BlenderRecipe blenderRecipe) {
                    for (Ingredient ingredient : blenderRecipe.getInputIngredients()) {
                        if (ingredient.test(stack)) {
                            return true;
                        }
                    }
                    return blenderRecipe.hasContainer() && blenderRecipe.getContainerIngredient().test(stack);
                }
                return recipe.getIngredient().test(stack);
            }

            private static boolean recipeHasOutput(SimpleMachineRecipe recipe, ItemStack stack) {
                if (recipe instanceof MultiOutputSingleItemRecipe multiOutputRecipe) {
                    for (ItemStack result : multiOutputRecipe.getAllResults()) {
                        if (ItemStack.isSameItem(result, stack)) {
                            return true;
                        }
                    }
                    return false;
                }
                ItemStack out = recipe.getResultItem(RegistryAccess.EMPTY);
                return ItemStack.isSameItem(out, stack);
            }
        }

    private static List<SimpleMachineRecipe> loadSimpleRecipesFromResources(ResourceManager resourceManager, String typePath) {
        List<SimpleMachineRecipe> list = new ArrayList<>();
        ResourceLocation typeId = new ResourceLocation(BakeriesMod.MODID, typePath);
        Map<ResourceLocation, Resource> resources = resourceManager.listResources("recipes", path -> path.getPath().endsWith(".json"));
        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            try {
                JsonObject json = JsonParser.parseReader(entry.getValue().openAsReader()).getAsJsonObject();
                String type = GsonHelper.getAsString(json, "type", "");
                if (!type.equals(typeId.toString())) {
                    continue;
                }
                if ("blender".equals(typePath)) {
                    ResourceLocation recipeFile = entry.getKey();
                    String recipePath = recipeFile.getPath().substring("recipes/".length(), recipeFile.getPath().length() - ".json".length());
                    ResourceLocation recipeId = new ResourceLocation(recipeFile.getNamespace(), recipePath);
                    SimpleMachineRecipe recipe = new BlenderRecipe.Serializer().fromJson(recipeId, json);
                    if (recipe.isValid()) {
                        list.add(recipe);
                    }
                    continue;
                }
                JsonObject ingredientObj = GsonHelper.getAsJsonObject(json, "ingredient");
                ResourceLocation ingredientId = new ResourceLocation(GsonHelper.getAsString(ingredientObj, "item"));
                JsonObject resultObj = GsonHelper.getAsJsonObject(json, "result");
                ResourceLocation resultId = new ResourceLocation(GsonHelper.getAsString(resultObj, "item"));
                int count = GsonHelper.getAsInt(resultObj, "count", 1);
                if (!BuiltInRegistries.ITEM.containsKey(ingredientId) || !BuiltInRegistries.ITEM.containsKey(resultId)) {
                    continue;
                }
                Ingredient ingredient = Ingredient.of(new ItemStack(BuiltInRegistries.ITEM.get(ingredientId)));
                ItemStack result = new ItemStack(BuiltInRegistries.ITEM.get(resultId), Math.max(1, count));
                int min = GsonHelper.getAsInt(json, "min", -1);
                int max = GsonHelper.getAsInt(json, "max", -1);
                int perfect = GsonHelper.getAsInt(json, "perfect", -1);
                int time = GsonHelper.getAsInt(json, "time", -1);
                ResourceLocation recipeFile = entry.getKey();
                String recipePath = recipeFile.getPath().substring("recipes/".length(), recipeFile.getPath().length() - ".json".length());
                ResourceLocation recipeId = new ResourceLocation(recipeFile.getNamespace(), recipePath);
                SimpleMachineRecipe recipe = new SimpleMachineRecipe(recipeId, ingredient, result, result.getCount(), min, max, typeId, typeId)
                        .setRecipeData(min, max, perfect, time);
                if (recipe.isValid()) {
                    list.add(recipe);
                }
            } catch (Exception ignored) {
            }
        }
        return list;
    }

    private static final class DynamicCoffeeRecipePlugin implements ISimpleRecipeManagerPlugin<CoffeeRecipe> {
        @Override
        public boolean isHandledInput(ITypedIngredient<?> input) {
            return input.getIngredient() instanceof ItemStack;
        }

        @Override
        public boolean isHandledOutput(ITypedIngredient<?> output) {
            return output.getIngredient() instanceof ItemStack;
        }

        @Override
        public @NotNull List<CoffeeRecipe> getRecipesForInput(ITypedIngredient<?> input) {
            if (!(input.getIngredient() instanceof ItemStack stack)) {
                return List.of();
            }
            List<CoffeeRecipe> result = new ArrayList<>();
            for (CoffeeRecipe recipe : getAllRecipes()) {
                for (Ingredient ingredient : recipe.getIngredientsList()) {
                    if (ingredient.test(stack)) {
                        result.add(recipe);
                        break;
                    }
                }
            }
            return result;
        }

        @Override
        public @NotNull List<CoffeeRecipe> getRecipesForOutput(ITypedIngredient<?> output) {
            if (!(output.getIngredient() instanceof ItemStack stack)) {
                return List.of();
            }
            List<CoffeeRecipe> result = new ArrayList<>();
            for (CoffeeRecipe recipe : getAllRecipes()) {
                ItemStack out = recipe.getResultItem(RegistryAccess.EMPTY);
                if (ItemStack.isSameItemSameTags(out, stack)) {
                    result.add(recipe);
                }
            }
            return result;
        }

        @Override
        public @NotNull List<CoffeeRecipe> getAllRecipes() {
            Minecraft minecraft = Minecraft.getInstance();
            RecipeManager manager = null;
            if (minecraft.getConnection() != null) {
                manager = minecraft.getConnection().getRecipeManager();
            } else if (minecraft.level != null) {
                manager = minecraft.level.getRecipeManager();
            }
            if (manager == null) {
                return List.of();
            }
            return manager.getAllRecipesFor(BakeriesRecipeTypes.COFFEE).stream()
                    .filter(JEIPlugin::isDisplayableCoffeeRecipe)
                    .toList();
        }
    }

    private static boolean isDisplayableCoffeeRecipe(CoffeeRecipe recipe) {
        if (!recipe.isValid() || recipe.getResultItem(RegistryAccess.EMPTY).isEmpty()) {
            return false;
        }
        for (Ingredient ingredient : recipe.getIngredientsList()) {
            if (ingredient.isEmpty() || ingredient.getItems().length == 0) {
                return false;
            }
        }
        return true;
    }

    private static List<CoffeeRecipe> loadCoffeeRecipesFromResources(ResourceManager resourceManager) {
        List<CoffeeRecipe> list = new ArrayList<>();
        ResourceLocation typeId = new ResourceLocation(BakeriesMod.MODID, "coffee");
        Map<ResourceLocation, Resource> resources = resourceManager.listResources("recipes", path -> path.getPath().endsWith(".json"));
        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            try {
                JsonObject json = JsonParser.parseReader(entry.getValue().openAsReader()).getAsJsonObject();
                String type = GsonHelper.getAsString(json, "type", "");
                if (!type.equals(typeId.toString())) {
                    continue;
                }
                ResourceLocation recipeFile = entry.getKey();
                String recipePath = recipeFile.getPath().substring("recipes/".length(), recipeFile.getPath().length() - ".json".length());
                ResourceLocation recipeId = new ResourceLocation(recipeFile.getNamespace(), recipePath);
                CoffeeRecipe recipe = ((CoffeeRecipe.Serializer) BakeriesRecipeTypes.COFFEE_SERIALIZER).fromJson(recipeId, json);
                if (isDisplayableCoffeeRecipe(recipe)) {
                    list.add(recipe);
                }
            } catch (Exception ignored) {
            }
        }
        return list;
    }

    private static Component buildIceSourceNames(ResourceManager resourceManager) {
        Set<Component> names = new LinkedHashSet<>();
        try {
            var stream = JEIPlugin.class.getClassLoader()
                    .getResourceAsStream("data/bakeries/loot_sources/ice_drop_sources.json");
            if (stream == null) {
                return Component.literal("minecraft:ice");
            }
            try (stream; var reader = new java.io.InputStreamReader(stream, java.nio.charset.StandardCharsets.UTF_8)) {
                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
                JsonArray blocks = root.getAsJsonArray("blocks");
                if (blocks == null) {
                    return Component.literal("minecraft:ice");
                }
                for (var entry : blocks) {
                    if (!entry.isJsonPrimitive()) {
                        continue;
                    }
                    ResourceLocation blockId = new ResourceLocation(entry.getAsString());
                    if (!BuiltInRegistries.BLOCK.containsKey(blockId)) {
                        continue;
                    }
                    Block block = BuiltInRegistries.BLOCK.get(blockId);
                    Component displayName = block.getName().copy();
                    if (blockId.equals(new ResourceLocation("minecraft", "frosted_ice"))) {
                        displayName = Component.translatable(
                                "bakeries.ice.source.frosted_ice",
                                block.getName().copy()
                        );
                    }
                    names.add(displayName);
                }
            }
        } catch (Exception ignored) {
        }
        if (names.isEmpty()) {
            return Component.literal("minecraft:ice");
        }
        return Component.literal(
                names.stream()
                        .map(Component::getString)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("minecraft:ice")
        );
    }
}
