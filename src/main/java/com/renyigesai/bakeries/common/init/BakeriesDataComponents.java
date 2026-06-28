package com.renyigesai.bakeries.common.init;

import com.mojang.serialization.Codec;
import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.weibai.rcglib.registration.impl.DataComponentDeferredRegister;
import net.weibai.rcglib.registration.impl.DeferredDataComponent;


public class BakeriesDataComponents {
    public static final DataComponentDeferredRegister DATA_COMPONENT_TYPE = new DataComponentDeferredRegister(BakeriesMod.MODID);
    public static final DeferredDataComponent<DataComponentType<Boolean>> PERFECT;
    public static final DeferredDataComponent<DataComponentType<Boolean>> PERFECT_FERMENTATION;
    public static final DeferredDataComponent<DataComponentType<Float>> ETERNAL_BAGUETTE_ADD_DAMAGE;
    public static final DeferredDataComponent<DataComponentType<Integer>> EAT_COUNT;
    public static final DeferredDataComponent<DataComponentType<Integer>> EAT_COUNT_MAX;
    static {
        PERFECT = DATA_COMPONENT_TYPE.register("perfect",  (DataComponentType.Builder<Boolean> booleanBuilder) ->
                booleanBuilder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL)
        );

        PERFECT_FERMENTATION = DATA_COMPONENT_TYPE.register("perfect_fermentation",  (DataComponentType.Builder<Boolean> booleanBuilder) ->
                booleanBuilder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL)
        );

        ETERNAL_BAGUETTE_ADD_DAMAGE = DATA_COMPONENT_TYPE.register("eternal_baguette_add_damage",  (DataComponentType.Builder<Float> floatBuilder) ->
                floatBuilder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT)
        );

        EAT_COUNT = DATA_COMPONENT_TYPE.register("eat_count", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
        EAT_COUNT_MAX = DATA_COMPONENT_TYPE.register("eat_count_max", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());

    }
}
