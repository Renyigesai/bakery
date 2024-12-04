package com.renyigesai.bakeries.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class Shortcuts {

    public static void setBlock(Level plevel, BlockPos pos, BlockState state,IntegerProperty pProperty, int pValue,int level){
        plevel.setBlock(pos, state.setValue(pProperty, pValue + level), 3);
    }

    /*
    * 使用减法
    * Use subtraction*/
    public static void setBlock(Level plevel, BlockPos pos, BlockState state,IntegerProperty pProperty, int pValue,int level,boolean isSubtract){
        plevel.setBlock(pos, state.setValue(pProperty, pValue - level), 3);
    }

    public static void giveItem(Player player, ItemLike item){
        player.getInventory().placeItemBackInInventory(new ItemStack(item));
    }

    //By Farmer's Delight
    public static void spawnItemEntity(Level level, ItemStack stack, double x, double y, double z, double xMotion, double yMotion, double zMotion) {
        ItemEntity entity = new ItemEntity(level, x, y, z, stack);
        entity.setDeltaMovement(xMotion, yMotion, zMotion);
        level.addFreshEntity(entity);
    }
}
