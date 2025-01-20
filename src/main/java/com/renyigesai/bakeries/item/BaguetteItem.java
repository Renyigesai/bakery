package com.renyigesai.bakeries.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.api.item.FoodBlockItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BaguetteItem extends FoodBlockItem {
    public BaguetteItem(Block pBlock, Properties properties) {
        super(pBlock, PileBlock.integerProperty, properties);
    }
    @Override
    public float getDestroySpeed(ItemStack itemstack, BlockState blockstate) {
        return 1;
    }
    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        itemstack.hurtAndBreak(2, entity, i -> i.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean isExtra(UseOnContext pContext) {
        return pContext.getItemInHand().getDamageValue() == 0;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pStack.getDamageValue() == pStack.getMaxDamage()-1) {
            return super.finishUsingItem(pStack, pLevel, pLivingEntity);
        }else {
            if (pLivingEntity instanceof Player player){
                player.getFoodData().eat(pStack.getItem().getFoodProperties().getNutrition()
                        ,pStack.getItem().getFoodProperties().getSaturationModifier());
            }
            pStack.hurt(1, RandomSource.create(), null);
            return pStack;
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 3f, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -3, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

}
