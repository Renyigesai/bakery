package com.renyigesai.bakery.init;

import com.renyigesai.bakery.BakeryMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakerySounds {

    public static final DeferredRegister<SoundEvent> REGISTRY =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,BakeryMod.MODID);

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return REGISTRY.register(name,()->SoundEvent.createFixedRangeEvent(BakeryMod.prefix(name),1F));
    }
    public static final RegistryObject<SoundEvent> OVEN_DRAW_SLIP = registerSoundEvents("oven_draw_slip");

}
