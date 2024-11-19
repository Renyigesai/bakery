package com.renyigesai.bakery.api.item;

import com.renyigesai.bakery.api.RandomEffect;
import com.renyigesai.bakery.api.TextUtils;
import com.renyigesai.bakery.api.block.PileBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
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

import javax.annotation.Nullable;
import java.util.List;

public class FoodBlockItem extends ItemNameBlockItem {

    public final boolean effectTooltip;
    public FoodBlockItem(Block block, Item.Properties pProperties, boolean effectTooltip) {
        super(block, pProperties);
        this.effectTooltip = effectTooltip;
    }

//    public FoodBlockItem(Block block, Item.Properties pProperties, RandomEffect effects, boolean effectTooltip) {
//        super(block, pProperties);
//        this.effectTooltip = effectTooltip;
//        this.effects = effects;
//    }

    public FoodBlockItem(Block block, Properties pProperties) {
        super(block, pProperties);
        this.effectTooltip=false;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pStack.getOrCreateTag().getBoolean("perfect")) {
            RandomEffect randomEffect = new RandomEffect();
            if(pStack.is(ItemTags.create(new ResourceLocation("bakery:sweet_bread")))) {
                pLivingEntity.addEffect(randomEffect.getSweetEffect());
            }else if (pStack.is(ItemTags.create(new ResourceLocation("bakery:salty_bread")))){
                pLivingEntity.addEffect(randomEffect.getSaltyEffect());
            }else {
                pLivingEntity.addEffect(randomEffect.getWholeWheatEffect());
            }
        }
        return this.isEdible() ? pLivingEntity.eat(pLevel, pStack) : pStack;
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
        if(Screen.hasShiftDown()) {
            return super.placeBlock(pContext, pState);
        }else {
            return false;
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack handStack = player.getItemInHand(hand);
        boolean isPile = handStack.is(asItem());
        if(block instanceof PileBlock){
            if (!level.isClientSide) {
                if (isPile && Screen.hasShiftDown()) {
                    return pileUp(level, pos, state, handStack);

                }
            }
            if (isPile && Screen.hasShiftDown()) {
                return pileUp(level, pos, state, handStack);
            }
        }
        return super.useOn(context);
    }

    public InteractionResult pileUp(Level level, BlockPos pos, BlockState state, ItemStack handStack){
        int pile = state.getValue(PileBlock.PILE);
        if (pile < 4) {
            level.setBlock(pos,state.setValue(PileBlock.PILE, pile + 1),4);
            handStack.shrink(1);
            level.playSound(null, pos, SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 0.8F, 0.8F);
        }else {
            return InteractionResult.FAIL;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        if (stack.getOrCreateTag().getBoolean("perfect")){
            tooltip.add(Component.translatable("item.bakery.tips.perfect_temperature").withStyle(ChatFormatting.GOLD));
        }
        if (effectTooltip) {
            TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
        }
    }
}
