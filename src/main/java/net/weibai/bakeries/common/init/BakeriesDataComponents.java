package net.weibai.bakeries.common.init;

import com.mojang.serialization.Codec;
import lombok.Getter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.weibai.bakeries.BakeriesMod;
import net.weibai.rcglib.registration.impl.DataComponentDeferredRegister;
import net.weibai.rcglib.registration.impl.DeferredDataComponent;


public class BakeriesDataComponents {
    @Getter
    private static final DataComponentDeferredRegister REGISTER = new DataComponentDeferredRegister(BakeriesMod.MODID);
    public static final DeferredDataComponent<DataComponentType<Boolean>> PERFECT;
    static {
        PERFECT = REGISTER.register("perfect",  (DataComponentType.Builder<Boolean> booleanBuilder) ->
                booleanBuilder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL)
        );

    }
}
