package com.renyigesai.bakeries.init;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.capabilities.PlayerKeyAuxiliary;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BakeriesCapabilities {

    public static final Capability<PlayerKeyAuxiliary> PLAYER_KEY_AUXILIARY = CapabilityManager.get(new CapabilityToken<>() {
    });

    @Mod.EventBusSubscriber
    public static class AdditionCapabilities{
        @SubscribeEvent
        public static void attachCapabilityToEntityHandler(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                final PlayerKeyAuxiliary playerKeyCap = new PlayerKeyAuxiliary();
                final LazyOptional<PlayerKeyAuxiliary> capOptional = LazyOptional.of(() -> playerKeyCap);
                ICapabilityProvider provider = new ICapabilitySerializable<CompoundTag>() {
                    @Nonnull
                    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction direction) {
                        return cap == PLAYER_KEY_AUXILIARY ? capOptional.cast() : LazyOptional.empty();
                    }

                    public CompoundTag serializeNBT() {
                        return playerKeyCap.serializeNBT();
                    }

                    public void deserializeNBT(CompoundTag nbt) {
                        playerKeyCap.deserializeNBT(nbt);
                    }
                };
                event.addCapability(new ResourceLocation(BakeriesMod.MODID,"key"), provider);
            }
        }
    }
}
