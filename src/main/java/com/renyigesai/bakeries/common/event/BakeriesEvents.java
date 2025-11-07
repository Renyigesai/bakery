package com.renyigesai.bakeries.common.event;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.utils.ItemUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = BakeriesMod.MODID)
public class BakeriesEvents {
//    @SubscribeEvent
//    public static void onUseCreeper(PlayerInteractEvent.EntityInteract event){
//        Player entity = event.getEntity();
//        Entity target = event.getTarget();
//        if (entity != null && entity.getItemInHand(InteractionHand.MAIN_HAND).is(BakeriesItems.BAGUETTE.get()) && target instanceof Creeper){
//            Level level = event.getEntity().level();
//            double x = target.getX();
//            double y = target.getY();
//            double z = target.getZ();
//            ItemStack hand = entity.getItemInHand(InteractionHand.MAIN_HAND);
//            if (((Creeper) target).getRandom().nextInt(3) == 0) {
//                target.spawnAtLocation(new ItemStack(BakeriesItems.MUSIC_DISC_BAKING_IN_PROGRESS.get()));
//            }
//            RepeatEatItem.rHurt(hand);
//            level.addParticle(new ItemParticleOption(ParticleTypes.ITEM,new ItemStack(Items.BREAD)),x,y+target.getBbHeight()/2,z,((double)level.random.nextFloat() - 0.5D) * 0.08D, ((double)level.random.nextFloat() - 0.5D) * 0.08D, ((double)level.random.nextFloat() - 0.5D) * 0.08D);
//            level.playSound(null,new BlockPos((int) x,(int)y,(int)z),SoundEvents.GENERIC_EAT, SoundSource.PLAYERS);
//        }
//    }

//    @SubscribeEvent
//    public static void onUseVillager(PlayerInteractEvent.EntityInteract event) {
//        Player entity = event.getEntity();
//        if (event.getHand() != entity.getUsedItemHand())
//            return;
//        ItemStack hand = entity.getItemInHand(InteractionHand.MAIN_HAND);
//        Entity target = event.getTarget();
//        if (hand.is(BakeriesItems.ICED_LATTE.get()) && target instanceof Villager villager){
//            if (event.isCancelable()) {
//                event.setCanceled(true);
//            }
//            villager.restock();
//            ((VillagerAccessor)villager).bakery$setNumberOfRestocksToday(0);
//            RepeatEatItem.rHurt(entity,hand,new ItemStack(BakeriesItems.DRINK_CUP.get()));
//            villager.level().playSound(null, BlockPos.containing(villager.getX(),villager.getY(),villager.getZ()), SoundEvents.GENERIC_DRINK, SoundSource.BLOCKS);
//        }
//    }

    @SubscribeEvent
    public static void onRightClickEgg(PlayerInteractEvent.RightClickItem event) {
        Player entity = event.getEntity();
        Level level = event.getLevel();
            ItemStack mainHandItem = entity.getMainHandItem();
            ItemStack offhandItem = entity.getOffhandItem();
            if (offhandItem.is(BakeriesItems.BREAD_KNIFE.get()) && mainHandItem.is(Items.EGG)) {
                if (!level.isClientSide()) {
                    event.setCanceled(true);
                    mainHandItem.shrink(1);
                    ItemUtil.givePlayerItem(entity, new ItemStack(BakeriesItems.WHOLE_EGG.get()));
                    if (!entity.getAbilities().instabuild) {
                        offhandItem.hurtAndBreak(1,entity, EquipmentSlot.OFFHAND);
                    }
                }
                level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TURTLE_EGG_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F, false);
            }
    }

//    @SubscribeEvent
//    public static void onLookBlock(PlayerLookBlockEvent event){
//        Level level = event.getPlayer().level();
//        Player player = event.getPlayer();
//        BlockState state = event.getBlockState();
//        BlockPos blockPos = event.getBlockPos();
//        if (state.getBlock() == BakeriesBlocks.DRINK_CUP.get()){
//            GlassDrinkCupBlockEntity blockEntity = (GlassDrinkCupBlockEntity) level.getBlockEntity(blockPos);
//            LookBlockEntityMap.setBlocks(player,blockEntity);
//            return;
//        }
//        if (state.getBlock() == BakeriesBlocks.STONE_KILN.get()){
//            StoneKilnBlockEntity blockEntity = (StoneKilnBlockEntity) level.getBlockEntity(blockPos);
//            LookBlockEntityMap.setBlocks(player,blockEntity);
//            return;
//        }
//        Map<UUID, BlockEntity> blocks = LookBlockEntityMap.getBlocks();
//        if (blocks.get(player.getUUID()) != null){
//            blocks.remove(player.getUUID());
//        }
//    }

//    @SubscribeEvent
//    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
//        if (!BakeriesConfig.provideTutorialBooks){
//            return;
//        }
//        Player entity = event.getEntity();
//        if (!ModList.get().isLoaded("patchouli")){
//            if (!entity.level().isClientSide){
//                entity.displayClientMessage(Component.translatable("tip.bakeries.player_logged_in"), false);
//                return;
//            }
//        }
//        boolean b1 = WorldUtil.isDoneAdvancement(entity,entity.level(),new ResourceLocation("bakeries:root"));
//        if (!b1){
//            LootTable lootTables = WorldUtil.getLootTables("bakeries:grant_patchi_book", entity.level());
//            List<ItemStack> fromLootTableItemStack = WorldUtil.getFromLootTableItemStack(lootTables, entity.level(), entity.getOnPos());
//            for (ItemStack itemStack : fromLootTableItemStack) {
//                ItemUtil.givePlayerItem(entity, itemStack);
//            }
//        }
//    }
}
