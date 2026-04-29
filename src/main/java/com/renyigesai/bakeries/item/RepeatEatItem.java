package com.renyigesai.bakeries.item;

import com.mojang.datafixers.util.Pair;
import com.renyigesai.bakeries.api.item.PileItem;
import com.renyigesai.bakeries.util.ItemUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public abstract class RepeatEatItem extends PileItem {

    public RepeatEatItem(Block block, IntegerProperty integerProperty, Properties pProperties, boolean effectTooltip, boolean customField) {
        super(block, integerProperty, pProperties, effectTooltip, customField);
    }

    public RepeatEatItem(Block block, IntegerProperty integerProperty, Properties pProperties) {
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
        return eat(pLevel,pStack,pLivingEntity);
    }

    public static void rHurt(Player entity,ItemStack hand,ItemStack stack){
        if (hand.getDamageValue() == hand.getMaxDamage()-1) {
            hand.shrink(1);
            ItemUtils.givePlayerItem(entity,stack);
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

    public void repeatEat(Level level, ItemStack food, LivingEntity living){

    }

    public ItemStack eat(Level pLevel, ItemStack pFood,LivingEntity living){
        if (living instanceof Player player){
            player.getFoodData().eat(pFood.getItem(),pFood,player);
            player.awardStat(Stats.ITEM_USED.get(pFood.getItem()));
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, pFood);
            }
        }
//        if (!isPlayer){
//            living.eat(pLevel,pFood);
//        }
        FoodProperties foodProperties = pFood.getFoodProperties(living);
        if (foodProperties != null){
            ForgeEventFactory.onItemUseFinish(living, pFood.copy(), 0, ItemStack.EMPTY);
            addAllEffect(foodProperties,living,pLevel);
        }
        pFood.hurt(1,living.getRandom(),null);
        repeatEat(pLevel,pFood,living);
        pLevel.playSound((Player)null, living.getX(), living.getY(), living.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, pLevel.random.nextFloat() * 0.1F + 0.9F);
        return pFood.getDamageValue() > pFood.getMaxDamage()-1 ? residue(pFood) : pFood;
    }

    public void addAllEffect(FoodProperties foodProperties,LivingEntity living,Level level){
        List<Pair<MobEffectInstance, Float>> effects = foodProperties.getEffects();
        for (Pair<MobEffectInstance, Float> next : effects) {
            if (!level.isClientSide && next != null && level.random.nextFloat() < next.getSecond()) {
                living.addEffect(new MobEffectInstance(next.getFirst()));
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
