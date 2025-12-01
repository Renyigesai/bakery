package com.renyigesai.bakeries.integration.jei.category;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.common.recipe.BreadKnifeRecipe;
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

import java.util.List;

public class BreadKnifeRecipeCategory implements IRecipeCategory<RecipeHolder<BreadKnifeRecipe>> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "textures/gui/jei_single_recipe.png");//配方gui贴图路径

    public final IDrawable back;
    public final IDrawable icon;

    public BreadKnifeRecipeCategory(IGuiHelper helper) {
        this.back = helper.createDrawable(TEXTURE,0, 0, 93, 21);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(BakeriesItems.BREAD_KNIFE.get()));
    }


    @Override
    public RecipeType<RecipeHolder<BreadKnifeRecipe>> getRecipeType() {
        return BakeriesRecipeTypes.JEI.BREAD_KNIFE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.bakeries.bread_knife");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<BreadKnifeRecipe> recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> recipeIngredients = recipe.value().getIngredients();
        //添加一个原料槽
        builder.addSlot(RecipeIngredientRole.INPUT,26,2).addItemStacks(List.of(recipeIngredients.getFirst().getItems()));
        //添加输出槽
        builder.addSlot(RecipeIngredientRole.OUTPUT,74,2).addItemStack(recipe.value().getResultItem(null));
        //添加图标
        builder.addSlot(RecipeIngredientRole.OUTPUT,3,3).addItemStack(new ItemStack(BakeriesItems.BREAD_KNIFE.get()));
    }

    @SuppressWarnings("removal")
    @Override
    public IDrawable getBackground() {
        return this.back;
    }
}
