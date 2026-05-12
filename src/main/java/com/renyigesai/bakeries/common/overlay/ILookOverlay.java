package com.renyigesai.bakeries.common.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
@OnlyIn(Dist.CLIENT)
public interface ILookOverlay<T extends BlockEntity> {

    void create(RenderGuiEvent.Pre event, T entity, Player localPlayer, Minecraft mc);

    boolean isOverlay(T entity, Player localPlayer, Minecraft mc);

}
