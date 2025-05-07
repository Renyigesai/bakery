package com.renyigesai.bakeries.jei_recipes;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.recipe.BreadKnifeRecipe;
import com.renyigesai.bakeries.recipe.flour_sieve.FlourSieveRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlourSieveRecipeCategory implements IRecipeCategory<FlourSieveRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "flour_sieve");//配方id
    public static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_single_recipe.png");//配方gui贴图路径

    public final IDrawable back;
    public final IDrawable icon;

    public FlourSieveRecipeCategory(IGuiHelper helper) {
        this.back = helper.createDrawable(TEXTURE,0, 0, 93, 21);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(BakeriesItems.FLOUR_SIEVE.get()));
    }

    @Override
    public RecipeType<FlourSieveRecipe> getRecipeType() {
        return BakeryJeiPlugin.FLOUR_SIEVE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.flour_sieve");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @SuppressWarnings("removal")
    @Override
    public IDrawable getBackground() {
        return this.back;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FlourSieveRecipe recipe, IFocusGroup iFocusGroup) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        //添加一个原料槽
        builder.addSlot(RecipeIngredientRole.INPUT,26,2).addItemStacks(List.of(recipeIngredients.get(0).getItems()));
        //添加输出槽
        builder.addSlot(RecipeIngredientRole.OUTPUT,74,2).addItemStack(recipe.getResultItem(null));
        builder.addSlot(RecipeIngredientRole.OUTPUT,3,3).addItemStack(new ItemStack(BakeriesItems.FLOUR_SIEVE.get()));
    }
}
