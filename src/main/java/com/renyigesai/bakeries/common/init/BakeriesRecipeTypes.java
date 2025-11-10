package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.recipe.BlenderRecipe;
import com.renyigesai.bakeries.common.recipe.BreadKnifeRecipe;
import com.renyigesai.bakeries.common.recipe.DoughCraftingRecipe;
import com.renyigesai.bakeries.common.recipe.FlourSieveRecipe;
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

    @SubscribeEvent
    public static void register(FMLConstructModEvent event) {
        event.enqueueWork(() -> {
            /*烤炉*/
            SERIALIZERS.register(OvenRecipe.ID, () -> OvenRecipe.Serializer.INSTANCE);
            RECIPE_TYPE.register(OvenRecipe.ID, () -> OvenRecipe.Type.INSTANCE);
            /*面包刀*/
            SERIALIZERS.register(BreadKnifeRecipe.ID, () -> BreadKnifeRecipe.Serializer.INSTANCE);
            RECIPE_TYPE.register(BreadKnifeRecipe.ID, () -> BreadKnifeRecipe.Type.INSTANCE);
            /*面粉筛*/
            SERIALIZERS.register(FlourSieveRecipe.ID, () -> FlourSieveRecipe.Serializer.INSTANCE);
            RECIPE_TYPE.register(FlourSieveRecipe.ID, () -> FlourSieveRecipe.Type.INSTANCE);
            /*搅拌机*/
            SERIALIZERS.register(BlenderRecipe.ID, () -> BlenderRecipe.Serializer.INSTANCE);
            RECIPE_TYPE.register(BlenderRecipe.ID, () -> BlenderRecipe.Type.INSTANCE);
        });
    }
    public static void getRegister(IEventBus bus){
        SERIALIZERS.register(bus);
        RECIPE_TYPE.register(bus);
    }

    public static class JEI {
        public static final mezz.jei.api.recipe.RecipeType<RecipeHolder<DoughCraftingRecipe>> DOUGH_CRAFTING = mezz.jei.api.recipe.RecipeType.createFromVanilla(DOUGH_CRAFTING_TYPE.get());
        public static final mezz.jei.api.recipe.RecipeType<RecipeHolder<OvenRecipe>> OVEN = mezz.jei.api.recipe.RecipeType.createFromVanilla(OvenRecipe.Type.INSTANCE);
        public static final mezz.jei.api.recipe.RecipeType<RecipeHolder<BlenderRecipe>> BLENDER = mezz.jei.api.recipe.RecipeType.createFromVanilla(BlenderRecipe.Type.INSTANCE);
    }
}
