package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.recipe.BlenderRecipe;
import com.renyigesai.bakeries.recipe.CoffeeRecipe;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

@SuppressWarnings("unused")
public final class BakeriesRecipeTypes {
    private static final ResourceLocation OVEN_ID = new ResourceLocation(BakeriesMod.MODID, "oven");
    private static final ResourceLocation BLENDER_ID = new ResourceLocation(BakeriesMod.MODID, "blender");
    private static final ResourceLocation DOUGH_ID = new ResourceLocation(BakeriesMod.MODID, "dough_crafting");
    private static final ResourceLocation BREAD_KNIFE_ID = new ResourceLocation(BakeriesMod.MODID, "bread_knife");
    private static final ResourceLocation FLOUR_SIEVE_ID = new ResourceLocation(BakeriesMod.MODID, "flour_sieve");
    private static final ResourceLocation COFFEE_ID = new ResourceLocation(BakeriesMod.MODID, "coffee");
    private static final ResourceLocation FERMENTATION_BOX_ID = new ResourceLocation(BakeriesMod.MODID, "fermentation_box");

    public static final RecipeType<SimpleMachineRecipe> OVEN = registerType("oven");
    public static final RecipeType<SimpleMachineRecipe> BLENDER = registerType("blender");
    public static final RecipeType<SimpleMachineRecipe> DOUGH_CRAFTING = registerType("dough_crafting");
    public static final RecipeType<SimpleMachineRecipe> BREAD_KNIFE = registerType("bread_knife");
    public static final RecipeType<SimpleMachineRecipe> FLOUR_SIEVE = registerType("flour_sieve");
    public static final RecipeType<CoffeeRecipe> COFFEE = registerType("coffee");
    public static final RecipeType<SimpleMachineRecipe> FERMENTATION_BOX = registerType("fermentation_box");

    public static final RecipeSerializer<SimpleMachineRecipe> OVEN_SERIALIZER = registerSerializer("oven",
            new SimpleMachineRecipe.Serializer((id, ingredient, result, count) ->
                    new SimpleMachineRecipe(id, ingredient, result, count, OVEN_ID, OVEN_ID)));

    public static final RecipeSerializer<SimpleMachineRecipe> BLENDER_SERIALIZER = registerSerializer("blender",
            new BlenderRecipe.Serializer());

    public static final RecipeSerializer<SimpleMachineRecipe> DOUGH_CRAFTING_SERIALIZER = registerSerializer("dough_crafting",
            new SimpleMachineRecipe.Serializer((id, ingredient, result, count) ->
                    new SimpleMachineRecipe(id, ingredient, result, count, DOUGH_ID, DOUGH_ID)));

    public static final RecipeSerializer<SimpleMachineRecipe> BREAD_KNIFE_SERIALIZER = registerSerializer("bread_knife",
            new SimpleMachineRecipe.Serializer((id, ingredient, result, count) ->
                    new SimpleMachineRecipe(id, ingredient, result, count, BREAD_KNIFE_ID, BREAD_KNIFE_ID)));

    public static final RecipeSerializer<SimpleMachineRecipe> FLOUR_SIEVE_SERIALIZER = registerSerializer("flour_sieve",
            new SimpleMachineRecipe.Serializer((id, ingredient, result, count) ->
                    new SimpleMachineRecipe(id, ingredient, result, count, FLOUR_SIEVE_ID, FLOUR_SIEVE_ID)));

    public static final RecipeSerializer<CoffeeRecipe> COFFEE_SERIALIZER = registerSerializer("coffee",
            new CoffeeRecipe.Serializer(COFFEE_ID, COFFEE_ID));

    public static final RecipeSerializer<SimpleMachineRecipe> FERMENTATION_BOX_SERIALIZER = registerSerializer("fermentation_box",
            new SimpleMachineRecipe.Serializer((id, ingredient, result, count) ->
                    new SimpleMachineRecipe(id, ingredient, result, count, FERMENTATION_BOX_ID, FERMENTATION_BOX_ID)));

    private BakeriesRecipeTypes() {
    }

    public static void init() {
        BakeriesMod.LOGGER.info("Registered Bakeries recipe types.");
    }

    private static <T extends net.minecraft.world.item.crafting.Recipe<?>> RecipeType<T> registerType(String path) {
        ResourceLocation key = new ResourceLocation(BakeriesMod.MODID, path);
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, key, new RecipeType<>() {
            @Override
            public String toString() {
                return key.toString();
            }
        });
    }

    private static <T extends RecipeSerializer<?>> T registerSerializer(String path, T serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(BakeriesMod.MODID, path), serializer);
    }
}
