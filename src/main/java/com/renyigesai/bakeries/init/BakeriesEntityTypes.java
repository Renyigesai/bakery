package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.sofa.SofaEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class BakeriesEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BakeriesMod.MODID);

    public static final RegistryObject<EntityType<SofaEntity>> SOFA = ENTITY.register("sofa",()->
            EntityType.Builder.of(SofaEntity::new, MobCategory.MISC).sized(0.25f, 0.35f).build(new ResourceLocation(BakeriesMod.MODID,"sofa").toString()));

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> EntityRenderers.register(SOFA.get(), SofaEntity.SofaEntityRender::new));
    }

    public static void register() {}
}
