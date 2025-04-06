package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.api.block.PileBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ColdDrinkItem extends RepeatEatItem{

    public ColdDrinkItem(Block block, Properties pProperties, boolean effectTooltip, boolean customField) {
        super(block, PileBlock.integerProperty, pProperties, effectTooltip, customField);
    }

    public ColdDrinkItem(Block block, Properties pProperties) {
        super(block, PileBlock.integerProperty, pProperties);
    }

    @Override
    public boolean canDrink() {
        return true;
    }

    @Override
    void eat(ItemStack pStack, Level level, LivingEntity pLivingEntity, Vec3 vec3) {
        level.gameEvent(pLivingEntity, GameEvent.EAT, vec3);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced) {
        tooltip.add(Component.translatable("item.bakeries.tips.cold_drink").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(stack, level, tooltip, isAdvanced);
    }
}
