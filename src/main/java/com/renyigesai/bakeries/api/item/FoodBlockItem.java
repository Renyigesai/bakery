package com.renyigesai.bakeries.api.item;

import com.renyigesai.bakeries.api.block.PileBlock;
import com.renyigesai.bakeries.util.Shortcuts;
import com.renyigesai.bakeries.util.TextUtils;
import com.renyigesai.bakeries.api.block.properties.ModIntegerProperty;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class FoodBlockItem extends ItemNameBlockItem {

    public final boolean effectTooltip;
    public final boolean customField;
    public final ModIntegerProperty integerProperty;

    public FoodBlockItem(Block block, ModIntegerProperty integerProperty, Item.Properties pProperties, boolean effectTooltip,boolean customField) {
        super(block, pProperties);
        this.integerProperty = integerProperty;
        this.effectTooltip = effectTooltip;
        this.customField = customField;
    }

    public FoodBlockItem(Block block, ModIntegerProperty integerProperty, Properties pProperties) {
        super(block, pProperties);
        this.integerProperty = integerProperty;
        this.effectTooltip = false;
        this.customField = false;
    }

    public SoundEvent getPlaceSound(){
        return SoundEvents.WOOL_STEP;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        InteractionResult result = this.use(pContext.getLevel(), player, pContext.getHand()).getResult();
        if (player.isShiftKeyDown() && this.isExtra(pContext)) {
            Level level = pContext.getLevel();
            Block thisBlock = this.getBlock();
            BlockPos pos = pContext.getClickedPos();
            BlockState state = level.getBlockState(pos);
            if (!state.is(thisBlock)) {
                return this.place(new BlockPlaceContext(pContext));
            }
            if (state.is(thisBlock) && state.hasProperty(this.integerProperty)) {
                Integer value = state.getValue(this.integerProperty);
                PileBlock newBlock = (PileBlock) thisBlock;
                if (value < newBlock.getMaxPile()) {
                    level.setBlock(pos, state.setValue(this.integerProperty, value + 1), 3);
                    level.playSound(null, pos, getPlaceSound(), SoundSource.PLAYERS, 0.8F, 0.8F);
                    if (!player.getAbilities().instabuild) {
                        pContext.getItemInHand().shrink(1);
                    }
                    result = InteractionResult.sidedSuccess(pContext.getLevel().isClientSide);
                }
            }
        }
        return result;
    }
    public boolean isExtra(UseOnContext pContext) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (itemstack.getOrCreateTag().getBoolean("perfect")){
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(itemstack);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public void getCustomField(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced){

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced) {
        if (customField){
            getCustomField(stack, level, tooltip, isAdvanced);
        }
        if (stack.getOrCreateTag().getBoolean("perfect")) {
            tooltip.add(Component.translatable("item.bakeries.tips.perfect_temperature").withStyle(ChatFormatting.GOLD));
        }
        if (effectTooltip) {
            TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
        }
    }
}
