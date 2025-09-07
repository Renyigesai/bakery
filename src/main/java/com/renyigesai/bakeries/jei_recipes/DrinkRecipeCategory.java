package com.renyigesai.bakeries.jei_recipes;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.recipe.CoffeeRecipe;
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

import java.util.Arrays;

public class DrinkRecipeCategory implements IRecipeCategory<CoffeeRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "drink");//配方id
    public static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_drink_gui.png");//配方gui贴图路径

    public final IDrawable back;
    public final IDrawable icon;


    public DrinkRecipeCategory(IGuiHelper helper) {
        this.back = helper.createDrawable(TEXTURE,0, 0, 116, 18);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(BakeriesItems.DRINK_CUP.get()));
    }

    @Override
    public RecipeType<CoffeeRecipe> getRecipeType() {
        return BakeryJeiPlugin.DRINK_TYPE;
    }


    @Override
    public Component getTitle() {
        return Component.translatable("container.drink");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @SuppressWarnings("removal")
    @Override
    public IDrawable getBackground() {
        return this.back;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CoffeeRecipe recipe, IFocusGroup iFocusGroup) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        int borderSlotSize = 18;
        //x和y轴的初始坐标，取值为gui贴图的x,y初始位置减一
        int x = 2;
        int y = 2;
        //添加原料槽
        for (int row = 0; row < 4; ++row) {
                if (row < recipeIngredients.size()) {
                    builder.addSlot(RecipeIngredientRole.INPUT, x + (row * borderSlotSize) + 1, y)
                            .addItemStacks(Arrays.asList(recipeIngredients.get(row).getItems()));
                }
        }
        //添加输出槽
        builder.addSlot(RecipeIngredientRole.OUTPUT,99,1).addItemStack(recipe.getResultItem(null));
    }
}
