package com.renyigesai.bakeries.config;

import com.renyigesai.bakeries.BakeriesMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = BakeriesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BakeriesConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec.BooleanValue PROVIDE_TUTORIAL_BOOKS = BUILDER.comment("A tutorial book is provided when players enter the world for the first time").define("provideTutorialBooks", true);
	public static final ForgeConfigSpec SPEC = BUILDER.build();;

	public static boolean provideTutorialBooks;

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
		provideTutorialBooks = PROVIDE_TUTORIAL_BOOKS.get();
	}
}
