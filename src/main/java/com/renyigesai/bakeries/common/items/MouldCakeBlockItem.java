package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.common.blocks.mould_cake.MouldCakeBlockEntity;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.tag.CommonTags;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.function.Supplier;

public class MouldCakeBlockItem extends BlockItem {

    public final Supplier<Item> mouldContent;

    public MouldCakeBlockItem(Block block, Properties pProperties, Supplier<Item> mouldContent) {
        super(block, pProperties);
        this.mouldContent = mouldContent;
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
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 20;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        if (mainHandItem.getItem() instanceof MouldCakeBlockItem) {
            if (isKnifeItem(offhandItem)) {
                player.startUsingItem(usedHand);
                return new InteractionResultHolder(InteractionResult.SUCCESS, player.getItemInHand(usedHand));
            }
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && stack.getItem() instanceof MouldCakeBlockItem mould) {
            ItemUtils.givePlayerItem(player,new ItemStack(BakeriesItems.MOULD_TWO.get()));
            ItemUtils.givePlayerItem(player,new ItemStack(mould.mouldContent.get()));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
                player.getOffhandItem().hurtAndBreak(1, player, EquipmentSlot.OFFHAND);
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
    public InteractionResult place(BlockPlaceContext context) {
        super.place(context);
        BlockPos clickedPos = context.getClickedPos();
        Level level = context.getPlayer().level();
        BlockEntity blockEntity = level.getBlockEntity(clickedPos);
        if (blockEntity instanceof MouldCakeBlockEntity mcb){
            mcb.getItems().setStackInSlot(0,new ItemStack(mouldContent.get()));
            mcb.update();
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltips.bakeries.mould").withStyle(ChatFormatting.BLUE));
    }
}
