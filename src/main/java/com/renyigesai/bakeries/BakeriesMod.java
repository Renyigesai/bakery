package com.renyigesai.bakeries;

import com.renyigesai.bakeries.capabilities.PlayerKeyAuxiliary;
import com.renyigesai.bakeries.compat.init.BakeriesCompatItems;
import com.renyigesai.bakeries.conditions.ConfigCondition;
import com.renyigesai.bakeries.config.BakeriesConfig;
import com.renyigesai.bakeries.fluid.BakeriesFluidTypes;
import com.renyigesai.bakeries.fluid.BakeriesFluids;
import com.renyigesai.bakeries.init.*;
import com.renyigesai.bakeries.key.BakeriesKeyMapping;
import com.renyigesai.bakeries.network.Messages;
import com.renyigesai.bakeries.villager.BakeriesVillagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.util.*;

@Mod(BakeriesMod.MODID)
public class BakeriesMod {

    public static final String MODID = "bakeries";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static boolean aprilFoolsDay;
    @SuppressWarnings("removal")
    public BakeriesMod() {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BakeriesItems.REGISTER.register(bus);
        BakeriesCompatItems.REGISTER.register(bus);
        BakeriesBlocks.BLOCK_REGISTRY.register(bus);
        BakeriesBlocks.BLOCK_ENTITY_REGISTRY.register(bus);
        BakeriesGroup.REGISTER.register(bus);
        BakeriesMobEffects.REGISTRY.register(bus);
        BakeriesMenuType.REGISTRY.register(bus);
        BakeriesSounds.REGISTRY.register(bus);
        BakeriesFluids.REGISTRY.register(bus);
        BakeriesFluidTypes.REGISTRY.register(bus);
        BakeriesEntityTypes.ENTITY.register(bus);
        BakeriesParticleTypes.REGISTRY.register(bus);
        BakeriesVillagers.register(bus);
        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BakeriesConfig.SPEC, "bakeries-common.toml");
    }
    private void commonSetup(FMLCommonSetupEvent event) {
        Messages.register();
        BakeriesConfig.ConfigMapping.init();
        CraftingHelper.register(ConfigCondition.Serializer.INSTANCE);
        if (BakeriesConfig.aprilFoolsDayEffect){
            Calendar calendar = Calendar.getInstance();
            aprilFoolsDay = (calendar.get(Calendar.MONTH) + 1 == 4 && calendar.get(Calendar.DATE) == 1);
        }
    }

    public static boolean onAuxiliaryKey(Player player){
        return player.getCapability(BakeriesCapabilities.PLAYER_KEY_AUXILIARY).orElse(new PlayerKeyAuxiliary()).key;
    }

    public static String getAuxiliaryKeyName(){
        return BakeriesKeyMapping.AUXILIARY.getKey().getDisplayName().getString();
    }

    private void clientSetup(FMLClientSetupEvent event) {

    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }
}
