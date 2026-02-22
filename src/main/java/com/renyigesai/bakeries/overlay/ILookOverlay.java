package com.renyigesai.bakeries.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.event.RenderGuiEvent;

public interface ILookOverlay<T extends BlockEntity> {

    /**创建覆盖层的方法，由子类实现*/
    void create(RenderGuiEvent.Pre event, T entity, Player localPlayer, Minecraft mc);

    /**判断覆盖层是否显示，始终显示直接返回true*/
    boolean isOverlay(T entity, Player localPlayer, Minecraft mc);

}
