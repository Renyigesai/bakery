package com.renyigesai.bakeries.item;


import com.renyigesai.bakeries.recipe.flour_sieve.FlourSieveRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Mod.EventBusSubscriber
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
        ItemStack sieveStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            if (!pPlayer.getMainHandItem().isEmpty()) {
                pPlayer.startUsingItem(pUsedHand);
                return InteractionResultHolder.consume(sieveStack);
            }
        }
        return InteractionResultHolder.pass(sieveStack);
    }


    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLevel.isClientSide){
            return pStack;
        }
        ItemStack mainHandItem = pLivingEntity.getMainHandItem();
        Optional<FlourSieveRecipe> recipe = getCurrentRecipe(pLivingEntity, pLevel);
        boolean success = recipe.isPresent() && hasRecipe(pLivingEntity,pLevel);

        if (pLivingEntity instanceof Player player) {
            if (success) {
                mainHandItem.shrink(1);
                pStack.hurtAndBreak(1, pLivingEntity, e -> e.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                player.getInventory().placeItemBackInInventory(recipe.get().getResultItem(null));
            } else {
                player.getCooldowns().addCooldown(this, 20);
                int randomInt = new Random().nextInt(0, 3);
                player.displayClientMessage(Component.translatable("player.bakeries.tips.flour_sieve." + randomInt), true);
            }
        }
        return pStack;
    }


    public boolean hasRecipe(LivingEntity player, Level pLevel) {
        Optional<FlourSieveRecipe> recipe = getCurrentRecipe(player, pLevel);
        return recipe.isPresent() && recipe.get().getIngredients().get(0).test(player.getMainHandItem());
    }

    public Optional<FlourSieveRecipe> getCurrentRecipe(LivingEntity player, Level pLevel) {
        return pLevel.getRecipeManager().getRecipeFor(FlourSieveRecipe.Type.INSTANCE,  new SimpleContainer(player.getMainHandItem()), pLevel);
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

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        ItemStack offhand = player.getOffhandItem();
        if (offhand.getItem() instanceof FlourSieveItem) {
            event.setCanceled(true);
        }
    }
}
