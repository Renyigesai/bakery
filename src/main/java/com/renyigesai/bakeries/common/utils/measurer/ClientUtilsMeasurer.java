package com.renyigesai.bakeries.common.utils.measurer;

import net.minecraft.client.Minecraft;

public class ClientUtilsMeasurer implements IClientUtilsMeasurer{
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
}
