package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.recipe.FermentationBoxRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

public class BakeriesRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS;
    public static final RegistryObject<RecipeSerializer<?>> FERMENTATION_BOX;

    public BakeriesRecipeSerializers() {
    }

    static {
        RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "bakeries");
        FERMENTATION_BOX = RECIPE_SERIALIZERS.register("fermentation_box", FermentationBoxRecipe.Serializer::new);
    }
}
