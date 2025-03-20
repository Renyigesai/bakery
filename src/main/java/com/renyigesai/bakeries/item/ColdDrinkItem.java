package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.api.block.PileBlock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class ColdDrinkItem extends RepeatEatItem{

    public ColdDrinkItem(Block block, Properties pProperties, boolean effectTooltip, boolean customField) {
        super(block, PileBlock.integerProperty, pProperties, effectTooltip, customField);
    }

    public ColdDrinkItem(Block block, Properties pProperties) {
        super(block, PileBlock.integerProperty, pProperties);
    }

    @Override
    public boolean canDrink() {
        return true;
    }

    @Override
    public void eat(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, Vec3 vec3) {
        super.eat(pStack, pLevel, pLivingEntity, vec3);
    }
}
