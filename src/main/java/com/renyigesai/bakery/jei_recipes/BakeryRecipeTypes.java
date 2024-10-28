package com.renyigesai.bakery.jei_recipes;

import com.renyigesai.bakery.BakeryMod;
import com.renyigesai.bakery.recipe.OvenRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = BakeryMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BakeryRecipeTypes {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BakeryMod.MODID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, BakeryMod.MODID);
	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		event.enqueueWork(() -> {
			SERIALIZERS.register(bus);
			RECIPE_TYPE.register(bus);
			SERIALIZERS.register("oven", () -> OvenRecipe.Serializer.INSTANCE);
			RECIPE_TYPE.register("oven", () -> OvenRecipe.Type.INSTANCE);


		});
	}
}
