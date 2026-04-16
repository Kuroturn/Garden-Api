package com.garden.api.lexiconfig.forge;

import com.garden.api.lexiconfig.Lexiconfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ForgeLexiconfig {
    
    public ForgeLexiconfig() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadCompletion);

        Lexiconfig.initialize();
    }

    @SubscribeEvent
    public void loadCompletion(FMLLoadCompleteEvent event) {
        Lexiconfig.postInitialize();
    }
}
