package com.renyigesai.bakery.item;

import com.renyigesai.bakery.init.BakeryItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlourSieveItem extends Item {

    public FlourSieveItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public SoundEvent getEatingSound() {return SoundEvents.SAND_BREAK;}

    public SoundEvent getDrinkingSound() {
        return SoundEvents.SAND_BREAK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack hand = pPlayer.getMainHandItem();
        if (hand.is(BakeryItems.WHOLE_WHEAT_FLOUR.get())){
            pPlayer.startUsingItem(pUsedHand);
            return new InteractionResultHolder(InteractionResult.SUCCESS, pPlayer.getItemInHand(pUsedHand));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        Player player = (Player)pLivingEntity;
        pLivingEntity.getMainHandItem().shrink(1);
        player.getInventory().placeItemBackInInventory(new ItemStack(BakeryItems.FLOUR.get()));
        pStack.hurt(1, RandomSource.create(), null);
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 16;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.bakery.tips.flour_sieve").withStyle(ChatFormatting.BLUE));
    }
}
