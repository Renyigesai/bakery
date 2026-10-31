package com.renyigesai.bakeries.block.luminous_light_sign;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.block.HorizontalConnectBlock;
import com.renyigesai.bakeries.util.ItemUtils;
import com.renyigesai.bakeries.util.WorldUtil;
import com.renyigesai.bakeries.util.measurer.ClientUtilsMeasurer;
import com.renyigesai.bakeries.util.measurer.IUtilsMeasurer;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.NameTagItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class LuminousLightSignBlock extends HorizontalConnectBlock implements EntityBlock {
    public LuminousLightSignBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.GLASS));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            default -> box(0, 10, 6, 16, 14, 10);
            case NORTH -> box(0, 10, 6, 16, 14, 10);
            case EAST -> box(6, 10, 0, 10, 14, 16);
            case WEST -> box(6, 10, 0, 10, 14, 16);
        };
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
            if (blockEntity instanceof LuminousLightSignBlockEntity){
                Component hoverName = itemInHand.getHoverName();
                continuousSetText(pLevel,pPos,hoverName,0,true);
                triggerAdvancement(pLevel,pPlayer);
                if (isHelloWorld(hoverName.getString())){
                    triggerAdvancement(pLevel,pPlayer);
                }
                return InteractionResult.SUCCESS;
            }
        }else {
            if (itemInHand.getItem() instanceof DyeItem dye){
                if (blockEntity instanceof LuminousLightSignBlockEntity sign){
                    sign.setColor(dye.getDyeColor().getTextColor());
                    ItemUtils.shrink(itemInHand,1,pPlayer);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    private void continuousSetText(Level level,BlockPos pos,Component component,int depth,boolean isCenter){
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof LuminousLightSignBlockEntity sign){
            IUtilsMeasurer utilsMeasurer = BakeriesMod.utilsMeasurer;
            String text = component.getString();
            if (utilsMeasurer instanceof ClientUtilsMeasurer clientUtilsMeasurer){
                int length = clientUtilsMeasurer.getLength(text);
                String cutText = component.getString(clientUtilsMeasurer.getLength(text,45));
                sign.setText(cutText);
                sign.setCenter(isCenter);
                sign.update();

                Direction direction = level.getBlockState(pos).getValue(FACING);
                Direction right = direction.getClockWise(Direction.Axis.Y);
                BlockPos rightPos = pos.relative(right);
                BlockState rightState = level.getBlockState(rightPos);
                depth ++;
                if (depth >= 16 || length < 45 || !rightState.is(this) || rightState.getValue(FACING) != direction){
                    return;
                }
                int consumed = Math.min(cutText.length(), text.length());
                String remaining = text.substring(consumed);
                if (remaining.isEmpty()) {
                    return;
                }
                continuousSetText(level, rightPos, Component.literal(remaining), depth,false);
            }
        }
    }

    private boolean isHelloWorld(String input) {
        if (input == null) return false;
        String normalized = input.toUpperCase(Locale.ROOT)
                .replace(",", "")
                .trim()
                .replaceAll("\\s+", " ");
        return normalized.equals("HELLO WORLD");
    }

    public void triggerAdvancement(Level level,Player player){
        if (!WorldUtil.isDoneAdvancement(player,level,new ResourceLocation(BakeriesMod.MODID,"hello_world"))){
            if (player instanceof ServerPlayer serverPlayer){
                Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("bakeries","hello_world"));
                if (advancement != null) {
                    serverPlayer.getAdvancements().award(advancement,"hello_world");
                }
            }
        }
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
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LuminousLightSignBlockEntity(pPos,pState);
    }
}
