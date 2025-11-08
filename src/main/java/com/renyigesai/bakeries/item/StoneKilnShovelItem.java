package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.block.stone_kiln.StoneKilnBlockEntity;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StoneKilnShovelItem extends ShovelItem {
    public StoneKilnShovelItem() {
        super(Tiers.WOOD, 1.5f,-3.0f, new Item.Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (player == null){
            return InteractionResult.PASS;
        }
        if (blockEntity instanceof StoneKilnBlockEntity kiln && player.isShiftKeyDown()) {
            ItemStack itemInHand = player.getItemInHand(pContext.getHand());
            if (itemInHand.getItem() instanceof StoneKilnShovelItem shovelItem && !kiln.isEmpty() && shovelItem.isEmpty(itemInHand)){
                addItem(player.getItemInHand(pContext.getHand()),kiln.getInventory().getStackInSlot(0).copy());
                kiln.getInventory().setStackInSlot(0,ItemStack.EMPTY);
                kiln.initialize();
                level.playSound(null, pos, SoundEvents.WOOL_STEP, SoundSource.BLOCKS, 0.8F, 0.8F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public boolean isEmpty(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("Inventory", Tag.TAG_COMPOUND)){
            return true;
        }else {
            ItemStackHandler handler = new ItemStackHandler(1);
            handler.deserializeNBT(tag.getCompound("Inventory"));
            return handler.getStackInSlot(0).isEmpty();
        }
    }

    public ItemStack getInventoryStack(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Inventory", Tag.TAG_COMPOUND)){
            ItemStackHandler handler = new ItemStackHandler(1);
            handler.deserializeNBT(tag.getCompound("Inventory"));
            return handler.getStackInSlot(0);
        }
        return ItemStack.EMPTY;
    }

    public void addItem(ItemStack stack,ItemStack addStack){
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Inventory", Tag.TAG_COMPOUND)){
            ItemStackHandler handler = new ItemStackHandler(1);
            handler.deserializeNBT(tag.getCompound("Inventory"));
            if (handler.getStackInSlot(0).isEmpty()){
                handler.setStackInSlot(0,addStack);
                tag.put("Inventory",handler.serializeNBT());
                stack.setTag(tag);
            }
        }else {
            ItemStackHandler handler = new ItemStackHandler(1);
            handler.setStackInSlot(0,addStack);
            tag.put("Inventory",handler.serializeNBT());
            stack.setTag(tag);
        }
    }

    public void removeItem(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Inventory", Tag.TAG_COMPOUND)){
            ItemStackHandler handler = new ItemStackHandler(1);
            handler.deserializeNBT(tag.getCompound("Inventory"));
            handler.setStackInSlot(0,ItemStack.EMPTY);
            tag.put("Inventory",handler.serializeNBT());
            stack.setTag(tag);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return !isEmpty(itemstack);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack hand = pPlayer.getItemInHand(pUsedHand);

        if (pPlayer.isShiftKeyDown()) {
            if (!isEmpty(hand)) {
                ItemUtil.givePlayerItem(pPlayer, getInventoryStack(hand).copy());
                removeItem(hand);
                pPlayer.startUsingItem(pUsedHand);
                pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.8F, 0.8F);
                return InteractionResultHolder.success(hand);
            }
        }
        if (!isEmpty(hand)){
            return super.use(pLevel, pPlayer, pUsedHand);
        }

        HitResult raytraceresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.NONE);
        if (!(raytraceresult instanceof BlockHitResult)){
            return super.use(pLevel, pPlayer, pUsedHand);
        }

        BlockHitResult ray = (BlockHitResult) raytraceresult;
        Vec3 hitVec = ray.getLocation();
        AABB bb = new AABB(hitVec, hitVec).inflate(1f);
        ItemEntity resultItemEntity = null;
        for (ItemEntity e : pLevel.getEntitiesOfClass(ItemEntity.class, bb)) {
            if (e.getItem().getCount() == 1) {
                resultItemEntity = e;
                break;
            }
        }

        if (resultItemEntity == null){
            return super.use(pLevel, pPlayer, pUsedHand);
        }
        addItem(hand,resultItemEntity.getItem());
        pLevel.playSound(null, resultItemEntity.getX(), resultItemEntity.getY(), resultItemEntity.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.8F, 0.8F);
        resultItemEntity.remove(Entity.RemovalReason.KILLED);
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.success(hand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        ItemStack boxStack = getInventoryStack(pStack);
        pTooltip.add(Component.literal(Component.translatable("item.bakeries.stone_kiln_shovel.tips").getString()).withStyle(ChatFormatting.BLUE));
        if (!boxStack.isEmpty()){
            pTooltip.add(Component.literal(Component.translatable("item.bakeries.containing.tips").getString()).withStyle(ChatFormatting.BLUE));
            pTooltip.add(Component.literal(boxStack.getItem().getName(boxStack).getString()).withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

}
