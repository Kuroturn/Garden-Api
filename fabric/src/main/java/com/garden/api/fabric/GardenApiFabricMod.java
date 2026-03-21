package com.garden.api.fabric;

import com.garden.api.GardenApi;
import com.garden.api.SoundPhysicsMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class GardenApiFabricMod extends SoundPhysicsMod implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        GardenApi.initialize();
        GardenApiFabricRuntime.initialize();
        init();
    }

    @Override
    public void onInitializeClient() {
        initClient();
    }

    @Override
    public Path getConfigFolder() {
        return FabricLoader.getInstance().getConfigDir();
    }

}

