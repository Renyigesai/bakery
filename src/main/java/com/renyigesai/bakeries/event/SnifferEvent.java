package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.api.event.SnifferDropSeedEvent;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SnifferEvent {
    @SubscribeEvent
    public static void onDropSeed(SnifferDropSeedEvent event){
        if (event.getLevel() instanceof ServerLevel serverLevel){
            BlockPos pos = event.getBlockPos();
            Holder<Biome> biomeHolder = event.getLevel().getBiome(pos);
            if (biomeHolder.is(BiomeTags.IS_JUNGLE)) {
                ItemEntity itemEntity = new ItemEntity(event.getLevel(), pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BakeriesItems.RAW_COFFEE_BEAN.get()));
                serverLevel.addFreshEntity(itemEntity);
            }
        }
    }
}
