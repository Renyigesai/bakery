package com.renyigesai.bakeries.client;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.glass_drink_cup.GlassDrinkCupBlockEntity;
import com.renyigesai.bakeries.block.magnetic_plate.MagneticPlateBlockEntity;
import com.renyigesai.bakeries.block.stone_kiln.StoneKilnBlockEntity;
import com.renyigesai.bakeries.block.toaster.ToasterBlockEntity;
import com.renyigesai.bakeries.client.overlay.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LookBlockEntityRegistries {
    public static final Map<UUID, BlockPos> blocks = new HashMap<>();
    private static final Map<Class<? extends BlockEntity>, ILookOverlay<? extends BlockEntity>> REGISTER = new HashMap<>();

    public static Map<UUID, BlockPos> getBlocks() {
        return blocks;
    }

    public static void setBlocks(Player player, BlockPos pos) {
        if (player.level().isClientSide) {
            blocks.put(player.getUUID(), pos);
        }
    }

    public static void removeBlocks(Player player) {
        blocks.remove(player.getUUID());
    }

    public static Map<Class<? extends BlockEntity>, ILookOverlay<? extends BlockEntity>> getRegister() {
        return REGISTER;
    }

    public static<T extends BlockEntity> void put(Class<T> _class,ILookOverlay<T> overlay){
        REGISTER.put(_class,overlay);
    }

    @Mod.EventBusSubscriber(modid = BakeriesMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientSetup{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LookBlockEntityRegistries.put(GlassDrinkCupBlockEntity.class,new GlassDrinkCupOverlay());
            LookBlockEntityRegistries.put(ToasterBlockEntity.class,new ToasterOverlay());
            LookBlockEntityRegistries.put(StoneKilnBlockEntity.class,new StoneKilnOverlay());
            LookBlockEntityRegistries.put(MagneticPlateBlockEntity.class,new MagneticPlateOverlay());
        }
    }
}
