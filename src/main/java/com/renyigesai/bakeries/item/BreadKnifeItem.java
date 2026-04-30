package com.renyigesai.bakeries.item;

import com.google.common.collect.Sets;
import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.compat.CompatMod;
import com.renyigesai.bakeries.recipe.BreadKnifeRecipe;
import com.renyigesai.bakeries.util.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
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
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.registry.ModAdvancements;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
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
        double x = resultItemEntity.getX();
        double y = resultItemEntity.getY();
        double z = resultItemEntity.getZ();
        Optional<BreadKnifeRecipe> recipeOptional = getCurrentRecipe(pLevel,resultItemEntity.getItem());
        if (recipeOptional.isPresent()){
            BreadKnifeRecipe recipe = recipeOptional.get();
            ItemStack resultItemStack = recipe.getResultItem(pLevel.registryAccess());
            hand.hurtAndBreak(1, pPlayer, (p_41300_) -> p_41300_.broadcastBreakEvent(pUsedHand));
            recipe.getOutput().forEach((item)->{
                ItemUtils.spawnItemEntity(pLevel,item,x,y,z,Vec3.ZERO);});
            pLevel.addParticle(new ItemParticleOption(ParticleTypes.ITEM,resultItemStack),x,y+0.5,z,((double)pLevel.random.nextFloat() - 0.5D) * 0.08D, ((double)pLevel.random.nextFloat() - 0.5D) * 0.08D, ((double)pLevel.random.nextFloat() - 0.5D) * 0.08D);
            pLevel.playSound(null,new BlockPos((int) x,(int)y,(int)z),SoundEvents.WOOL_BREAK, SoundSource.BLOCKS);
            resultItemEntity.remove(Entity.RemovalReason.KILLED);
        }else {
            if (CompatMod.FARMER_S_DELIGHT) {
                return InteractionResultHolder.sidedSuccess(hand, processStoredItemUsingTool(pLevel, hand, resultItemEntity,pPlayer, x, y, z));
            }else {
                return super.use(pLevel,pPlayer,pUsedHand);
            }
        }
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.success(hand);
    }


    public boolean processStoredItemUsingTool(Level level,ItemStack toolStack, ItemEntity item,@javax.annotation.Nullable Player player,double x,double y,double z) {
        if (!CompatMod.FARMER_S_DELIGHT){
            return false;
        }
        try {
            if (level == null) {
                return false;
            } else {
                ItemStackHandler helper = new ItemStackHandler(1);
                helper.setStackInSlot(0,item.getItem());
                Optional<CuttingBoardRecipe> matchingRecipe = this.getMatchingRecipe(level,new RecipeWrapper(helper),toolStack,player);
                matchingRecipe.ifPresent((recipe) -> {
                    List<ItemStack> results;
                    if (isFarmersDelightAbove_1_3_0()){
                        results = recipe.rollResults(level.random,EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, toolStack),new RecipeWrapper(helper));
                    }else {
                        //使用反射获取旧版本方法
                        try {
                            Method rollResults = CuttingBoardRecipe.class.getMethod("rollResults", RandomSource.class, int.class);
                            results = (List<ItemStack>)rollResults.invoke(recipe, level.random, EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, toolStack));
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            results = List.of();
                            BakeriesMod.LOGGER.error(e);
                        }
                    }
                    Iterator var5 = results.iterator();

                    while(var5.hasNext()) {
                        ItemStack resultStack = (ItemStack)var5.next();
                        vectorwing.farmersdelight.common.utility.ItemUtils.spawnItemEntity(level, resultStack.copy(),x,y,z,0,0,0);
                    }
                    level.addParticle(new ItemParticleOption(ParticleTypes.ITEM,item.getItem()),x,y+0.5,z,((double)level.random.nextFloat() - 0.5D) * 0.08D, ((double)level.random.nextFloat() - 0.5D) * 0.08D, ((double)level.random.nextFloat() - 0.5D) * 0.08D);
                    item.remove(Entity.RemovalReason.KILLED);
                    if (player != null) {
                        toolStack.hurtAndBreak(1, player, (user) -> {
                            user.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                        });
                    } else if (toolStack.hurt(1, level.random, null)) {
                        toolStack.setCount(0);
                    }

                    level.playSound(null,new BlockPos((int) x,(int)y,(int)z),SoundEvents.WOOL_BREAK, SoundSource.BLOCKS);
                    if (player instanceof ServerPlayer) {
                        ModAdvancements.CUTTING_BOARD.trigger((ServerPlayer)player);
                    }
                });
                return matchingRecipe.isPresent();
            }
        }catch (NoSuchFieldError error){
            BakeriesMod.LOGGER.error(error);
        }
        return false;
    }

    private boolean isFarmersDelightAbove_1_3_0(){
        String fullVersion = ModList.get().getModFileById("farmersdelight").versionString();
        if (fullVersion == null || fullVersion.isEmpty()) {
            return false;
        }
        String modVersion = fullVersion;
        int dashIdx = fullVersion.indexOf('-');
        if (dashIdx != -1 && dashIdx + 1 < fullVersion.length()) {
            modVersion = fullVersion.substring(dashIdx + 1);
        }
        ComparableVersion current = new ComparableVersion(modVersion);
        ComparableVersion target = new ComparableVersion("1.2.9");
        return current.compareTo(target) > 0;
    }

    private Optional<CuttingBoardRecipe> getMatchingRecipe(Level level,RecipeWrapper recipeWrapper, ItemStack toolStack, @Nullable Player player) {
        if (!CompatMod.FARMER_S_DELIGHT){
            return Optional.empty();
        }
        try {
            if (level == null) {
                return Optional.empty();
            } else {
                List<CuttingBoardRecipe> recipeList = level.getRecipeManager().getRecipesFor((RecipeType)ModRecipeTypes.CUTTING.get(), recipeWrapper, level);
                if (recipeList.isEmpty()) {
                    if (player != null) {
                        player.displayClientMessage(TextUtils.getTranslation("block.cutting_board.invalid_item", new Object[0]), true);
                    }

                    return Optional.empty();
                } else {
                    Optional<CuttingBoardRecipe> recipe = recipeList.stream().filter((cuttingRecipe) -> {
                        return cuttingRecipe.getTool().test(toolStack);
                    }).findFirst();
                    if (!recipe.isPresent()) {
                        if (player != null) {
                            player.displayClientMessage(TextUtils.getTranslation("block.cutting_board.invalid_tool", new Object[0]), true);
                        }

                        return Optional.empty();
                    } else {
                        return recipe;
                    }
                }
            }
        }catch (NoSuchFieldError error){
            BakeriesMod.LOGGER.error(error);
        }
        return Optional.empty();
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

    public int getEnchantmentValue() {
        return 15;
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
    public boolean isEnchantable(ItemStack pStack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        Set<Enchantment> ALLOWED_ENCHANTMENTS = Sets.newHashSet(Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.MOB_LOOTING);
        if (ALLOWED_ENCHANTMENTS.contains(enchantment)) {
            return true;
        } else {
            Set<Enchantment> DENIED_ENCHANTMENTS = Sets.newHashSet(Enchantments.BLOCK_FORTUNE);
            return !DENIED_ENCHANTMENTS.contains(enchantment) && enchantment.category.canEnchant(stack.getItem());
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.bakeries.bread_knife.tips").withStyle(ChatFormatting.BLUE));
    }
}
