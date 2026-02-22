package com.renyigesai.bakeries.jei_recipes;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.recipe.BreadKnifeRecipe;
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

public class BreadKnifeRecipeCategory implements IRecipeCategory<BreadKnifeRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "bread_knife");//配方id
    public static final ResourceLocation TEXTURE = new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei/jei_single_recipe.png");//配方gui贴图路径

    public final IDrawable back;
    public final IDrawable icon;

    public BreadKnifeRecipeCategory(IGuiHelper helper) {
        this.back = helper.createDrawable(TEXTURE,0, 0, 109, 21);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(BakeriesItems.BREAD_KNIFE.get()));
    }

    @Override
    public RecipeType<BreadKnifeRecipe> getRecipeType() {
        return BakeryJeiPlugin.BREAD_KNIFE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.bread_knife");
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
    public void setRecipe(IRecipeLayoutBuilder builder, BreadKnifeRecipe recipe, IFocusGroup iFocusGroup) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        //添加一个原料槽
        builder.addSlot(RecipeIngredientRole.INPUT,26,2).addItemStacks(List.of(recipeIngredients.get(0).getItems()));
        //添加输出槽
//        builder.addSlot(RecipeIngredientRole.OUTPUT,74,2).addItemStack(recipe.getResultItem(null));
        NonNullList<ItemStack> output = recipe.getOutput();
        for (int i = 0; i < output.size(); i++) {
            builder.addSlot(RecipeIngredientRole.OUTPUT,74 + (i * 16),2).addItemStack(output.get(i));
        }
        //添加图标
        builder.addSlot(RecipeIngredientRole.OUTPUT,3,3).addItemStack(new ItemStack(BakeriesItems.BREAD_KNIFE.get()));
    }
}
