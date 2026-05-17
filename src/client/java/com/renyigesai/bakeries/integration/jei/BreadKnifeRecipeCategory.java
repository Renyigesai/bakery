package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.recipe.MultiOutputSingleItemRecipe;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class BreadKnifeRecipeCategory implements IRecipeCategory<SimpleMachineRecipe> {
    public static final RecipeType<SimpleMachineRecipe> TYPE =
            RecipeType.create(BakeriesMod.MODID, "bread_knife", SimpleMachineRecipe.class);

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BakeriesMod.MODID, "textures/gui/jei_single_recipe.png");
    private final IDrawable background;
    private final IDrawable icon;

    public BreadKnifeRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 109, 21);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BakeriesItems.BREAD_KNIFE));
    }

    @Override
    public @NotNull RecipeType<SimpleMachineRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("container.bakeries.bread_knife");
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
    public void draw(SimpleMachineRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SimpleMachineRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 26, 2).addIngredients(recipe.getIngredient());
        if (recipe instanceof MultiOutputSingleItemRecipe multiOutputRecipe) {
            int[][] outputPositions = new int[][] { {74, 2}, {92, 2} };
            int index = 0;
            for (ItemStack result : multiOutputRecipe.getAllResults()) {
                if (index >= outputPositions.length) {
                    break;
                }
                if (!result.isEmpty()) {
                    builder.addSlot(RecipeIngredientRole.OUTPUT, outputPositions[index][0], outputPositions[index][1])
                            .addItemStack(result);
                    index++;
                }
            }
        } else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 74, 2).addItemStack(recipe.getResultItem(net.minecraft.core.RegistryAccess.EMPTY));
        }
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 3, 3).addItemStack(new ItemStack(BakeriesItems.BREAD_KNIFE));
    }
}
