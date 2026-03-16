package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.block.pizza.CustomPizzaBlockEntity;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.util.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomPizzaItem extends BlockItem {
    public CustomPizzaItem(Block block) {
        super(block,new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    public List<ItemStack> getInventoryList(ItemStack stack){
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

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        if (!pContext.getItemInHand().is(BakeriesItems.CUSTOM_PIZZA.get())){
            return InteractionResult.FAIL;
        }
        super.place(pContext);
        List<ItemStack> inventoryList = getInventoryList(pContext.getItemInHand());
        if (!inventoryList.isEmpty()) {
            Level level = pContext.getLevel();
            BlockPos pos = pContext.getClickedPos();
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CustomPizzaBlockEntity entity) {
                for (int i = 0; i < entity.getInventory().getSlots(); i++) {
                    ItemStack itemStack = inventoryList.get(i);
                    if (!itemStack.isEmpty()) {
                        entity.getInventory().setStackInSlot(i, itemStack);
                    }
                }
                List<Object> nutritionAndSaturationMod = getNutritionAndSaturationMod(inventoryList);
                entity.setFoodProperties((int)nutritionAndSaturationMod.get(0),(float)nutritionAndSaturationMod.get(1));
            }
        }
        return InteractionResult.SUCCESS;
    }

    public List<Object> getNutritionAndSaturationMod(List<ItemStack> stacks){
        List<Object> list = new ArrayList<>();
        int nutrition = 0;
        float saturationMod = 0.0f;
        int i = 0;
        for (ItemStack itemStack : stacks) {
            FoodProperties foodProperties = itemStack.getFoodProperties(null);
            if (foodProperties != null){
                nutrition += foodProperties.getNutrition();
                saturationMod += foodProperties.getSaturationModifier();
                i ++;
            }
        }
        int x = 1;
        if (i != 0){
            x = i;
        }
        list.add(nutrition / x + 4);
        list.add(saturationMod / x + 0.5F);
        return list;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
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
