package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BaguetteItem extends RepeatEatItem {


    public BaguetteItem(Block block, Properties properties, boolean effectTooltip) {
        super(block, properties, effectTooltip,false);
    }

    public BaguetteItem(Block block, Properties properties,int eatCount) {
        super(block, properties, eatCount,false);
    }

    @Override
    public float getDestroySpeed(ItemStack itemstack, BlockState blockstate) {
        return 1;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        if (itemstack.getMaxDamage() > 0){
            itemstack.hurtAndBreak(1, entity, entity.getEquipmentSlotForItem(itemstack));
        }
        if (BakeriesMod.aprilFoolsDay){
            if (sourceentity.level() instanceof ServerLevel serverLevel){
                serverLevel.playSound(null,sourceentity.getX(),sourceentity.getY(),sourceentity.getZ(), BakeriesSounds.STEEL_PIPE.get(), SoundSource.PLAYERS,1,1);
            }
        }
        return true;
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,new AttributeModifier(BASE_ATTACK_DAMAGE_ID,3d,AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,new AttributeModifier(BASE_ATTACK_SPEED_ID,-3d,AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.MAINHAND)
                .build();
    }
}
