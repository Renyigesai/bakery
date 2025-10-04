package net.weibai.bakeries.common.tags;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class IntrinsicBakeriesTagBuilder<TYPE> extends BakeriesTagBuilder<TYPE, IntrinsicBakeriesTagBuilder<TYPE>> {

    private final Function<TYPE, ResourceKey<TYPE>> keyExtractor;

    public IntrinsicBakeriesTagBuilder(Function<TYPE, ResourceKey<TYPE>> keyExtractor, TagBuilder builder, String modID) {
        super(builder, modID);
        this.keyExtractor = keyExtractor;
    }

    public IntrinsicBakeriesTagBuilder<TYPE> add(Collection<? extends Supplier<? extends TYPE>> elements) {
        return addTyped(Supplier::get, elements);
    }

    @SafeVarargs
    public final IntrinsicBakeriesTagBuilder<TYPE> add(Supplier<TYPE>... elements) {
        return addTyped(Supplier::get, elements);
    }

    private ResourceLocation getKey(TYPE element) {
        return keyExtractor.apply(element).location();
    }

    @SafeVarargs
    public final IntrinsicBakeriesTagBuilder<TYPE> add(TYPE... elements) {
        return add(this::getKey, elements);
    }

    @SafeVarargs
    public final <T> IntrinsicBakeriesTagBuilder<TYPE> addTyped(Function<T, TYPE> converter, T... elements) {
        return add(converter.andThen(this::getKey), elements);
    }

    public <T> IntrinsicBakeriesTagBuilder<TYPE> addTyped(Function<T, TYPE> converter, Collection<T> elements) {
        return add(converter.andThen(this::getKey), elements);
    }

    @SafeVarargs
    public final IntrinsicBakeriesTagBuilder<TYPE> addOptional(TYPE... elements) {
        return addOptional(this::getKey, elements);
    }

    @SafeVarargs
    public final IntrinsicBakeriesTagBuilder<TYPE> remove(TYPE... elements) {
        return remove(this::getKey, elements);
    }

    @SafeVarargs
    public final IntrinsicBakeriesTagBuilder<TYPE> remove(Holder<TYPE>... elements) {
        return remove(element -> getKey(element.value()), elements);
    }
}