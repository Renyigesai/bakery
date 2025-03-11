package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.util.ItemUtil;
import com.renyigesai.bakeries.util.Shortcuts;
import com.renyigesai.bakeries.api.block.properties.ModIntegerProperty;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class YeastTankBlock extends TankBlock {

    public static final ModIntegerProperty YEAST = ModIntegerProperty.create("yeast", 0, 3);

    public YeastTankBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(YEAST, 3));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        ItemStack hand = pPlayer.getItemInHand(pHand);

        if (hand.is(Items.GLASS_BOTTLE)){
            return ladleOut(pLevel,pPos,pState,pPlayer,pHand);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public static InteractionResult ladleOut(Level level, BlockPos pos, BlockState state, Player playerIn, InteractionHand pHand){
        ItemStack hand = playerIn.getItemInHand(pHand);
        int yeast = state.getValue(YEAST);
        if (yeast > 1){
            Shortcuts.setBlock(level,pos,state,YEAST,1,false);
        }else {
            level.setBlock(pos, BakeriesBlocks.FERMENTATION_TANK.get().defaultBlockState(),0);
        }
        hand.shrink(1);
        ItemUtil.givePlayerItem(playerIn,new ItemStack(BakeriesItems.BOTTLE_YEAST.get()));
        level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(YEAST);
    }
}
