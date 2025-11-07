package com.renyigesai.bakeries.common.blocks.dough_crafting_table;

import com.mojang.serialization.MapCodec;
import com.renyigesai.bakeries.common.inventory.dough_crafting_table.DoughCraftingTableMenu;
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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DoughCraftingTableBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final Component DOUCH_CRAFTING_TABLE_TITLE = Component.translatable("container.dough_crafting_table");
    public DoughCraftingTableBlock() {
        super(Properties.of().strength(2.5f, 10f).sound(SoundType.WOOD).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
       this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

   @Override
   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (level.isClientSide) {
         return InteractionResult.SUCCESS;
      } else {
         if (hitResult.getDirection().equals(Direction.UP)){
            openDoughCraftingTableMenu(state, level, pos, player, hitResult);
         }else {
            openChestMenu(state, level, pos, player, hitResult);
         }
         return InteractionResult.CONSUME;
      }
   }

   public void openDoughCraftingTableMenu(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
      pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
      pPlayer.awardStat(Stats.INTERACT_WITH_LOOM);
   }

   public void openChestMenu(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit){
      BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
      if (blockEntity instanceof DoughCraftingTableBlockEntity){
         pPlayer.openMenu((DoughCraftingTableBlockEntity)blockEntity);
         pPlayer.awardStat(Stats.OPEN_BARREL);
      }
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext pContext) {
      return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
   }
   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(FACING);
   }
   public MenuProvider getMenuProvider(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
      return new SimpleMenuProvider((p_54783_, p_54784_, p_54785_) -> {
         return new DoughCraftingTableMenu(p_54783_, p_54784_, ContainerLevelAccess.create(pLevel, pPos));
      }, DOUCH_CRAFTING_TABLE_TITLE);
   }
   public boolean useShapeForLightOcclusion(@NotNull BlockState pState) {
      return true;
   }

   @Override
   protected MapCodec<? extends BaseEntityBlock> codec() {
      return simpleCodec(DaylightDetectorBlock::new);
   }

   public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
      return RenderShape.MODEL;
   }
   public boolean isPathfindable(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull PathComputationType pType) {
      return false;
   }

   @Override
   public void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
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
   public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
      return new DoughCraftingTableBlockEntity(pPos,pState);
   }
}