package com.renyigesai.bakeries.potion;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TeaAstringentMobEffect extends MobEffect {
    public TeaAstringentMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -10769635);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        double x = pLivingEntity.getX();
        double y = pLivingEntity.getY();
        double z = pLivingEntity.getZ();
        double range = 3.5;
        List<Entity> entities = pLivingEntity.level().getEntities(pLivingEntity, new AABB(x - range, y - range, z - range, x + range, y + range, z + range), Entity::isAlive);
        RandomSource random = pLivingEntity.level().random;
        for (Entity entity : entities){
            if (entity == pLivingEntity){
                continue;
            }
            if (entity instanceof ItemEntity){
                continue;
            }
            if (entity instanceof Projectile projectile && projectile.getOwner() == pLivingEntity){
                continue;
            }
            Vec3 motion = entity.getDeltaMovement();
            double horizontalSpeed = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
            if (!(horizontalSpeed < 0.01)){
                entity.setDeltaMovement(motion.x * 0.5,motion.y,motion.z * 0.5);
                if (pLivingEntity.level() instanceof ServerLevel serverLevel){
                    double $$4 = entity.getX();
                    double $$5 = entity.getY();
                    double $$6 = entity.getZ();
                    double $$7 = $$4 + random.nextDouble();
                    double $$8 = $$5 + 0.7;
                    double $$9 = $$6 + random.nextDouble();
                    serverLevel.sendParticles(ParticleTypes.FALLING_SPORE_BLOSSOM,$$7,$$8,$$9,1,0,0,0,0.25);
                }
            }
        }
    }
}
