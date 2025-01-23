package com.renyigesai.bakeries.jei_recipes;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.recipe.dough_crafting_table.DoughCraftingRecipe;
import com.renyigesai.bakeries.recipe.flour_sieve.FlourSieveRecipe;
import com.renyigesai.bakeries.recipe.oven.OvenRecipe;
import com.renyigesai.bakeries.recipe.toaster.ToasterRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = BakeriesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BakeryRecipeTypes {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BakeriesMod.MODID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, BakeriesMod.MODID);

	@SuppressWarnings("removal")
	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		event.enqueueWork(() -> {
			SERIALIZERS.register(bus);
			RECIPE_TYPE.register(bus);
			SERIALIZERS.register(OvenRecipe.Type.ID, () -> OvenRecipe.Serializer.INSTANCE);
			RECIPE_TYPE.register(OvenRecipe.Type.ID, () -> OvenRecipe.Type.INSTANCE);
			SERIALIZERS.register(DoughCraftingRecipe.Type.ID, () -> DoughCraftingRecipe.Serializer.INSTANCE);
			RECIPE_TYPE.register(DoughCraftingRecipe.Type.ID, () -> DoughCraftingRecipe.Type.INSTANCE);
			SERIALIZERS.register(FlourSieveRecipe.Type.ID, () -> FlourSieveRecipe.Serializer.INSTANCE);
			RECIPE_TYPE.register(FlourSieveRecipe.Type.ID, () -> FlourSieveRecipe.Type.INSTANCE);
			SERIALIZERS.register(ToasterRecipe.Type.ID, () -> ToasterRecipe.Serializer.INSTANCE);
			RECIPE_TYPE.register(ToasterRecipe.Type.ID, () -> ToasterRecipe.Type.INSTANCE);
//			SERIALIZERS.register(PizzaRecipe.Type.ID,() ->PizzaRecipe.Serializer.INSTANCE);
//			RECIPE_TYPE.register(PizzaRecipe.Type.ID,() ->PizzaRecipe.Type.INSTANCE);
		});
	}
}
