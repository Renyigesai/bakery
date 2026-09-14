package com.renyigesai.bakeries.mixin.accessor;

import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CreativeModeInventoryScreen.ItemPickerMenu.class)
public interface CreativeModeInventoryScreenItemPickerMenuAccessor {

    @Invoker("getRowIndexForScroll")
    int invokeGetRowIndexForScroll(float pScrollOffs);
}
