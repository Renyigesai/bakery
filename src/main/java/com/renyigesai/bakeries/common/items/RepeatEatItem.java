package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.api.items.PileItem;
import com.renyigesai.bakeries.common.init.BakeriesDataComponents;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
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

    public RepeatEatItem(Block block, Properties properties, int eatCount,boolean canDrink) {
        super(block, properties.component(BakeriesDataComponents.EAT_COUNT_MAX,eatCount).component(BakeriesDataComponents.EAT_COUNT,eatCount));
        this.canDrink = canDrink;
    }

    public RepeatEatItem(Block block, Properties properties, int eatCount,boolean effectTooltip,boolean canDrink) {
        super(block, properties.component(BakeriesDataComponents.EAT_COUNT_MAX,eatCount).component(BakeriesDataComponents.EAT_COUNT,eatCount),effectTooltip);
        this.canDrink = canDrink;
    }

    public RepeatEatItem(Block block, Properties properties, boolean effectTooltip, boolean canDrink) {
        super(block, properties, effectTooltip);
        this.canDrink = canDrink;
    }

    public RepeatEatItem(Block block, Properties properties, boolean canDrink) {
        super(block, properties);
        this.canDrink = canDrink;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        if (!this.canDrink){
            return UseAnim.EAT;
        }
        return UseAnim.DRINK;
    }

    @Override
    public boolean isExtra(UseOnContext pContext) {
        ItemStack itemInHand = pContext.getItemInHand();
        if (isRepeatEat(itemInHand)){
            int eatCount = itemInHand.getOrDefault(BakeriesDataComponents.EAT_COUNT,-1);
            int eatCountMax = itemInHand.getOrDefault(BakeriesDataComponents.EAT_COUNT_MAX,-1);
            return eatCount == eatCountMax;
        }
        return super.isExtra(pContext);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return this.canDrink ? 5592575 : 15574564;
    }

    public static boolean isRepeatEat(ItemStack stack){
        return stack.has(BakeriesDataComponents.EAT_COUNT_MAX) && stack.has(BakeriesDataComponents.EAT_COUNT);
    }

    public int getBarWidth(ItemStack stack) {
        if (isRepeatEat(stack)) {
            Integer eatCount = stack.get(BakeriesDataComponents.EAT_COUNT);
            Integer eatCountMax = stack.get(BakeriesDataComponents.EAT_COUNT_MAX);
            if (eatCount != null && eatCountMax != null && eatCountMax > 0) {
                return Mth.clamp(Math.round(13.0F * eatCount / eatCountMax), 0, 13);
            }
        }
        return super.getBarWidth(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if (!isRepeatEat(stack)){
            return false;
        }
        Integer eatCount = stack.get(BakeriesDataComponents.EAT_COUNT);
        Integer eatCountMax = stack.get(BakeriesDataComponents.EAT_COUNT_MAX);
        if (eatCount != null &&  eatCountMax != null){
            return eatCount < eatCountMax;
        }
        return false;
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
        return onConsume(level,livingEntity,stack);
    }

    public ItemStack onConsume(Level level,LivingEntity living,ItemStack stack){
        if (isRepeatEat(stack)){
            int eatCount = stack.getOrDefault(BakeriesDataComponents.EAT_COUNT,-1);
            ItemStack cache = ItemStack.EMPTY;
            eat(level, living, stack);
            if (eatCount - 1 == 0){
                cache = stack.copy();
                stack.consume(1,living);
            }else {
                stack.set(BakeriesDataComponents.EAT_COUNT,eatCount - 1);
            }
            return (eatCount - 1 == 0 && cache.hasCraftingRemainingItem()) ? cache.getCraftingRemainingItem() : stack;
        }
        return stack;
    }

    public void eat(Level level,LivingEntity living,ItemStack stack){

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
        if (isRepeatEat(stack)){
            int eatCount = stack.getOrDefault(BakeriesDataComponents.EAT_COUNT,-1);
            int eatCountMax = stack.getOrDefault(BakeriesDataComponents.EAT_COUNT_MAX,-1);
            String translatable = this.canDrink ? "tooltips.bakeries.repeat_eat_item_drink" : "tooltips.bakeries.repeat_eat_item_eat";
            tooltip.add(Component.literal(Component.translatable(translatable).getString() + eatCount + " / " + eatCountMax));
        }
    }

}
