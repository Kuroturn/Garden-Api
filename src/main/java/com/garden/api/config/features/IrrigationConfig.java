package com.garden.api.config.features;

import net.minecraftforge.common.ForgeConfigSpec;

public class IrrigationConfig {
    
    // Irrigation feature settings
    public static ForgeConfigSpec.BooleanValue ENABLE_IRRIGATION;
    public static ForgeConfigSpec.IntValue IRRIGATION_CHECK_INTERVAL;
    public static ForgeConfigSpec.DoubleValue WATER_RANGE_MULTIPLIER;
    public static ForgeConfigSpec.BooleanValue ENABLE_WATER_SPREAD;
    public static ForgeConfigSpec.IntValue MAX_WATER_RADIUS;
    public static ForgeConfigSpec.BooleanValue CONSUME_WATER_ITEMS;
    
    public static void init(ForgeConfigSpec.Builder builder) {
        builder.push("irrigation");
        
        ENABLE_IRRIGATION = builder
                .comment("Enable automatic irrigation system")
                .define("enableIrrigation", true);
                
        IRRIGATION_CHECK_INTERVAL = builder
                .comment("How often to check for dry crops (in ticks)")
                .defineInRange("irrigationCheckInterval", 200, 20, 2400);
                
        WATER_RANGE_MULTIPLIER = builder
                .comment("Multiplier for water source effectiveness")
                .defineInRange("waterRangeMultiplier", 1.0, 0.1, 5.0);
                
        ENABLE_WATER_SPREAD = builder
                .comment("Enable water spreading to adjacent blocks")
                .define("enableWaterSpread", true);
                
        MAX_WATER_RADIUS = builder
                .comment("Maximum radius for water source effects")
                .defineInRange("maxWaterRadius", 8, 1, 32);
                
        CONSUME_WATER_ITEMS = builder
                .comment("Consume water buckets/items when irrigating")
                .define("consumeWaterItems", false);
                
        builder.pop();
    }
    
    // Runtime values
    public static boolean enableIrrigation;
    public static int irrigationCheckInterval;
    public static double waterRangeMultiplier;
    public static boolean enableWaterSpread;
    public static int maxWaterRadius;
    public static boolean consumeWaterItems;
    
    public static void loadConfig() {
        enableIrrigation = ENABLE_IRRIGATION.get();
        irrigationCheckInterval = IRRIGATION_CHECK_INTERVAL.get();
        waterRangeMultiplier = WATER_RANGE_MULTIPLIER.get();
        enableWaterSpread = ENABLE_WATER_SPREAD.get();
        maxWaterRadius = MAX_WATER_RADIUS.get();
        consumeWaterItems = CONSUME_WATER_ITEMS.get();
    }
}
