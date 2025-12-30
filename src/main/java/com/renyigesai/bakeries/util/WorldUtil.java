package com.renyigesai.bakeries.util;

import com.renyigesai.bakeries.recipe.CoffeeRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class WorldUtil {
    public static List<Entity> setSightEntityList(LevelAccessor world, Entity entity, int repeat, double wide){
        double direction = 0;
        double x = entity.getX();
        double y = entity.getY() + entity.getBbHeight()/1.35;
        double z = entity.getZ();
        double ex = entity.getLookAngle().x;
        double ey = entity.getLookAngle().y;
        double ez = entity.getLookAngle().z;
        List<Entity> entityList = new ArrayList<>();
        for (int i = 0; i < repeat*(1/wide) ; i++) {
            Vec3 vec3 = new Vec3((x + ex * direction),(y + ey * direction),z + ez * direction);
            List<Entity> tempEntityList = world.getEntitiesOfClass(Entity.class, new AABB(vec3, vec3).inflate(wide / 2d), e
                    -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(vec3))).toList();
            if (!tempEntityList.isEmpty()){
                entityList.addAll(tempEntityList);
            }
            direction += wide;
        }
        return entityList;
    }

    /*通过输入资源地址获取一个战利品表*/
    public static LootTable getLootTables(String name, Level world){
        if (!world.isClientSide() && world.getServer() != null) {
            return world.getServer().getLootData().getLootTable(new ResourceLocation(name));
        }
        return LootTable.lootTable().build();
    }
    /*从战利品表中获取物品列表*/
    public static List<ItemStack> getFromLootTableItemStack(LootTable lootTable, Level world, BlockPos pos){
        List<ItemStack> stacks = new ArrayList<>();
        if (!world.isClientSide() && world.getServer() != null){
            stacks.addAll(lootTable.getRandomItems(new LootParams.Builder((ServerLevel) world).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos)).withParameter(LootContextParams.BLOCK_STATE, world.getBlockState(pos)).withOptionalParameter(LootContextParams.BLOCK_ENTITY, world.getBlockEntity(pos)).create(LootContextParamSets.EMPTY)));
        }
        return stacks;
    }

    public static boolean isDoneAdvancement(Player player,Level level,ResourceLocation resourceLocation){
        if (player instanceof ServerPlayer serverPlayer && level instanceof ServerLevel) {
            return serverPlayer.getAdvancements().getOrStartProgress(serverPlayer.server.getAdvancements().getAdvancement(resourceLocation)).isDone();
        }
        return false;
    }

    public static BlockPos findFirstIn5x5Cube(Level level, BlockPos centerPos, Block targetBlock) {
        for (int dy = -2; dy <= 2; dy++) {
            for (int dx = -2; dx <= 2; dx++) {
                for (int dz = -2; dz <= 2; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) {
                        continue;
                    }
                    BlockPos checkPos = centerPos.offset(dx, dy, dz);
                    if (level.getBlockState(checkPos).is(targetBlock)) {
                        return checkPos;
                    }
                }
            }
        }
        return null;
    }

}
