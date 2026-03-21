package com.garden.api.forge;

import java.nio.file.Path;

import com.garden.api.GardenApi;
import com.garden.api.Loggers;
import com.garden.api.SoundPhysicsMod;
import com.garden.api.cache.GardenApiCache;
import com.garden.api.example.GardenApiMod;
import com.garden.api.integration.ClothConfigIntegration;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(SoundPhysicsMod.MODID)
public class GardenApiForgeMod extends SoundPhysicsMod {

    public GardenApiForgeMod() {
        new GardenApiMod();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerReloadListeners);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        GardenApi.initialize();
        GardenApiForgeRuntime.initialize();
        init();
    }

    public void clientSetup(FMLClientSetupEvent event) {
        initClient();
        if (isClothConfigLoaded()) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> {
                return ClothConfigIntegration.createConfigScreen(parent);
            }));
        }
    }

    public void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        GardenApiCache.registerReloadListener(event);
    }

    private static boolean isClothConfigLoaded() {
        if (ModList.get().isLoaded("cloth_config")) {
            try {
                Class.forName("me.shedaniel.clothconfig2.api.ConfigBuilder");
                Loggers.log("Using Cloth Config GUI");
                return true;
            } catch (Exception e) {
                Loggers.log("Failed to load Cloth Config: {}", e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Path getConfigFolder() {
        return FMLLoader.getGamePath().resolve("config");
    }
}
