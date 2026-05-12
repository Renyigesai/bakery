package com.renyigesai.bakeries.common.mixin;

import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(MaceItem.class)
public abstract class MaceMixin extends Item {
    public MaceMixin(Properties properties) {
        super(properties);
    }
    @Inject(method = "knockback",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/level/Level;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;",shift = At.Shift.AFTER))
    private static void knockback(Level level, Player player, Entity entity, CallbackInfo ci){
        level.getEntitiesOfClass(ItemEntity.class,entity.getBoundingBox().inflate(3.5)).forEach(item -> {
            Item itemStack = bakeries1_21_1$getItems().get(item.getItem().getItem());
            if (itemStack != null){
                double x = item.getX();
                double y = item.getY();
                double z = item.getZ();
                int count = item.getItem().getCount();
                item.remove(Entity.RemovalReason.DISCARDED);
                ItemUtils.spawnItemEntity(level, new ItemStack(itemStack,count),x,y,z,Vec3.ZERO);
            }
        });
    }

    @Unique
    private static Map<Item,Item> bakeries1_21_1$getItems(){
        Map<Item,Item> items = new HashMap<>();
        items.put(Items.WHEAT,BakeriesItems.WHOLE_WHEAT_FLOUR.get());
        items.put(BakeriesItems.CROISSANT.get(),BakeriesItems.FLAT_CROISSANT.get());
        return items;
    }
}
