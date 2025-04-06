package com.renyigesai.bakeries.mixin;

import com.renyigesai.bakeries.api.event.SnifferDropSeedEvent;
import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Sniffer.class)
public abstract class SnifferMixin extends Animal {


    @Shadow @Final private static EntityDataAccessor<Integer> DATA_DROP_SEED_AT_TICK;

    @Shadow protected abstract BlockPos getHeadBlock();

    protected SnifferMixin(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Inject(method = "dropSeed",at = @At(value = "INVOKE",target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z",shift = At.Shift.AFTER))
    private void dropSeed(CallbackInfo ci){
        Level level = this.level();
        BlockPos blockpos = this.getHeadBlock();
        SnifferDropSeedEvent snifferDropSeedEvent = new SnifferDropSeedEvent(level,blockpos);
        MinecraftForge.EVENT_BUS.post(snifferDropSeedEvent);
    }

//    @Inject(method = "dropSeed",at = @At("HEAD"))
//    private void dropSeed(CallbackInfo ci){
//        if (!this.level().isClientSide() && this.entityData.get(DATA_DROP_SEED_AT_TICK) == this.tickCount){
//            BlockPos blockpos = this.getHeadBlock();
//            Holder<Biome> biomeHolder = this.level().getBiome(blockpos);
//            ServerLevel serverlevel = (ServerLevel)this.level();
//            if (biomeHolder.is(BiomeTags.IS_JUNGLE)){
//                ItemEntity itemEntity = new ItemEntity(serverlevel, blockpos.getX(),blockpos.getY(),blockpos.getZ(),new ItemStack(BakeriesItems.RAW_COFFEE_BEAN.get()));
//                itemEntity.setDefaultPickUpDelay();
//                serverlevel.addFreshEntity(itemEntity);
//            }
//        }
//    }

}
