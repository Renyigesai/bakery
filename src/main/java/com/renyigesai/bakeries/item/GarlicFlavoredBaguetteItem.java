package com.renyigesai.bakeries.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class GarlicFlavoredBaguetteItem extends BaguetteItem{
    public GarlicFlavoredBaguetteItem(Block block, IntegerProperty integerProperty, Properties pProperties, boolean effectTooltip, boolean customField) {
        super(block, integerProperty, pProperties, effectTooltip, customField);
    }
    public GarlicFlavoredBaguetteItem(Block pBlock, Properties properties) {
        super(pBlock, properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        List<LivingEntity> entitys = getEntitys(pLivingEntity, pLevel);
        for (LivingEntity living : entitys){
            if (living.getMobType() == MobType.UNDEAD){
                living.hurt(pLivingEntity.damageSources().generic(),5f);
                if (pLevel.isClientSide) {
                    makePoofParticles(living, pLevel);
                }
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    private void makePoofParticles(LivingEntity living, Level level) {
        for(int i = 0; i < 3; ++i) {
            double d0 = living.getRandom().nextGaussian() * 0.02D;
            double d1 = living.getRandom().nextGaussian() * 0.02D;
            double d2 = living.getRandom().nextGaussian() * 0.02D;
            level.addParticle(ParticleTypes.POOF, living.getRandomX(1.0D), living.getRandomY(), living.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    public static List<LivingEntity> getEntitys(Entity entity, Level level){
        if (entity == null){
            return null;
        }
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        final Vec3 _center = new Vec3(x, y, z);
        return level.getEntitiesOfClass(LivingEntity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
    }
}
