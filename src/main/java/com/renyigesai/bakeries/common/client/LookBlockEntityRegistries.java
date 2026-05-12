package com.renyigesai.bakeries.common.client;

import com.google.common.collect.ImmutableMap;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.blocks.glass_drink_cup.GlassDrinkCupBlockEntity;
import com.renyigesai.bakeries.common.blocks.toaster.ToasterBlockEntity;
import com.renyigesai.bakeries.common.overlay.GlassDrinkCupOverlay;
import com.renyigesai.bakeries.common.overlay.ILookOverlay;
import com.renyigesai.bakeries.common.overlay.ToasterOverlay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class LookBlockEntityRegistries {
    public static final Map<UUID, BlockEntity> BLOCKS = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>,ILookOverlay<? extends BlockEntity>> REGISTER = new HashMap<>();

    public static Map<UUID, BlockEntity> getBlocks() {
        return BLOCKS;
    }

    public static void setBlocks(Player player, BlockEntity entity) {
        if (player.level().isClientSide) {
            BLOCKS.put(player.getUUID(), entity);
        }
    }

    public static Map<Class<? extends BlockEntity>, ILookOverlay<? extends BlockEntity>> getRegister() {
        return REGISTER;
    }

    public static<T extends BlockEntity> void put(Class<T> _class,ILookOverlay<T> overlay){
        REGISTER.put(_class,overlay);
    }

    @EventBusSubscriber(modid = BakeriesMod.MODID, value = Dist.CLIENT)
    public static class ClientSetup{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LookBlockEntityRegistries.put(GlassDrinkCupBlockEntity.class,new GlassDrinkCupOverlay());
            LookBlockEntityRegistries.put(ToasterBlockEntity.class,new ToasterOverlay());
        }
    }
}
