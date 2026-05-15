package com.renyigesai.bakeries.init.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public final class StateBlocks {
    private StateBlocks() {
    }

    public static class ToasterBlock extends MachineBlocks.FacingMachineBlock {
        public static final EnumProperty<ToasterState> STATE = EnumProperty.create("state", ToasterState.class);

        public ToasterBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState().setValue(STATE, ToasterState.IDLE));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(STATE);
        }

        @Override
        public @NotNull InteractionResult use(BlockState state, Level level, net.minecraft.core.BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    public static class FermentationTankBlock extends Block {
        public static final IntegerProperty FLOUR = IntegerProperty.create("flour", 0, 3);
        public static final BooleanProperty WATER = BooleanProperty.create("water");

        public FermentationTankBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.stateDefinition.any().setValue(FLOUR, 0).setValue(WATER, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FLOUR, WATER);
        }
    }

    public static class WoodCounterBlock extends FacingBlock {
        public static final BooleanProperty PLATE = BooleanProperty.create("plate");

        public WoodCounterBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState().setValue(PLATE, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(PLATE);
        }
    }

    public static class MilkTankBlock extends Block {
        public static final IntegerProperty MILK = IntegerProperty.create("milk", 1, 3);
        public static final BooleanProperty SALT = BooleanProperty.create("salt");

        public MilkTankBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.stateDefinition.any().setValue(MILK, 1).setValue(SALT, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(MILK, SALT);
        }
    }

    public static class YeastTankBlock extends Block {
        public static final IntegerProperty YEAST = IntegerProperty.create("yeast", 1, 3);

        public YeastTankBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.stateDefinition.any().setValue(YEAST, 1));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(YEAST);
        }
    }

    public static class RackBlock extends FacingBlock {
        public static final EnumProperty<RackType> TYPE = EnumProperty.create("type", RackType.class);

        public RackBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState().setValue(TYPE, RackType.SINGLE));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(TYPE);
        }
    }

    public static class BreadBasketBlock extends FacingBlock {
        public static final BooleanProperty FILL = BooleanProperty.create("fill");

        public BreadBasketBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState().setValue(FILL, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(FILL);
        }
    }

    public static class GlassCabinetDoorBlock extends FacingBlock {
        public GlassCabinetDoorBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState()
                    .setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER)
                    .setValue(BlockStateProperties.OPEN, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(BlockStateProperties.DOUBLE_BLOCK_HALF, BlockStateProperties.OPEN);
        }
    }

    public static class ToastBlock extends FacingBlock {
        public static final IntegerProperty PILE = IntegerProperty.create("pile", 1, 2);
        public static final IntegerProperty SLICE = IntegerProperty.create("slice", 1, 4);

        public ToastBlock(BlockBehaviour.Properties properties) {
            super(properties);
            this.registerDefaultState(this.defaultBlockState().setValue(PILE, 1).setValue(SLICE, 1));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(PILE, SLICE);
        }
    }

    public static class FacingPileBlock extends FacingBlock {
        public static final IntegerProperty PILE = IntegerProperty.create("pile", 1, 4);
        private final int maxPile;

        public FacingPileBlock(BlockBehaviour.Properties properties, int maxPile) {
            super(properties);
            this.maxPile = Math.max(1, Math.min(4, maxPile));
            this.registerDefaultState(this.defaultBlockState().setValue(PILE, 1));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(PILE);
        }

        @Override
        public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
            BlockState state = super.getStateForPlacement(context);
            return (state == null ? this.defaultBlockState() : state).setValue(PILE, 1);
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull BlockState updateShape(BlockState state, Direction facing, BlockState facingState, net.minecraft.world.level.LevelAccessor level, net.minecraft.core.BlockPos currentPos, net.minecraft.core.BlockPos facingPos) {
            int pile = state.getValue(PILE);
            if (pile > maxPile) {
                return state.setValue(PILE, maxPile);
            }
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        }
    }

    public static class CropLikeBlock extends Block {
        public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
        private final int maxAge;

        public CropLikeBlock(BlockBehaviour.Properties properties, int maxAge) {
            super(properties);
            this.maxAge = maxAge;
            this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
        }

        @Override
        public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
            return this.defaultBlockState();
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(AGE);
        }

        @Override
        @SuppressWarnings("deprecation")
        public @NotNull BlockState updateShape(BlockState state, Direction facing, BlockState facingState, net.minecraft.world.level.LevelAccessor level, net.minecraft.core.BlockPos currentPos, net.minecraft.core.BlockPos facingPos) {
            int age = state.getValue(AGE);
            if (age > maxAge) {
                return state.setValue(AGE, maxAge);
            }
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        }
    }
}
