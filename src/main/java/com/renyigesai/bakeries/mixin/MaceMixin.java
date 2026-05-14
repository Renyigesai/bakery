package com.renyigesai.bakeries.mixin;

import com.renyigesai.bakeries.init.BakeriesItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Pseudo
@Mixin(targets = "net.minecraft.world.item.MaceItem")
public abstract class MaceMixin {

    @Inject(method = "knockback", at = @At("TAIL"), remap = false)
    private static void bakeries$knockback(Level level, Player player, Entity entity, CallbackInfo ci) {
        level.getEntitiesOfClass(ItemEntity.class, entity.getBoundingBox().inflate(3.5)).forEach(itemEntity -> {
            Item mapped = bakeries$getItems().get(itemEntity.getItem().getItem());
            if (mapped == null) {
                return;
            }
            double x = itemEntity.getX();
            double y = itemEntity.getY();
            double z = itemEntity.getZ();
            int count = itemEntity.getItem().getCount();
            itemEntity.remove(Entity.RemovalReason.DISCARDED);
            ItemEntity spawned = new ItemEntity(level, x, y, z, new ItemStack(mapped, count));
            spawned.setDeltaMovement(Vec3.ZERO);
            level.addFreshEntity(spawned);
        });
    }

    @Unique
    private static Map<Item, Item> bakeries$getItems() {
        Map<Item, Item> items = new HashMap<>();
        items.put(Items.WHEAT, BakeriesItems.WHOLE_WHEAT_FLOUR);
        items.put(BakeriesItems.CROISSANT, BakeriesItems.FLAT_CROISSANT);
        return items;
    }
}
