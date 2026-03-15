package com.garden.api.config;

import com.garden.api.GardenApi;
import com.garden.api.config.features.AutoHarvestConfig;
import com.garden.api.config.features.IrrigationConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = GardenApi.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigManager {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    public static void registerConfigs() {
        // Register only the main config - this will include all feature configs
        FMLJavaModLoadingContext.get().getModEventBus().register(MainConfig.class);
        FMLJavaModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, MainConfig.SPEC);
        
        LOGGER.info("Garden API configurations registered");
    }
    
    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        loadAllConfigs();
        LOGGER.info("Garden API configurations loaded");
    }
    
    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
        loadAllConfigs();
        LOGGER.info("Garden API configurations reloaded");
    }
    
    private static void loadAllConfigs() {
        // Load main config
        MainConfig.loadConfig();
        
        // Load feature configs
        AutoHarvestConfig.loadConfig();
        IrrigationConfig.loadConfig();
        
        // Log loaded values if debug mode is enabled
        if (MainConfig.enableDebugMode) {
            LOGGER.info("=== Garden API Configuration ===");
            LOGGER.info("Enable Garden API: {}", MainConfig.enableGardenApi);
            LOGGER.info("Max Garden Radius: {}", MainConfig.maxGardenRadius);
            LOGGER.info("Log Feature Load: {}", MainConfig.logFeatureLoad);
            LOGGER.info("Debug Mode: {}", MainConfig.enableDebugMode);
            
            LOGGER.info("=== Auto Harvest Configuration ===");
            LOGGER.info("Enable Auto Harvest: {}", AutoHarvestConfig.enableAutoHarvest);
            LOGGER.info("Harvest Check Interval: {}", AutoHarvestConfig.harvestCheckInterval);
            LOGGER.info("Harvest Crops: {}", AutoHarvestConfig.harvestCrops);
            LOGGER.info("Harvest Trees: {}", AutoHarvestConfig.harvestTrees);
            LOGGER.info("Harvest Flowers: {}", AutoHarvestConfig.harvestFlowers);
            LOGGER.info("Max Harvest Radius: {}", AutoHarvestConfig.maxHarvestRadius);
            
            LOGGER.info("=== Irrigation Configuration ===");
            LOGGER.info("Enable Irrigation: {}", IrrigationConfig.enableIrrigation);
            LOGGER.info("Irrigation Check Interval: {}", IrrigationConfig.irrigationCheckInterval);
            LOGGER.info("Water Range Multiplier: {}", IrrigationConfig.waterRangeMultiplier);
            LOGGER.info("Enable Water Spread: {}", IrrigationConfig.enableWaterSpread);
            LOGGER.info("Max Water Radius: {}", IrrigationConfig.maxWaterRadius);
            LOGGER.info("Consume Water Items: {}", IrrigationConfig.consumeWaterItems);
        }
    }
}