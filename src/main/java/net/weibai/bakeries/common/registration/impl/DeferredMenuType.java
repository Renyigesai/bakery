package net.weibai.bakeries.common.registration.impl;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.weibai.bakeries.common.registration.BDeferredHolder;

public class DeferredMenuType <E extends MenuType<?>> extends BDeferredHolder<MenuType<?>, E> {
    public DeferredMenuType(ResourceKey<MenuType<?>> key) {
        super(key);
    }
}