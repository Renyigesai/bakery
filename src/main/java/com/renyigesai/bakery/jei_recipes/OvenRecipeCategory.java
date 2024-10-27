
package com.renyigesai.bakery.jei_recipes;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.renyigesai.bakery.init.BakeryBlocks;
import com.renyigesai.bakery.recipe.OvenRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;


public class OvenRecipeCategory implements IRecipeCategory<OvenRecipe> {
	public final static ResourceLocation UID = new ResourceLocation("underworld_magic_craftsmanship", "sound_collector_recipe");
	public final static ResourceLocation TEXTURE = new ResourceLocation("underworld_magic_craftsmanship", "textures/screens/jei_gui/sound_collector_jei_menu.png");
	public final static ResourceLocation SOUND_2 = new ResourceLocation("underworld_magic_craftsmanship", "textures/screens/jei_gui/sound_2.png");
	public final static ResourceLocation PROGRESS_1 = new ResourceLocation("underworld_magic_craftsmanship", "textures/screens/jei_gui/progress_1.png");
	protected final IDrawable background;
//	protected final IDrawableStatic staticFlame;
//	protected final IDrawableAnimated animatedFlame;
	private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
//	protected final IDrawableStatic staticFlame1;
//	protected final IDrawableAnimated animatedFlame1;
	protected final IDrawable icon;

	public OvenRecipeCategory(IGuiHelper helper) {//96, 87
		this.background = helper.createDrawable(TEXTURE, 0, 0, 96, 87);
//		this.staticFlame = helper.createDrawable(SOUND_2,0,0,16,60);
//		this.animatedFlame = helper.createAnimatedDrawable(this.staticFlame, 10, IDrawableAnimated.StartDirection.BOTTOM, true);
		this.cachedArrows = CacheBuilder.newBuilder().maximumSize(25L).build(new CacheLoader<Integer, IDrawableAnimated>() {
			public IDrawableAnimated load(Integer cookTime) {
				return helper.drawableBuilder(SOUND_2, 0, 0, 16, 60).buildAnimated(20, IDrawableAnimated.StartDirection.LEFT, false);
			}
		});
//		this.staticFlame1 = helper.createDrawable(PROGRESS_1,0,0,28,22);
//		this.animatedFlame1 = helper.createAnimatedDrawable(this.staticFlame1, 10, IDrawableAnimated.StartDirection.TOP, true);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BakeryBlocks.OVEN.get()));
	}

	@Override
	public mezz.jei.api.recipe.RecipeType<OvenRecipe> getRecipeType() {
		return BakeryJeiPlugin.Oven_Type;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("block.underworld_magic_craftsmanship.sound_collector");
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, OvenRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 55, 17).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55, 61).addItemStack(recipe.getResultItem(null));
	}
}
