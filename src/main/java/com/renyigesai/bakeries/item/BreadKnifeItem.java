package com.renyigesai.bakeries.item;

import com.renyigesai.bakeries.recipe.BreadKnifeRecipe;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BreadKnifeItem extends DiggerItem {
    public static final Set<ToolAction> KNIFE_ACTIONS = Set.of(ToolActions.SHEARS_CARVE);
    public BreadKnifeItem(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, BlockTags.MINEABLE_WITH_AXE, pProperties);
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

        Optional<BreadKnifeRecipe> recipeOptional = getCurrentRecipe(pLevel,resultItemEntity.getItem());
        if (recipeOptional.isPresent()){
            BreadKnifeRecipe recipe = recipeOptional.get();
            ItemStack resultItemStack = recipe.getResultItem(pLevel.registryAccess()).copy();
            double x = resultItemEntity.getX();
            double y = resultItemEntity.getY();
            double z = resultItemEntity.getZ();
            hand.hurtAndBreak(1, pPlayer, (p_41300_) -> p_41300_.broadcastBreakEvent(pUsedHand));
            ItemUtil.spawnItemEntity(pLevel, resultItemStack, x,y,z, new Vec3(0.0,0.0,0.0));
            pLevel.addParticle(new ItemParticleOption(ParticleTypes.ITEM,resultItemStack),x,y+0.5,z,((double)pLevel.random.nextFloat() - 0.5D) * 0.08D, ((double)pLevel.random.nextFloat() - 0.5D) * 0.08D, ((double)pLevel.random.nextFloat() - 0.5D) * 0.08D);
            pLevel.playSound(null,new BlockPos((int) x,(int)y,(int)z),SoundEvents.WOOL_BREAK, SoundSource.BLOCKS);
            resultItemEntity.remove(Entity.RemovalReason.KILLED);
        }
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.success(hand);
    }

    private Optional<BreadKnifeRecipe> getCurrentRecipe(Level level, ItemStack stack) {
        SimpleContainer inventory = new SimpleContainer(1);
            inventory.setItem(0, stack);
        if (level == null) {
            return Optional.empty();
        }
        return level.getRecipeManager()
                .getRecipeFor(BreadKnifeRecipe.Type.INSTANCE, inventory, level);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return KNIFE_ACTIONS.contains(toolAction);
    }

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
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.bakeries.bread_knife.tips").withStyle(ChatFormatting.BLUE));
    }
}
