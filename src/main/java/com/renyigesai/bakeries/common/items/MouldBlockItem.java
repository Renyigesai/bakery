package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.api.block.IMouldBlock;
import com.renyigesai.bakeries.common.tag.CommonTags;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class MouldBlockItem extends BlockItem {
    public MouldBlockItem(Block block, Properties pProperties) {
        super(block, pProperties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 20;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null){
            ItemStack offhandItem = player.getOffhandItem();
            if (isKnifeItem(offhandItem)){
                return InteractionResult.FAIL;
            }
        }
        return super.useOn(context);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        if (mainHandItem.getItem() instanceof MouldBlockItem mould && mould.getBlock() instanceof IMouldBlock) {
            if (isKnifeItem(offhandItem)) {
                player.startUsingItem(usedHand);
                return new InteractionResultHolder(InteractionResult.SUCCESS, player.getItemInHand(usedHand));
            }
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && stack.getItem() instanceof MouldBlockItem mould && mould.getBlock() instanceof IMouldBlock iMouldBlock) {
            ItemUtils.givePlayerItem(player,new ItemStack(iMouldBlock.getMouldItem().get()));
            ItemUtils.givePlayerItem(player,new ItemStack(iMouldBlock.getMouldContentItem().get()));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
                player.getOffhandItem().hurtAndBreak(1, player,EquipmentSlot.OFFHAND);
            }
            player.getCooldowns().addCooldown(this,10);
            level.playSound(null, player.getOnPos(), SoundEvents.LANTERN_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    public boolean isKnifeItem(ItemStack itemStack) {
        return itemStack.is(CommonTags.KNIFE);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltips.bakeries.mould").withStyle(ChatFormatting.BLUE));
    }
}
