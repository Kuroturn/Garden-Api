package com.garden.api.config;

import com.garden.api.config.features.AutoHarvestConfig;
import com.garden.api.config.features.IrrigationConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = com.garden.api.GardenApi.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MainConfig {
    
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    
    // Main mod settings
    public static final ForgeConfigSpec.BooleanValue ENABLE_GARDEN_API;
    public static final ForgeConfigSpec.BooleanValue LOG_FEATURE_LOAD;
    public static final ForgeConfigSpec.IntValue MAX_GARDEN_RADIUS;
    public static final ForgeConfigSpec.BooleanValue ENABLE_DEBUG_MODE;
    
    static {
        BUILDER.push("main");
        
        ENABLE_GARDEN_API = BUILDER
                .comment("Enable the Garden API functionality")
                .define("enableGardenApi", true);
                
        LOG_FEATURE_LOAD = BUILDER
                .comment("Log when garden features are loaded")
                .define("logFeatureLoad", true);
                
        MAX_GARDEN_RADIUS = BUILDER
                .comment("Maximum radius for garden automation features")
                .defineInRange("maxGardenRadius", 32, 1, 256);
                
        ENABLE_DEBUG_MODE = BUILDER
                .comment("Enable debug logging for development")
                .define("enableDebugMode", false);
                
        BUILDER.pop();
        
        // Include Auto Harvest config
        AutoHarvestConfig.init(BUILDER);
        
        // Include Irrigation config
        IrrigationConfig.init(BUILDER);
        
        SPEC = BUILDER.build();
    }
    
    // Runtime values
    public static boolean enableGardenApi;
    public static boolean logFeatureLoad;
    public static int maxGardenRadius;
    public static boolean enableDebugMode;
    
    public static void loadConfig() {
        enableGardenApi = ENABLE_GARDEN_API.get();
        logFeatureLoad = LOG_FEATURE_LOAD.get();
        maxGardenRadius = MAX_GARDEN_RADIUS.get();
        enableDebugMode = ENABLE_DEBUG_MODE.get();
        
        // Load feature configs
        AutoHarvestConfig.loadConfig();
        IrrigationConfig.loadConfig();
    }
}
