package com.renyigesai.bakeries.common.blocks;

import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class YeastTankBlock extends TankBlock {

    public static final IntegerProperty YEAST = IntegerProperty.create("yeast", 0, 3);

    public YeastTankBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(YEAST, 3));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack handItem = player.getItemInHand(hand);
        if (handItem.is(Items.GLASS_BOTTLE)){
            return ladleOut(level,pos,state,player,hand);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    public static ItemInteractionResult ladleOut(Level level, BlockPos pos, BlockState state, Player playerIn, InteractionHand pHand){
        ItemStack hand = playerIn.getItemInHand(pHand);
        int yeast = state.getValue(YEAST);
        if (yeast > 1){
            level.setBlock(pos,state.setValue(YEAST,yeast -1),3);
        }else {
            level.setBlock(pos, BakeriesBlocks.FERMENTATION_TANK.get().defaultBlockState(),0);
        }
        hand.shrink(1);
        ItemUtils.givePlayerItem(playerIn,new ItemStack(BakeriesItems.BOTTLE_YEAST.get()));
        level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 0.8F, 0.8F);
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(YEAST);
    }
}
