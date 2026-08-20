package com.renyigesai.bakeries.common.recipe.oven;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

public class OvenRecipe extends AbstractOvenRecipe{
	public static final String ID = "oven";
	public OvenRecipe(ItemStack output, int time, int minTemperature, int maxTemperature, int perfectTemperature, Ingredient recipeItems) {
		super(Type.INSTANCE, Serializer.INSTANCE, output, time, minTemperature, maxTemperature, perfectTemperature, recipeItems);
	}

	public enum Type implements RecipeType<OvenRecipe> {
		INSTANCE
	}
	public static class Serializer implements RecipeSerializer<OvenRecipe> {
		public static final Serializer INSTANCE = new Serializer(OvenRecipe::new);
		private static Factory<OvenRecipe> factory;
		public Serializer(Factory<OvenRecipe> pFactory) {
			factory = pFactory;
		}
		public static final MapCodec<OvenRecipe> CODEC = RecordCodecBuilder.mapCodec(
				recipeInstance -> recipeInstance.group(
								ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
								Codec.INT.fieldOf("time").forGetter(recipe -> recipe.time),
								Codec.INT.fieldOf("min").forGetter(recipe -> recipe.minTemperature),
								Codec.INT.fieldOf("max").forGetter(recipe -> recipe.maxTemperature),
								Codec.INT.optionalFieldOf("perfect",0).forGetter(recipe -> recipe.perfectTemperature),
								Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.recipeItems)
						)
						.apply(recipeInstance, factory::create)
		);

		public static final StreamCodec<RegistryFriendlyByteBuf, OvenRecipe> STREAM_CODEC = StreamCodec.of(
				Serializer::toNetwork, Serializer::fromNetwork
		);
		@Override
		public @NotNull MapCodec<OvenRecipe> codec() {
			return CODEC;
		}
		@Override
		public @NotNull StreamCodec<RegistryFriendlyByteBuf, OvenRecipe> streamCodec() {
			return STREAM_CODEC;
		}
		private static OvenRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
			int time = buffer.readVarInt();
			int min = buffer.readVarInt();
			int max = buffer.readVarInt();
			int perfect = buffer.readVarInt();
			Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			return factory.create(result, time, min, max, perfect, ingredient);
		}
		private static void toNetwork(RegistryFriendlyByteBuf buffer, OvenRecipe recipe) {
			ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
			buffer.writeVarInt(recipe.time);
			buffer.writeVarInt(recipe.minTemperature);
			buffer.writeVarInt(recipe.maxTemperature);
			buffer.writeVarInt(recipe.perfectTemperature);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.recipeItems);
		}
		interface Factory<T extends AbstractOvenRecipe> {
			T create(ItemStack output, int time, int minTemperature, int maxTemperature, int perfectTemperature, Ingredient recipeItems);
		}
	}
}