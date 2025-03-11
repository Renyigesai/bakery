package com.renyigesai.bakeries.block;

import com.renyigesai.bakeries.util.ItemUtil;
import com.renyigesai.bakeries.util.Shortcuts;
import com.renyigesai.bakeries.api.block.properties.ModIntegerProperty;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

public class MilkTankBlock extends TankBlock {

    public static final ModIntegerProperty MILK = ModIntegerProperty.create("milk", 0, 3);
    public static final BooleanProperty SALT = BooleanProperty.create("salt");

    public MilkTankBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(MILK, 3).setValue(SALT,false));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        ItemStack hand = pPlayer.getItemInHand(pHand);

        if (hand.is(Items.GLASS_BOTTLE)){
            return ladleOut(pLevel,pPos,pState,pPlayer,pHand);
        } else if (hand.is(BakeriesItems.BOTTLE_MILK.get())) {
            return fillMilk(pLevel,pPos,pState,pPlayer,pHand);
        } else if (hand.is(ItemTags.create(new ResourceLocation("forge:salt")))) {
            return fillSalt(pLevel,pPos,pState,pPlayer,pHand);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public static InteractionResult ladleOut(Level level, BlockPos pos, BlockState state, Player playerIn, InteractionHand pHand){
        ItemStack hand = playerIn.getItemInHand(pHand);
        int milk = state.getValue(MILK);
        if (milk > 1){
            Shortcuts.setBlock(level,pos,state,MILK,1,false);
        }else {
            level.setBlock(pos, BakeriesBlocks.FERMENTATION_TANK.get().defaultBlockState(),0);
        }
        hand.shrink(1);
        ItemUtil.givePlayerItem(playerIn,new ItemStack(BakeriesItems.BOTTLE_MILK.get()));
        level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    public InteractionResult fillMilk(Level level, BlockPos pos, BlockState state, Player playerIn, InteractionHand pHand){
        ItemStack hand = playerIn.getItemInHand(pHand);
        int milk = state.getValue(MILK);
        if (milk < 3){
            Shortcuts.setBlock(level,pos,state,MILK,1,true);
            hand.shrink(1);
            ItemUtil.givePlayerItem(playerIn,new ItemStack(BakeriesItems.BOTTLE_MILK.get()));
        }return InteractionResult.SUCCESS;
    }

    public static InteractionResult fillSalt(Level level, BlockPos pos, BlockState state, Player playerIn,InteractionHand pHand){
        ItemStack handStack = playerIn.getItemInHand(pHand);
        int milk = state.getValue(MILK);
        if (milk == 3) {
            level.setBlock(pos, state.setValue(SALT, true), 0);
            handStack.shrink(1);
            level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 0.8F, 0.8F);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MILK,SALT);
    }

    @Override
    public void randomTick(BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        int milk = pState.getValue(MILK);
        boolean sweet_berries = pState.getValue(SALT);
        if (milk == 3 && sweet_berries) {
                pLevel.setBlock(pPos, BakeriesBlocks.CHEESE_TANK.get().defaultBlockState(), 3);
        }
    }

    @Override
    public void animateTick(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        int milk = pState.getValue(MILK);
        boolean sweet_berries = pState.getValue(SALT);
        Direction direction = Direction.getRandom(pRandom);
        double d0 = direction.getStepX() == 0 ? pRandom.nextDouble() : 0.5D + (double) direction.getStepX() * 0.6D;
        double d1 = direction.getStepY() == 0 ? pRandom.nextDouble() : 0.5D + (double) direction.getStepY() * 0.6D;
        double d2 = direction.getStepZ() == 0 ? pRandom.nextDouble() : 0.5D + (double) direction.getStepZ() * 0.6D;
        if (milk == 3 && sweet_berries){
            pLevel.addParticle(ParticleTypes.ENTITY_EFFECT, (double) pPos.getX() + d0, (double) pPos.getY() + d1, (double) pPos.getZ() + d2, 0.0D, 0.0D, 0.0D);
        }
    }


}
