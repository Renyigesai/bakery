package com.renyigesai.bakeries;

import com.mojang.logging.LogUtils;
import com.renyigesai.bakeries.common.capabilities.BakeriesCapabilities;
import com.renyigesai.bakeries.common.init.*;
import com.renyigesai.bakeries.common.villager.BakeriesVillagers;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

import java.util.Locale;

@Mod(BakeriesMod.MODID)
public class BakeriesMod {
    public static final String MODID = "bakeries";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BakeriesMod(IEventBus modEventBus, ModContainer modContainer) {
        BakeriesCreativeModeTabs.REGISTER.register(modEventBus);
        BakeriesBlocks.REGISTER.register(modEventBus);
        BakeriesBlocks.Entities.REGISTER.register(modEventBus);
        BakeriesItems.REGISTER.register(modEventBus);
        BakeriesRecipeTypes.getRegister(modEventBus);
        BakeriesMenuType.REGISTRY.register(modEventBus);
        BakeriesSounds.REGISTRY.register(modEventBus);
        BakeriesMobEffects.EFFECTS.register(modEventBus);
        BakeriesDataComponents.REGISTER.register(modEventBus);
        BakeriesEntityTypes.ENTITY.register(modEventBus);
        BakeriesVillagers.register(modEventBus);

        modEventBus.addListener(BakeriesCapabilities::registerFluidCapabilities);

//        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }



}
