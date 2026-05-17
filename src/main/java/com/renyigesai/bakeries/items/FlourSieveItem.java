package com.renyigesai.bakeries.items;

import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import com.renyigesai.bakeries.utils.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FlourSieveItem extends Item {
    private static final int USE_DURATION = 32;
    private static final List<String> FAIL_MESSAGES = List.of(
            "tooltips.bakeries.flour_sieve_1",
            "tooltips.bakeries.flour_sieve_2",
            "tooltips.bakeries.flour_sieve_3"
    );

    public FlourSieveItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack sieve = player.getItemInHand(usedHand);
        if (usedHand != InteractionHand.OFF_HAND || player.getMainHandItem().isEmpty()) {
            return InteractionResultHolder.pass(sieve);
        }
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(sieve);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!(entity instanceof Player player)) {
            return stack;
        }
        ItemStack input = player.getMainHandItem();
        if (!level.isClientSide) {
            SimpleMachineRecipe recipe = input.isEmpty() ? null : level.getRecipeManager()
                    .getRecipeFor(BakeriesRecipeTypes.FLOUR_SIEVE, new SimpleContainer(input), level)
                    .orElse(null);
            if (recipe == null || !recipe.isValid()) {
                sendFailedMessage(player, level.random);
            } else {
                ItemUtils.shrink(input, 1, player);
                ItemUtils.givePlayerItem(player, recipe.getResultItem(level.registryAccess()));
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return USE_DURATION;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    private static void sendFailedMessage(Player player, RandomSource random) {
        String key = FAIL_MESSAGES.get(random.nextInt(FAIL_MESSAGES.size()));
        player.displayClientMessage(Component.translatable(key).withStyle(ChatFormatting.GRAY), true);
    }
}
