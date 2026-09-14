package com.renyigesai.bakeries.api.creative_mode_tab;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

public class TabEntry {
    public final Supplier<CreativeModeTab> tab;
    public final ResourceLocation id;

    public TabEntry(Supplier<CreativeModeTab> tab, ResourceLocation id) {
        this.tab = tab;
        this.id = id;
    }

    public TabEntry(Supplier<CreativeModeTab> tab) {
        this.tab = tab;
        this.id = new ResourceLocation(BakeriesMod.MODID,"stacking_creative_mode_tab");
    }

    public static TabEntry of(Supplier<CreativeModeTab> tab, ResourceLocation id){
        return new TabEntry(tab,id);
    }

    public static TabEntry of(Supplier<CreativeModeTab> tab){
        return new TabEntry(tab);
    }

    public ResourceLocation getTexture(){
        return new ResourceLocation(id.getNamespace(),"textures/gui/" + id.getPath() + ".png");
    }
}
