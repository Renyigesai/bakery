package com.renyigesai.bakeries.util;

import com.renyigesai.bakeries.api.LazyMobEffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.TextColor;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class ItemUtils {

    public static final Rarity ADVANCED = Rarity.create("advanced", style -> style.withColor(0XEDA624));//0XEDA624
    public static final Rarity TARO = Rarity.create("taro", style -> style.withColor(0XC889FF));//0XC889FF

    public static List<LazyMobEffectInstance> addEffects(LazyMobEffectInstance... effects){
        return List.of(effects);
    }

    public static void givePlayerItem(Player player, ItemStack item){
        player.getInventory().placeItemBackInInventory(item);
    }

    public static void shrink(ItemStack stack,int count,Player player){
        if (!player.getAbilities().instabuild){
            stack.shrink(count);
        }
    }

    public static void shrinkAndReturn(ItemStack stack,int count,Player player){
        shrink(stack,count,player);
        if (stack.hasCraftingRemainingItem()){
            givePlayerItem(player,new ItemStack(stack.getItem(),count));
        }
    }

    //By Farmer's Delight
    public static void spawnItemEntity(Level level, ItemStack stack, double x, double y, double z, Vec3 pDeltaMovement) {
        ItemEntity entity = new ItemEntity(level, x, y, z, stack);
        entity.setDeltaMovement(pDeltaMovement);
        level.addFreshEntity(entity);
    }

    public static void spawnItemEntity(Level level, ItemStack stack, BlockPos pos) {
        ItemEntity entity = new ItemEntity(level, pos.getX() +  0.5,pos.getY() +  0.5,pos.getZ() +  0.5, stack);
        entity.setDeltaMovement(new Vec3(0.0,0.0,0.0));
        level.addFreshEntity(entity);
    }

    public static boolean isTag(ItemStack stack, Item item){
        List<TagKey<Item>> tags = BuiltInRegistries.ITEM.getHolderOrThrow(BuiltInRegistries.ITEM.getResourceKey(item).get()).tags().toList();
        boolean flag = false;
        for (int i = 0; i < tags.size(); i++) {
            if (stack.is(tags.get(i))){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static boolean isExist(ItemStackHandler handler,Item item){
        for (int i = 0; i < handler.getSlots(); i++) {
            if (handler.getStackInSlot(i).is(item)){
                return true;
            }
        }
        return false;
    }

    public static boolean isExist(ItemStackHandler handler,TagKey<Item> pTag){
        for (int i = 0; i < handler.getSlots(); i++) {
            if (handler.getStackInSlot(i).is(pTag)){
                return true;
            }
        }
        return false;
    }
}
