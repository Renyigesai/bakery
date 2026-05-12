package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.common.init.BakeriesSounds;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 48;
    }

    @Override
    public @NotNull InteractionResultHolder use(@NotNull Level world, Player entity, @NotNull InteractionHand hand) {
        /*物品装物品示例不要删除注册的时候记得加.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)*/
//        ItemStack itemInHand = entity.getItemInHand(hand);
//        List<ItemStack> stacks = new ArrayList<>();
//        stacks.add(new ItemStack(Items.APPLE));
//        stacks.add(new ItemStack(Items.DIAMOND));
//        stacks.add(new ItemStack(Items.STONE));
//        itemInHand.set(DataComponents.CONTAINER,ItemContainerContents.fromItems(stacks));
        entity.startUsingItem(hand);
        return new InteractionResultHolder(InteractionResult.PASS, entity.getItemInHand(hand));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        pStack.shrink(1);
        if (pLivingEntity instanceof Player player){
            player.getCooldowns().addCooldown(this.getFinishItem().getItem(),10);
            ItemUtils.givePlayerItem(player,this.getFinishItem());
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltips.bakeries.shake").withStyle(ChatFormatting.BLUE));
    }
}
