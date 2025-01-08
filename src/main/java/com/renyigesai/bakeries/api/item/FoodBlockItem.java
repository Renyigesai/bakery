package com.renyigesai.bakeries.api.item;

import com.renyigesai.bakeries.api.Shortcuts;
import com.renyigesai.bakeries.api.TextUtils;
import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.api.block.properties.ModIntegerProperty;
import com.renyigesai.bakeries.key.BakeriesKeyMapping;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
    public final ModIntegerProperty integerProperty;
    public FoodBlockItem(Block block, ModIntegerProperty integerProperty, Item.Properties pProperties, boolean effectTooltip) {
        super(block, pProperties);
        this.integerProperty = integerProperty;
        this.effectTooltip = effectTooltip;
    }

    public FoodBlockItem(Block block, ModIntegerProperty integerProperty, Properties pProperties) {
        super(block, pProperties);
        this.integerProperty = integerProperty;
        this.effectTooltip=false;
    }
// 暂时弃用
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
//        if (itemStack.getOrCreateTag().getBoolean("perfect")) {
//            pPlayer.startUsingItem(pUsedHand);
//            return new InteractionResultHolder(InteractionResult.PASS, pPlayer.getItemInHand(pUsedHand));
//        }return super.use(pLevel, pPlayer, pUsedHand);
//    }


// 暂时弃用
//    @Override
//    protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
//        if(Screen.hasShiftDown()) {
//            return super.placeBlock(pContext, pState);
//        }else {
//            return false;
//        }
//    }

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
    @Override
    protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
        if(BakeriesKeyMapping.place.consumeClick()) {
            return super.placeBlock(pContext, pState);
        }else {
            return false;
        }
    }

    public InteractionResult pileUp(Level level, BlockPos pos, BlockState state, ItemStack handStack){
        int pile = state.getValue(this.integerProperty);
        if (pile < this.integerProperty.getPossibleValues().size()) {
            Shortcuts.setBlock(level, pos, state, this.integerProperty, 1, true);
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
            tooltip.add(Component.translatable("item.bakeries.tips.perfect_temperature").withStyle(ChatFormatting.GOLD));
        }
        if (effectTooltip) {
            TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
        }
    }
}
