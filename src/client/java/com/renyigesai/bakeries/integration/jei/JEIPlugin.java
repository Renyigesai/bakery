package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import com.renyigesai.bakeries.screen.BlenderScreen;
import com.renyigesai.bakeries.screen.DoughCraftingTableScreen;
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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.util.GsonHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "jei_plugin");

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
                new BreadKnifeRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new FlourSieveRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new DrinkRecipeCategory(registration.getJeiHelpers().getGuiHelper())
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

        List<SimpleMachineRecipe> ovenRecipes = List.of();
        List<SimpleMachineRecipe> blenderRecipes = List.of();
        List<SimpleMachineRecipe> doughRecipes = List.of();
        List<SimpleMachineRecipe> breadKnifeRecipes = List.of();
        List<SimpleMachineRecipe> flourSieveRecipes = List.of();
        List<SimpleMachineRecipe> drinkRecipes = List.of();

        if (manager != null) {
            ovenRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.OVEN);
            blenderRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.BLENDER);
            doughRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.DOUGH_CRAFTING);
            breadKnifeRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.BREAD_KNIFE);
            flourSieveRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.FLOUR_SIEVE);
            drinkRecipes = manager.getAllRecipesFor(BakeriesRecipeTypes.DRINK);
        } else {
            minecraft.getResourceManager();
            ovenRecipes = loadSimpleRecipesFromResources(minecraft.getResourceManager(), "oven");
            blenderRecipes = loadSimpleRecipesFromResources(minecraft.getResourceManager(), "blender");
            doughRecipes = loadSimpleRecipesFromResources(minecraft.getResourceManager(), "dough_crafting");
            breadKnifeRecipes = loadSimpleRecipesFromResources(minecraft.getResourceManager(), "bread_knife");
            flourSieveRecipes = loadSimpleRecipesFromResources(minecraft.getResourceManager(), "flour_sieve");
            drinkRecipes = loadSimpleRecipesFromResources(minecraft.getResourceManager(), "drink");
        }
        registration.addRecipes(OvenRecipeCategory.TYPE, ovenRecipes);
        registration.addRecipes(BlenderRecipeCategory.TYPE, blenderRecipes);
        registration.addRecipes(DoughCraftingRecipeCategory.TYPE, doughRecipes);
        registration.addRecipes(BreadKnifeRecipeCategory.TYPE, breadKnifeRecipes);
        registration.addRecipes(FlourSieveRecipeCategory.TYPE, flourSieveRecipes);
        registration.addRecipes(DrinkRecipeCategory.TYPE, drinkRecipes);
        registration.addItemStackInfo(new ItemStack(BakeriesItems.BOTTLE_YEAST),
                Component.translatable("bakeries.bottle_yeast.description"));
        registration.addItemStackInfo(new ItemStack(BakeriesItems.CHEESE_CUBE),
                Component.translatable("bakeries.cheese_cube.description"));
        registration.addItemStackInfo(new ItemStack(BakeriesItems.OLIVE),
                Component.translatable("bakeries.olive.description"));
        registration.addItemStackInfo(new ItemStack(BakeriesItems.RAW_COFFEE_BEAN),
                Component.translatable("bakeries.raw_coffee_bean.description"));
        BakeriesMod.LOGGER.info("[JEI] recipes registered: oven={}, blender={}, dough={}, bread_knife={}, flour_sieve={}, drink={}",
                ovenRecipes.size(), blenderRecipes.size(), doughRecipes.size(), breadKnifeRecipes.size(), flourSieveRecipes.size(), drinkRecipes.size());
    }

    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {
        registration.addTypedRecipeManagerPlugin(OvenRecipeCategory.TYPE, new DynamicSimpleMachineRecipePlugin(BakeriesRecipeTypes.OVEN));
        registration.addTypedRecipeManagerPlugin(BlenderRecipeCategory.TYPE, new DynamicSimpleMachineRecipePlugin(BakeriesRecipeTypes.BLENDER));
        registration.addTypedRecipeManagerPlugin(DoughCraftingRecipeCategory.TYPE, new DynamicSimpleMachineRecipePlugin(BakeriesRecipeTypes.DOUGH_CRAFTING));
        registration.addTypedRecipeManagerPlugin(BreadKnifeRecipeCategory.TYPE, new DynamicSimpleMachineRecipePlugin(BakeriesRecipeTypes.BREAD_KNIFE));
        registration.addTypedRecipeManagerPlugin(FlourSieveRecipeCategory.TYPE, new DynamicSimpleMachineRecipePlugin(BakeriesRecipeTypes.FLOUR_SIEVE));
        registration.addTypedRecipeManagerPlugin(DrinkRecipeCategory.TYPE, new DynamicSimpleMachineRecipePlugin(BakeriesRecipeTypes.DRINK));
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
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(OvenScreen.class, 110, 16, 8, 54, OvenRecipeCategory.TYPE);
        registration.addRecipeClickArea(BlenderScreen.class, 136, 38, 20, 20, BlenderRecipeCategory.TYPE);
        registration.addRecipeClickArea(DoughCraftingTableScreen.class, 118, 30, 22, 18, DoughCraftingRecipeCategory.TYPE);
    }

    private static class DynamicSimpleMachineRecipePlugin implements ISimpleRecipeManagerPlugin<SimpleMachineRecipe> {
        private final RecipeType<SimpleMachineRecipe> type;

        private DynamicSimpleMachineRecipePlugin(RecipeType<SimpleMachineRecipe> type) {
            this.type = type;
        }

        @Override
        public boolean isHandledInput(ITypedIngredient<?> input) {
            return input.getIngredient() instanceof ItemStack;
        }

        @Override
        public boolean isHandledOutput(ITypedIngredient<?> output) {
            return output.getIngredient() instanceof ItemStack;
        }

        @Override
        public List<SimpleMachineRecipe> getRecipesForInput(ITypedIngredient<?> input) {
            if (!(input.getIngredient() instanceof ItemStack stack)) {
                return List.of();
            }
            List<SimpleMachineRecipe> result = new ArrayList<>();
            for (SimpleMachineRecipe recipe : getAllRecipes()) {
                Ingredient ingredient = recipe.getIngredient();
                if (ingredient.test(stack)) {
                    result.add(recipe);
                }
            }
            return result;
        }

        @Override
        public List<SimpleMachineRecipe> getRecipesForOutput(ITypedIngredient<?> output) {
            if (!(output.getIngredient() instanceof ItemStack stack)) {
                return List.of();
            }
            List<SimpleMachineRecipe> result = new ArrayList<>();
            for (SimpleMachineRecipe recipe : getAllRecipes()) {
                ItemStack out = recipe.getResultItem(net.minecraft.core.RegistryAccess.EMPTY);
                if (ItemStack.isSameItemSameTags(out, stack)) {
                    result.add(recipe);
                }
            }
            return result;
        }

        @Override
        public List<SimpleMachineRecipe> getAllRecipes() {
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
            return manager.getAllRecipesFor(type);
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
                ResourceLocation recipeFile = entry.getKey();
                String recipePath = recipeFile.getPath().substring("recipes/".length(), recipeFile.getPath().length() - ".json".length());
                ResourceLocation recipeId = new ResourceLocation(recipeFile.getNamespace(), recipePath);
                list.add(new SimpleMachineRecipe(recipeId, ingredient, result, result.getCount(), typeId, typeId));
            } catch (Exception ignored) {
            }
        }
        return list;
    }
}
