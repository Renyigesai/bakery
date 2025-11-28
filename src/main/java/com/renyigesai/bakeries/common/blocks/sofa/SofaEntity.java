package com.renyigesai.bakeries.common.blocks.sofa;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;

public class SofaEntity extends Entity implements IEntityWithComplexSpawn {
    public SofaEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void setPos(double x, double y, double z) {
        super.setPos(x, y, z);
        AABB bb = getBoundingBox();
        Vec3 diff = new Vec3(x, y, z).subtract(bb.getCenter());
        setBoundingBox(bb.move(diff));
    }

    @Override
    protected void positionRider(Entity pEntity, MoveFunction pCallback) {
        if (!this.hasPassenger(pEntity))
            return;
        double heightOffset = this.getPassengerRidingPosition(pEntity).y - pEntity.getVehicleAttachmentPoint(this).y;
        pCallback.accept(pEntity, this.getX(), heightOffset - 0.0625d, this.getZ());
    }

    @Override
    public void setDeltaMovement(Vec3 p_213317_1_) {}

    @Override
    public void tick() {
        if (level().isClientSide)
            return;
        boolean blockPresent = level().getBlockState(blockPosition()).getBlock() instanceof SofaBlock;
        if (isVehicle() && blockPresent)
            return;
        this.discard();
    }

    @Override
    protected boolean canRide(Entity entity) {
        return !(entity instanceof FakePlayer);
    }

    @Override
    protected void removePassenger(Entity entity) {
        super.removePassenger(entity);
        if (entity instanceof TamableAnimal ta)
            ta.setInSittingPose(false);
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        return super.getDismountLocationForPassenger(pLivingEntity).add(0, 0.5f, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_70037_1_) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag p_213281_1_) {}

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf registryFriendlyByteBuf) {

    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf registryFriendlyByteBuf) {

    }

    public static class SofaEntityRender extends EntityRenderer<SofaEntity> {

        public SofaEntityRender(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public boolean shouldRender(SofaEntity p_225626_1_, Frustum p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
            return false;
        }

        @Override
        public ResourceLocation getTextureLocation(SofaEntity p_110775_1_) {
            return null;
        }
    }
}
