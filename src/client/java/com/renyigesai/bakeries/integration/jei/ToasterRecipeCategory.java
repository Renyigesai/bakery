package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class ToasterRecipeCategory implements IRecipeCategory<CampfireCookingRecipe> {
    public static final RecipeType<CampfireCookingRecipe> TYPE =
            RecipeType.create(BakeriesMod.MODID, "toaster", CampfireCookingRecipe.class);

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_single_recipe.png");
    private final IDrawable background;
    private final IDrawable icon;

    public ToasterRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 82, 34);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BakeriesBlocks.TOASTER));
    }

    @Override
    public @NotNull RecipeType<CampfireCookingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.bakeries.toaster");
    }

    @Override
    public int getWidth() {
        return background.getWidth();
    }

    @Override
    public int getHeight() {
        return background.getHeight();
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(CampfireCookingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);
        int toasterTime = Math.max(1, recipe.getCookingTime() / 3);
        guiGraphics.drawString(Minecraft.getInstance().font,
                Component.translatable("container.bakeries.toaster.time", toasterTime),
                31, 24, 0x555555, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CampfireCookingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 8).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 60, 8).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }
}
