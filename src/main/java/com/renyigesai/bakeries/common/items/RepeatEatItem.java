package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.api.items.PileItem;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Iterator;
import java.util.List;

public class RepeatEatItem extends PileItem {
    private final boolean canDrink;
    public RepeatEatItem(Block block, Properties properties, boolean effectTooltip, boolean canDrink) {
        super(block, properties, effectTooltip);
        this.canDrink = canDrink;
    }

    public RepeatEatItem(Block block, Properties properties, boolean canDrink) {
        super(block, properties);
        this.canDrink = canDrink;
    }

    @Override
    public boolean isExtra(UseOnContext pContext) {
        return pContext.getItemInHand().getDamageValue() == 0;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        if (!this.canDrink){
            return UseAnim.EAT;
        }
        return UseAnim.DRINK;
    }

    public boolean getCanDrink(){
        return this.canDrink;
    }

    public static void repeatEatItemHurt(Player entity,ItemStack hand,ItemStack stack){
        if (hand.getDamageValue() == hand.getMaxDamage()-1) {
            hand.shrink(1);
            ItemUtils.givePlayerItem(entity,stack);
        }else {
            hand.hurtAndBreak(1, entity, LivingEntity.getSlotForHand(entity.getUsedItemHand()));
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        return eat(level,stack,livingEntity);
    }

    public ItemStack eat(Level pLevel, ItemStack pFood, LivingEntity living){
        if (living instanceof Player player){
            FoodProperties foodProperties = pFood.getFoodProperties(living);
            if (foodProperties != null){
                player.getFoodData().eat(foodProperties);
                addAllEffect(foodProperties,player,pLevel);
                rEat(pLevel,pFood,living);
                player.awardStat(Stats.ITEM_USED.get(pFood.getItem()));
                pLevel.playSound((Player)null, living.getX(), living.getY(), living.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, pLevel.random.nextFloat() * 0.1F + 0.9F);
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, pFood);
                }
                pFood.hurtAndBreak(1,living,LivingEntity.getSlotForHand(living.getUsedItemHand()));
                return pFood.getDamageValue() > pFood.getMaxDamage()-1 ? residue(pFood) : pFood;
            }
        }
        return pFood;
    }

    public ItemStack residue(ItemStack stack){
        return stack.getCraftingRemainingItem();
    }

    public void rEat(Level level,ItemStack food,LivingEntity living){

    }

    private void addAllEffect(FoodProperties foodProperties,Player player,Level level){
        for (FoodProperties.PossibleEffect next : foodProperties.effects()) {
            if (!level.isClientSide && next != null && level.random.nextFloat() < next.probability()) {
                player.addEffect(next.effect());
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltip, tooltipFlag);
        String translatable = this.canDrink ? "tooltips.bakeries.repeat_eat_item_drink" : "tooltips.bakeries.repeat_eat_item_eat";
        tooltip.add(Component.nullToEmpty(Component.translatable(translatable).getString() + (stack.getMaxDamage() - stack.getDamageValue()) + " / " + stack.getMaxDamage()));
    }

}
