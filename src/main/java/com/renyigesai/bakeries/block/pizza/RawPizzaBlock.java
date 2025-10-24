package com.renyigesai.bakeries.block.pizza;

import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.item.StoneKilnShovelItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class RawPizzaBlock extends Block {
    public static final VoxelShape BOX = box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D);
    public final Supplier<Item> rawItem;

    public RawPizzaBlock(Supplier<Item> rawItem) {
        super(BlockBehaviour.Properties.copy(Blocks.CAKE));
        this.rawItem = rawItem;
    }

    public ItemStack getRawItem() {
        return new ItemStack(this.rawItem.get());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (itemInHand.is(BakeriesItems.STONE_KILN_SHOVEL.get()) && ((StoneKilnShovelItem)itemInHand.getItem()).isEmpty(itemInHand)) {
            ((StoneKilnShovelItem)itemInHand.getItem()).addItem(itemInHand, getRawItem());
            pLevel.removeBlock(pPos,false);
            pLevel.playSound(null, pPos, SoundEvents.WOOL_STEP, SoundSource.BLOCKS, 0.8F, 0.8F);
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BOX;
    }
}
