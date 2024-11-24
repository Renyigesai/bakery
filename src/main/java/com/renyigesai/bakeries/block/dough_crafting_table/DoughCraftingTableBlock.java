package com.renyigesai.bakeries.block.dough_crafting_table;

import com.renyigesai.bakeries.inventory.dough_crafting_table.DoughCraftingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class DoughCraftingTableBlock extends Block {
    public static final Component DOUCH_CRAFTING_TABLE_TITLE = Component.translatable("container.dough_crafting_table");    public DoughCraftingTableBlock() {
        super(BlockBehaviour.Properties.of().strength(2.5f, 10f).sound(SoundType.WOOD).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }
   public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      if (pLevel.isClientSide) {
         return InteractionResult.SUCCESS;
      } else {
         pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
         pPlayer.awardStat(Stats.INTERACT_WITH_LOOM);
         return InteractionResult.CONSUME;
      }
   }
   public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
      return new SimpleMenuProvider((p_54783_, p_54784_, p_54785_) -> {
         return new DoughCraftingTableMenu(p_54783_, p_54784_, ContainerLevelAccess.create(pLevel, pPos));
      }, DOUCH_CRAFTING_TABLE_TITLE);
   }
}