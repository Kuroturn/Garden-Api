package com.garden.api.fabric;

import com.garden.api.GardenApi;
import com.garden.api.lexiconfig.fabric.FabricLexiconfig;
import com.garden.api.lexiconfig.fabric.FabricLexiconfigClient;
import com.garden.api.SoundPhysicsMod;
import com.garden.api.voicechat.fabric.FabricVoicechatClientMod;
import com.garden.api.voicechat.fabric.FabricVoicechatMod;
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
        new FabricLexiconfig().onInitialize();
        new FabricVoicechatMod().onInitialize();
    }

    @Override
    public void onInitializeClient() {
        initClient();
        new FabricLexiconfigClient().onInitializeClient();
        new FabricVoicechatClientMod().onInitializeClient();
    }

    @Override
    public Path getConfigFolder() {
        return FabricLoader.getInstance().getConfigDir();
    }

}

