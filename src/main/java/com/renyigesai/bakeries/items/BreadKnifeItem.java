package com.renyigesai.bakeries.items;

import com.renyigesai.bakeries.init.BakeriesRecipeTypes;
import com.renyigesai.bakeries.recipe.MultiOutputSingleItemRecipe;
import com.renyigesai.bakeries.recipe.SimpleMachineRecipe;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class BreadKnifeItem extends DiggerItem {
    private static final double CUT_REACH = 4.5D;

    public BreadKnifeItem(Properties properties) {
        super(2.5F, -0.2F, Tiers.STONE, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    @Override
    public @NotNull InteractionResult useOn(net.minecraft.world.item.context.UseOnContext context) {
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull net.minecraft.world.InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack knife = player.getItemInHand(usedHand);
        if (usedHand != InteractionHand.MAIN_HAND) {
            return net.minecraft.world.InteractionResultHolder.pass(knife);
        }
        ItemEntity target = findTargetItem(level, player);
        if (target == null || target.getItem().isEmpty()) {
            return net.minecraft.world.InteractionResultHolder.pass(knife);
        }
        if (!level.isClientSide) {
            ItemStack input = target.getItem();
            SimpleMachineRecipe recipe = level.getRecipeManager()
                    .getRecipeFor(BakeriesRecipeTypes.BREAD_KNIFE, new SimpleContainer(input), level)
                    .orElse(null);
            if (recipe == null || !recipe.isValid()) {
                return net.minecraft.world.InteractionResultHolder.pass(knife);
            }
            input.shrink(1);
            if (recipe instanceof MultiOutputSingleItemRecipe multiOutputRecipe) {
                for (ItemStack result : multiOutputRecipe.getAllResults()) {
                    spawnCutResult(level, target, result);
                }
            } else {
                spawnCutResult(level, target, recipe.getResultItem(level.registryAccess()));
            }
            if (input.isEmpty()) {
                target.discard();
            } else {
                target.setItem(input);
            }
            if (!player.getAbilities().instabuild) {
                knife.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(usedHand));
            }
        }
        return net.minecraft.world.InteractionResultHolder.sidedSuccess(knife, level.isClientSide);
    }

    private static ItemEntity findTargetItem(Level level, Player player) {
        Vec3 eye = player.getEyePosition();
        Vec3 look = player.getViewVector(1.0F);
        Vec3 end = eye.add(look.scale(CUT_REACH));
        AABB searchBox = player.getBoundingBox().expandTowards(look.scale(CUT_REACH)).inflate(1.0D);
        List<ItemEntity> candidates = level.getEntitiesOfClass(ItemEntity.class, searchBox, entity -> entity.isAlive() && !entity.getItem().isEmpty());
        return candidates.stream()
                .map(entity -> new EntityHitResult(entity, closestPointOnSegment(entity, eye, end)))
                .filter(hit -> hit.getType() != HitResult.Type.MISS)
                .filter(hit -> hit.getLocation().distanceToSqr(eye) <= CUT_REACH * CUT_REACH)
                .filter(hit -> isLookingAt(player, hit.getEntity()))
                .min(Comparator.comparingDouble(hit -> hit.getLocation().distanceToSqr(eye)))
                .map(hit -> (ItemEntity) hit.getEntity())
                .orElse(null);
    }

    private static Vec3 closestPointOnSegment(Entity entity, Vec3 start, Vec3 end) {
        AABB box = entity.getBoundingBox().inflate(0.35D);
        return box.clip(start, end).orElse(entity.position().add(0.0D, entity.getBbHeight() * 0.5D, 0.0D));
    }

    private static boolean isLookingAt(Player player, Entity entity) {
        Vec3 eye = player.getEyePosition();
        Vec3 toEntity = entity.position().add(0.0D, entity.getBbHeight() * 0.5D, 0.0D).subtract(eye).normalize();
        return player.getViewVector(1.0F).normalize().dot(toEntity) > 0.965D;
    }

    private static void spawnCutResult(Level level, ItemEntity source, ItemStack result) {
        if (result.isEmpty()) {
            return;
        }
        ItemEntity entity = new ItemEntity(level, source.getX(), source.getY(), source.getZ(), result.copy());
        entity.setDeltaMovement(source.getDeltaMovement());
        level.addFreshEntity(entity);
    }
}
