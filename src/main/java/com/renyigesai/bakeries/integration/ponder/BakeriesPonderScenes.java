package com.renyigesai.bakeries.integration.ponder;

import com.renyigesai.bakeries.common.blocks.FermentationTankBlock;
import com.renyigesai.bakeries.common.blocks.YeastTankBlock;
import com.renyigesai.bakeries.common.blocks.oven.OvenBlock;
import com.renyigesai.bakeries.common.init.BakeriesBlocks;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class BakeriesPonderScenes {

    public static void yeastTankScene(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("fermentation_tank_interaction", "Using the Fermentation Tank");
        scene.configureBasePlate(0, 0, 3);
        scene.showBasePlate();
        scene.idle(10);

        BlockPos center = util.grid().at(1, 1, 1);
        scene.world().showSection(util.select().layer(1), Direction.DOWN);
        scene.idle(20);

        Vec3 topNode = util.vector().topOf(center);
        Vec3 textNode = util.vector().blockSurface(center, Direction.WEST);

        scene.overlay().showText(60)
                .placeNearTarget()
                .text("Add 3 scoops of Whole Wheat Flour")
                .pointAt(textNode);

        for (int i = 1; i <= 3; i++) {
            scene.overlay().showControls(topNode, Pointing.DOWN, 30)
                    .rightClick()
                    .withItem(new ItemStack(BakeriesItems.WHOLE_WHEAT_FLOUR.get()));

            final int flourCount = i;
            scene.world().modifyBlock(center, state -> state.setValue(FermentationTankBlock.FLOUR, flourCount), false);

            scene.idle(40);
        }

        scene.overlay().showText(40)
                .placeNearTarget()
                .text("Add water to begin fermentation")
                .pointAt(textNode);

        scene.overlay().showControls(topNode, Pointing.DOWN, 30)
                .rightClick()
                .withItem(new ItemStack(Items.POTION));

        scene.world().modifyBlock(center, state -> state.setValue(FermentationTankBlock.WATER, true), false);
        scene.idle(40);

        scene.overlay().showText(60)
                .independent(40)
                .text("When full of flour and water, it will eventually ferment into yeast");

        for(int i = 0; i < 20; i++) {
            scene.effects().emitParticles(topNode.add(0, 0.2, 0), scene.effects().simpleParticleEmitter(net.minecraft.core.particles.ParticleTypes.EFFECT, Vec3.ZERO), 1, 1);
            scene.idle(2);
        }

        scene.world().setBlock(center, BakeriesBlocks.YEAST_TANK.get().defaultBlockState(), true);
        scene.idle(20);

        scene.overlay().showText(80)
                .placeNearTarget()
                .text("Yeast can be extracted multiple times using glass bottles")
                .pointAt(textNode);

        scene.overlay().showControls(topNode, Pointing.DOWN, 30)
                .rightClick()
                .withItem(new ItemStack(Items.GLASS_BOTTLE));
        scene.idle(15);
        scene.world().modifyBlock(center, state -> state.setValue(YeastTankBlock.YEAST, 2), false);
        scene.idle(25);

        scene.overlay().showControls(topNode, Pointing.DOWN, 30)
                .rightClick()
                .withItem(new ItemStack(Items.GLASS_BOTTLE));
        scene.idle(15);
        scene.world().modifyBlock(center, state -> state.setValue(YeastTankBlock.YEAST, 1), false);
        scene.idle(25);

        scene.overlay().showControls(topNode, Pointing.DOWN, 30)
                .rightClick()
                .withItem(new ItemStack(Items.GLASS_BOTTLE));
        scene.idle(15);
        scene.world().setBlock(center, BakeriesBlocks.FERMENTATION_TANK.get().defaultBlockState(), false);
        scene.idle(20);

        scene.markAsFinished();
    }

    public static void cheeseTankScene(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("cheese_tank_interaction", "Making Cheese in the Fermentation Tank");
        scene.configureBasePlate(0, 0, 3);
        scene.showBasePlate();
        scene.idle(10);

        BlockPos center = util.grid().at(1, 1, 1);
        scene.world().showSection(util.select().layer(1), Direction.DOWN);
        scene.idle(20);

        Vec3 topNode = util.vector().topOf(center);
        Vec3 textNode = util.vector().blockSurface(center, Direction.WEST);

        scene.overlay().showText(60)
                .placeNearTarget()
                .text("If you add milk and salt instead...")
                .pointAt(textNode);

        scene.overlay().showControls(topNode, Pointing.DOWN, 30)
                .rightClick()
                .withItem(new ItemStack(Items.MILK_BUCKET));
        scene.idle(10);
        scene.world().setBlock(center, BakeriesBlocks.MILk_TANK.get().defaultBlockState(), true);
        scene.idle(30);

        scene.overlay().showControls(topNode, Pointing.DOWN, 30)
                .rightClick()
                .withItem(new ItemStack(BakeriesItems.SALT.get()));
        scene.idle(40);

        scene.overlay().showText(60)
                .independent(40)
                .text("The mixture will solidify into a batch of cheese");

        for(int i = 0; i < 20; i++) {
            scene.effects().emitParticles(topNode.add(0, 0.2, 0), scene.effects().simpleParticleEmitter(net.minecraft.core.particles.ParticleTypes.EFFECT, Vec3.ZERO), 1, 1);
            scene.idle(2);
        }

        scene.world().setBlock(center, BakeriesBlocks.CHEESE_TANK.get().defaultBlockState(), true);
        scene.idle(20);

        scene.overlay().showText(60)
                .placeNearTarget()
                .text("Simply use an empty hand to collect the finished cheese")
                .pointAt(textNode);

        scene.overlay().showControls(topNode, Pointing.DOWN, 30)
                .rightClick();
        scene.idle(15);

        scene.world().setBlock(center, BakeriesBlocks.FERMENTATION_TANK.get().defaultBlockState(), false);
        scene.idle(25);

        scene.markAsFinished();
    }

    public static void autoBakingScene(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("auto_baking_line", "Automated Baking Production Line");
        scene.configureBasePlate(0, 0, 7);

        scene.rotateCameraY(90);
        scene.showBasePlate();
        scene.idle(10);

        scene.world().showSection(util.select().layer(1), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().layer(2), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().layer(3), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().layer(4), Direction.DOWN);
        scene.idle(20);

        BlockPos basinPos = util.grid().at(6, 2, 2);
        Vec3 dropPos = util.vector().centerOf(basinPos.above(2));
        Vec3 downwardMotion = new Vec3(0, -0.15, 0);

        scene.overlay().showText(50)
                .placeNearTarget()
                .text("Drop ingredients directly into the Basin")
                .pointAt(util.vector().topOf(basinPos));
        scene.idle(30);

        ElementLink<EntityElement> saltItem = scene.world().createItemEntity(dropPos, downwardMotion, new ItemStack(BakeriesItems.SALT.get(), 4));
        scene.idle(12);
        scene.world().modifyEntity(saltItem, Entity::discard);
        scene.effects().indicateSuccess(basinPos);
        scene.idle(10);

        ElementLink<EntityElement> yeastItem = scene.world().createItemEntity(dropPos, downwardMotion, new ItemStack(BakeriesItems.BOTTLE_YEAST.get(), 4));
        scene.idle(12);
        scene.world().modifyEntity(yeastItem, Entity::discard);
        scene.effects().indicateSuccess(basinPos);
        scene.idle(10);

        ElementLink<EntityElement> flourItem = scene.world().createItemEntity(dropPos, downwardMotion, new ItemStack(BakeriesItems.FLOUR.get(), 16));
        scene.idle(12);
        scene.world().modifyEntity(flourItem, Entity::discard);
        scene.effects().indicateSuccess(basinPos);
        scene.idle(20);

        scene.world().modifyBlockEntity(basinPos, net.minecraft.world.level.block.entity.BlockEntity.class, be -> {
            if (be.getLevel() != null) {
                var handler = be.getLevel().getCapability(net.neoforged.neoforge.capabilities.Capabilities.ItemHandler.BLOCK, basinPos, null);
                if (handler != null) {
                    handler.insertItem(0, new ItemStack(BakeriesItems.SALT.get(), 4), false);
                    handler.insertItem(1, new ItemStack(BakeriesItems.BOTTLE_YEAST.get(), 4), false);
                    handler.insertItem(2, new ItemStack(BakeriesItems.FLOUR.get(), 16), false);
                }
            }
        });

        BlockPos mixerPos = util.grid().at(6, 4, 2);
        scene.world().modifyBlockEntityNBT(util.select().position(mixerPos), net.minecraft.world.level.block.entity.BlockEntity.class, nbt -> {
            nbt.putBoolean("Running", true);
        });

        scene.overlay().showText(60)
                .independent(40)
                .text("The Mechanical Mixer processes the mixture using blender recipes");

        scene.rotateCameraY(-90);
        scene.idle(80);

        scene.world().modifyBlockEntity(basinPos, net.minecraft.world.level.block.entity.BlockEntity.class, be -> {
            if (be.getLevel() != null) {
                var handler = be.getLevel().getCapability(net.neoforged.neoforge.capabilities.Capabilities.ItemHandler.BLOCK, basinPos, null);
                if (handler != null) {
                    handler.extractItem(0, 64, false);
                    handler.extractItem(1, 64, false);
                    handler.extractItem(2, 64, false);
                    handler.insertItem(0, new ItemStack(BakeriesItems.COUNTRY_BREAD_DOUGH.get(), 4), false);
                }
            }
        });

        scene.addKeyframe();
        BlockPos beltStart = util.grid().at(5, 2, 1);
        Vec3 outPos = util.vector().centerOf(beltStart).add(0, 0.2, 0);

        scene.overlay().showText(60)
                .placeNearTarget()
                .text("The Brass Funnel filters out byproducts like empty bottles")
                .pointAt(outPos);

        ElementLink<EntityElement> movingDough = scene.world().createItemEntity(
                util.vector().centerOf(basinPos),
                new Vec3(-0.1, 0, -0.1),
                new ItemStack(BakeriesItems.SALTED_DOUGH.get())
        );
        scene.idle(80);

        scene.world().modifyEntity(movingDough, e -> e.setPos(util.vector().centerOf(4, 2, 1).add(0, 0.15, 0)));
        scene.idle(10);

        BlockPos sawPos = util.grid().at(4, 2, 1);
        scene.world().modifyBlockEntityNBT(util.select().position(sawPos), BlockEntity.class, nbt -> {
            nbt.putBoolean("Running", true);
        });

        scene.overlay().showText(40)
                .placeNearTarget()
                .text("The Mechanical Saw cuts the dough into embryos")
                .pointAt(util.vector().topOf(sawPos));

        scene.idle(60);

        scene.world().modifyEntity(movingDough, e -> e.discard());
        ElementLink<EntityElement> shapedDough = scene.world().createItemEntity(
                util.vector().centerOf(4, 2, 1).add(0, 0.15, 0),
                Vec3.ZERO,
                new ItemStack(BakeriesItems.COUNTRY_BREAD_DOUGH.get())
        );

        scene.world().modifyBlockEntityNBT(util.select().position(sawPos), BlockEntity.class, nbt -> {
            nbt.putBoolean("Running", false);
        });

        scene.addKeyframe();
        scene.world().modifyEntity(shapedDough, e -> e.setPos(util.vector().centerOf(2, 2, 1).add(0, 0.15, 0)));
        scene.idle(10);

        scene.world().modifyEntity(shapedDough, e -> e.discard());
        BlockPos ovenPos = util.grid().at(1, 3, 1);

        scene.world().modifyBlock(ovenPos, state -> state.setValue(OvenBlock.LIT, true), false);

        scene.overlay().showText(60)
                .placeNearTarget()
                .text("Once the oven temperature is set, baking begins automatically")
                .pointAt(util.vector().blockSurface(ovenPos, Direction.WEST));

        for(int i = 0; i < 10; i++) {
            scene.effects().emitParticles(util.vector().centerOf(ovenPos),
                    scene.effects().simpleParticleEmitter(net.minecraft.core.particles.ParticleTypes.SMOKE, Vec3.ZERO), 1, 1);
            scene.idle(5);
        }

        scene.world().modifyBlock(ovenPos, state -> state.setValue(OvenBlock.LIT, false), false);
        scene.effects().indicateSuccess(ovenPos);

        scene.idle(75);
        scene.markAsFinished();
    }
}