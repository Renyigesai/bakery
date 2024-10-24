package com.renyigesai.bakery.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class FoodBlockSwordItem extends BlockSwordItem {
    public FoodBlockSwordItem(Block pBlock, Properties pProperties, Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier) {
        super(pBlock, pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
    @Override
    public ItemStack finishUsingItem(ItemStack pItemstack, Level world, LivingEntity entity) {
        ItemStack itemStack = new ItemStack(this);
        itemStack.setDamageValue(itemStack.getDamageValue()+1);
        return null;
    }
}
