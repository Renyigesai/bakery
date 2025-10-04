package net.weibai.bakeries.common.registration.impl;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.weibai.bakeries.common.registration.BDeferredRegister;

import java.util.function.Supplier;

public class CreativeModeTabDeferredRegister extends BDeferredRegister<CreativeModeTab> {

    public CreativeModeTabDeferredRegister(String modid) {
        super(Registries.CREATIVE_MODE_TAB, modid, DeferredCreativeModeTab::new);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <CHEM extends CreativeModeTab> DeferredCreativeModeTab<CHEM> register(String name, Supplier<? extends CHEM> sup) {
        return (DeferredCreativeModeTab<CHEM>) super.register(name, sup);
    }

}
