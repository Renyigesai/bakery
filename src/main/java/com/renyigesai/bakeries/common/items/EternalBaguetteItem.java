package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;
import java.util.Set;

public class EternalBaguetteItem extends Item{
    public static final Set<ItemAbility> BAGUETTE_ACTIONS;

    public static final ResourceLocation BASE_ATTACK_KNOCKBACK_ID = ResourceLocation.fromNamespaceAndPath("bakeries","base_attack_knockback");

    public EternalBaguetteItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).attributes(createAttributes()));
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,new AttributeModifier(BASE_ATTACK_DAMAGE_ID,7d,AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,new AttributeModifier(BASE_ATTACK_SPEED_ID,-2.5d,AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_KNOCKBACK,new AttributeModifier(BASE_ATTACK_KNOCKBACK_ID,2d,AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        Vec3 vec3 = entity.getDeltaMovement();
        double s = 0.85;
        Vec3 vec31 = (new Vec3(Mth.sin(sourceentity.getYRot() * ((float)Math.PI / 180F)), 0.8D,(-Mth.cos(sourceentity.getYRot() * ((float)Math.PI / 180F))))).normalize().scale(s);
        entity.setDeltaMovement(vec3.x / 2.0D - vec31.x, entity.onGround() ? Math.min(0.4D, vec3.y / 2.0 + s) : vec3.y, vec3.z / 2.0D - vec31.z);
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,50,2));
        if (BakeriesMod.aprilFoolsDay){
            if (sourceentity.level() instanceof ServerLevel serverLevel){
                serverLevel.playSound(null,sourceentity.getX(),sourceentity.getY(),sourceentity.getZ(), BakeriesSounds.STEEL_PIPE.get(), SoundSource.PLAYERS,1,1);
            }
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("tooltips.bakeries.eternal_baguette").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return !enchantment.is(Enchantments.SWEEPING_EDGE) && super.isPrimaryItemFor(stack, enchantment);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return !enchantment.is(Enchantments.SWEEPING_EDGE) && super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
        return BAGUETTE_ACTIONS.contains(toolAction);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 15;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    static {
        BAGUETTE_ACTIONS = Set.of(ItemAbilities.SHEARS_CARVE, ItemAbilities.SWORD_DIG);
    }

}
