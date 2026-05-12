package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.weibai.rcglib.registration.impl.DeferredSoundEvent;
import net.weibai.rcglib.registration.impl.SoundEventDeferredRegister;

import java.util.function.Supplier;


public class BakeriesSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT,BakeriesMod.MODID);

    private static Supplier<SoundEvent> registerSoundEvents(String name) {
        return REGISTRY.register(name,()-> SoundEvent.createFixedRangeEvent(BakeriesMod.rl(name),16F));
    }

    public static final Supplier<SoundEvent> OVEN_DRAW_SLIP = registerSoundEvents("block.generic.oven_draw_slip");
    public static final Supplier<SoundEvent> SHAKE = registerSoundEvents("entity.generic.shake");
    public static final Supplier<SoundEvent> OVEN_OPEN = registerSoundEvents("block.generic.open_oven");
    public static final Supplier<SoundEvent> BLENDER = registerSoundEvents("block.generic.blender");
    public static final Supplier<SoundEvent> PUT_ON_ICE = registerSoundEvents("block.generic.put_on_ice");
    public static final Supplier<SoundEvent> INSERT_STRAW = registerSoundEvents("block.generic.insert_straw");
    public static final Supplier<SoundEvent> MUSIC_DISC_BAKING_IN_PROGRESS = registerSoundEvents("item.generic.music_disc_baking_in_progress");
    public static final Supplier<SoundEvent> PASTRY_PLACE = registerSoundEvents("block.generic.pastry_place");
    public static final Supplier<SoundEvent> STEEL_PIPE = registerSoundEvents("block.generic.steel_pipe");
    public static final Supplier<SoundEvent> TOASTER_IN = registerSoundEvents("block.generic.toaster_in");
    public static final Supplier<SoundEvent> TOASTER_OUT = registerSoundEvents("block.generic.toaster_out");

}
