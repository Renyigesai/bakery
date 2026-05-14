package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public final class BakeriesRecipeTypes {
    private static final ResourceLocation OVEN_ID = new ResourceLocation(BakeriesMod.MODID, "oven");
    private static final ResourceLocation BLENDER_ID = new ResourceLocation(BakeriesMod.MODID, "blender");
    private static final ResourceLocation DOUGH_ID = new ResourceLocation(BakeriesMod.MODID, "dough_crafting");

    public static final RecipeType<SimpleMachineRecipe> OVEN = registerType("oven");
    public static final RecipeType<SimpleMachineRecipe> BLENDER = registerType("blender");
    public static final RecipeType<SimpleMachineRecipe> DOUGH_CRAFTING = registerType("dough_crafting");

    public static final RecipeSerializer<SimpleMachineRecipe> OVEN_SERIALIZER = registerSerializer("oven",
            new SimpleMachineRecipe.Serializer((id, ingredient, result, count) ->
                    new SimpleMachineRecipe(id, ingredient, result, count, OVEN_ID, OVEN_ID)));

    public static final RecipeSerializer<SimpleMachineRecipe> BLENDER_SERIALIZER = registerSerializer("blender",
            new SimpleMachineRecipe.Serializer((id, ingredient, result, count) ->
                    new SimpleMachineRecipe(id, ingredient, result, count, BLENDER_ID, BLENDER_ID)));

    public static final RecipeSerializer<SimpleMachineRecipe> DOUGH_CRAFTING_SERIALIZER = registerSerializer("dough_crafting",
            new SimpleMachineRecipe.Serializer((id, ingredient, result, count) ->
                    new SimpleMachineRecipe(id, ingredient, result, count, DOUGH_ID, DOUGH_ID)));

    private BakeriesRecipeTypes() {
    }

    public static void init() {
        BakeriesMod.LOGGER.info("Registered Bakeries recipe types.");
    }

    private static RecipeType<SimpleMachineRecipe> registerType(String path) {
        ResourceLocation key = new ResourceLocation(BakeriesMod.MODID, path);
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, key, new RecipeType<>() {
            @Override
            public String toString() {
                return key.toString();
            }
        });
    }

    private static RecipeSerializer<SimpleMachineRecipe> registerSerializer(String path, RecipeSerializer<SimpleMachineRecipe> serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(BakeriesMod.MODID, path), serializer);
    }
}
