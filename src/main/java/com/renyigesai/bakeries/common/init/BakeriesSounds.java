package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.sounds.SoundEvent;
import net.weibai.rcglib.registration.impl.DeferredSoundEvent;
import net.weibai.rcglib.registration.impl.SoundEventDeferredRegister;


public class BakeriesSounds {
    public static final SoundEventDeferredRegister REGISTRY = new SoundEventDeferredRegister(BakeriesMod.MODID);

    private static DeferredSoundEvent<SoundEvent> registerSoundEvents(String name) {
        return REGISTRY.register(name,()-> SoundEvent.createFixedRangeEvent(BakeriesMod.rl(name),16F));
    }

    public static final DeferredSoundEvent<SoundEvent> OVEN_DRAW_SLIP = registerSoundEvents("block.generic.oven_draw_slip");
    public static final DeferredSoundEvent<SoundEvent> SHAKE = registerSoundEvents("entity.generic.shake");
    public static final DeferredSoundEvent<SoundEvent> OVEN_OPEN = registerSoundEvents("block.generic.open_oven");
    public static final DeferredSoundEvent<SoundEvent> BLENDER = registerSoundEvents("block.generic.blender");
    public static final DeferredSoundEvent<SoundEvent> PUT_ON_ICE = registerSoundEvents("block.generic.put_on_ice");
    public static final DeferredSoundEvent<SoundEvent> INSERT_STRAW = registerSoundEvents("block.generic.insert_straw");
    public static final DeferredSoundEvent<SoundEvent> MUSIC_DISC_BAKING_IN_PROGRESS = registerSoundEvents("item.generic.music_disc_baking_in_progress");

}
