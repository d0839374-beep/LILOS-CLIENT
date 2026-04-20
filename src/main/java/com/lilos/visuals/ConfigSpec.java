package com.lilos.visuals;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigSpec {
    public static final ForgeConfigSpec SPEC;
    
    public static final ForgeConfigSpec.BooleanValue ENABLE_HUD;
    public static final ForgeConfigSpec.BooleanValue ENABLE_CUSTOM_MENU;
    
    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        
        builder.push("general");
        ENABLE_HUD = builder.comment("Enable custom HUD").define("enableHUD", true);
        ENABLE_CUSTOM_MENU = builder.comment("Enable custom main menu and pause screen").define("enableCustomMenu", true);
        builder.pop();
        
        SPEC = builder.build();
    }
}
