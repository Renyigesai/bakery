package com.renyigesai.bakeries.util;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FluidUtil {
    /**
     * 从流体箱中获取指定流体
     * 此方法在玩家使用特定物品时，从流体箱中抽取指定流体，并播放音效，同时将玩家手中的物品替换为指定物品
     *
     * @param player 玩家对象，用于检查玩家正在使用的物品并更新玩家的库存
     * @param pos 位置对象，用于指定音效播放的位置
     * @param tank 流体箱对象，用于存储和抽取流体
     * @param sound 音效事件，用于播放流体被抽取时的音效
     * @param useItem 使用中的物品堆，用于检查玩家正在使用的物品
     * @param outItem 输出的物品堆，玩家在抽取流体后手中获得的物品
     * @param fluid 流体堆，指定要从流体箱中抽取的流体类型和数量
     */
    public static void getFluid(Player player, BlockPos pos, FluidTank tank, SoundEvent sound, ItemStack useItem, ItemStack outItem, FluidStack fluid){
        if(tank.drain(fluid, IFluidHandler.FluidAction.SIMULATE).getAmount() >= 1000){
            if (!player.getAbilities().instabuild) {
                useItem.shrink(1);
                player.getInventory().placeItemBackInInventory(outItem);
            }
            player.level().playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            tank.drain(fluid, IFluidHandler.FluidAction.EXECUTE);
        }
    }
    /**
     * 向指定的流体箱添加流体
     * 此方法检查玩家是否正在使用指定的物品，如果使用的是正确的物品并且流体箱可以接受更多的流体，则将流体添加到流体箱中
     * 玩家的物品会相应地消耗，并播放指定的声音效果
     *
     * @param player 玩家对象，用于检查其正在使用的物品并管理其库存
     * @param pos 块的位置，用于播放声音效果
     * @param tank 流体箱对象，用于添加流体
     * @param sound 播放的声音事件，当流体成功添加时触发
     * @param useItem 玩家需要使用的物品，以触发流体添加
     * @param outItem 玩家使用后获得的物品
     * @param fluid 要添加到流体箱的流体
     */
    public static void addFluid(Player player, BlockPos pos, FluidTank tank, SoundEvent sound, ItemStack useItem, ItemStack outItem, FluidStack fluid){
        if(tank.fill(fluid, IFluidHandler.FluidAction.SIMULATE) >= 1000){
            if (!player.getAbilities().instabuild) {
                useItem.shrink(1);
                player.getInventory().placeItemBackInInventory(outItem);
            }
            player.level().playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            tank.fill(fluid, IFluidHandler.FluidAction.EXECUTE);
        }
    }
}
