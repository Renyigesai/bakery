package com.renyigesai.bakeries.util.measurer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.SeparateTransformsModel;

import java.util.function.Function;

public class ClientUtilsMeasurer implements IUtilsMeasurer {
    public int getLength(String string,int maxLength){
        if (string == null || maxLength == 0){
            throw new IllegalArgumentException("Text cannot be null or Max width must be positive");
        }
        int width = 0;
        int length = 0;
        Minecraft mc = Minecraft.getInstance();
        for (int i = 0; i < string.length(); i++) {
            char _char = string.charAt(i);
            width += mc.font.width(String.valueOf(_char));
            length ++;
            if (width > maxLength){
                return length - 1;
            }
        }
        return maxLength;
    }

    public int getLength(String string){
        if (string == null){
            throw new IllegalArgumentException("Text cannot be null or Max width must be positive");
        }
        int width = 0;
        Minecraft mc = Minecraft.getInstance();
        for (int i = 0; i < string.length(); i++) {
            char _char = string.charAt(i);
            width += mc.font.width(String.valueOf(_char));
        }
        return width;
    }

    public Font getCustomFont(ResourceLocation fontLocation, boolean filterFishyGlyphs){
        FontSet fontSet = Minecraft.getInstance().font.getFontSet(fontLocation);
        Function<ResourceLocation, FontSet> function = location -> fontSet;
        return new Font(function,filterFishyGlyphs);
    }

    public int getPixelLength(String string){
        if (string == null){
            throw new IllegalArgumentException("The text cannot be empty and its length must be greater than 0 and less than 1024.");
        }
        int width = 0;
        Minecraft mc = Minecraft.getInstance();
        for (int i = 0; i < string.length(); i++) {
            char _char = string.charAt(i);
            width += mc.font.width(String.valueOf(_char));
        }
        return width;
    }

    public boolean isItem3D(ItemStack stack){
        boolean isBlock = stack.getItem() instanceof BlockItem;
        BakedModel itemModel = Minecraft.getInstance().getItemRenderer().getModel(stack, null, null, 0);
        boolean is3d;
        if (itemModel instanceof SeparateTransformsModel.Baked){
            is3d = isBlock && (itemModel.isGui3d() || itemModel.applyTransform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND,new PoseStack(),false).isGui3d());
        }else {
            is3d = isBlock && itemModel.isGui3d();
        }
        return is3d;
    }
}
