package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.recipe.BreadKnifeRecipe;
import com.renyigesai.bakeries.common.recipe.FlourSieveRecipe;
import com.renyigesai.bakeries.common.recipe.oven.OvenRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.neoforge.registries.DeferredRegister;


@EventBusSubscriber(modid = BakeriesMod.MODID)
public class BakeriesRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, BakeriesMod.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE = DeferredRegister.create(Registries.RECIPE_TYPE, BakeriesMod.MODID);

    @SuppressWarnings("removal")
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

        });
    }
    public static void getRegister(IEventBus bus){
        SERIALIZERS.register(bus);
        RECIPE_TYPE.register(bus);
    }
}
