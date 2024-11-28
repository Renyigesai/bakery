package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.init.BakeriesItemTag;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.recipe.flour_sieve.FlourSieveRecipe;
import com.renyigesai.bakeries.recipe.oven.OvenRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class FlourSieveItem extends Item {

    public FlourSieveItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public SoundEvent getEatingSound() {return SoundEvents.SAND_BREAK;}

    public SoundEvent getDrinkingSound() {
        return SoundEvents.SAND_BREAK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack mainHandItem = pPlayer.getMainHandItem();

        if (mainHandItem.is(BakeriesItemTag.FLOUR)){
            pPlayer.startUsingItem(pUsedHand);
            return new InteractionResultHolder(InteractionResult.PASS, pPlayer.getItemInHand(pUsedHand));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        Player player = (Player)pLivingEntity;
        ItemStack mainHandItem = player.getMainHandItem();
        Optional<FlourSieveRecipe> recipe =getCurrentRecipe(player,pLevel);
        if (hasRecipe(player, pLevel)) {
            mainHandItem.shrink(1);
            player.getInventory().placeItemBackInInventory(recipe.get().getResultItem(null));
            pStack.hurt(1, RandomSource.create(), null);
        }


        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
    public boolean hasRecipe(Player player, Level pLevel) {
        Optional<FlourSieveRecipe> recipe = getCurrentRecipe(player, pLevel);
        return recipe.isPresent() && recipe.get().getIngredients().get(0).test(player.getMainHandItem());
    }
    public Optional<FlourSieveRecipe> getCurrentRecipe(Player player, Level pLevel) {
        SimpleContainer inventory = new SimpleContainer(1);
        inventory.setItem(0, player.getMainHandItem()); // 使用玩家的主手物品
        Optional<FlourSieveRecipe> recipe = pLevel.getRecipeManager().getRecipeFor(FlourSieveRecipe.Type.INSTANCE, inventory, pLevel);

        if (recipe.isPresent()) {
            player.displayClientMessage(Component.literal("Found recipe: " + recipe.get().getResultItem(null).getItem().toString()), false);
        } else {
            player.displayClientMessage(Component.literal("No recipe found"), false);
        }

        return recipe;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 16;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.bakeries.tips.flour_sieve").withStyle(ChatFormatting.BLUE));
    }

}
