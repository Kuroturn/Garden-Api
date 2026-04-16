package com.garden.api.voicechat.fabric;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.fabric.integration.ViaVersionCompatibility;
import com.garden.api.voicechat.fabric.integration.vanish.VanishIntegration;
import net.fabricmc.api.ModInitializer;

public class FabricVoicechatMod extends Voicechat implements ModInitializer {

    @Override
    public void onInitialize() {
        initialize();
        ViaVersionCompatibility.register();
        VanishIntegration.init();
    }

}
