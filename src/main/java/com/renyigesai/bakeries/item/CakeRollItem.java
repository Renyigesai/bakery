package com.renyigesai.bakeries.item;

import com.mojang.datafixers.util.Pair;
import com.renyigesai.bakeries.util.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CakeRollItem extends Item {
    public CakeRollItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        addEffects(pLevel,pStack,pLivingEntity);
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    public static List<ItemStack> getInventoryList(ItemStack stack){
        List<ItemStack> stacks = new ArrayList<>();
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Inventory", Tag.TAG_COMPOUND)){
            ItemStackHandler handler = new ItemStackHandler(4);
            handler.deserializeNBT(tag.getCompound("Inventory"));
            for (int i = 0; i < handler.getSlots(); i++) {
                stacks.add(handler.getStackInSlot(i));
            }
        }
        return stacks;
    }

    public void addEffects(Level level, ItemStack stack, LivingEntity entity){
        List<ItemStack> inventoryList = getInventoryList(stack);
        if (inventoryList.isEmpty()){
            return;
        }
        for (ItemStack iStack : inventoryList) {
            if (!iStack.isEmpty() && iStack.getItem().isEdible()) {
                List<Pair<MobEffectInstance, Float>> effects = Objects.requireNonNull(new ItemStack(iStack.getItem()).getFoodProperties(entity)).getEffects();
                for (Pair<MobEffectInstance, Float> effect : effects) {
                    if (!level.isClientSide && effect.getFirst() != null && level.random.nextFloat() < effect.getSecond()) {
                        entity.addEffect(new MobEffectInstance(effect.getFirst()));
                    }
                }
            }
        }
    }

    public static void setName(ItemStack stack){
        List<ItemStack> inventoryList = getInventoryList(stack);
        StringBuilder strings = new StringBuilder();
        for (ItemStack iStack : inventoryList) {
            if (!iStack.isEmpty()) {
                strings.append(iStack.getItem().getName(iStack).getString());
            }
        }
        strings.append(stack.getItem().getName(stack).getString());
        stack.setHoverName(Component.nullToEmpty(String.valueOf(strings)));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        TextUtils.addFoodEffectTooltip(pStack, pTooltip, 1.0F);
        pTooltip.add(Component.literal(Component.translatable("item.bakeries.custom_containing.tips").getString()).withStyle(ChatFormatting.BLUE));
        List<ItemStack> inventoryList = getInventoryList(pStack);
        if (!inventoryList.isEmpty()){
            for (ItemStack itemStack : inventoryList) {
                if (!itemStack.isEmpty()) {
                    pTooltip.add(Component.literal(itemStack.getItem().getName(itemStack).getString()).withStyle(ChatFormatting.GRAY));
                }
            }
        }
    }
}
