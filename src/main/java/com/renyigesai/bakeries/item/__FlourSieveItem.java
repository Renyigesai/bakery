//package com.renyigesai.bakeries.item;
//
//import com.renyigesai.bakeries.recipe.flour_sieve.FlourSieveHardRecipeList;
//import net.minecraft.ChatFormatting;
//import net.minecraft.network.chat.Component;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.*;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.*;
//import net.minecraft.world.level.Level;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
////测试代码，原来的代码在 _FlourSieveItem.java里
//public class FlourSieveItem extends Item {
//
//    private ItemStack outputItem;
//
//    public FlourSieveItem(Properties pProperties) {
//        super(pProperties);
//    }
//
//    @Override
//    public SoundEvent getEatingSound() {return SoundEvents.SAND_BREAK;}
//    @Override
//    public SoundEvent getDrinkingSound() {return SoundEvents.SAND_BREAK;}
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        ItemStack mainHandItem = pPlayer.getMainHandItem();
//        FlourSieveHardRecipeList FlourSieveRecipe = new FlourSieveHardRecipeList();
//        for (int i = 0; i < FlourSieveRecipe.getFlourSieveRecipe().size() ; i++) {
//            if (mainHandItem.is(FlourSieveRecipe.getFlourSieveRecipe().get(i).getinput().getItem())){
//                outputItem = FlourSieveRecipe.getFlourSieveRecipe().get(i).getoutput();
//                pPlayer.startUsingItem(pUsedHand);
//                return new InteractionResultHolder(InteractionResult.PASS, pPlayer.getItemInHand(pUsedHand));
//            }
//        }
//        return super.use(pLevel, pPlayer, pUsedHand);
//    }
//
//    @Override
//    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
//        Player player = (Player)pLivingEntity;
//        ItemStack mainHandItem = player.getMainHandItem();
//            mainHandItem.shrink(1);
//            player.getInventory().placeItemBackInInventory(outputItem);
//            pStack.hurt(1, RandomSource.create(), null);
//        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
//    }
//
//    @Override
//    public UseAnim getUseAnimation(ItemStack pStack) {
//        return UseAnim.EAT;
//    }
//
//    @Override
//    public int getUseDuration(ItemStack pStack) {
//        return 16;
//    }
//
//    @Override
//    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
//        pTooltipComponents.add(Component.translatable("item.bakeries.tips.flour_sieve").withStyle(ChatFormatting.BLUE));
//    }
//}
