package net.weibai.bakeries.data;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.weibai.bakeries.common.tags.BakeriesTagBuilder;
import net.weibai.bakeries.common.tags.IntrinsicBakeriesTagBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class AbstractTagProvider  implements DataProvider {

//    protected static final TagKey<EntityType<?>> PVI_COMPAT = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("per-viam-invenire", "replace_vanilla_navigator"));
//    private static final TagKey<Fluid> CREATE_NO_INFINITE_FLUID = FluidTags.create(ResourceLocation.fromNamespaceAndPath("create", "no_infinite_draining"));
//    protected static final TagKey<Block> FRAMEABLE = BlockTags.create(ResourceLocation.fromNamespaceAndPath("framedblocks", "frameable"));
//    protected static final TagKey<Block> FB_BE_WHITELIST = BlockTags.create(ResourceLocation.fromNamespaceAndPath("framedblocks", "blockentity_whitelisted"));

    private final Map<ResourceKey<? extends Registry<?>>, Map<TagKey<?>, TagBuilder>> supportedTagTypes = new Object2ObjectLinkedOpenHashMap<>();
    private final Set<Block> knownHarvestRequirements = new ReferenceOpenHashSet<>();
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final ExistingFileHelper existingFileHelper;
    private final PackOutput output;
    private final String modid;

    protected AbstractTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modid, @Nullable ExistingFileHelper existingFileHelper) {
        this.output = output;
        this.modid = modid;
        this.lookupProvider = lookupProvider;
        this.existingFileHelper = existingFileHelper;
    }

    @NotNull
    @Override
    public String getName() {
        return "Tags: " + modid;
    }

    protected abstract void registerTags(HolderLookup.Provider registries);

    protected Collection<? extends Holder<Block>> getAllBlocks() {
        return Collections.emptyList();
    }

    protected void hasHarvestData(Block block) {
        knownHarvestRequirements.add(block);
    }

    @NotNull
    @Override
    public CompletableFuture<?> run(@NotNull CachedOutput cache) {
        return this.lookupProvider.thenApply(registries -> {
            supportedTagTypes.values().forEach(Map::clear);
            registerTags(registries);
            return registries;
        }).thenCompose(registries -> {
            for (Holder<Block> blockProvider : getAllBlocks()) {
                Block block = blockProvider.value();
                if (block.defaultBlockState().requiresCorrectToolForDrops() && !knownHarvestRequirements.contains(block)) {
                    throw new IllegalStateException("Missing harvest tool type for block '" + BuiltInRegistries.BLOCK.getKey(block) + "' that requires the correct tool for drops.");
                }
            }
            List<CompletableFuture<?>> futures = new ArrayList<>();
            for (Map.Entry<ResourceKey<? extends Registry<?>>, Map<TagKey<?>, TagBuilder>> entry : supportedTagTypes.entrySet()) {
                Map<TagKey<?>, TagBuilder> tagTypeMap = entry.getValue();
                if (!tagTypeMap.isEmpty()) {
                    //Create a dummy provider and pass all our collected data through to it
                    futures.add(new TagsProvider(output, entry.getKey(), lookupProvider, modid, existingFileHelper) {
                        @Override
                        protected void addTags(@NotNull HolderLookup.Provider lookupProvider) {
                            //Add each tag builder to the wrapped provider's builder
                            for (Map.Entry<TagKey<?>, TagBuilder> e : tagTypeMap.entrySet()) {
                                builders.put(e.getKey().location(), e.getValue());
                            }
                        }
                    }.run(cache));
                }
            }
            return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        });
    }

    private <TYPE> Map<TagKey<?>, TagBuilder> getTagTypeMap(ResourceKey<? extends Registry<TYPE>> registry) {
        return supportedTagTypes.computeIfAbsent(registry, type -> new Object2ObjectLinkedOpenHashMap<>());
    }

    private <TYPE> TagBuilder getTagBuilder(ResourceKey<? extends Registry<TYPE>> registry, TagKey<TYPE> tag) {
        return getTagTypeMap(registry).computeIfAbsent(tag, ignored -> TagBuilder.create());
    }

    protected <TYPE> BakeriesTagBuilder<TYPE, ?> getBuilder(ResourceKey<? extends Registry<TYPE>> registry, TagKey<TYPE> tag) {
        return new BakeriesTagBuilder<>(getTagBuilder(registry, tag), modid);
    }

    protected <TYPE> IntrinsicBakeriesTagBuilder<TYPE> getBuilder(ResourceKey<? extends Registry<TYPE>> registry, Function<TYPE, ResourceKey<TYPE>> keyExtractor, TagKey<TYPE> tag) {
        return new IntrinsicBakeriesTagBuilder<>(keyExtractor, getTagBuilder(registry, tag), modid);
    }

    protected <TYPE> IntrinsicBakeriesTagBuilder<TYPE> getBuilder(Registry<TYPE> registry, TagKey<TYPE> tag) {
        return new IntrinsicBakeriesTagBuilder<>(element -> registry.getResourceKey(element).orElseThrow(), getTagBuilder(registry.key(), tag), modid);
    }

    protected IntrinsicBakeriesTagBuilder<Item> getItemBuilder(TagKey<Item> tag) {
        return getBuilder(BuiltInRegistries.ITEM, tag);
    }

    protected IntrinsicBakeriesTagBuilder<Block> getBlockBuilder(TagKey<Block> tag) {
        return getBuilder(BuiltInRegistries.BLOCK, tag);
    }

    protected IntrinsicBakeriesTagBuilder<EntityType<?>> getEntityTypeBuilder(TagKey<EntityType<?>> tag) {
        return getBuilder(BuiltInRegistries.ENTITY_TYPE, tag);
    }

    protected IntrinsicBakeriesTagBuilder<Fluid> getFluidBuilder(TagKey<Fluid> tag) {
        return getBuilder(BuiltInRegistries.FLUID, tag);
    }

    protected IntrinsicBakeriesTagBuilder<BlockEntityType<?>> getTileEntityTypeBuilder(TagKey<BlockEntityType<?>> tag) {
        return getBuilder(BuiltInRegistries.BLOCK_ENTITY_TYPE, tag);
    }

    protected BakeriesTagBuilder<GameEvent, ?> getGameEventBuilder(TagKey<GameEvent> tag) {
        return getBuilder(Registries.GAME_EVENT, tag);
    }

    protected BakeriesTagBuilder<DamageType, ?> getDamageTypeBuilder(TagKey<DamageType> tag) {
        return getBuilder(Registries.DAMAGE_TYPE, tag);
    }

    protected BakeriesTagBuilder<Biome, ?> getBiomeBuilder(TagKey<Biome> tag) {
        return getBuilder(Registries.BIOME, tag);
    }

//    protected IntrinsicBakeriesTagBuilder<Chemical> getChemicalBuilder(TagKey<Chemical> tag) {
//        return getBuilder(MekanismAPI.CHEMICAL_REGISTRY, tag);
//    }

    protected IntrinsicBakeriesTagBuilder<MobEffect> getMobEffectBuilder(TagKey<MobEffect> tag) {
        return getBuilder(BuiltInRegistries.MOB_EFFECT, tag);
    }

    protected void addToTag(TagKey<Item> tag, ItemLike... itemProviders) {
        getItemBuilder(tag).addTyped(ItemLike::asItem, itemProviders);
    }

    @SafeVarargs
    protected final void addToTag(TagKey<Block> tag, Holder<Block>... blockProviders) {
        getBlockBuilder(tag).addTyped(Holder::value, blockProviders);
    }

    @SafeVarargs
    protected final void addToTag(TagKey<Block> blockTag, Map<?, ? extends Holder<Block>>... blockProviders) {
        IntrinsicBakeriesTagBuilder<Block> tagBuilder = getBlockBuilder(blockTag);
        for (Map<?, ? extends Holder<Block>> blockProvider : blockProviders) {
            for (Holder<Block> value : blockProvider.values()) {
                tagBuilder.add(value.value());
            }
        }
    }

    @SafeVarargs
    protected final void addToHarvestTag(TagKey<Block> tag, Holder<Block>... blockProviders) {
        IntrinsicBakeriesTagBuilder<Block> tagBuilder = getBlockBuilder(tag);
        for (Holder<Block> blockProvider : blockProviders) {
            Block block = blockProvider.value();
            tagBuilder.add(block);
            hasHarvestData(block);
        }
    }

    @SafeVarargs
    protected final void addToHarvestTag(TagKey<Block> blockTag, Map<?, ? extends Holder<Block>>... blockProviders) {
        IntrinsicBakeriesTagBuilder<Block> tagBuilder = getBlockBuilder(blockTag);
        for (Map<?, ? extends Holder<Block>> blockProvider : blockProviders) {
            for (Holder<Block> value : blockProvider.values()) {
                Block block = value.value();
                tagBuilder.add(block);
                hasHarvestData(block);
            }
        }
    }
    @SafeVarargs
    protected final void addToTags(TagKey<Item> itemTag, TagKey<Block> blockTag, Holder<Block>... blockProviders) {
        IntrinsicBakeriesTagBuilder<Item> itemTagBuilder = getItemBuilder(itemTag);
        IntrinsicBakeriesTagBuilder<Block> blockTagBuilder = getBlockBuilder(blockTag);
        for (Holder<Block> blockProvider : blockProviders) {
            itemTagBuilder.add(blockProvider.value().asItem());
            blockTagBuilder.add(blockProvider.value());
        }
    }



    @SafeVarargs
    protected final void addToTag(TagKey<GameEvent> tag, DeferredHolder<GameEvent, GameEvent>... gameEventROs) {
        getGameEventBuilder(tag).add(DeferredHolder::getId, gameEventROs);
    }



    @SafeVarargs
    protected final void addEntitiesToTag(TagKey<EntityType<?>> tag, Holder<EntityType<?>>... entityTypeProviders) {
        getEntityTypeBuilder(tag).addTyped(Holder::value, entityTypeProviders);
    }

    @SafeVarargs
    protected final <TYPE> void addToTag(TagKey<TYPE> tag, ResourceKey<TYPE>... values) {
        final TagBuilder builder = getTagBuilder(tag.registry(), tag);
        for (ResourceKey<TYPE> value : values) {
            builder.addElement(value.location());
        }
    }

//    protected final void addToTag(IntrinsicBakeriesTagBuilder<Chemical> tagBuilder, IChemicalProvider... providers) {
//        tagBuilder.addTyped(IChemicalProvider::getChemical, providers);
//    }
}
