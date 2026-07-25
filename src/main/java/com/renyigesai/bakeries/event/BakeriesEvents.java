package com.renyigesai.bakeries.event;

import com.renyigesai.bakeries.api.event.AnvilLandingEvent;
import com.renyigesai.bakeries.api.event.CakeEffectRulesRegistrationEvent;
import com.renyigesai.bakeries.api.event.PlayerLookBlockEvent;
import com.renyigesai.bakeries.block.custom_cake.CakePartData;
import com.renyigesai.bakeries.client.LookBlockEntityRegistries;
import com.renyigesai.bakeries.config.BakeriesConfig;
import com.renyigesai.bakeries.init.BakeriesItems;
import com.renyigesai.bakeries.item.BaguetteItem;
import com.renyigesai.bakeries.item.BreadKnifeItem;
import com.renyigesai.bakeries.item.ColdDrinkItem;
import com.renyigesai.bakeries.network.CakePartTypeSyncS2CPacket;
import com.renyigesai.bakeries.network.Messages;
import com.renyigesai.bakeries.util.ItemUtils;
import com.renyigesai.bakeries.util.WorldUtil;
import com.renyigesai.bakeries.util.measurer.CakeEffectRules;
import com.renyigesai.bakeries.util.measurer.CakePartMeasurer;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Function;

@Mod.EventBusSubscriber
public class BakeriesEvents {

    @SubscribeEvent
    public static void onUseCreeper(PlayerInteractEvent.EntityInteract event){
        Player entity = event.getEntity();
        Entity target = event.getTarget();
        if (entity != null && entity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BaguetteItem baguetteItem && target instanceof Creeper){
            Level level = event.getEntity().level();
            double x = target.getX();
            double y = target.getY();
            double z = target.getZ();
            ItemStack hand = entity.getItemInHand(InteractionHand.MAIN_HAND);
            if (((Creeper) target).getRandom().nextInt(3) == 0) {
                target.spawnAtLocation(new ItemStack(BakeriesItems.MUSIC_DISC_BAKING_IN_PROGRESS.get()));
            }
            baguetteItem.consume(hand,null);
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
        if (hand.getItem() instanceof ColdDrinkItem drinkItem && target instanceof Villager villager){
            if (event.isCancelable()) {
                event.setCanceled(true);
            }
            villager.restock();
            villager.numberOfRestocksToday = 0;
            ItemUtils.givePlayerItem(entity,drinkItem.consume(hand,null));
            villager.level().playSound(null, BlockPos.containing(villager.getX(),villager.getY(),villager.getZ()), SoundEvents.GENERIC_DRINK, SoundSource.BLOCKS);
        }
    }

    @SubscribeEvent
    public static void onRightClickEgg(PlayerInteractEvent.RightClickItem event) {
        Player entity = event.getEntity();
        Level level = event.getLevel();
        if (entity == null || level == null) {
            return;
        }
            ItemStack mainHandItem = entity.getMainHandItem();
            ItemStack offhandItem = entity.getOffhandItem();
            if (offhandItem.getItem() instanceof BreadKnifeItem && mainHandItem.is(Items.EGG)) {
                if (!level.isClientSide()) {
                    event.setCanceled(true);
                    mainHandItem.shrink(1);
                    ItemUtils.givePlayerItem(entity, new ItemStack(BakeriesItems.WHOLE_EGG.get()));
                    if (!entity.getAbilities().instabuild) {
                        offhandItem.hurtAndBreak(1,entity, (p_41300_) -> p_41300_.broadcastBreakEvent(entity.getUsedItemHand()));
                    }
                }
                level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TURTLE_EGG_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F, false);
            }
    }

    @SubscribeEvent
    public static void onLookBlock(PlayerLookBlockEvent event){
        Level level = event.getPlayer().level();
        Player player = event.getPlayer();
        BlockPos blockPos = event.getBlockPos();
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity != null){
            LookBlockEntityRegistries.setBlocks(player,blockPos);
        }else {
            LookBlockEntityRegistries.removeBlocks(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!BakeriesConfig.provideTutorialBooks){
            return;
        }

        Player entity = event.getEntity();
        if (!ModList.get().isLoaded("patchouli")){
            if (!entity.level().isClientSide){
                entity.displayClientMessage(Component.translatable("tip.bakeries.player_logged_in"), false);
                return;
            }
        }
        boolean root = WorldUtil.isDoneAdvancement(entity,entity.level(),new ResourceLocation("bakeries","root"));
        if (!root){
            LootTable lootTables = WorldUtil.getLootTables("grant_patchi_book", entity.level(),"bakeries");
            List<ItemStack> fromLootTableItemStack = WorldUtil.getFromLootTableItemStack(lootTables, entity.level(), entity.getOnPos());
            for (ItemStack itemStack : fromLootTableItemStack) {
                ItemUtils.givePlayerItem(entity, itemStack);
            }
        }
    }

    @SubscribeEvent
    public static void onAnvilLanding(AnvilLandingEvent event){
        Entity entity = event.getEntity();
        Level level = event.getLevel();
        List<Entity> entities = level.getEntities(entity, entity.getBoundingBox(), (e) -> e instanceof ItemEntity);
        for (Entity item : entities) {
            if (item instanceof ItemEntity itemEntity && itemEntity.getItem().is(BakeriesItems.CROISSANT.get())){
                double x = itemEntity.getX();
                double y = itemEntity.getY();
                double z = itemEntity.getZ();
                if (itemEntity.getItem().getCount() == 1){
                    itemEntity.remove(Entity.RemovalReason.DISCARDED);
                }else {
                    ItemStack itemStack = new ItemStack(BakeriesItems.CROISSANT.get());
                    itemStack.setCount(itemEntity.getItem().getCount()-1);
                    itemEntity.setItem(itemStack);
                }
                level.addFreshEntity(new ItemEntity(level,x,y,z,new ItemStack(BakeriesItems.FLAT_CROISSANT.get())));
                List<Player> players = level.getEntitiesOfClass(Player.class,entity.getBoundingBox().inflate(6));
                players.forEach(player -> {
                    if (!WorldUtil.isDoneAdvancement(player,level,new ResourceLocation("bakeries","get_flat_croissant_null"))){
                        if (player instanceof ServerPlayer serverPlayer){
                            Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("bakeries","get_flat_croissant"));
                            if (advancement != null) {
                                serverPlayer.getAdvancements().award(advancement,"witness_anvil_fall");
                            }
                        }
                    }
                });
                break;
            }
        }
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        CakePartMeasurer.cakePartMeasurerBuilder();
        initCakeEffectRules();
    }

    private static void initCakeEffectRules(){

        CakeEffectRulesRegistrationEvent event = new CakeEffectRulesRegistrationEvent();
        event.registerRule(CakeEffectRules::tooDisgusting);
        event.registerRule(CakeEffectRules::amplification);
        MinecraftForge.EVENT_BUS.post(event);

        for (Function<List<MobEffectInstance>, List<MobEffectInstance>> rule : event.getRules()) {
            CakeEffectRules.registerRule(rule);
        }
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event){
        CakePartMeasurer.loadAllClientPartsType(event);
    }

}
