package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.mooncake.MooncakeBlock;
import com.renyigesai.bakeries.block.mooncake.MooncakeBlockEntity;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.init.BakeriesSounds;
import com.renyigesai.bakeries.util.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class MooncakeItem extends ItemNameBlockItem {
    private static boolean eating = false;
    public MooncakeItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        ItemStack itemInHand = pContext.getItemInHand();
        if (itemInHand.is(BakeriesItems.RAW_MOONCAKE.get())){
            return InteractionResult.FAIL;
        }
        if (player != null && BakeriesMod.onAuxiliaryKey(player)){
            return pileUseOn(pContext);
        }
        return InteractionResult.PASS;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        eat(pLevel,pLivingEntity,pStack);
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    private void eat(Level level, LivingEntity living, ItemStack food){
        if (level.isClientSide){
            return;
        }
        if (eating){
            return;
        }
        eating = true;
        try {
            List<String> itemIds = getItemIds(food);
            itemIds.forEach(id -> {
                try {
                    Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(id));
                    ItemStack itemStack = new ItemStack(item);
                    item.finishUsingItem(itemStack, level, living);
                }catch (Exception exception){
                    BakeriesMod.LOGGER.error("Failed to apply sub-item {}", id,exception);
                }

            });
        } finally {
            eating = false;
        }
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (!pStack.is(BakeriesItems.MOONCAKE.get())){
            return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
        }
        if (!pPlayer.level().isClientSide){
            if (pInteractionTarget instanceof Player givePlayer){
                ItemStack moonCake = pStack.copy();
                moonCake.setCount(1);
                ItemUtils.givePlayerItem(givePlayer,moonCake);
                ItemUtils.shrink(pStack,1,pPlayer);
                givePlayer.displayClientMessage(Component.translatable("tip.bakeries.give_mooncake",pPlayer.getName().getString()),true);
                return InteractionResult.SUCCESS;
            }
        }
        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
    }

    public InteractionResult pileUseOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        if (player == null) return InteractionResult.PASS;
        Level level = pContext.getLevel();
        Block thisBlock = this.getBlock();
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (BakeriesMod.onAuxiliaryKey(player)) {
            if (state.is(thisBlock)) {
                addPileBlock(state, thisBlock, level, pos, player, pContext);
                return InteractionResult.SUCCESS;
            }
            BlockPlaceContext placeContext = new BlockPlaceContext(pContext);
            InteractionResult placeResult = this.place(placeContext);
            if (placeResult.consumesAction()) {
                player.awardStat(Stats.ITEM_USED.get(this));
                level.playSound(null, pos, BakeriesSounds.PASTRY_PLACE.get(), SoundSource.PLAYERS, 0.8F, 0.8F);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    public InteractionResult addPileBlock(BlockState state, Block thisBlock, Level level, BlockPos pos, Player player, UseOnContext pContext) {
        if (state.hasProperty(MooncakeBlock.PILE)) {
            int pile = state.getValue(MooncakeBlock.PILE);
            if (pile < 4) {
                level.setBlock(pos, state.setValue(MooncakeBlock.PILE, pile + 1), 3);
                if (level.getBlockEntity(pos) instanceof MooncakeBlockEntity mc){
                    ItemStack copy = pContext.getItemInHand().copy();
                    copy.setCount(1);
                    mc.getItems().setStackInSlot(pile,copy);
                    mc.update();
                }
                level.playSound(null, pos, BakeriesSounds.PASTRY_PLACE.get(), SoundSource.PLAYERS, 0.8F, 0.8F);
                ItemUtils.shrink(pContext.getItemInHand(),1,player);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public static ItemStack copyAttribute(ItemStack input, ItemStack output){
        CompoundTag tag = output.getOrCreateTag();
        List<String> itemIds = getItemIds(input);
        StringBuffer buffer = new StringBuffer();
        itemIds.forEach(id -> buffer.append("&").append(id));
        tag.putString("ItemId",buffer.toString());
        return output;
    }

    public static List<String> getItemIds(ItemStack stack) {
        if (!stack.getOrCreateTag().contains("ItemId")){
            return Collections.emptyList();
        }
        return Arrays.stream(stack.getOrCreateTag().getString("ItemId").split("&"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        ItemStack itemInHand = pContext.getItemInHand();
        BlockPos clickedPos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        if (itemInHand.is(BakeriesItems.RAW_MOONCAKE.get())){
            return InteractionResult.FAIL;
        }
        if (player != null){
            ItemStack copy = itemInHand.copy();
            super.place(pContext);
            if (level.getBlockEntity(clickedPos) instanceof MooncakeBlockEntity mc){
                copy.setCount(1);
                mc.getItems().setStackInSlot(0,copy);
                mc.update();
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if (pStack.is(BakeriesItems.RAW_MOONCAKE.get())){
            pTooltip.add(Component.translatable("tip.bakeries.raw_mooncake").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC));
            pTooltip.add(Component.literal("Min170°C").withStyle(ChatFormatting.BLUE));
        }
        if (pStack.getOrCreateTag().getBoolean("perfect")) {
            pTooltip.add(Component.translatable("item.bakeries.tips.perfect_temperature").withStyle(ChatFormatting.GOLD));
        }
        StringBuffer buffer = new StringBuffer();
        getItemIds(pStack).forEach(id -> buffer.append(BuiltInRegistries.ITEM.get(new ResourceLocation(id)).getDescription().getString()));
        if (!buffer.isEmpty()){
            pTooltip.add(Component.translatable("tip.bakeries.mooncake",buffer.toString()).withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC));
        }
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
