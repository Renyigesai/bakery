package com.renyigesai.bakery.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.renyigesai.bakery.BakeryMod;
import lombok.Getter;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class OvenRecipe implements Recipe<SimpleContainer> {
	private final ResourceLocation id;
	private final ItemStack output;
	@Getter
	private final int time;
	@Getter
	private final int temperature;
	private final NonNullList<Ingredient> recipeItems;

	public OvenRecipe(ResourceLocation id, ItemStack output, int time, int temperature, NonNullList<Ingredient> recipeItems) {
		this.id = id;
		this.output = output;
        this.time = time;
        this.temperature = temperature;
        this.recipeItems = recipeItems;
	}

	@Override
	public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return !pLevel.isClientSide();
    }

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return recipeItems;
	}

	@Override
	public ItemStack assemble(SimpleContainer pContainer, RegistryAccess access) {
		return output.copy();
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess access) {
		return output.copy();
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeType<?> getType() {
		return Type.INSTANCE;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}

	public static class Type implements RecipeType<OvenRecipe> {
		private Type() {
		}

		public static final Type INSTANCE = new Type();
		public static final String ID = "oven";
	}

	public static class Serializer implements RecipeSerializer<OvenRecipe> {
		public static final Serializer INSTANCE = new Serializer();
		public static final ResourceLocation ID = new ResourceLocation(BakeryMod.MODID, "oven");

		@Override
		public OvenRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
			int time = GsonHelper.getAsInt(pSerializedRecipe, "time");
			int temperature = GsonHelper.getAsInt(pSerializedRecipe, "temperature");
			JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredient");
			NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
			for (int i = 0; i < inputs.size(); i++) {
				inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
			}
			return new OvenRecipe(pRecipeId, output, time, temperature, inputs);
		}

		@Override
		public @Nullable OvenRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			int time = buf.readInt();
			int temperature = buf.readInt();
			NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
			for (int i = 0; i < inputs.size(); i++) {
				inputs.set(i, Ingredient.fromNetwork(buf));
			}
			ItemStack output = buf.readItem();
			return new OvenRecipe(id, output, time, temperature, inputs);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, OvenRecipe recipe) {
			buf.writeInt(recipe.time);
			buf.writeInt(recipe.temperature);
			buf.writeInt(recipe.getIngredients().size());
			for (Ingredient ing : recipe.getIngredients()) {
				ing.toNetwork(buf);
			}
			buf.writeItemStack(recipe.getResultItem(null), false);
		}
	}
}