package net.weibai.bakeries.common.registration.impl;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.weibai.bakeries.common.registration.BDeferredRegister;

import java.util.function.Supplier;

public class MenuTypeDeferredRegister extends BDeferredRegister<MenuType<?>> {

    public MenuTypeDeferredRegister(String modid) {
        super(Registries.MENU, modid, DeferredMenuType::new);
    }
    public<T extends AbstractContainerMenu> DeferredMenuType<MenuType<T>> register(String name, MenuType.MenuSupplier<T> pFactory) {
        return register(name, () -> new MenuType<>(pFactory, FeatureFlags.VANILLA_SET));
    }
    public<T extends AbstractContainerMenu> DeferredMenuType<MenuType<T>> register(String name, MenuType.MenuSupplier<T> pFactory, FeatureFlag... pRequiredFeatures) {
        return register(name, () -> new MenuType<>(pFactory, FeatureFlags.REGISTRY.subset(pRequiredFeatures)));
    }
    public<T extends AbstractContainerMenu> DeferredMenuType<MenuType<T>> register(String name, IContainerFactory<T> pFactory) {
        return this.register(name, () -> IMenuTypeExtension.create(pFactory));
    }
    @Override
    @SuppressWarnings("unchecked")
    public <CHEM extends MenuType<?>> DeferredMenuType<CHEM> register(String name, Supplier<? extends CHEM> sup) {
        return (DeferredMenuType<CHEM>) super.register(name, sup);
    }

}
