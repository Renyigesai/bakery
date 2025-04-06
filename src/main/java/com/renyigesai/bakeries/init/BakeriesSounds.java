package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeriesSounds {

    public static final DeferredRegister<SoundEvent> REGISTRY =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BakeriesMod.MODID);

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return REGISTRY.register(name,()->SoundEvent.createFixedRangeEvent(BakeriesMod.prefix(name),16F));
    }
    public static final RegistryObject<SoundEvent> OVEN_DRAW_SLIP = registerSoundEvents("block.generic.oven_draw_slip");
    public static final RegistryObject<SoundEvent> SHAKE = registerSoundEvents("entity.generic.shake");
    public static final RegistryObject<SoundEvent> OVEN_OPEN = registerSoundEvents("block.generic.open_oven");
    public static final RegistryObject<SoundEvent> BLENDER = registerSoundEvents("block.generic.blender");
    public static final RegistryObject<SoundEvent> PUT_ON_ICE = registerSoundEvents("block.generic.put_on_ice");
    public static final RegistryObject<SoundEvent> INSERT_STRAW = registerSoundEvents("block.generic.insert_straw");
}
