package com.renyigesai.bakeries;

import com.mojang.logging.LogUtils;
import com.renyigesai.bakeries.common.blocks.fluid.BakeriesFluidTypes;
import com.renyigesai.bakeries.common.blocks.fluid.BakeriesFluids;
import com.renyigesai.bakeries.common.blocks.glass_drink_cup.GlassDrinkCupBlockEntity;
import com.renyigesai.bakeries.common.blocks.toaster.ToasterBlockEntity;
import com.renyigesai.bakeries.common.capabilities.BakeriesCapabilities;
import com.renyigesai.bakeries.common.client.LookBlockEntityRegistries;
import com.renyigesai.bakeries.common.init.*;
import com.renyigesai.bakeries.common.key.BakeriesKeyMapping;
import com.renyigesai.bakeries.common.network.Messages;
import com.renyigesai.bakeries.common.overlay.GlassDrinkCupOverlay;
import com.renyigesai.bakeries.common.overlay.ToasterOverlay;
import com.renyigesai.bakeries.common.villager.BakeriesVillagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

@Mod(BakeriesMod.MODID)
public class BakeriesMod {
    public static final String MODID = "bakeries";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static boolean aprilFoolsDay;
    public static int floatingTemperature;

    public BakeriesMod(IEventBus modEventBus, ModContainer modContainer) {
        BakeriesCreativeModeTabs.REGISTER.register(modEventBus);
        BakeriesBlocks.REGISTER.register(modEventBus);
        BakeriesBlocks.Entities.REGISTER.register(modEventBus);
        BakeriesFluids.REGISTRY.register(modEventBus);
        BakeriesFluidTypes.REGISTRY.register(modEventBus);
        BakeriesItems.REGISTER.register(modEventBus);
        BakeriesRecipeTypes.getRegister(modEventBus);
        BakeriesMenuType.REGISTRY.register(modEventBus);
        BakeriesMenuType.MENU.register(modEventBus);
        BakeriesSounds.REGISTRY.register(modEventBus);
        BakeriesMobEffects.EFFECTS.register(modEventBus);
        BakeriesDataComponents.REGISTER.register(modEventBus);
        BakeriesEntityTypes.ENTITY.register(modEventBus);
        BakeriesVillagers.register(modEventBus);
        BakeriesAttributes.ATTRIBUTES.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(BakeriesCapabilities::registerFluidCapabilities);

//        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
//        event.enqueueWork(this::registerOverlays);
//        Messages.register();
//        if (BakeriesConfig.aprilFoolsDayEffect){
            Calendar calendar = Calendar.getInstance();
            aprilFoolsDay = (calendar.get(Calendar.MONTH) + 1 == 4 && calendar.get(Calendar.DATE) == 1);
//        }
        refreshFloatingTemperature();
    }

    public static void refreshFloatingTemperature(){
        floatingTemperature = new Random().nextInt(-5,10);
    }

    public static boolean onAuxiliaryKey(Player player){
        AttributeInstance instance = player.getAttributes().getInstance(BakeriesAttributes.DOWN);
        if (instance != null) {
            return instance.getValue() != 0;
        }
        return false;
    }

    public static String getAuxiliaryKeyName(){
        return BakeriesKeyMapping.AUXILIARY.getKey().getDisplayName().getString();
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
