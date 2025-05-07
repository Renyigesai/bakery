package com.renyigesai.bakeries.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
}
