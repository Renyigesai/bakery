package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.block.pizza.CustomPizzaBlockEntity;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomPizzaItem extends BlockItem {
    public CustomPizzaItem(Block block) {
        super(block,new Item.Properties().stacksTo(1).rarity(ItemUtil.ADVANCED));
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
        for (ItemStack itemStack : stacks) {
            FoodProperties foodProperties = itemStack.getFoodProperties(null);
            if (foodProperties != null){
                nutrition += foodProperties.getNutrition();
                saturationMod += foodProperties.getSaturationModifier();
            }
        }
        list.add(nutrition);
        list.add(saturationMod);
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
