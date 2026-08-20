package com.renyigesai.bakeries.common.potion;

import com.renyigesai.bakeries.common.init.BakeriesMobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;
import java.util.Random;

public class BerrySourMobEffect extends MobEffect {
    public BerrySourMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -1619356);
    }

    @EventBusSubscriber
    public static class BerrySourPotionEffect{
        @SubscribeEvent
        public static void onBerrySour(LivingIncomingDamageEvent event){
            DamageContainer container = event.getContainer();
            Entity attacker = container.getSource().getEntity();
            Entity entity = event.getEntity();
            if (attacker instanceof LivingEntity living  && living.hasEffect(BakeriesMobEffects.BERRY_SOUR)){
                int amplifier = living.getEffect((BakeriesMobEffects.BERRY_SOUR)).getAmplifier();
                int realAmplifier = amplifier == 0 ? 1 : amplifier + 1;

                float modifying;
                /*如果生物在水中或者雨天，造成1.5倍伤害*/
                modifying = entity.isInWater() || entity.level().isRainingAt(entity.blockPosition()) ? 1.5f * realAmplifier: 0f;

                Random random = new Random();
                boolean flag = false;

                /*始终有百分之二十的概率触发二倍伤害，同时生成闪电束*/
                if (random.nextDouble() <= 0.2 * realAmplifier){
                    modifying = 2f * realAmplifier;
                    flag = true;
                }
                if (modifying > 0){
                    float amount = event.getAmount();
                    event.setAmount(amount * modifying);
                }

                if (attacker.level() instanceof ServerLevel serverLevel){
                    if (flag){
                        addLightningBolt(serverLevel,entity,attacker);
                    }
                    for (int i = 0; i < 16; i++) {
                        double xo = new Random().nextDouble(-0.75, 0.75);
                        double yo = new Random().nextDouble(-0.75, 0.75);
                        double zo = new Random().nextDouble(-0.75, 0.75);
                        serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, entity.getX() + xo, entity.getY(0.5) + yo, entity.getZ() + zo, 1,0, 0, 0,0.125);
                    }
                }
            }
        }

        /*此闪电束不造成伤害和火焰，但转换生物和除锈*/
        private static void addLightningBolt(ServerLevel serverLevel,Entity entity,Entity attacker){
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
            lightningBolt.setVisualOnly(true);
            lightningBolt.setPos(entity.getX(),entity.getY(),entity.getZ());
            serverLevel.addFreshEntity(lightningBolt);
            List<Entity> list1 = attacker.level().getEntities(lightningBolt, new AABB(lightningBolt.getX() - 3.0, lightningBolt.getY() - 3.0, lightningBolt.getZ() - 3.0, lightningBolt.getX() + 3.0, lightningBolt.getY() + 6.0 + 3.0, lightningBolt.getZ() + 3.0), Entity::isAlive);
            for (Entity entity1 : list1) {
                if (!EventHooks.onEntityStruckByLightning(entity1, lightningBolt)) {
                    entity1.thunderHit(serverLevel, lightningBolt);
                }
            }
        }
    }

}
