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
	private static final ForgeConfigSpec.BooleanValue COCOA_MANIN_DAMAGE_EFFECT = BUILDER.comment("After being turned off, the Cocoa Manin effect will not remove the invincibility frame of the creatures").define("cocoaManinDamageEffect", true);
	public static final ForgeConfigSpec SPEC = BUILDER.build();

	public static boolean provideTutorialBooks;
	public static boolean cocoaManinDamageEffect;

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
		provideTutorialBooks = PROVIDE_TUTORIAL_BOOKS.get();
		cocoaManinDamageEffect = COCOA_MANIN_DAMAGE_EFFECT.get();
	}
}
