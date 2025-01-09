package com.renyigesai.bakeries.api.item;

import com.renyigesai.bakeries.api.Shortcuts;
import com.renyigesai.bakeries.api.TextUtils;
import com.renyigesai.bakeries.api.block.properties.ModIntegerProperty;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class FoodBlockItem extends ItemNameBlockItem {

    public final boolean effectTooltip;
    public ModIntegerProperty integerProperty;

    public FoodBlockItem(Block block, ModIntegerProperty integerProperty, Item.Properties pProperties, boolean effectTooltip) {
        super(block, pProperties);
        this.integerProperty = integerProperty;
        this.effectTooltip = effectTooltip;
    }

    public FoodBlockItem(Block block, ModIntegerProperty integerProperty, Properties pProperties) {
        super(block, pProperties);
        this.integerProperty = integerProperty;
        this.effectTooltip = false;
    }
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        ServerPlayer player = (ServerPlayer)pContext.getPlayer();
        InteractionResult result = this.use(pContext.getLevel(), player, pContext.getHand()).getResult();
        if(player != null){
            if (player.isShiftKeyDown()) {
                if (!pContext.getLevel().getBlockState(pContext.getClickedPos()).is(this.getBlock())) {
                    result = this.place(new BlockPlaceContext(pContext));
                } else {
                    if (pContext.getLevel().getBlockState(pContext.getClickedPos()).getValue(this.integerProperty) < this.integerProperty.getMax()) {
                        Shortcuts.setBlock(pContext.getLevel(), pContext.getClickedPos(), pContext.getLevel().getBlockState(pContext.getClickedPos()), this.integerProperty, 1, true);
                        if (!player.getAbilities().instabuild) {
                            pContext.getItemInHand().shrink(1);
                        }
                        result = InteractionResult.sidedSuccess(pContext.getLevel().isClientSide);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.canEat(itemstack.getFoodProperties(pPlayer).canAlwaysEat())) {
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(itemstack);
        }
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        if (stack.getOrCreateTag().getBoolean("perfect")) {
            tooltip.add(Component.translatable("item.bakeries.tips.perfect_temperature").withStyle(ChatFormatting.GOLD));
        }
        if (effectTooltip) {
            TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
        }
    }
}
