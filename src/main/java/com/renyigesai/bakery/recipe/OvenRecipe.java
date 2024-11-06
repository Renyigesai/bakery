package com.renyigesai.bakery.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.renyigesai.bakery.BakeryMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nullable;

public class OvenRecipe extends AbstractOvenRecipe{
	public OvenRecipe(ResourceLocation id, ItemStack output, int time, int min_temperature, int max_temperature, Ingredient recipeItems) {
		super(Type.INSTANCE, Serializer.INSTANCE, id, output, time, min_temperature, max_temperature, recipeItems);
	}

	public static class Type implements RecipeType<OvenRecipe> {
		private Type() {
		}

		public static final Type INSTANCE = new Type();
		public static final String ID = "oven";
	}

	public static class Serializer <T extends AbstractOvenRecipe> implements RecipeSerializer<T> {
		public static final Serializer INSTANCE = new Serializer<>(OvenRecipe::new);
		public static final ResourceLocation ID = new ResourceLocation(BakeryMod.MODID, "oven");
		private final Serializer.CookieBaker<T> factory;

		public Serializer(Serializer.CookieBaker<T> pFactory) {

			this.factory = pFactory;
		}
		@Override
		public T fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
			int time = GsonHelper.getAsInt(pSerializedRecipe, "time");
			int min_temperature = GsonHelper.getAsInt(pSerializedRecipe, "min_temperature");
			int max_temperature = GsonHelper.getAsInt(pSerializedRecipe, "max_temperature");
			JsonElement jsonelement = (JsonElement)(GsonHelper.isArrayNode(pSerializedRecipe, "ingredient") ? GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredient") : GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient"));
			Ingredient ingredient = Ingredient.fromJson(jsonelement, false);
			return this.factory.create(pRecipeId, output, time, min_temperature, max_temperature, ingredient);
		}

		@Override
		public @Nullable T fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			int time = buf.readInt();
			int min_temperature = buf.readInt();
			int max_temperature = buf.readInt();
			Ingredient ingredient = Ingredient.fromNetwork(buf);
			ItemStack output = buf.readItem();
			return this.factory.create(id, output, time, min_temperature, max_temperature, ingredient);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, T recipe) {
			buf.writeInt(recipe.time);
			buf.writeInt(recipe.min_temperature);
			buf.writeInt(recipe.max_temperature);
			buf.writeInt(recipe.getIngredients().size());
			for (Ingredient ing : recipe.getIngredients()) {
				ing.toNetwork(buf);
			}
			buf.writeItemStack(recipe.getResultItem(null), false);
		}
		interface CookieBaker<T extends AbstractOvenRecipe> {
			T create(ResourceLocation id, ItemStack output, int time, int min_temperature, int max_temperature, Ingredient recipeItems);
		}
	}
}