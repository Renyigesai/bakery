package com.renyigesai.bakeries.integration.ponder;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BakeriesPonderPlugin implements PonderPlugin {

    public static final ResourceLocation BAKERIES_TAG = ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "bakeries_equipment");

    @Override
    public String getModId() {
        return BakeriesMod.MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<DeferredHolder<?, ?>> HELPER = helper.withKeyFunction(DeferredHolder::getId);

        HELPER.forComponents(
                BakeriesItems.FERMENTATION_TANK,
                BakeriesBlocks.YEAST_TANK,
                BakeriesBlocks.CHEESE_TANK
        ).addStoryBoard(
                "fermentation_tank",
                BakeriesPonderScenes::yeastTankScene,
                BAKERIES_TAG
        ).addStoryBoard("fermentation_tank",
                BakeriesPonderScenes::cheeseTankScene,
                BAKERIES_TAG)
        ;

        HELPER.forComponents(
                BakeriesItems.OVEN
        ).addStoryBoard(
                "auto_baking",
                BakeriesPonderScenes::autoBakingScene,
                BAKERIES_TAG
        );
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<DeferredHolder<?, ?>> HELPER = helper.withKeyFunction(DeferredHolder::getId);
        helper.registerTag(BAKERIES_TAG)
                .addToIndex()
                .item(BakeriesBlocks.OVEN.get(), true, false)
                .title("Bakery Equipment")
                .description("Specialized equipment for flour processing, fermentation, and cheese production.")
                .register();

        HELPER.addToTag(BAKERIES_TAG)
                .add(BakeriesBlocks.FERMENTATION_TANK)
                .add(BakeriesBlocks.YEAST_TANK)
                .add(BakeriesBlocks.CHEESE_TANK)
                .add(BakeriesBlocks.OVEN);
    }

}