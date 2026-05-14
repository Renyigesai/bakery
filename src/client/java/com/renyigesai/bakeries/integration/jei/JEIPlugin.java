package com.renyigesai.bakeries.integration.jei;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.screen.BlenderScreen;
import com.renyigesai.bakeries.screen.DoughCraftingTableScreen;
import com.renyigesai.bakeries.screen.OvenScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation UID = new ResourceLocation(BakeriesMod.MODID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.OVEN), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.BLENDER), RecipeTypes.CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(BakeriesBlocks.DOUGH_CRAFTING_TABLE), RecipeTypes.CRAFTING);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(OvenScreen.class, 110, 16, 8, 54, RecipeTypes.SMELTING);
        registration.addRecipeClickArea(BlenderScreen.class, 136, 38, 20, 20, RecipeTypes.CRAFTING);
        registration.addRecipeClickArea(DoughCraftingTableScreen.class, 118, 30, 22, 18, RecipeTypes.CRAFTING);
    }
}
