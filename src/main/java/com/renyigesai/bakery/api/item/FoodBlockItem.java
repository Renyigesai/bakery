package com.renyigesai.bakery.api.item;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.renyigesai.bakery.api.block.PileBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FoodBlockItem extends ItemNameBlockItem {

    private static final Component NO_EFFECT = Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);

    public FoodBlockItem(Block block, Properties pProperties) {
        super(block, pProperties);
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

    private static void listPotionEffects(ItemStack pStack, Consumer<MobEffectInstance> pOutput) {
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag != null && compoundtag.contains("Effects", 9)) {
            ListTag listtag = compoundtag.getList("Effects", 10);

            for(int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag1 = listtag.getCompound(i);
                int j;
                if (compoundtag1.contains("EffectDuration", 99)) {
                    j = compoundtag1.getInt("EffectDuration");
                } else {
                    j = 160;
                }

                MobEffect mobeffect = MobEffect.byId(compoundtag1.getInt("EffectId"));
                mobeffect = net.minecraftforge.common.ForgeHooks.loadMobEffect(compoundtag1, "forge:effect_id", mobeffect);
                if (mobeffect != null) {
                    pOutput.accept(new MobEffectInstance(mobeffect, j));
                }
            }
        }

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel,List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
            List<MobEffectInstance> list = new ArrayList<>();
            listPotionEffects(pStack, list::add);
            PotionUtils.addPotionTooltip(list, pTooltipComponents, 1.0F);
    }
}
