package com.garden.api.lexiconfig.fabric.platform;

import com.garden.api.lexiconfig.LexiconfigApi;
import com.garden.api.lexiconfig.Library;
import com.garden.api.lexiconfig.Lexiconfig;
import com.garden.api.lexiconfig.platform.services.PlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricPlatformHelper implements PlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public void shelveLexicons() {
        FabricLoader.getInstance().invokeEntrypoints(Lexiconfig.ID, Library.class, library -> {
            LexiconfigApi.LIBRARIES.add(library);
            library.shelveLexicons();
        });
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
