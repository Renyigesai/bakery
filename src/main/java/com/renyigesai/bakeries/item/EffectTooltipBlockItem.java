package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.util.ItemUtils;
import com.renyigesai.bakeries.util.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class EffectTooltipBlockItem extends BlockItem {

    public final boolean effectTooltip;

    public EffectTooltipBlockItem(Block pBlock, Properties pProperties, boolean effectTooltip) {
        super(pBlock, pProperties);
        this.effectTooltip = effectTooltip;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pStack.hasCraftingRemainingItem()){
            if (pLivingEntity instanceof Player player){
                if (!player.getAbilities().instabuild){
                    ItemUtils.givePlayerItem(player,pStack.getCraftingRemainingItem());
                }
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced) {
        if (stack.getOrCreateTag().getBoolean("perfect")) {
            tooltip.add(Component.translatable("item.bakeries.tips.perfect_temperature").withStyle(ChatFormatting.GOLD));
        }
        if (effectTooltip) {
            TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
        }
    }
}
