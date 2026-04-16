package com.garden.api.voicechat.fabric.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.garden.api.voicechat.gui.VoiceChatSettingsScreen;
import com.garden.api.voicechat.gui.onboarding.OnboardingManager;
import com.garden.api.voicechat.integration.clothconfig.ClothConfig;
import com.garden.api.voicechat.integration.clothconfig.ClothConfigIntegration;

public class ModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            if (OnboardingManager.isOnboarding()) {
                return OnboardingManager.getOnboardingScreen(parent);
            }
            if (ClothConfig.isLoaded()) {
                return ClothConfigIntegration.createConfigScreen(parent);
            }
            return new VoiceChatSettingsScreen(parent);
        };
    }

}
