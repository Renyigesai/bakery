package com.renyigesai.bakeries.common.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.blocks.sofa.SofaEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
@SuppressWarnings("removal")
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class BakeriesEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY =
            DeferredRegister.create(Registries.ENTITY_TYPE, BakeriesMod.MODID);

    public static final Supplier<EntityType<SofaEntity>> SOFA = ENTITY.register("sofa", () ->
            EntityType.Builder.of(SofaEntity::new, MobCategory.MISC).sized(0.25f, 0.35f).build(ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "sofa").toString()));

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> EntityRenderers.register(SOFA.get(), SofaEntity.SofaEntityRender::new));
    }
}
