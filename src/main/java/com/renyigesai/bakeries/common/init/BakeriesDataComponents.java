package com.renyigesai.bakeries.common.init;

import com.mojang.serialization.Codec;
import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;


public class BakeriesDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE,BakeriesMod.MODID);
    public static final Supplier<DataComponentType<Boolean>> PERFECT;
    public static final Supplier<DataComponentType<Boolean>> PERFECT_FERMENTATION;
    public static final Supplier<DataComponentType<Float>> ETERNAL_BAGUETTE_ADD_DAMAGE;
    public static final Supplier<DataComponentType<Integer>> EAT_COUNT;
    public static final Supplier<DataComponentType<Integer>> EAT_COUNT_MAX;

    public static final Supplier<DataComponentType<String>> CUSTOM_CAKE_PART_ID;
    public static final Supplier<DataComponentType<Integer>> CUSTOM_CAKE_HUNGER;
    public static final Supplier<DataComponentType<Float>> CUSTOM_CAKE_SATURATION;
    public static final Supplier<DataComponentType<String>> CUSTOM_CAKE_NAME;
    public static final Supplier<DataComponentType<List<Integer>>> CUSTOM_CAKE_PART_USE;
    static {

        PERFECT = DATA_COMPONENT_TYPE.register("perfect",()-> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());

        PERFECT_FERMENTATION = DATA_COMPONENT_TYPE.register("perfect_fermentation",()-> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());

        ETERNAL_BAGUETTE_ADD_DAMAGE = DATA_COMPONENT_TYPE.register("eternal_baguette_add_damage",()-> DataComponentType.<Float>builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build());

        EAT_COUNT = DATA_COMPONENT_TYPE.register("eat_count", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
        EAT_COUNT_MAX = DATA_COMPONENT_TYPE.register("eat_count_max", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());

        CUSTOM_CAKE_PART_ID = DATA_COMPONENT_TYPE.register("part_id",()-> DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
        CUSTOM_CAKE_HUNGER = DATA_COMPONENT_TYPE.register("hunger", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
        CUSTOM_CAKE_SATURATION = DATA_COMPONENT_TYPE.register("saturation", () -> DataComponentType.<Float>builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build());
        CUSTOM_CAKE_NAME = DATA_COMPONENT_TYPE.register("name",()-> DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
        CUSTOM_CAKE_PART_USE = DATA_COMPONENT_TYPE.register("part_use",()-> DataComponentType.<List<Integer>>builder().persistent(Codec.INT.listOf()).networkSynchronized(ByteBufCodecs.INT.apply(ByteBufCodecs.list())).build());

    }
}
