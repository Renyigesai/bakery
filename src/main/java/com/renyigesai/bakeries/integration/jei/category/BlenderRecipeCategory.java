package com.renyigesai.bakeries.integration.jei.category;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.common.recipe.BlenderRecipe;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class BlenderRecipeCategory implements IRecipeCategory<RecipeHolder<BlenderRecipe>>{

    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "blender");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "textures/gui/jei_blender_gui.png");

    public final IDrawable back;
    public final IDrawable icon;

    public BlenderRecipeCategory(IGuiHelper helper) {
        this.back = helper.createDrawable(TEXTURE,0, 0, 90, 69);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(BakeriesItems.BLENDER.get()));
    }

    @SuppressWarnings("removal")
    @Override
    public @Nullable IDrawable getBackground() {
        return back;
    }

    @Override
    public RecipeType<RecipeHolder<BlenderRecipe>> getRecipeType() {
        return BakeriesRecipeTypes.JEI.BLENDER;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.bakeries.blender");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<BlenderRecipe> recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> recipeIngredients = recipe.value().getInputItems();
        int borderSlotSize = 18;
        //x和y轴的初始坐标，取值为gui贴图的x,y初始位置减一
        int x = 4;
        int y = 7;
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
        //添加容器槽
        builder.addSlot(RecipeIngredientRole.INPUT,67,8).addItemStack(recipe.value().getContainer());
        //添加输出槽
        builder.addSlot(RecipeIngredientRole.OUTPUT,67,43).addItemStack(recipe.value().getResultItem(null));
    }
}
