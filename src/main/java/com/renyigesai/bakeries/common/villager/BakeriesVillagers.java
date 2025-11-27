package com.renyigesai.bakeries.common.villager;

import com.google.common.collect.ImmutableSet;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BakeriesVillagers {

    public static final DeferredRegister<PoiType> POI_TYPE = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, BakeriesMod.MODID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION = DeferredRegister.create(Registries.VILLAGER_PROFESSION,BakeriesMod.MODID);

    public static final Supplier<PoiType> DOUGH_CRAFTING_TABLE_POI = POI_TYPE.register("dough_crafting_table_poi",
            ()-> new PoiType(ImmutableSet.copyOf(BakeriesBlocks.DOUGH_CRAFTING_TABLE.get().getStateDefinition().getPossibleStates())
                    ,1,1));

    public static final Supplier<VillagerProfession> PISTRINA_MASTER =
            VILLAGER_PROFESSION.register("pistrinamaster",()-> new VillagerProfession("pistrinamaster",
                    holder -> holder.value() == DOUGH_CRAFTING_TABLE_POI.get(),holder -> holder.value() == DOUGH_CRAFTING_TABLE_POI.get(),
                    ImmutableSet.of(),ImmutableSet.of(BakeriesBlocks.DOUGH_CRAFTING_TABLE.get()),SoundEvents.VILLAGER_WORK_ARMORER));

    public static void register(IEventBus eventBus){
        POI_TYPE.register(eventBus);
        VILLAGER_PROFESSION.register(eventBus);
    }
}
