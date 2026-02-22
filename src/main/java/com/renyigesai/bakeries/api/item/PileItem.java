package com.renyigesai.bakeries.api.item;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.mix_block.MixBlock;
import com.renyigesai.bakeries.block.mix_block.MixBlockEntity;
import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesSounds;
import com.renyigesai.bakeries.util.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class PileItem extends ItemNameBlockItem {

    public final boolean effectTooltip;
    public final boolean customField;
    public final IntegerProperty integerProperty;

    public PileItem(Block block, IntegerProperty integerProperty, Item.Properties pProperties, boolean effectTooltip, boolean customField) {
        super(block, pProperties);
        this.integerProperty = integerProperty;
        this.effectTooltip = effectTooltip;
        this.customField = customField;
    }

    public PileItem(Block block, IntegerProperty integerProperty, Properties pProperties) {
        super(block, pProperties);
        this.integerProperty = integerProperty;
        this.effectTooltip = false;
        this.customField = false;
    }

    public SoundEvent getPlaceSound(){
        return BakeriesSounds.PASTRY_PLACE.get();
    }

    /*不使用原版放置方法*/
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return InteractionResult.FAIL;
    }

    public InteractionResult pileUseOn(UseOnContext pContext){
        Player player = pContext.getPlayer();
        InteractionResult result = this.use(pContext.getLevel(), player, pContext.getHand()).getResult();
        if (BakeriesMod.onAuxiliaryKey(player) && this.isExtra(pContext)) {
            Level level = pContext.getLevel();
            Block thisBlock = this.getBlock();
            BlockPos pos = pContext.getClickedPos();
            BlockState state = level.getBlockState(pos);
            if (state.is(BakeriesBlocks.MIX_BREAD_BLOCK.get())){
                return addMixBlock(level,pos,thisBlock,player,pContext);
            }
            if (!state.is(thisBlock)) {
                if (state.getBlock() instanceof PileBlock pileBlock && state.getValue(PileBlock.integerProperty) < pileBlock.getMaxPile() && thisBlock instanceof PileBlock){
                    return placeMixBlock(state,thisBlock,level,pos,player,pContext);
                }else {
                    this.place(new BlockPlaceContext(pContext));
                    player.awardStat(Stats.ITEM_USED.get(this));
                    level.playSound(null, pos, getPlaceSound(), SoundSource.PLAYERS, 0.8F, 0.8F);
                    return InteractionResult.SUCCESS;
                }
            }
            if (state.is(thisBlock)) {
                return addPileBlock(state,thisBlock,level,pos,player,pContext);
            }
        }
        return result;
    }

    public InteractionResult addMixBlock(Level level,BlockPos pos,Block thisBlock,Player player,UseOnContext pContext){
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MixBlockEntity mix){
            boolean flag = mix.addItem(new ItemStack(thisBlock.asItem()));
            if (flag){
                if (!player.getAbilities().instabuild) {
                    pContext.getItemInHand().shrink(1);
                }
                level.playSound(null, pos, getPlaceSound(), SoundSource.PLAYERS, 0.8F, 0.8F);
            }
            return InteractionResult.sidedSuccess(flag);
        }
        return InteractionResult.FAIL;
    }

    public InteractionResult addPileBlock(BlockState state,Block thisBlock,Level level,BlockPos pos,Player player,UseOnContext pContext){
        if (state.hasProperty(this.integerProperty)){
            int value = state.getValue(this.integerProperty);
            PileBlock newBlock = (PileBlock) thisBlock;
            if (value < newBlock.getMaxPile()) {
                level.setBlock(pos, state.setValue(this.integerProperty, value + 1), 3);
                level.playSound(null, pos, getPlaceSound(), SoundSource.PLAYERS, 0.8F, 0.8F);
                if (!player.getAbilities().instabuild) {
                    pContext.getItemInHand().shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    public InteractionResult placeMixBlock(BlockState state,Block thisBlock,Level level,BlockPos pos,Player player,UseOnContext pContext){
        fillMixBlock(state, thisBlock, level, pos);
        if (!player.getAbilities().instabuild) {
            pContext.getItemInHand().shrink(1);
        }
        level.playSound(null, pos, getPlaceSound(), SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    private void fillMixBlock(BlockState state,Block block,Level level,BlockPos pos){
        int integerProperty = state.getValue(PileBlock.integerProperty);
        Item item = state.getBlock().asItem();
        level.setBlock(pos, BakeriesBlocks.MIX_BREAD_BLOCK.get().defaultBlockState().setValue(MixBlock.FACING,state.getValue(PileBlock.FACING)),3);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MixBlockEntity mix){
            for (int i = 0; i < integerProperty; i++) {
                mix.addItem(new ItemStack(item));
                mix.updateBlock();
            }
            mix.addItem(new ItemStack(block.asItem()));
            mix.updateBlock();
        }
    }

    public boolean isExtra(UseOnContext pContext) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (itemstack.getOrCreateTag().getBoolean("perfect")){
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(itemstack);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public void getCustomField(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced){

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced) {
        if (customField){
            getCustomField(stack, level, tooltip, isAdvanced);
        }
        if (stack.getOrCreateTag().getBoolean("perfect")) {
            tooltip.add(Component.translatable("item.bakeries.tips.perfect_temperature").withStyle(ChatFormatting.GOLD));
        }
        if (effectTooltip) {
            TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
        }
    }
}
