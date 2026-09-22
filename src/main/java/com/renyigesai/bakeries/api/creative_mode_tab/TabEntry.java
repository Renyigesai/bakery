package com.renyigesai.bakeries.api.creative_mode_tab;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

public class TabEntry {
    public final Supplier<CreativeModeTab> tab;
    public ResourceLocation id = new ResourceLocation(BakeriesMod.MODID,"stacking_creative_mode_tab");
    public Component title = Component.empty();
    public int titleX = 0;
    public int titleY = 0;
    public int amountOfSheets = 0;
    public int duration = 0;
    public boolean leftJustifying = true;

    public TabEntry(Supplier<CreativeModeTab> tab) {
        this.tab = tab;
    }

    public static TabEntry of(Supplier<CreativeModeTab> tab){
        return new TabEntry(tab);
    }

    public ResourceLocation getTexture(){
        return new ResourceLocation(id.getNamespace(),"textures/gui/" + id.getPath() + ".png");
    }

    /**添加一张贴图*/
    public TabEntry texture(ResourceLocation path){
        this.id = path;
        return this;
    }

    /**调整标题的显示位置*/
    public TabEntry titlePos(int x,int y){
        this.titleX = x;
        this.titleY = y;
        return this;
    }

    /**添加自定义标题，添加后将覆盖getDisplayName()*/
    public TabEntry title(Component title){
        this.title = title;
        return this;
    }

    /**设置贴图是否为动态贴图*/
    public TabEntry animation(int amountOfSheets,int duration){
        this.amountOfSheets = amountOfSheets;
        this.duration = duration;
        return this;
    }

    /**设置标题位置为右对齐*/
    public TabEntry rightJustifying(){
        this.leftJustifying = false;
        return this;
    }


}
