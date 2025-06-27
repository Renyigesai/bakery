package com.renyigesai.bakeries.item;

import com.mojang.datafixers.util.Pair;
import com.renyigesai.bakeries.api.block.properties.ModIntegerProperty;
import com.renyigesai.bakeries.api.item.FoodBlockItem;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public abstract class RepeatEatItem extends FoodBlockItem {

    public RepeatEatItem(Block block, ModIntegerProperty integerProperty, Properties pProperties, boolean effectTooltip, boolean customField) {
        super(block, integerProperty, pProperties, effectTooltip, customField);
    }

    public RepeatEatItem(Block block, ModIntegerProperty integerProperty, Properties pProperties) {
        super(block, integerProperty, pProperties);
    }

    @Override
    public boolean isExtra(UseOnContext pContext) {
        return pContext.getItemInHand().getDamageValue() == 0;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        if (!canDrink()){
            return UseAnim.EAT;
        }
        return UseAnim.DRINK;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    public boolean canDrink(){
        return false;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pStack.getDamageValue() == pStack.getMaxDamage()-1) {
            eat(pStack, pLevel, pLivingEntity,new Vec3(pLivingEntity.getX(),pLivingEntity.getY(),pLivingEntity.getZ()));
            addEffect(pLevel,pLivingEntity);
            return pStack.hasCraftingRemainingItem() && !pStack.getCraftingRemainingItem().isEmpty()? super.finishUsingItem(residue(pStack),pLevel,pLivingEntity): super.finishUsingItem(pStack,pLevel,pLivingEntity);
        }
        pStack.hurt(1, RandomSource.create(), null);
        eat(pStack, pLevel, pLivingEntity,new Vec3(pLivingEntity.getX(),pLivingEntity.getY(),pLivingEntity.getZ()));
        addEffect(pLevel,pLivingEntity);
        return pStack;
    }

    public static void rHurt(Player entity,ItemStack hand,ItemStack stack){
        if (hand.getDamageValue() == hand.getMaxDamage()-1) {
            hand.shrink(1);
            ItemUtil.givePlayerItem(entity,stack);
        }else {
            hand.hurt(1, RandomSource.create(), null);
        }
    }

    public static void rHurt(ItemStack hand){
        if (hand.getDamageValue() == hand.getMaxDamage()-1) {
            hand.shrink(1);
        }else {
            hand.hurt(1, RandomSource.create(), null);
        }
    }

    abstract void eat(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, Vec3 vec3);

    public void addEffect(Level level, LivingEntity pLivingEntity){
        List<Pair<MobEffectInstance, Float>> effects = Objects.requireNonNull(new ItemStack(this).getFoodProperties(pLivingEntity)).getEffects();
        for (Pair<MobEffectInstance, Float> effect : effects) {
            if (!level.isClientSide && effect.getFirst() != null && level.random.nextFloat() < effect.getSecond()) {
                pLivingEntity.addEffect(new MobEffectInstance(effect.getFirst()));
            }
        }
    }


    public ItemStack residue(ItemStack stack){
        return stack.getCraftingRemainingItem();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);
        String translatable = canDrink()?"item.bakeries.tips.repeat_eat_item.drink":"item.bakeries.tips.repeat_eat_item";
        tooltip.add(Component.nullToEmpty(Component.translatable(translatable).getString() + (stack.getMaxDamage() - stack.getDamageValue()) + " / " + stack.getMaxDamage()));
    }
}
