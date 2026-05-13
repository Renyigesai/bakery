package com.renyigesai.bakeries.api.items;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.block.AbstractPileBlock;
import com.renyigesai.bakeries.common.blocks.BreadBlock;
import com.renyigesai.bakeries.common.blocks.mix_block.MixBlock;
import com.renyigesai.bakeries.common.blocks.mix_block.MixBlockEntity;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesDataComponents;
import com.renyigesai.bakeries.common.init.BakeriesSounds;
import com.renyigesai.bakeries.common.utils.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PileItem extends BlockItem {
    public final boolean effectTooltip;
    private final SoundEvent placeSound;
    public PileItem(Block block, Properties properties, boolean effectTooltip) {
        super(block, properties);
        this.effectTooltip = effectTooltip;
        this.placeSound = BakeriesSounds.PASTRY_PLACE.get();
    }

    public PileItem(Block block, Properties properties) {
        super(block, properties);
        this.effectTooltip = false;
        this.placeSound = BakeriesSounds.PASTRY_PLACE.get();
    }

    public PileItem(Block block, PileItem.PileProperties pileProperties) {
        super(block, pileProperties.itemProperties);
        this.placeSound = pileProperties.placeSound;
        this.effectTooltip = pileProperties.effectTooltip;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        if (isPerfect(itemInHand)){
            player.startUsingItem(usedHand);
            return InteractionResultHolder.consume(itemInHand);
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
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
            if (state.is(BakeriesBlocks.MIX_BLOCK.get())){
                return addMixBlock(level,pos,thisBlock,player,pContext);
            }
            if (!state.is(thisBlock)) {
                    if (state.getBlock() instanceof AbstractPileBlock pileBlock &&  state.getValue(pileBlock.getPileProperty()) < pileBlock.getMaxPile() && thisBlock instanceof AbstractPileBlock) {
                        return placeMixBlock(state,(AbstractPileBlock) thisBlock,pileBlock,level,pos,player,pContext);
                    } else {
                        this.place(new BlockPlaceContext(pContext));
                        try {
                            level.playSound(null, pos, ((AbstractPileBlock) thisBlock).getPlaceSound(), SoundSource.PLAYERS, 0.8F, 0.8F);
                        }catch (ClassCastException e){
                            BakeriesMod.LOGGER.error("PileItem pileUseOn",e);
                        }
                        return InteractionResult.SUCCESS;
                    }
            }
            AbstractPileBlock pileBlock = (AbstractPileBlock) thisBlock;
            Property<Integer> pileProperty = pileBlock.getPileProperty();
            if (state.is(thisBlock)) {
                if (state.hasProperty(pileProperty)){
                    int value = state.getValue(pileProperty);
                    if (value < pileBlock.getMaxPile()) {
                        level.setBlock(pos, state.setValue(pileProperty, value + 1), 3);
                        level.playSound(null, pos, pileBlock.getPlaceSound(), SoundSource.PLAYERS, 0.8F, 0.8F);
                        if (!player.getAbilities().instabuild) {
                            pContext.getItemInHand().shrink(1);
                        }
                        result = InteractionResult.sidedSuccess(pContext.getLevel().isClientSide);
                    }
                }
            }
        }
        return result;
    }


    public boolean isExtra(UseOnContext pContext) {
        return true;
    }

    public boolean isPerfect(ItemStack stack){
        return stack.has(BakeriesDataComponents.PERFECT) && Boolean.TRUE.equals(stack.get(BakeriesDataComponents.PERFECT));
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    public InteractionResult addMixBlock(Level level,BlockPos pos,Block thisBlock,Player player,UseOnContext pContext){
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MixBlockEntity mix){
            boolean flag = mix.addItem(new ItemStack(thisBlock.asItem()));
            if (flag){
                if (!player.getAbilities().instabuild) {
                    pContext.getItemInHand().shrink(1);
                }
                if (thisBlock instanceof AbstractPileBlock){
                    level.playSound(null, pos, ((AbstractPileBlock)thisBlock).getPlaceSound(), SoundSource.PLAYERS, 0.8F, 0.8F);
                }
            }
            return InteractionResult.sidedSuccess(flag);
        }
        return InteractionResult.FAIL;
    }

    private void fillMixBlock(BlockState state,AbstractPileBlock block,AbstractPileBlock pileBlock,Level level,BlockPos pos){
        int integerProperty = state.getValue(pileBlock.getPileProperty());
        Item item = state.getBlock().asItem();
        level.setBlock(pos, BakeriesBlocks.MIX_BLOCK.get().defaultBlockState().setValue(MixBlock.FACING,state.getValue(BreadBlock.FACING)),3);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MixBlockEntity mix){
            for (int i = 0; i < integerProperty; i++) {
                mix.addItem(new ItemStack(item));
            }
            mix.addItem(new ItemStack(block.asItem()));
            mix.updateBlock();
        }
    }

    public InteractionResult placeMixBlock(BlockState state,AbstractPileBlock thisBlock,AbstractPileBlock pileBlock,Level level,BlockPos pos,Player player,UseOnContext pContext){
        fillMixBlock(state, thisBlock,pileBlock ,level, pos);
        if (!player.getAbilities().instabuild) {
            pContext.getItemInHand().shrink(1);
        }
        level.playSound(null, pos, pileBlock.getPlaceSound(), SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (isPerfect(stack)){
            tooltipComponents.add(Component.translatable("tooltips.bakeries.pile_item_perfect").withStyle(ChatFormatting.GOLD));
        }
        if (effectTooltip) {
            TextUtils.addFoodEffectTooltip(stack, tooltipComponents::add, 1.0F,context.tickRate());
        }
    }

    public static class PileProperties{
        private SoundEvent placeSound;
        private boolean effectTooltip;
        private Item.Properties itemProperties;

        public PileProperties() {
            this.itemProperties = new Item.Properties();
            this.placeSound = BakeriesSounds.PASTRY_PLACE.get();
            this.effectTooltip = false;
        }

        public PileProperties itemProperties(Item.Properties properties){
            this.itemProperties = properties;
            return this;
        }

        public PileProperties effectTooltip(boolean effectTooltip){
            this.effectTooltip = effectTooltip;
            return this;
        }

        public PileProperties placeSound(SoundEvent placeSound){
            this.placeSound = placeSound;
            return this;
        }
    }
}
