package com.garden.api.config.features;

import net.minecraftforge.common.ForgeConfigSpec;

public class AutoHarvestConfig {
    
    // Auto Harvest feature settings
    public static ForgeConfigSpec.BooleanValue ENABLE_AUTO_HARVEST;
    public static ForgeConfigSpec.IntValue HARVEST_CHECK_INTERVAL;
    public static ForgeConfigSpec.BooleanValue HARVEST_CROPS;
    public static ForgeConfigSpec.BooleanValue HARVEST_TREES;
    public static ForgeConfigSpec.BooleanValue HARVEST_FLOWERS;
    public static ForgeConfigSpec.IntValue MAX_HARVEST_RADIUS;
    
    public static void init(ForgeConfigSpec.Builder builder) {
        builder.push("auto_harvest");
        
        ENABLE_AUTO_HARVEST = builder
                .comment("Enable automatic crop harvesting")
                .define("enableAutoHarvest", true);
                
        HARVEST_CHECK_INTERVAL = builder
                .comment("How often to check for harvestable crops (in ticks)")
                .defineInRange("harvestCheckInterval", 100, 10, 1200);
                
        HARVEST_CROPS = builder
                .comment("Enable automatic harvesting of crops (wheat, carrots, potatoes, etc.)")
                .define("harvestCrops", true);
                
        HARVEST_TREES = builder
                .comment("Enable automatic harvesting of trees (logs and leaves)")
                .define("harvestTrees", false);
                
        HARVEST_FLOWERS = builder
                .comment("Enable automatic harvesting of flowers and plants")
                .define("harvestFlowers", false);
                
        MAX_HARVEST_RADIUS = builder
                .comment("Maximum radius for auto harvest detection")
                .defineInRange("maxHarvestRadius", 16, 1, 64);
                
        builder.pop();
    }
    
    // Runtime values
    public static boolean enableAutoHarvest;
    public static int harvestCheckInterval;
    public static boolean harvestCrops;
    public static boolean harvestTrees;
    public static boolean harvestFlowers;
    public static int maxHarvestRadius;
    
    public static void loadConfig() {
        enableAutoHarvest = ENABLE_AUTO_HARVEST.get();
        harvestCheckInterval = HARVEST_CHECK_INTERVAL.get();
        harvestCrops = HARVEST_CROPS.get();
        harvestTrees = HARVEST_TREES.get();
        harvestFlowers = HARVEST_FLOWERS.get();
        maxHarvestRadius = MAX_HARVEST_RADIUS.get();
    }
}
