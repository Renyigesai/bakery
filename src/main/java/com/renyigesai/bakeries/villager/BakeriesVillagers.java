package com.renyigesai.bakeries.villager;

import com.google.common.collect.ImmutableSet;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BakeriesVillagers {

    public static final DeferredRegister<PoiType> POI_TYPE =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, BakeriesMod.MODID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS,BakeriesMod.MODID);

    public static final RegistryObject<PoiType> DOUGH_CRAFTING_TABLE_POI = POI_TYPE.register("dough_crafting_table_poi",
            ()-> new PoiType(ImmutableSet.copyOf(BakeriesBlocks.DOUGH_CRAFTING_TABLE.get().getStateDefinition().getPossibleStates())
                    ,1,1));

    public static final RegistryObject<VillagerProfession> PISTRINA_MASTER =
            VILLAGER_PROFESSION.register("pistrinamaster",()-> new VillagerProfession("pistrinamaster",
                    holder -> holder.get() == DOUGH_CRAFTING_TABLE_POI.get(),holder -> holder.get() == DOUGH_CRAFTING_TABLE_POI.get(),
                    ImmutableSet.of(),ImmutableSet.of(BakeriesBlocks.DOUGH_CRAFTING_TABLE.get()),SoundEvents.VILLAGER_WORK_ARMORER));

    public static void register(IEventBus eventBus){
        POI_TYPE.register(eventBus);
        VILLAGER_PROFESSION.register(eventBus);
    }

}
