package com.renyigesai.bakery.api.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FoodBlockSwordItem extends BlockSwordItem{
    public FoodBlockSwordItem(Block pBlock, Properties pProperties, Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier) {
        super(pBlock, pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack pItemstack, Level world, LivingEntity entity) {
        ItemStack itemStack = new ItemStack(this);
        itemStack.setDamageValue(itemStack.getDamageValue()+1);
        return null;
    }
}
