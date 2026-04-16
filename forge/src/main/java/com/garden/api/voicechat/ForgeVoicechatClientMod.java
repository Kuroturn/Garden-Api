package com.garden.api.voicechat.forge;

import com.garden.api.voicechat.VoicechatClient;
import com.garden.api.voicechat.forge.config.ConfigMigrator;
import com.garden.api.voicechat.gui.VoiceChatSettingsScreen;
import com.garden.api.voicechat.gui.onboarding.OnboardingManager;
import com.garden.api.voicechat.integration.clothconfig.ClothConfig;
import com.garden.api.voicechat.integration.clothconfig.ClothConfigIntegration;
import com.garden.api.voicechat.intercompatibility.ClientCompatibilityManager;
import com.garden.api.voicechat.forge.intercompatibility.ForgeClientCompatibilityManager;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ForgeVoicechatClientMod extends VoicechatClient {

    public ForgeVoicechatClientMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(((ForgeClientCompatibilityManager) ClientCompatibilityManager.INSTANCE)::onRegisterKeyBinds);
    }

    public void clientSetup(FMLClientSetupEvent event) {
        initializeClient();
        MinecraftForge.EVENT_BUS.register(ClientCompatibilityManager.INSTANCE);
        ClothConfig.init();
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> {
            if (OnboardingManager.isOnboarding()) {
                return OnboardingManager.getOnboardingScreen(parent);
            }
            if (ClothConfig.isLoaded()) {
                return ClothConfigIntegration.createConfigScreen(parent);
            } else {
                return new VoiceChatSettingsScreen(parent);
            }
        }));
    }

    @Override
    public void initializeConfigs() {
        super.initializeConfigs();
        ConfigMigrator.migrateClientConfig();
    }

}
