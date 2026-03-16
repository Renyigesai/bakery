package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.api.block.IMouldBlock;
import com.renyigesai.bakeries.init.BakeriesItemTag;
import com.renyigesai.bakeries.util.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MouldBlockItem extends BlockItem {
    public MouldBlockItem(Block block, Properties pProperties) {
        super(block, pProperties);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 20;
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
                player.getOffhandItem().hurtAndBreak(1, player, (p_41300_) -> p_41300_.broadcastBreakEvent(EquipmentSlot.OFFHAND));
            }
            player.getCooldowns().addCooldown(this,10);
            level.playSound(null, player.getOnPos(), SoundEvents.LANTERN_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    public boolean isKnifeItem(ItemStack itemStack) {
        return itemStack.is(BakeriesItemTag.BREAD_KNIFE) || itemStack.is(ItemTags.create(new ResourceLocation("forge","tools/knives")));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("item.bakeries.tips.mould").withStyle(ChatFormatting.BLUE));
    }

}
