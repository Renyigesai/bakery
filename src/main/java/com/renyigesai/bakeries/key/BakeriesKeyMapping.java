package com.renyigesai.bakeries.key;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class BakeriesKeyMapping {
    public static KeyMapping place;
    @OnlyIn(Dist.CLIENT)
    public static void register(final FMLClientSetupEvent event){
        place = create("xuixian_menu", GLFW.GLFW_KEY_LEFT_SHIFT);
        registerKeyBinding(place);
    }
    private static KeyMapping create(String name, int key){
        return new KeyMapping("key." + BakeriesMod.MODID + "." + name, key, Component.translatable("key.bakeries."+BakeriesMod.MODID).getString());
    }
    public static synchronized void registerKeyBinding(KeyMapping key) {
        Minecraft.getInstance().options.keyMappings = ArrayUtils.add(Minecraft.getInstance().options.keyMappings, key);
    }
}
