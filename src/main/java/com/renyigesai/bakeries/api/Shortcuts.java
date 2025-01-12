package com.renyigesai.bakeries.api;

import com.renyigesai.bakeries.api.block.properties.ModIntegerProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;

public class Shortcuts {
    /**
     * 在给定的位置设置方块状态，并指定其特定属性值
     *
     * @param <T> 可比较类型的泛型，用于指定属性的类型
     * @param <V> 继承自T的泛型，用于指定要设置的属性值的类型
     * @param plevel 世界等级对象，用于操作世界中的方块
     * @param pos 方块的位置，指定在哪里设置方块
     * @param state 方块的状态对象，定义了方块的各种属性
     * @param pProperty 方块属性，指定了需要修改的属性
     * @param pValue 属性的值，将属性设置为这个值
     */
    public static <T extends Comparable<T>, V extends T> void setBlock(Level plevel, BlockPos pos, BlockState state, Property<T> pProperty, V pValue){
        plevel.setBlock(pos, state.setValue(pProperty, pValue), 3);
    }
    /**
     * 在指定位置设置方块，并在指定的整数属性上增加或减少给定的值
     * 此方法用于在不超出属性最大值或小于属性最小值的情况下，更新方块状态中的某个整数属性
     * 如果增加或减少后的属性值超过最大值或小于最小值，则抛出非法参数异常
     *
     * @param plevel 游戏世界级别对象，用于访问和修改世界数据
     * @param pos 方块的位置，用于指定需要更新的方块
     * @param state 当前方块的状态，用于读取和更新方块属性
     * @param pProperty 需要更新的整数属性对象，用于指定要修改的属性
     * @param pValue 需要增加或减少的值，用于更新属性
     * @param isAdd 如果为true，则增加属性值；如果为false，则减少属性值
     * @throws IllegalArgumentException 如果更新后的属性值超过属性的最大值或小于最小值，则抛出此异常
     */
    public static void setBlock(Level plevel, BlockPos pos, BlockState state, ModIntegerProperty pProperty, int pValue, boolean isAdd){
        if(isAdd) {
            if(pProperty.getMax() < (state.getValue(pProperty) + pValue)){
                throw new IllegalArgumentException("setBlock : " + "This is " + state + ", Error Property:" + pProperty);
            } else {
                setBlock(plevel, pos, state, pProperty, state.getValue(pProperty) + pValue);
            }
        } else {
            if(pProperty.getMin() > (state.getValue(pProperty) - pValue)){
                throw new IllegalArgumentException("setBlock : " + "This is " + state + ", Error Property:" + pProperty);
            } else {
                setBlock(plevel, pos, state, pProperty, state.getValue(pProperty) - pValue);
            }
        }
    }
    public static void givePlayerItem(Player player, ItemStack item){
        player.getInventory().placeItemBackInInventory(item);
    }

    //By Farmer's Delight
    public static void spawnItemEntity(Level level, ItemStack stack, double x, double y, double z, Vec3 pDeltaMovement) {
        ItemEntity entity = new ItemEntity(level, x, y, z, stack);
        entity.setDeltaMovement(pDeltaMovement);
        level.addFreshEntity(entity);
    }
}
