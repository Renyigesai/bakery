package com.renyigesai.bakeries.block.light_box;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.luminous_light_sign.LuminousLightSignBlockEntity;
import com.renyigesai.bakeries.util.ItemUtils;
import com.renyigesai.bakeries.util.measurer.ClientUtilsMeasurer;
import com.renyigesai.bakeries.util.measurer.IUtilsMeasurer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.NameTagItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LightBoxBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public LightBoxBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (itemInHand.getItem() instanceof NameTagItem){
            if (blockEntity instanceof LightBoxBlockEntity box){
                Component hoverName = itemInHand.getHoverName();
                IUtilsMeasurer utilsMeasurer = BakeriesMod.utilsMeasurer;
                if (utilsMeasurer instanceof ClientUtilsMeasurer clientUtilsMeasurer){
                    int length = clientUtilsMeasurer.getLength(hoverName.getString(), 40);
                    String string = hoverName.getString(length);
                    box.setText(string);
                    box.update();
                    return InteractionResult.SUCCESS;
                }
            }
        }else {
            if (itemInHand.getItem() instanceof DyeItem dye){
                if (blockEntity instanceof LightBoxBlockEntity box){
                    box.setColor(dye.getDyeColor().getTextColor());
                    box.update();
                    ItemUtils.shrink(itemInHand,1,pPlayer);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public boolean propagatesSkylightDown(BlockState p_154824_, BlockGetter p_154825_, BlockPos p_154826_) {
        return true;
    }

    public int getLightBlock(BlockState p_154828_, BlockGetter p_154829_, BlockPos p_154830_) {
        return 0;
    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pSide);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LightBoxBlockEntity(blockPos,blockState);
    }
}
