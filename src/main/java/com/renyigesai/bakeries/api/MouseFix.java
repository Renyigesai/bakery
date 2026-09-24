package com.renyigesai.bakeries.api;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class MouseFix {
    public static double savedX, savedY;
    private static boolean pending = false;

    public static void mouseSaved(){
        Minecraft mc = Minecraft.getInstance();
        MouseFix.savedX = mc.mouseHandler.xpos();
        MouseFix.savedY = mc.mouseHandler.ypos();
        pending = true;
    }

    public static void mouseRecovery(){
        if (!pending){
            return;
        }
        pending = false;
        Minecraft mc = Minecraft.getInstance();
        try {
            GLFW.glfwSetCursorPos(mc.getWindow().getWindow(), savedX, savedY);
        }catch (Exception exception){
            BakeriesMod.LOGGER.error(exception);
        }
    }
}
