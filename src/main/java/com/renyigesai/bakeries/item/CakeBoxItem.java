package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.block.cake_box.CakeBoxBlockEntity;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
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

import java.util.List;

public class CakeBoxItem extends BlockItem {

    public CakeBoxItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public static ItemStack getBoxStack(ItemStack stack){
        ItemStack boxStack = ItemStack.EMPTY;
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Inventory", Tag.TAG_COMPOUND)){
            ItemStackHandler handler = new ItemStackHandler(1);
            handler.deserializeNBT(tag.getCompound("Inventory"));
            boxStack = handler.getStackInSlot(0);
        }
        return boxStack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        /*主手蛋糕盒,副手是被放入的物品*/
        ItemStack mainHandItem = pPlayer.getMainHandItem();
        Item mitem = mainHandItem.getItem();
        if (mitem instanceof CakeBoxItem){
            if (!getBoxStack(mainHandItem).isEmpty())//如果蛋糕盒已存储物品直接返回
                return super.use(pLevel, pPlayer, pUsedHand);

            ItemStack offhandItem = pPlayer.getOffhandItem();
            Item fitem = offhandItem.getItem();
            if (offhandItem.isEmpty() || fitem instanceof CakeBoxItem || offhandItem.is(ItemTags.create(new ResourceLocation("bakeries","not_cake_box_ingredients"))))//如果副手为空,或为蛋糕盒直接返回
                return super.use(pLevel, pPlayer, pUsedHand);

            CompoundTag compoundTag = new CompoundTag();
            ItemStackHandler handler = new ItemStackHandler(1);
            ItemStack copyItem = offhandItem.copy();
            copyItem.setCount(1);
            handler.setStackInSlot(0,copyItem);
            compoundTag.put("Inventory",handler.serializeNBT());
            mainHandItem.setTag(compoundTag);
            if (!pPlayer.getAbilities().instabuild){
               offhandItem.shrink(1);
            }
            pPlayer.startUsingItem(pUsedHand);
            return new InteractionResultHolder(InteractionResult.SUCCESS, pPlayer.getItemInHand(pUsedHand));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        super.place(pContext);
        ItemStack boxStack = getBoxStack(pContext.getItemInHand());
        if (!boxStack.isEmpty()) {
            Level level = pContext.getLevel();
            BlockPos pos = pContext.getClickedPos();
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CakeBoxBlockEntity boxBlock) {
                boxBlock.getInventory().setStackInSlot(0,boxStack);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        ItemStack boxStack = getBoxStack(pStack);
        pTooltip.add(Component.literal(Component.translatable("item.bakeries.cake_box.tips").getString()).withStyle(ChatFormatting.BLUE));
        if (!boxStack.isEmpty()){
            pTooltip.add(Component.literal(Component.translatable("item.bakeries.containing.tips").getString()).withStyle(ChatFormatting.BLUE));
            pTooltip.add(Component.literal(boxStack.getItem().getName(boxStack).getString()).withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
