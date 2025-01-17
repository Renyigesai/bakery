package com.renyigesai.bakeries.block.dough_crafting_table;

import com.renyigesai.bakeries.inventory.dough_crafting_table.DoughCraftingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class DoughCraftingTableBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final Component DOUCH_CRAFTING_TABLE_TITLE = Component.translatable("container.dough_crafting_table");
    public DoughCraftingTableBlock() {
        super(BlockBehaviour.Properties.of().strength(2.5f, 10f).sound(SoundType.WOOD).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }
   public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      if (pLevel.isClientSide) {
         return InteractionResult.SUCCESS;
      } else {
         if (pHit.getDirection().equals(Direction.UP)){
            openDoughCraftingTableMenu(pState, pLevel, pPos, pPlayer, pHand, pHit);
         }else {
            openChestMenu(pState, pLevel, pPos, pPlayer, pHand, pHit);
         }
//         pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
//         pPlayer.awardStat(Stats.INTERACT_WITH_LOOM);
         return InteractionResult.CONSUME;
      }
   }

   public InteractionResult openDoughCraftingTableMenu(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
      pPlayer.awardStat(Stats.INTERACT_WITH_LOOM);
      return InteractionResult.CONSUME;
   }

   public InteractionResult openChestMenu(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
      BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
      if (blockEntity instanceof DoughCraftingTableBlockEntity){
         pPlayer.openMenu((DoughCraftingTableBlockEntity)blockEntity);
         pPlayer.awardStat(Stats.OPEN_BARREL);
      }
      return InteractionResult.CONSUME;
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext pContext) {
      return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
   }
   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(FACING);
   }
   public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
      return new SimpleMenuProvider((p_54783_, p_54784_, p_54785_) -> {
         return new DoughCraftingTableMenu(p_54783_, p_54784_, ContainerLevelAccess.create(pLevel, pPos));
      }, DOUCH_CRAFTING_TABLE_TITLE);
   }
   public boolean useShapeForLightOcclusion(BlockState pState) {
      return true;
   }
   public RenderShape getRenderShape(BlockState pState) {
      return RenderShape.MODEL;
   }
   public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }

   @Override
   public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
      if (!pState.is(pNewState.getBlock())) {
         BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
         if (blockEntity instanceof Container container){
            Containers.dropContents(pLevel,pPos,container);
            pLevel.updateNeighbourForOutputSignal(pPos,this);
         }
      }
      super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
   }

   @Nullable
   @Override
   public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
      return new DoughCraftingTableBlockEntity(pPos,pState);
   }
}