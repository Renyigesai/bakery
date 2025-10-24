package net.weibai.bakeries.common.init;

import lombok.Getter;
import net.minecraft.sounds.SoundEvent;
import net.weibai.bakeries.BakeriesMod;
import net.weibai.rcglib.registration.impl.DeferredSoundEvent;
import net.weibai.rcglib.registration.impl.SoundEventDeferredRegister;


public class BakeriesSounds {
    @Getter
    private static final SoundEventDeferredRegister REGISTRY = new SoundEventDeferredRegister(BakeriesMod.MODID);

    private static DeferredSoundEvent<SoundEvent> registerSoundEvents(String name) {
        return REGISTRY.register(name,()-> SoundEvent.createFixedRangeEvent(BakeriesMod.rl(name),16F));
    }

}
