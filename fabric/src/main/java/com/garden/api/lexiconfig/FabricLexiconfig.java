package com.garden.api.lexiconfig.fabric;

import com.garden.api.lexiconfig.Lexiconfig;
import net.fabricmc.api.ModInitializer;

public class FabricLexiconfig implements ModInitializer {
    
    @Override
    public void onInitialize() {
        Lexiconfig.initialize();
        Lexiconfig.postInitialize();
    }

}
