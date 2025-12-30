package com.renyigesai.bakeries.client;

import com.google.common.collect.ImmutableMap;
import com.renyigesai.bakeries.block.glass_drink_cup.GlassDrinkCupBlockEntity;
import com.renyigesai.bakeries.block.stone_kiln.StoneKilnBlockEntity;
import com.renyigesai.bakeries.block.toaster.ToasterBlockEntity;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LookBlockEntityMap {
    public static final Map<UUID, BlockEntity> blocks = new HashMap<>();

    private static final ImmutableMap<Block,Class<? extends BlockEntity>> REGISTER = ImmutableMap.of(BakeriesBlocks.DRINK_CUP.get(), GlassDrinkCupBlockEntity.class,BakeriesBlocks.STONE_KILN.get(), StoneKilnBlockEntity.class,BakeriesBlocks.TOASTER.get(), ToasterBlockEntity.class);

    public static Map<UUID, BlockEntity> getBlocks() {
        return blocks;
    }

    public static void setBlocks(Player player, BlockEntity entity) {
        if (player.level().isClientSide) {
            blocks.put(player.getUUID(), entity);
        }
    }

//    public ImmutableMap<Block, Class<? extends BlockEntity>> getMap() {
//        return map;
//    }


    public static ImmutableMap<Block, Class<? extends BlockEntity>> getRegister() {
        return REGISTER;
    }
}
