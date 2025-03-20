package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.api.block.properties.ModIntegerProperty;
import com.renyigesai.bakeries.api.item.FoodBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class RepeatEatItem extends FoodBlockItem {

    public RepeatEatItem(Block block, ModIntegerProperty integerProperty, Properties pProperties, boolean effectTooltip, boolean customField) {
        super(block, integerProperty, pProperties, effectTooltip, customField);
    }

    public RepeatEatItem(Block block, ModIntegerProperty integerProperty, Properties pProperties) {
        super(block, integerProperty, pProperties);
    }

    @Override
    public boolean isExtra(UseOnContext pContext) {
        return pContext.getItemInHand().getDamageValue() == 0;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        if (!canDrink()){
            return UseAnim.EAT;
        }
        return UseAnim.DRINK;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    public boolean canDrink(){
        return false;
    }


    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {

        if (pStack.getDamageValue() == pStack.getMaxDamage()-1) {
            return super.finishUsingItem(pStack, pLevel, pLivingEntity);
        }
        eat(pStack, pLevel, pLivingEntity,new Vec3(pLivingEntity.getX(),pLivingEntity.getY(),pLivingEntity.getZ()));
        pStack.hurt(1, RandomSource.create(), null);
        return pStack;
    }

    public void eat(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, Vec3 vec3){

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);
        String translatable = canDrink()?"item.bakeries.tips.repeat_eat_item.drink":"item.bakeries.tips.repeat_eat_item";
        tooltip.add(Component.nullToEmpty(Component.translatable(translatable).getString() + (stack.getMaxDamage() - stack.getDamageValue()) + " / " + stack.getMaxDamage()));
    }
}
