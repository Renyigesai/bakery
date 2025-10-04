package net.weibai.bakeries.common.registration.impl;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.weibai.bakeries.common.registration.BDeferredHolder;

public class DeferredCreativeModeTab <E extends CreativeModeTab> extends BDeferredHolder<CreativeModeTab, E> {
    public DeferredCreativeModeTab(ResourceKey<CreativeModeTab> key) {
        super(key);
    }
}
