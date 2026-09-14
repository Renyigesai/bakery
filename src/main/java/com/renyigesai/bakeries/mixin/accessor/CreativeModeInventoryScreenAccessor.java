package com.renyigesai.bakeries.mixin.accessor;

import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CreativeModeInventoryScreen.class)
public interface CreativeModeInventoryScreenAccessor {

    @Accessor("selectedTab")
    CreativeModeTab invokeGetSelectedTab();

    @Accessor("scrollOffs")
    float invokeGetScrollOffs();
}
