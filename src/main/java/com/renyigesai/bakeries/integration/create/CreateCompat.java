package com.renyigesai.bakeries.integration.create;

import com.renyigesai.bakeries.common.items.CustomCakeItem;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class CreateCompat {

    public static void onUseBlazeBurner(PlayerInteractEvent.RightClickBlock event){
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        ItemStack itemInHand = player.getItemInHand(event.getHand());
        BlockState blockState = level.getBlockState(pos);
        Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create","blaze_burner"));
        if (CustomCakeItem.contains(itemInHand,"bakeries:blaze_cake") && blockState.is(block)){
            event.setCanceled(true);
            Item cake = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("create", "blaze_cake"));
            boolean doNotConsume = player.isCreative();
            boolean forceOverflow = !(player instanceof FakePlayer);
            InteractionResultHolder<ItemStack> res = BlazeBurnerBlock.tryInsert(blockState, level, pos, new ItemStack(cake), doNotConsume, forceOverflow, false);
            if (res.getResult() != InteractionResult.FAIL){
                ((CustomCakeItem)itemInHand.getItem()).consume(itemInHand,player);
            }
            event.setCancellationResult(res.getResult() == InteractionResult.SUCCESS ? InteractionResult.SUCCESS : InteractionResult.PASS);
        }
    }

}
