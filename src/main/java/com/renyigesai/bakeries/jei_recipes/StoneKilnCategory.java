package com.renyigesai.bakeries.jei_recipes;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.init.BakeriesParticleTypes;
import com.renyigesai.bakeries.recipe.StoneKilnRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StoneKilnCategory implements IRecipeCategory<StoneKilnRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "stone_kiln");
    public static final ResourceLocation TEXTURE =
            new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_stone_kiln.png");

    public final IDrawable back;
    public final IDrawable icon;

    public StoneKilnCategory(IGuiHelper helper) {
        this.back = helper.createDrawable(TEXTURE,0, 0, 148, 78);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(BakeriesItems.STONE_KILN.get()));
    }

    @Override
    public RecipeType<StoneKilnRecipe> getRecipeType() {
        return BakeryJeiPlugin.STONE_KILN_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.stone_kiln");
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
    public void draw(StoneKilnRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        int length = recipe.getTime().length;
        if (length > 1){
            String string = "X" + (length - 1);
            guiGraphics.drawString(minecraft.font,string,98,48, 16777215);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, StoneKilnRecipe recipe, IFocusGroup iFocusGroup) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        builder.addSlot(RecipeIngredientRole.INPUT,72,32).addItemStacks(List.of(recipeIngredients.get(0).getItems()));
        builder.addSlot(RecipeIngredientRole.OUTPUT,119,32).addItemStack(recipe.getResultItem(null));
    }
}
