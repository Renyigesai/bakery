package net.weibai.bakeries.api.items;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.weibai.bakeries.api.blocks.BreadBlock;
import org.jetbrains.annotations.NotNull;

public class BreadItem extends ItemNameBlockItem {
    public BreadItem(Block block, Properties properties) {
        super(block, properties);

    }
    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        assert player != null;
        if (player.isShiftKeyDown()) {
            if(!pContext.getLevel().getBlockState(pContext.getClickedPos()).is(this.getBlock())){
                return this.place(new BlockPlaceContext(pContext));
            }else if(pContext.getLevel().getBlockState(pContext.getClickedPos()).is(this.getBlock()) && pContext.getLevel().getBlockState(pContext.getClickedPos()).hasProperty(BreadBlock.PILE)) {
                int value = pContext.getLevel().getBlockState(pContext.getClickedPos()).getValue(BreadBlock.PILE);
                if (pContext.getLevel().getBlockState(pContext.getClickedPos()).getBlock() instanceof BreadBlock block && value < block.getMaxPile()) {
                    pContext.getLevel().setBlock(pContext.getClickedPos(), pContext.getLevel().getBlockState(pContext.getClickedPos()).setValue(BreadBlock.PILE, value + 1), 3);
                    if (!player.getAbilities().instabuild) {
                        player.getUseItem().shrink(1);
                    }
                    if(block.getPlaceSound() != null){
                        pContext.getLevel().playSound(null, pContext.getClickedPos(), block.getPlaceSound(), SoundSource.BLOCKS);
                    }
                    return InteractionResult.sidedSuccess(pContext.getLevel().isClientSide);
                }
            }
        }
        return super.use(pContext.getLevel(), pContext.getPlayer(), pContext.getHand()).getResult();
    }
//    @Override
//    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
//        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
//        if (itemstack.getOrDefault().getBoolean("perfect")){
//            pPlayer.startUsingItem(pUsedHand);
//            return InteractionResultHolder.consume(itemstack);
//        }
//        return super.use(pLevel, pPlayer, pUsedHand);
//    }
}