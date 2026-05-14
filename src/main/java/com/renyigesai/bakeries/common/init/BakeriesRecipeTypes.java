package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.recipe.*;
import com.renyigesai.bakeries.common.recipe.oven.OvenRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


@EventBusSubscriber(modid = BakeriesMod.MODID)
public class BakeriesRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, BakeriesMod.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE = DeferredRegister.create(Registries.RECIPE_TYPE, BakeriesMod.MODID);

    /*面胚制作台*/
    public static final Supplier<RecipeType<DoughCraftingRecipe>> DOUGH_CRAFTING_TYPE = RECIPE_TYPE.register("dough_crafting_table", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID,"dough_crafting_table")));
    public static final Supplier<RecipeSerializer<?>> DOUGH_CRAFTING_SERIALIZERS = SERIALIZERS.register("dough_crafting_table", ()-> new SingleItemRecipe.Serializer<>(DoughCraftingRecipe::new));

    public static final Supplier<RecipeType<BreadKnifeRecipe>> BREAD_KNIFE_TYPE = RECIPE_TYPE.register("bread_knife", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID,"bread_knife")));
    public static final Supplier<RecipeSerializer<?>> BREAD_KNIFE_SERIALIZERS = SERIALIZERS.register("bread_knife", ()-> new MultiOutputSingleItemRecipe.Serializer<>(BreadKnifeRecipe::new));

    public static final Supplier<RecipeType<FermentationBoxRecipe>> FERMENTATION_BOX_TYPE = RECIPE_TYPE.register("fermentation_box", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID,"fermentation_box")));
    public static final Supplier<RecipeSerializer<?>> FERMENTATION_BOX_SERIALIZERS = SERIALIZERS.register("fermentation_box", ()-> new MultiOutputSingleItemRecipe.Serializer<>(FermentationBoxRecipe::new));

    @SubscribeEvent
    public static void register(FMLConstructModEvent event) {
        event.enqueueWork(() -> {
            /*烤炉*/
            SERIALIZERS.register(OvenRecipe.ID, () -> OvenRecipe.Serializer.INSTANCE);
            RECIPE_TYPE.register(OvenRecipe.ID, () -> OvenRecipe.Type.INSTANCE);
            /*面粉筛*/
            SERIALIZERS.register(FlourSieveRecipe.ID, () -> FlourSieveRecipe.Serializer.INSTANCE);
            RECIPE_TYPE.register(FlourSieveRecipe.ID, () -> FlourSieveRecipe.Type.INSTANCE);
            /*搅拌机*/
            SERIALIZERS.register(BlenderRecipe.ID, () -> BlenderRecipe.Serializer.INSTANCE);
            RECIPE_TYPE.register(BlenderRecipe.ID, () -> BlenderRecipe.Type.INSTANCE);
            /*饮料*/
            SERIALIZERS.register(DrinkRecipe.ID, () -> DrinkRecipe.Serializer.INSTANCE);
            RECIPE_TYPE.register(DrinkRecipe.ID, () -> DrinkRecipe.Type.INSTANCE);
        });
    }
    public static void getRegister(IEventBus bus){
        SERIALIZERS.register(bus);
        RECIPE_TYPE.register(bus);
    }

    public static class JEI {
        public static final mezz.jei.api.recipe.RecipeType<RecipeHolder<DoughCraftingRecipe>> DOUGH_CRAFTING = mezz.jei.api.recipe.RecipeType.createFromVanilla(DOUGH_CRAFTING_TYPE.get());
        public static final mezz.jei.api.recipe.RecipeType<RecipeHolder<BreadKnifeRecipe>> BREAD_KNIFE = mezz.jei.api.recipe.RecipeType.createFromVanilla(BREAD_KNIFE_TYPE.get());
        public static final mezz.jei.api.recipe.RecipeType<RecipeHolder<OvenRecipe>> OVEN = mezz.jei.api.recipe.RecipeType.createFromVanilla(OvenRecipe.Type.INSTANCE);
        public static final mezz.jei.api.recipe.RecipeType<RecipeHolder<BlenderRecipe>> BLENDER = mezz.jei.api.recipe.RecipeType.createFromVanilla(BlenderRecipe.Type.INSTANCE);
        public static final mezz.jei.api.recipe.RecipeType<RecipeHolder<FlourSieveRecipe>> FLOUR_SIEVE = mezz.jei.api.recipe.RecipeType.createFromVanilla(FlourSieveRecipe.Type.INSTANCE);
        public static final mezz.jei.api.recipe.RecipeType<RecipeHolder<DrinkRecipe>> DRINK = mezz.jei.api.recipe.RecipeType.createFromVanilla(DrinkRecipe.Type.INSTANCE);
        public static final mezz.jei.api.recipe.RecipeType<RecipeHolder<FermentationBoxRecipe>> FERMENTATION_BOX = mezz.jei.api.recipe.RecipeType.createFromVanilla(FERMENTATION_BOX_TYPE.get());
    }
}
