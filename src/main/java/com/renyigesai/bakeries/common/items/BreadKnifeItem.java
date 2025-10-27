package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.common.compat.CompatMod;
import com.renyigesai.bakeries.common.recipe.BreadKnifeRecipe;
import com.renyigesai.bakeries.common.utils.ItemUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;

import java.util.List;
import java.util.Optional;

public class BreadKnifeItem extends DiggerItem {

    private static final RecipeManager.CachedCheck<RecipeInput, BreadKnifeRecipe> CHECK = RecipeManager.createCheck(BreadKnifeRecipe.Type.INSTANCE);

    public BreadKnifeItem(Tier tier, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_AXE, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack hand = pPlayer.getItemInHand(pUsedHand);

        HitResult raytraceresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.NONE);
        if (!(raytraceresult instanceof BlockHitResult)){
            return super.use(pLevel, pPlayer, pUsedHand);
        }

        BlockHitResult ray = (BlockHitResult) raytraceresult;
        Vec3 hitVec = ray.getLocation();
        AABB bb = new AABB(hitVec, hitVec).inflate(1f);
        ItemEntity resultItemEntity = null;
        for (ItemEntity e : pLevel.getEntitiesOfClass(ItemEntity.class, bb)) {
            if (e.getItem().getCount() == 1) {
                resultItemEntity = e;
                break;
            }
        }

        if (resultItemEntity == null){
            return super.use(pLevel, pPlayer, pUsedHand);
        }
        double x = resultItemEntity.getX();
        double y = resultItemEntity.getY();
        double z = resultItemEntity.getZ();
        Optional<RecipeHolder<BreadKnifeRecipe>> optional = getCurrentRecipe(pLevel, resultItemEntity.getItem());

        if (optional.isPresent()){
            SingleRecipeInput singleRecipeInput = new SingleRecipeInput(resultItemEntity.getItem());
            ItemStack resultItemStack = CHECK.getRecipeFor(singleRecipeInput, pLevel).map((p_344662_) -> p_344662_.value().assemble(singleRecipeInput, pLevel.registryAccess())).orElse(resultItemEntity.getItem());
            hand.hurtAndBreak(1,pPlayer,LivingEntity.getSlotForHand(pPlayer.getUsedItemHand()));
            ItemUtil.spawnItemEntity(pLevel, resultItemStack, x,y,z, new Vec3(0.0,0.0,0.0));
            pLevel.addParticle(new ItemParticleOption(ParticleTypes.ITEM,resultItemStack),x,y+0.5,z,((double)pLevel.random.nextFloat() - 0.5D) * 0.08D, ((double)pLevel.random.nextFloat() - 0.5D) * 0.08D, ((double)pLevel.random.nextFloat() - 0.5D) * 0.08D);
            pLevel.playSound(null,new BlockPos((int) x,(int)y,(int)z), SoundEvents.WOOL_BREAK, SoundSource.BLOCKS);
            resultItemEntity.remove(Entity.RemovalReason.KILLED);
        }else {
            if (ModList.get().isLoaded(CompatMod.FARMER_S_DELIGHT)) {
//                return InteractionResultHolder.sidedSuccess(hand, processStoredItemUsingTool(pLevel, hand, resultItemEntity,pPlayer, x, y, z));
            }else {
                return super.use(pLevel,pPlayer,pUsedHand);
            }
        }
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.success(hand);
    }

    private Optional<RecipeHolder<BreadKnifeRecipe>> getCurrentRecipe(Level level, ItemStack stack) {
        if (level == null) {
            return Optional.empty();
        }
        return CHECK.getRecipeFor(new SingleRecipeInput(stack),level);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1,target,EquipmentSlot.MAINHAND);
        return true;
    }

//    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
//        return KNIFE_ACTIONS.contains(toolAction);
//    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        return new ItemStack(this);
    }

    @Override
    public boolean isRepairable(ItemStack itemstack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.bakeries.bread_knife.tips").withStyle(ChatFormatting.BLUE));
    }
}
