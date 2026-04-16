package com.garden.api.lexiconfig.fabric;

import com.garden.api.lexiconfig.LexiconfigClient;
import net.fabricmc.api.ClientModInitializer;

public class FabricLexiconfigClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LexiconfigClient.initialize();
    }
}
