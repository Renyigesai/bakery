package com.renyigesai.bakeries.mixin.accessor;

import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Function;

@Mixin(CreativeModeTab.Builder.class)
public interface CreativeModeTabBuilderAccessor {
    @Accessor("displayItemsGenerator")
    CreativeModeTab.DisplayItemsGenerator getDisplayItemsGenerator();

    @Accessor("tabFactory")
    Function<CreativeModeTab.Builder, CreativeModeTab> getTabFactory();
}