package com.renyigesai.bakeries.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.init.BakeriesSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaguetteItem extends RepeatEatItem {
    public BaguetteItem(Block block, IntegerProperty integerProperty, Properties pProperties, boolean effectTooltip, boolean customField) {
        super(block, integerProperty, pProperties, 4,effectTooltip, customField);
    }

    public BaguetteItem(Block pBlock, Properties properties) {
        super(pBlock, PileBlock.integerProperty, properties,4,false,false);
    }
    @Override
    public float getDestroySpeed(ItemStack itemstack, BlockState blockstate) {
        return 1;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        if (BakeriesMod.aprilFoolsDay){
            if (sourceentity.level() instanceof ServerLevel serverLevel){
                serverLevel.playSound(null,sourceentity.getX(),sourceentity.getY(),sourceentity.getZ(), BakeriesSounds.STEEL_PIPE.get(), SoundSource.PLAYERS,1,1);
            }
        }
        return true;
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

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.UNBREAKING;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced) {
        tooltip.add(Component.translatable("item.bakeries.tips.baguette").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(stack, level, tooltip, isAdvanced);
    }

}
