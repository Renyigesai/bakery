package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.accessor.VillagerAccessor;
import com.renyigesai.bakeries.api.event.PlayerLookBlockEvent;
import com.renyigesai.bakeries.block.blender.BlenderBlockEntity;
import com.renyigesai.bakeries.block.cake.CakeProcessingInitialBlock;
import com.renyigesai.bakeries.block.cake.CakeProcessingInitialBlockEntity;
import com.renyigesai.bakeries.block.glass_drink_cup.GlassDrinkCupBlockEntity;
import com.renyigesai.bakeries.client.LookBlockEntityMap;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.item.RepeatEatItem;
import com.renyigesai.bakeries.util.ItemUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber
public class BakeriesEvents {
    @SubscribeEvent
    public static void onUseCreeper(PlayerInteractEvent.EntityInteract event){
        Player entity = event.getEntity();
        Entity target = event.getTarget();
        if (entity != null && entity.getItemInHand(InteractionHand.MAIN_HAND).is(BakeriesItems.BAGUETTE.get()) && target instanceof Creeper){
            Level level = event.getEntity().level();
            double x = target.getX();
            double y = target.getY();
            double z = target.getZ();
            ItemStack hand = entity.getItemInHand(InteractionHand.MAIN_HAND);
            if (((Creeper) target).getRandom().nextInt(3) == 0) {
                target.spawnAtLocation(new ItemStack(BakeriesItems.MUSIC_DISC_BAKING_IN_PROGRESS.get()));
            }
            RepeatEatItem.rHurt(hand);
            level.addParticle(new ItemParticleOption(ParticleTypes.ITEM,new ItemStack(Items.BREAD)),x,y+target.getBbHeight()/2,z,((double)level.random.nextFloat() - 0.5D) * 0.08D, ((double)level.random.nextFloat() - 0.5D) * 0.08D, ((double)level.random.nextFloat() - 0.5D) * 0.08D);
            level.playSound(null,new BlockPos((int) x,(int)y,(int)z),SoundEvents.GENERIC_EAT, SoundSource.PLAYERS);
        }
    }

    @SubscribeEvent
    public static void onUseVillager(PlayerInteractEvent.EntityInteract event) {
        Player entity = event.getEntity();
        if (event.getHand() != entity.getUsedItemHand())
            return;
        ItemStack hand = entity.getItemInHand(InteractionHand.MAIN_HAND);
        Entity target = event.getTarget();
        if (hand.is(BakeriesItems.ICED_LATTE.get()) && target instanceof Villager villager){
            if (event.isCancelable()) {
                event.setCanceled(true);
            }
            villager.restock();
            ((VillagerAccessor)villager).bakery$setNumberOfRestocksToday(0);
            RepeatEatItem.rHurt(entity,hand,new ItemStack(BakeriesItems.DRINK_CUP.get()));
            villager.level().playSound(null, BlockPos.containing(villager.getX(),villager.getY(),villager.getZ()), SoundEvents.GENERIC_DRINK, SoundSource.BLOCKS);
        }
    }

    @SubscribeEvent
    public static void onRightClickEgg(PlayerInteractEvent.RightClickItem event) {
        Player entity = event.getEntity();
        Level level = event.getLevel();
        if (entity == null || level == null)
            return;

            ItemStack mainHandItem = entity.getMainHandItem();
            ItemStack offhandItem = entity.getOffhandItem();
            if (offhandItem.is(BakeriesItems.BREAD_KNIFE.get()) && mainHandItem.is(Items.EGG)) {
                if (!level.isClientSide()) {
                    event.setCanceled(true);
                    mainHandItem.shrink(1);
                    ItemUtil.givePlayerItem(entity, new ItemStack(BakeriesItems.WHOLE_EGG.get()));
                }
                level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TURTLE_EGG_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F, false);
            }
    }

    @SubscribeEvent
    public static void onLookBlock(PlayerLookBlockEvent event){
        Level level = event.getPlayer().level();
        Player player = event.getPlayer();
        BlockState state = event.getBlockState();
        BlockPos blockPos = event.getBlockPos();
        if (state.getBlock() == BakeriesBlocks.DRINK_CUP.get()){
            GlassDrinkCupBlockEntity blockEntity = (GlassDrinkCupBlockEntity) level.getBlockEntity(blockPos);
            LookBlockEntityMap.setBlocks(player,blockEntity);
            return;
        }
        if (state.getBlock() == BakeriesBlocks.BLENDER.get()){
            BlenderBlockEntity blockEntity = (BlenderBlockEntity) level.getBlockEntity(blockPos);
            LookBlockEntityMap.setBlocks(player,blockEntity);
            return;
        }
        Map<UUID, BlockEntity> blocks = LookBlockEntityMap.getBlocks();
        if (blocks.get(player.getUUID()) != null){
            blocks.remove(player.getUUID());
        }
    }

    @SubscribeEvent
    public static void onUseCakeBlock(PlayerInteractEvent.RightClickBlock event){
        if (event.getHand() != event.getEntity().getUsedItemHand())
            return;
        BlockState blockState = event.getLevel().getBlockState(event.getPos());
        ItemStack hand = event.getEntity().getItemInHand(event.getHand());
        boolean flag;
        if (blockState.is(BakeriesBlocks.CUT_CAKE_BASE.get()) && !hand.isEmpty()){
            BlockEntity blockEntity = event.getLevel().getBlockEntity(event.getPos());
            if (blockEntity instanceof CakeProcessingInitialBlockEntity cake && event.getEntity().isShiftKeyDown()){
                flag = hand.getItem().isEdible() && !(hand.getItem() instanceof BlockItem) && !hand.hasCraftingRemainingItem() && !hand.is(BakeriesItems.CAKE_ROLL.get());
                if (flag){
                    if (!event.getLevel().isClientSide) {
                        event.setCanceled(true);
                    }
                    event.setCancellationResult(InteractionResult.sidedSuccess(cake.addItem(hand)));
                }
            }

        }
    }
}
