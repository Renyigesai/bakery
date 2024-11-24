package com.renyigesai.bakeries.config;

import net.minecraftforge.common.ForgeConfigSpec;


public class BakeriesConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
//	public static final ForgeConfigSpec.ConfigValue<> oven;
	static {
		BUILDER.push("oven");//标体类
//
//		oven = BUILDER.comment("max_progress")
//				.define("max_progress", OvenMaxProgress.BAGEL_DOUGH);
		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
