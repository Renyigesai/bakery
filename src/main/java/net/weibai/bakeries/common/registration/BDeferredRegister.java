package net.weibai.bakeries.common.registration;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;
import java.util.function.Supplier;

public class BDeferredRegister<T> extends net.neoforged.neoforge.registries.DeferredRegister<T> {

    private final Function<ResourceKey<T>, ? extends BDeferredHolder<T, ?>> holderCreator;

    public BDeferredRegister(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
        this(registryKey, namespace, BDeferredHolder::new);
    }

    public BDeferredRegister(ResourceKey<? extends Registry<T>> registryKey, String namespace,
                             Function<ResourceKey<T>, ? extends BDeferredHolder<T, ? extends T>> holderCreator) {
        super(registryKey, namespace);
        this.holderCreator = holderCreator;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <I extends T> BDeferredHolder<T, I> register(String name, Function<ResourceLocation, ? extends I> func) {
        return (BDeferredHolder<T, I>) super.register(name, func);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <I extends T> BDeferredHolder<T, I> register(String name, Supplier<? extends I> sup) {
        return (BDeferredHolder<T, I>) super.register(name, sup);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <I extends T> BDeferredHolder<T, I> createHolder(ResourceKey<? extends Registry<T>> registryKey, ResourceLocation key) {
        return (BDeferredHolder<T, I>) holderCreator.apply(ResourceKey.create(registryKey, key));
    }
}