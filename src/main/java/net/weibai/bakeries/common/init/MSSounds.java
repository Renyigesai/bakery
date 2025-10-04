package net.weibai.bakeries.common.init;

import lombok.Getter;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.weibai.mechanical_soar.common.MechanicalSoarMod;

public class MSSounds {
    @Getter
    private static final DeferredRegister<SoundEvent> REGISTRY =
            DeferredRegister.create(Registries.SOUND_EVENT, MechanicalSoarMod.MODID);
    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvents(String name) {
        return REGISTRY.register(name,()-> SoundEvent.createFixedRangeEvent(MechanicalSoarMod.prefix(name),16F));
    }

    public static final DeferredHolder<SoundEvent, SoundEvent> WOOD_LIGHTER_USE =
            registerSoundEvents("wood_lighter_use");
}
