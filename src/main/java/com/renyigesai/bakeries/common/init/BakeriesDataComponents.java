package com.renyigesai.bakeries.common.init;

import com.mojang.serialization.Codec;
import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.weibai.rcglib.registration.impl.DataComponentDeferredRegister;
import net.weibai.rcglib.registration.impl.DeferredDataComponent;


public class BakeriesDataComponents {
    public static final DataComponentDeferredRegister REGISTER = new DataComponentDeferredRegister(BakeriesMod.MODID);
    public static final DeferredDataComponent<DataComponentType<Boolean>> PERFECT;
    public static final DeferredDataComponent<DataComponentType<Boolean>> PERFECT_FERMENTATION;
    static {
        PERFECT = REGISTER.register("perfect",  (DataComponentType.Builder<Boolean> booleanBuilder) ->
                booleanBuilder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL)
        );

        PERFECT_FERMENTATION = REGISTER.register("perfect_fermentation",  (DataComponentType.Builder<Boolean> booleanBuilder) ->
                booleanBuilder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL)
        );

    }
}
