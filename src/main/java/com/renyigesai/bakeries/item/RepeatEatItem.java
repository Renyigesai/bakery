package com.renyigesai.bakeries.item;

import com.mojang.datafixers.util.Pair;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.item.PileItem;
import com.renyigesai.bakeries.util.ItemUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
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

    public final int eatCountMax;

    public RepeatEatItem(Block block, IntegerProperty integerProperty, Properties pProperties, int eatCountMax,boolean effectTooltip, boolean customField) {
        super(block, integerProperty, pProperties.stacksTo(1), effectTooltip, customField);
        this.eatCountMax = eatCountMax;
    }

    @Override
    public boolean isExtra(UseOnContext pContext) {
        return !pContext.getItemInHand().getOrCreateTag().contains("EatCountMax");
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
        return onConsume(pLevel,pLivingEntity,pStack);
    }

    public ItemStack onConsume(Level level,LivingEntity living,ItemStack stack){
        eat(level, stack,living);
        try {
            if (stack.getFoodProperties(living) != null){
                addAllEffect(stack.getFoodProperties(living),living,level);
            }
        }catch (NullPointerException exception){
            BakeriesMod.LOGGER.error(String.valueOf(exception));
        }
        return consume(stack,living);
    }

    public ItemStack consume(ItemStack stack, @Nullable LivingEntity living){

        if (living instanceof Player player && player.getAbilities().instabuild){
            return stack;
        }

        ItemStack cache = ItemStack.EMPTY;
        stack.getOrCreateTag().putInt("EatCountMax",this.eatCountMax);
        if (stack.getOrCreateTag().contains("EatCount")){
            int oleEatCount = stack.getOrCreateTag().getInt("EatCount");
            if (oleEatCount - 1 == 0){
                cache = stack.copy();
                stack.shrink(1);
            }else {
                stack.getOrCreateTag().putInt("EatCount",oleEatCount - 1);
            }
        }else {
            stack.getOrCreateTag().putInt("EatCount",this.eatCountMax - 1);
        }
        if (stack.getOrCreateTag().contains("EatCount")){
            int eatCount = stack.getOrCreateTag().getInt("EatCount");
            return (eatCount - 1 == 0 && cache.hasCraftingRemainingItem()) ? cache.getCraftingRemainingItem() : stack;
        }
        return stack;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return canDrink() ? 5592575 : 15574564;
    }

    public int getBarWidth(ItemStack stack) {
        if (stack.getOrCreateTag().contains("EatCountMax")) {
            int eatCount = stack.getOrCreateTag().getInt("EatCount");
            int eatCountMax = stack.getOrCreateTag().getInt("EatCountMax");
            return Mth.clamp(Math.round(13.0F * eatCount / eatCountMax), 0, 13);
        }
        return super.getBarWidth(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if (!stack.getOrCreateTag().contains("EatCountMax")){
            return false;
        }
        int eatCount = stack.getOrCreateTag().getInt("EatCount");
        int eatCountMax = stack.getOrCreateTag().getInt("EatCountMax");
        return eatCount < eatCountMax;
    }

    public void repeatEat(Level level, ItemStack food, LivingEntity living){

    }

    public void eat(Level pLevel, ItemStack pFood,LivingEntity living){
        if (living instanceof Player player){
            player.getFoodData().eat(pFood.getItem(),pFood,player);
            player.awardStat(Stats.ITEM_USED.get(pFood.getItem()));
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, pFood);
            }
        }
        FoodProperties foodProperties = pFood.getFoodProperties(living);
        if (foodProperties != null){
            ForgeEventFactory.onItemUseFinish(living, pFood.copy(), 0, ItemStack.EMPTY);
            addAllEffect(foodProperties,living,pLevel);
        }
        repeatEat(pLevel,pFood,living);
        pLevel.playSound((Player)null, living.getX(), living.getY(), living.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, pLevel.random.nextFloat() * 0.1F + 0.9F);
    }

    public void addAllEffect(FoodProperties foodProperties,LivingEntity living,Level level){
        List<Pair<MobEffectInstance, Float>> effects = foodProperties.getEffects();
        for (Pair<MobEffectInstance, Float> next : effects) {
            if (!level.isClientSide && next != null && level.random.nextFloat() < next.getSecond()) {
                living.addEffect(new MobEffectInstance(next.getFirst()));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);
        String translatable = canDrink() ? "item.bakeries.tips.repeat_eat_item.drink" : "item.bakeries.tips.repeat_eat_item";
        if (stack.getOrCreateTag().contains("EatCountMax")){
            int eatCount = stack.getOrCreateTag().getInt("EatCount");
            int eatCountMax = stack.getOrCreateTag().getInt("EatCountMax");
            tooltip.add(Component.translatable(translatable).append(String.valueOf(eatCount)).append(" / ").append(String.valueOf(eatCountMax)));
        }else {
            tooltip.add(Component.translatable(translatable).append(String.valueOf(this.eatCountMax)).append(" / ").append(String.valueOf(this.eatCountMax)));
        }
    }

    @Deprecated
    public ItemStack residue(ItemStack stack){
        return stack.getCraftingRemainingItem();
    }

    @Deprecated
    public static void rHurt(Player entity,ItemStack hand,ItemStack stack){
        if (hand.getDamageValue() == hand.getMaxDamage()-1) {
            hand.shrink(1);
            ItemUtils.givePlayerItem(entity,stack);
        }else {
            hand.hurt(1, RandomSource.create(), null);
        }
    }

    @Deprecated
    public static void rHurt(ItemStack hand){
        if (hand.getDamageValue() == hand.getMaxDamage()-1) {
            hand.shrink(1);
        }else {
            hand.hurt(1, RandomSource.create(), null);
        }
    }
}
