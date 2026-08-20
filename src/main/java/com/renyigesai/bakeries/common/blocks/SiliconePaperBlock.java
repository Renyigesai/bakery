package com.renyigesai.bakeries.common.blocks;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.common.blocks.custom_cake.CakePartData;
import com.renyigesai.bakeries.common.blocks.custom_cake.CustomCakeBlock;
import com.renyigesai.bakeries.common.blocks.custom_cake.CustomCakeBlockEntity;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import com.renyigesai.bakeries.common.utils.measurer.CakePartMeasurer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class SiliconePaperBlock extends HorizontalDirectionalBlock {
    private static final VoxelShape BOX = box(1,0,1,15,1,15);
    public SiliconePaperBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BOX;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemHand = player.getItemInHand(hand);
        Optional<CakePartData> optional = CakePartMeasurer.isIngredient(itemHand);
        if (optional.isPresent()){
            CakePartData cakePartData = optional.get();
            if (!CakePartMeasurer.CAKE_BASE.equals(cakePartData.getType())){
                return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
            }
            Direction direction = state.getValue(FACING);
            level.setBlock(pos,BakeriesBlocks.CUSTOM_CAKE.get().defaultBlockState().setValue(CustomCakeBlock.FACING,direction),3);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CustomCakeBlockEntity cc){
                cc.addCakePart(cakePartData);
            }
            ItemUtils.shrink(itemHand,1,player);
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
