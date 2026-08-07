package com.renyigesai.bakeries.common.items;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.common.recipe.BreadKnifeRecipe;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import com.renyigesai.bakeries.common.utils.ModIsLoaded;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipeInput;
import vectorwing.farmersdelight.common.registry.ModAdvancements;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BreadKnifeItem extends DiggerItem {
    public static final Set<ItemAbility> KNIFE_ACTIONS;

    public BreadKnifeItem(Tier tier, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_AXE, properties.attributes(createAttributes()));
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
        Optional<RecipeHolder<BreadKnifeRecipe>> optional = getBreadKnifeRecipe(pLevel,resultItemEntity.getItem());
        if (optional.isPresent()){
            hand.hurtAndBreak(1,pPlayer,LivingEntity.getSlotForHand(pPlayer.getUsedItemHand()));
            optional.get().value().getAllResults().forEach(item -> {
                ItemUtils.spawnItemEntity(pLevel, item, x,y,z, new Vec3(0.0,0.0,0.0));
                pLevel.addParticle(new ItemParticleOption(ParticleTypes.ITEM,item),x,y+0.5,z,((double)pLevel.random.nextFloat() - 0.5D) * 0.08D, ((double)pLevel.random.nextFloat() - 0.5D) * 0.08D, ((double)pLevel.random.nextFloat() - 0.5D) * 0.08D);
            });
            pLevel.playSound(null,new BlockPos((int) x,(int)y,(int)z), SoundEvents.WOOL_BREAK, SoundSource.BLOCKS);
            resultItemEntity.remove(Entity.RemovalReason.KILLED);
        }else {
            if (ModIsLoaded.isFarmerSDelight()) {
                return InteractionResultHolder.sidedSuccess(hand, processStoredItemUsingTool(pLevel, hand, resultItemEntity,pPlayer, x, y, z));
            }else {
                return super.use(pLevel,pPlayer,pUsedHand);
            }
        }
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.success(hand);
    }

    public boolean processStoredItemUsingTool(Level level,ItemStack hand,ItemEntity itemEntity, @Nullable Player player,double x,double y,double z) {
        if (level == null) return false;

        Optional<RecipeHolder<CuttingBoardRecipe>> matchingRecipe = getCuttingRecipe(level,itemEntity.getItem());

        matchingRecipe.ifPresent(recipe -> {
            List<ItemStack> results = recipe.value().rollResults(level.random, EnchantmentHelper.getTagEnchantmentLevel(level.holder(Enchantments.FORTUNE).get(), hand));
            for (ItemStack resultStack : results) {
                vectorwing.farmersdelight.common.utility.ItemUtils.spawnItemEntity(level,resultStack.copy(),x,y,z,0,0,0);
            }
            if (!level.isClientSide) {
                hand.hurtAndBreak(1, (ServerLevel) level, player, (item) -> {
                });
            }

            level.playSound(null,new BlockPos((int) x,(int)y,(int)z),SoundEvents.WOOL_BREAK, SoundSource.BLOCKS);
            itemEntity.remove(Entity.RemovalReason.KILLED);
            if (player instanceof ServerPlayer) {
                ModAdvancements.USE_CUTTING_BOARD.get().trigger((ServerPlayer) player);
            }
        });

        return matchingRecipe.isPresent();
    }

    private Optional<RecipeHolder<BreadKnifeRecipe>> getBreadKnifeRecipe(Level level, ItemStack stack) {
        if (level == null) {
            return Optional.empty();
        }
        return RecipeManager.createCheck(BakeriesRecipeTypes.BREAD_KNIFE_TYPE.get()).getRecipeFor(new SingleRecipeInput(stack),level);
    }

    private Optional<RecipeHolder<CuttingBoardRecipe>> getCuttingRecipe(Level level, ItemStack stack) {
        if (!ModIsLoaded.isFarmerSDelight())
            return Optional.empty();
        if (level == null) {
            return Optional.empty();
        }
        return RecipeManager.createCheck(ModRecipeTypes.CUTTING.get()).getRecipeFor(new CuttingBoardRecipeInput(stack,new ItemStack(BakeriesItems.BREAD_KNIFE.get())),level);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
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
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return !enchantment.is(Enchantments.SWEEPING_EDGE) && super.isPrimaryItemFor(stack, enchantment);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return !enchantment.is(Enchantments.SWEEPING_EDGE) && super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
        return KNIFE_ACTIONS.contains(toolAction);
    }

    @Override
    public int getEnchantmentLevel(ItemStack stack, Holder<Enchantment> enchantment) {
        return 14;
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 14;
    }

    @Override
    public int getEnchantmentValue() {
        return 14;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltips.bakeries.bread_knife").withStyle(ChatFormatting.BLUE));
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,new AttributeModifier(BASE_ATTACK_DAMAGE_ID,2.5d,AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,new AttributeModifier(BASE_ATTACK_SPEED_ID,-0.2d,AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.MAINHAND)
                .build();
    }

    static {
        KNIFE_ACTIONS = Set.of(ItemAbilities.SHEARS_CARVE, ItemAbilities.SWORD_DIG);
    }
}
