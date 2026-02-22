package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.init.BakeriesSounds;
import com.renyigesai.bakeries.util.ItemUtils;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
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
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull SoundEvent getEatingSound() {return BakeriesSounds.SHAKE.get();}

    public @NotNull SoundEvent getDrinkingSound() {
        return BakeriesSounds.SHAKE.get();
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return 48;
    }

    @Override
    public @NotNull InteractionResultHolder use(@NotNull Level world, Player entity, @NotNull InteractionHand hand) {
            entity.startUsingItem(hand);
            return new InteractionResultHolder(InteractionResult.PASS, entity.getItemInHand(hand));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        pStack.shrink(1);
        if (pLivingEntity instanceof Player player){
            ItemUtils.givePlayerItem(player,this.getFinishItem());
        }else {
            ItemUtils.spawnItemEntity(pLevel,this.getFinishItem(),pLivingEntity.getX()+0.5,pLivingEntity.getY(),pLivingEntity.getZ()+0.5,new Vec3(0.0,0.0,0.0));
        }

        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.bakeries.tips.shake").withStyle(ChatFormatting.BLUE));
    }
}
