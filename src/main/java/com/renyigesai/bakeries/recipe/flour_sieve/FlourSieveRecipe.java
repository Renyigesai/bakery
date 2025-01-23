package com.renyigesai.bakeries.recipe.flour_sieve;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nullable;

public class FlourSieveRecipe extends AbstractFlourSieveRecipe {
	public FlourSieveRecipe(ResourceLocation id, ItemStack output, Ingredient recipeItems) {
		super(Type.INSTANCE, Serializer.INSTANCE, id, output, recipeItems);
	}

	public static class Type implements RecipeType<FlourSieveRecipe> {
		private Type() {
		}

		public static final Type INSTANCE = new Type();
		public static final String ID = "flour_sieve";
	}

	public static class Serializer<T extends AbstractFlourSieveRecipe> implements RecipeSerializer<T> {
		public static final Serializer INSTANCE = new Serializer<>(FlourSieveRecipe::new);

		public static final ResourceLocation ID = new ResourceLocation(BakeriesMod.MODID, "flour_sieve");
		private final CookieBaker<T> factory;

		private Serializer(CookieBaker<T> pFactory) {
			this.factory = pFactory;
		}

		@Override
		public T fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
			JsonElement jsonelement = (JsonElement)(GsonHelper.isArrayNode(pSerializedRecipe, "ingredient") ? GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredient") : GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient"));
			Ingredient ingredient = Ingredient.fromJson(jsonelement, false);
			return this.factory.create(pRecipeId, output, ingredient);
		}

		@Override
		public @Nullable T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf buf) {
			int ingredientCount = buf.readInt();//疑似修复代码
			Ingredient ingredient = Ingredient.fromNetwork(buf);
			ItemStack output = buf.readItem();
			return this.factory.create(pRecipeId, output, ingredient);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, T recipe) {
			buf.writeInt(recipe.getIngredients().size());
			for (Ingredient ing : recipe.getIngredients()) {
				ing.toNetwork(buf);
			}
			buf.writeItemStack(recipe.getResultItem(null), false);
		}

		interface CookieBaker<T extends AbstractFlourSieveRecipe> {
			T create(ResourceLocation id, ItemStack output, Ingredient recipeItems);
		}
	}
}
