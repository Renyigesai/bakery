package com.renyigesai.bakeries.jade.provider;

import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import com.renyigesai.bakeries.jade.Identifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

import java.util.ArrayList;
import java.util.List;

public enum OvenComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {

        if (accessor.getServerData().contains("cooking_times") && accessor.getServerData().contains("max_cooking_times")) {
            int[] cooking_times = accessor.getServerData().getIntArray("cooking_times");
            int[] max_cooking_times = accessor.getServerData().getIntArray("max_cooking_times");

            List<SlotStack> stacks = new ArrayList<>();
            ItemStackHandler handler = new ItemStackHandler(6);
            handler.deserializeNBT(accessor.getServerData().getCompound("items"));
            for (int i = 0; i < handler.getSlots(); i++) {
                if (!handler.getStackInSlot(i).isEmpty()) {
                    stacks.add(new SlotStack(handler.getStackInSlot(i), i));
                }
            }
            if (stacks.isEmpty()){
                return;
            }
            if (allEquivalence(stacks,cooking_times,max_cooking_times)){
                stackingToolTip(tooltip,stacks,cooking_times,max_cooking_times);
            }else {
                allToolTip(tooltip,handler,cooking_times,max_cooking_times);
            }
        }
    }

    private boolean allEquivalence(List<SlotStack> stacks,int[] cookingTimes,int[] maxCookingTimes){
        ItemStack comparison = stacks.get(0).stack;
        int slot = stacks.get(0).slot;
        int cookingTime = cookingTimes[slot];
        int maxCookingTime = maxCookingTimes[slot];
        for (int i = slot; i < stacks.size(); i++) {
            if (!(stacks.get(i).stack.is(comparison.getItem()) && cookingTimes[i] == cookingTime && maxCookingTimes[i] == maxCookingTime)){
                return false;
            }
        }
        return true;
    }

    private void stackingToolTip(ITooltip tooltip,List<SlotStack> stacks, int[] cookingTimes,int[] maxCookingTimes){
        int slot = stacks.get(0).slot;
        int cookingTime = cookingTimes[slot];
        int maxCookingTime = maxCookingTimes[slot];
        int p = maxCookingTime == 0 ? 0 : (cookingTime * 100 / maxCookingTime);
        IElementHelper helper = IElementHelper.get();
        tooltip.add(helper.item(stacks.get(0).stack));
        tooltip.append(Component.literal( stacks.size() + "x ").withStyle(ChatFormatting.BLUE));
        tooltip.append(stacks.get(0).stack.getHoverName());
        if (p != 0){
            tooltip.append(Component.literal( " "+ p + "%"));
        }

    }

    private void allToolTip(ITooltip tooltip,ItemStackHandler handler,int[] cookingTimes,int[] maxCookingTimes){
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty()) {
                int cookingTime = cookingTimes[i];
                int maxCookingTime = maxCookingTimes[i];
                int p = maxCookingTime == 0 ? 0 : (cookingTime * 100 / maxCookingTime);
                IElementHelper helper = IElementHelper.get();
                tooltip.append(helper.item(handler.getStackInSlot(i)));
                tooltip.append(handler.getStackInSlot(i).getHoverName());
                if (p != 0){
                    tooltip.append(Component.literal(" " + p + "%"));
                }
            }
        }
    }


    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        OvenBlockEntity brewingStand = (OvenBlockEntity)accessor.getBlockEntity();
        tag.put("items",brewingStand.getItemHandler().serializeNBT());
        tag.putIntArray("cooking_times", brewingStand.cooking_times);
        tag.putIntArray("max_cooking_times", brewingStand.max_cooking_times);
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.OVEN;
    }

    record SlotStack(ItemStack stack, int slot) {
    }
}
