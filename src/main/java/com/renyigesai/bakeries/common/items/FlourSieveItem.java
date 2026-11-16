package com.renyigesai.bakeries.common.items;


import com.renyigesai.bakeries.common.recipe.FlourSieveRecipe;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@EventBusSubscriber
public class FlourSieveItem extends Item {

    public static final Set<ItemAbility> SIEVE_ACTIONS;

    private static final RecipeManager.CachedCheck<RecipeInput, FlourSieveRecipe> CHECK = RecipeManager.createCheck(FlourSieveRecipe.Type.INSTANCE);

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
        ItemStack ingredient = pPlayer.getItemInHand(getIngredientHand(pUsedHand));
        if (!pLevel.isClientSide && !ingredient.isEmpty()) {
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(sieveStack);
        }
        return InteractionResultHolder.pass(sieveStack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLevel.isClientSide) {
            return pStack;
        }

        InteractionHand sieveHand = pLivingEntity.getUsedItemHand();
        InteractionHand ingredientHand = getIngredientHand(sieveHand);
        ItemStack ingredient = pLivingEntity.getItemInHand(ingredientHand);
        Optional<RecipeHolder<FlourSieveRecipe>> recipe = getCurrentRecipe(pLevel, ingredient);


        if (pLivingEntity instanceof Player player) {
            if (recipe.isPresent()) {
                ingredient.shrink(1);
                pStack.hurtAndBreak(1, pLivingEntity, (player.getMainHandItem().getItem() instanceof FlourSieveItem) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                player.getInventory().placeItemBackInInventory(
                        recipe.get().value().getResultItem(null));
            } else {
                player.getCooldowns().addCooldown(this, 20);
                int randomInt = new Random().nextInt(0, 3);
                player.displayClientMessage(
                        Component.translatable("tooltips.bakeries.flour_sieve_" + randomInt), true);
            }
        }
        return pStack;
    }

    private Optional<RecipeHolder<FlourSieveRecipe>> getCurrentRecipe(Level level, ItemStack stack) {
        if (level == null) {
            return Optional.empty();
        }
        return CHECK.getRecipeFor(new SingleRecipeInput(stack),level);
    }

    public InteractionHand getIngredientHand(InteractionHand sieveHand) {
        return sieveHand == InteractionHand.MAIN_HAND
                ? InteractionHand.OFF_HAND
                : InteractionHand.MAIN_HAND;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 16;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltips.bakeries.flour_sieve_0").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return !enchantment.is(Enchantments.SWEEPING_EDGE) && super.isPrimaryItemFor(stack, enchantment);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return !enchantment.is(Enchantments.SWEEPING_EDGE) && super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
        return SIEVE_ACTIONS.contains(toolAction);
    }

    @Override
    public int getEnchantmentValue() {
        return 14;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    static {
        SIEVE_ACTIONS = Set.of(ItemAbilities.SHEARS_CARVE);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (player.isShiftKeyDown()) return;

        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (!(stack.getItem() instanceof FlourSieveItem sieve)) continue;

            InteractionHand otherHand = sieve.getIngredientHand(hand);
            ItemStack ingredient = player.getItemInHand(otherHand);
            if (ingredient.isEmpty()) continue;

            Optional<RecipeHolder<FlourSieveRecipe>> recipe = sieve.getCurrentRecipe(player.level(), ingredient);
            if (recipe.isPresent()) {
                event.setCanceled(true);
                return;
            }
        }
    }

}
