package com.renyigesai.bakeries.init.blocks;

import com.renyigesai.bakeries.block.entity.MachineBlockEntity;
import com.renyigesai.bakeries.capabilities.PlayerKeyAuxiliary;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.renyigesai.bakeries.menu.DoughCraftingTableMenu;

public final class MachineBlocks {
    private MachineBlocks() {
    }

    public static class MachineBlock extends BaseEntityBlock {
        public MachineBlock(BlockBehaviour.Properties properties) {
            super(properties);
        }

        @Override
        public @NotNull RenderShape getRenderShape(BlockState state) {
            return RenderShape.MODEL;
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new MachineBlockEntity(pos, state);
        }

        @Override
        public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
            return level.isClientSide ? null : (lvl, pos, st, be) -> {
                if (be instanceof MachineBlockEntity machine) {
                    MachineBlockEntity.serverTick(lvl, pos, st, machine);
                }
            };
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            }
            BlockEntity be = level.getBlockEntity(pos);
            if (state.is(BakeriesBlocks.DRINK_CUP) && be instanceof MachineBlockEntity machine) {
                ItemStack output = machine.getItem(4);
                if (!output.isEmpty()) {
                    if (!player.getInventory().add(output.copy())) {
                        player.drop(output.copy(), false);
                    }
                    machine.setItem(4, ItemStack.EMPTY);
                    machine.setChanged();
                    return InteractionResult.CONSUME;
                }
                ItemStack held = player.getItemInHand(hand);
                if (!held.isEmpty()) {
                    for (int i = 0; i < 4; i++) {
                        ItemStack input = machine.getItem(i);
                        if (input.isEmpty()) {
                            ItemStack copy = held.copy();
                            copy.setCount(1);
                            machine.setItem(i, copy);
                            held.shrink(1);
                            machine.setChanged();
                            return InteractionResult.CONSUME;
                        }
                    }
                }
                if (PlayerKeyAuxiliary.isKeyDown(player.getUUID())) {
                    for (int i = 0; i < 5; i++) {
                        machine.setItem(i, ItemStack.EMPTY);
                    }
                    machine.resetMachineProgress();
                    machine.setChanged();
                    return InteractionResult.CONSUME;
                }
            }
            if (be instanceof MenuProvider provider && player instanceof ServerPlayer serverPlayer) {
                if (state.is(BakeriesBlocks.DOUGH_CRAFTING_TABLE)) {
                    if (hit.getDirection() == Direction.UP) {
                        serverPlayer.openMenu(new SimpleMenuProvider(
                                (syncId, inventory, p) -> new DoughCraftingTableMenu(syncId, inventory, ContainerLevelAccess.create(level, pos)),
                                state.getBlock().getName()
                        ));
                        player.awardStat(Stats.INTERACT_WITH_LOOM);
                    } else {
                        serverPlayer.openMenu(provider);
                        player.awardStat(Stats.OPEN_BARREL);
                    }
                    return InteractionResult.CONSUME;
                }
                serverPlayer.openMenu(provider);
            }
            return InteractionResult.CONSUME;
        }
    }

    public static class FacingMachineBlock extends MachineBlock {
        public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

        public FacingMachineBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING);
        }

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        }
    }
}
