package net.weibai.bakeries.common.registration.impl;

import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.weibai.bakeries.common.registration.BDeferredHolder;

public class DeferredSoundEvent <E extends SoundEvent> extends BDeferredHolder<SoundEvent, E> {
    public DeferredSoundEvent(ResourceKey<SoundEvent> key) {
        super(key);
    }
}
