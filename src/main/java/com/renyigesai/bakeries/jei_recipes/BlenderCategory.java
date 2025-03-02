package com.renyigesai.bakeries.jei_recipes;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.recipe.blender.BlenderRecipe;
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

import java.util.Arrays;

public class BlenderCategory implements IRecipeCategory<BlenderRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "blender");
    public static final ResourceLocation TEXTURE =
            new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_blender_gui.png");

    public final IDrawable back;
    public final IDrawable icon;

    public BlenderCategory(IGuiHelper helper) {
        this.back = helper.createDrawable(TEXTURE,0, 0, 125, 86);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(BakeriesItems.BLENDER.get()));
    }

    @Override
    public RecipeType<BlenderRecipe> getRecipeType() {
        return BakeryJeiPlugin.BLENDER_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.blender");
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
    public void setRecipe(IRecipeLayoutBuilder builder, BlenderRecipe recipe, IFocusGroup iFocusGroup) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        int borderSlotSize = 18;
        //x和y轴的初始坐标，取值为gui贴图的x,y初始位置减一
        int x = 18;
        int y = 16;
        //添加原料槽
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                int inputIndex = row * 3 + column;
                if (inputIndex < recipeIngredients.size()) {
                    builder.addSlot(RecipeIngredientRole.INPUT, x + (column * borderSlotSize) + 1, y + (row * borderSlotSize) + 1)
                            .addItemStacks(Arrays.asList(recipeIngredients.get(inputIndex).getItems()));
                }
            }
        }
        //添加输出槽
        builder.addSlot(RecipeIngredientRole.OUTPUT,90,35).addItemStack(recipe.getResultItem(null));
    }
}
