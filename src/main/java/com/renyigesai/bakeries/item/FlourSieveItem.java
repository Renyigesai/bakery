package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.util.RandomText;
import com.renyigesai.bakeries.recipe.flour_sieve.FlourSieveRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

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
        if (!mainHandItem.isEmpty()){
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
        if (hasRecipe(player, player.level())) {
            if(recipe.isPresent()){
                if (!player.getAbilities().instabuild) {
                    mainHandItem.shrink(1);
                    pStack.hurt(1, RandomSource.create(), null);
                }
                player.getInventory().placeItemBackInInventory(recipe.get().getResultItem(null));

            }
        }else {
            player.displayClientMessage(Component.translatable(RandomText.getFlourSieveRandomText()),true);
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
    public boolean hasRecipe(Player player, Level pLevel) {
        Optional<FlourSieveRecipe> recipe = getCurrentRecipe(player, pLevel);
        return recipe.isPresent() && recipe.get().getIngredients().get(0).test(player.getMainHandItem());
    }
    public Optional<FlourSieveRecipe> getCurrentRecipe(Player player, Level pLevel) {
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
}
