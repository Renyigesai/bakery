package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.BooleanAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BakeriesAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, BakeriesMod.MODID);

    public static final Holder<Attribute> DOWN = ATTRIBUTES.register("down",()-> new BooleanAttribute("attribute.name.generic.down",false));
}
