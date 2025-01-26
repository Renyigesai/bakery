package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.init.BakeriesSounds;
import com.renyigesai.bakeries.util.Shortcuts;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ShakeItem extends Item {

    public final Supplier<Item> FINISH_ITEM;

    public ShakeItem(Properties pProperties, Supplier<Item> finishItem) {
        super(pProperties);
        FINISH_ITEM = finishItem;
    }

    public ItemStack getFinishItem() {
        return new ItemStack(this.FINISH_ITEM.get());
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {return BakeriesSounds.SHAKE.get();}

    public SoundEvent getDrinkingSound() {
        return BakeriesSounds.SHAKE.get();
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 48;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
            entity.startUsingItem(hand);
            return new InteractionResultHolder(InteractionResult.PASS, entity.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        Player player = (Player)pLivingEntity;
        pStack.shrink(1);
        Shortcuts.givePlayerItem(player,this.getFinishItem());
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.bakeries.tips.shake").withStyle(ChatFormatting.BLUE));
    }
}
