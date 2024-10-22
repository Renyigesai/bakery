package com.renyigesai.bakery.config;

import net.minecraftforge.common.ForgeConfigSpec;


public class BakeryConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	public static final ForgeConfigSpec.ConfigValue<Boolean> IS_GUI_ENABLE;
	static {
		BUILDER.push("gui");//标体类
		IS_GUI_ENABLE = BUILDER.comment("是否启用默认GUI")
				.define("is_gui_enable", true);

		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
