package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.recipe.FermentationBoxRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeriesRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES;
    public static final RegistryObject<RecipeType<FermentationBoxRecipe>> FERMENTATION_BOX;

    public BakeriesRecipeTypes() {
    }

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<T>() {
            public String toString() {
                return "bakeries:" + identifier;
            }
        };
    }

    static {
        RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, "bakeries");
        FERMENTATION_BOX = RECIPE_TYPES.register("fermentation_box", () -> registerRecipeType("fermentation_box"));
    }
}
