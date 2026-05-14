package com.renyigesai.bakeries.common.event;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.event.AnvilLandingEvent;
import com.renyigesai.bakeries.api.event.PlayerLookBlockEvent;
import com.renyigesai.bakeries.api.event.SnifferDropSeedEvent;
import com.renyigesai.bakeries.api.items.PileItem;
import com.renyigesai.bakeries.common.client.LookBlockEntityRegistries;
import com.renyigesai.bakeries.common.init.BakeriesAttributes;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.items.RepeatEatItem;
import com.renyigesai.bakeries.common.utils.ItemUtils;
import com.renyigesai.bakeries.common.utils.WorldUtils;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import vazkii.patchouli.common.item.PatchouliItems;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    @SubscribeEvent
    public static void createDefaultAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER,BakeriesAttributes.DOWN);
    }

    @SubscribeEvent
    public static void onUseVillager(PlayerInteractEvent.EntityInteract event) {
        Player entity = event.getEntity();
        if (event.getHand() != entity.getUsedItemHand())
            return;
        ItemStack hand = entity.getItemInHand(InteractionHand.MAIN_HAND);
        Entity target = event.getTarget();
        if (hand.is(BakeriesItems.ICED_LATTE.get()) && target instanceof Villager villager){
            if (event.isCanceled()) {
                event.setCanceled(true);
            }
            if (!villager.level().isClientSide()){
                villager.restock();
                villager.numberOfRestocksToday = 0;
            }
            RepeatEatItem.repeatEatItemHurt(entity,hand,new ItemStack(BakeriesItems.DRINK_CUP.get()));
            villager.level().playSound(null, BlockPos.containing(villager.getX(),villager.getY(),villager.getZ()), SoundEvents.GENERIC_DRINK, SoundSource.BLOCKS);
        }
    }

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
                    ItemUtils.givePlayerItem(entity, new ItemStack(BakeriesItems.WHOLE_EGG.get()));
                    if (!entity.getAbilities().instabuild) {
                        offhandItem.hurtAndBreak(1,entity, EquipmentSlot.OFFHAND);
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
            LookBlockEntityRegistries.setBlocks(player,blockEntity);
            return;
        }
        Map<UUID, BlockEntity> blocks = LookBlockEntityRegistries.getBlocks();
        if (blocks.get(player.getUUID()) != null){
            blocks.remove(player.getUUID());
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
                    if (!WorldUtils.isDoneAdvancement(player,level,ResourceLocation.fromNamespaceAndPath("bakeries","get_flat_croissant"))){
                        if (player instanceof ServerPlayer serverPlayer){
                            AdvancementHolder advancement = serverPlayer.server.getAdvancements().get(ResourceLocation.fromNamespaceAndPath("bakeries","get_flat_croissant"));
                            if (advancement != null) {
                                serverPlayer.getAdvancements().award(advancement, "witness_anvil_fall");
                            }
                        }
                    }
                });
                break;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player entity = event.getEntity();
        if (!ModList.get().isLoaded("patchouli")){
            if (!entity.level().isClientSide){
                entity.displayClientMessage(Component.translatable("tooltips.bakeries.player_logged_in"), false);
            }
        }
    }

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        event.addPackFinders(ResourceLocation.fromNamespaceAndPath("bakeries","resourcepacks/b_16x"),PackType.CLIENT_RESOURCES,Component.literal("Bakeries 16x Texture"),PackSource.DEFAULT,true,Pack.Position.TOP);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        ItemStack handItem = event.getItemStack();
        InteractionHand hand = event.getHand();
        if (level.isClientSide()){
            return;
        }
        if (!BakeriesMod.onAuxiliaryKey(player)){
            return;
        }
        if (!(handItem.getItem() instanceof PileItem pileItem)){
            return;
        }
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
        UseOnContext context = new UseOnContext(level, player, hand, handItem, event.getHitVec());
        InteractionResult result = pileItem.pileUseOn(context);
        if (result == InteractionResult.PASS) {

        }
    }

    @SubscribeEvent
    public static void onDropSeed(SnifferDropSeedEvent event){
        if (event.getLevel() instanceof ServerLevel serverLevel){
            BlockPos pos = event.getBlockPos();
            Holder<Biome> biomeHolder = event.getLevel().getBiome(pos);
            if (biomeHolder.is(BiomeTags.IS_JUNGLE)) {
                ItemEntity itemEntity = new ItemEntity(event.getLevel(), pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BakeriesItems.RAW_COFFEE_BEAN.get()));
                serverLevel.addFreshEntity(itemEntity);
            }
        }
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Register Oven
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BakeriesBlocks.Entities.OVEN_BLOCK_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getItemHandler()
        );

        // Register Blender
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BakeriesBlocks.Entities.BLENDER_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getCapabilitieHandler()
        );

        // Register Toaster
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BakeriesBlocks.Entities.TOASTER_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getItems()
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BakeriesBlocks.Entities.FERMENTATION_BOX_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getHandlerItems()
        );
    }
}
