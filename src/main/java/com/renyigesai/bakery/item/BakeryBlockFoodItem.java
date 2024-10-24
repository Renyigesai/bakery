package com.renyigesai.bakery.item;

import com.renyigesai.bakery.block.PileBlock;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import static com.renyigesai.bakery.block.PileBlock.pileUp;

public class BakeryBlockFoodItem extends ItemNameBlockItem {
    public BakeryBlockFoodItem(RegistryObject<Block> block, Properties properties) {
        super(block.get(),properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack handStack = player.getItemInHand(hand);
        boolean isPile = handStack.is(asItem());
        if(block instanceof PileBlock){
            if (!level.isClientSide) {
                if (isPile && Screen.hasShiftDown()) {
                    return pileUp(level, pos, state,player,context);
                }
            }
            if (isPile && Screen.hasShiftDown()) {
                return pileUp(level, pos, state,player,context);
            }
        }else if (Screen.hasShiftDown()){
            return super.useOn(context);
        }return InteractionResult.FAIL;
    }
}
