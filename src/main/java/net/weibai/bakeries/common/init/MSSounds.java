package net.weibai.bakeries.common.init;

import lombok.Getter;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.weibai.bakeries.BakeriesMod;

public class MSSounds {
    @Getter
    private static final DeferredRegister<SoundEvent> REGISTRY =
            DeferredRegister.create(Registries.SOUND_EVENT, BakeriesMod.MODID);
    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvents(String name) {
        return REGISTRY.register(name,()-> SoundEvent.createFixedRangeEvent(BakeriesMod.rl(name),16F));
    }

    public static final DeferredHolder<SoundEvent, SoundEvent> WOOD_LIGHTER_USE =
            registerSoundEvents("wood_lighter_use");
}
