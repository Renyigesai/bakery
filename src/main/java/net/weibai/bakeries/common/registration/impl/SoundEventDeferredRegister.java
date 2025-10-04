package net.weibai.bakeries.common.registration.impl;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.weibai.bakeries.common.registration.BDeferredRegister;

import java.util.function.Supplier;

public class SoundEventDeferredRegister extends BDeferredRegister<SoundEvent> {

    public SoundEventDeferredRegister(String modid) {
        super(Registries.SOUND_EVENT, modid, DeferredSoundEvent::new);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <CHEM extends SoundEvent> DeferredSoundEvent<CHEM> register(String name, Supplier<? extends CHEM> sup) {
        return (DeferredSoundEvent<CHEM>) super.register(name, sup);
    }

}
