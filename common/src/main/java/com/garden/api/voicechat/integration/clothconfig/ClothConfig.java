package com.garden.api.voicechat.integration.clothconfig;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.gui.VoiceChatSettingsScreen;
import com.garden.api.voicechat.intercompatibility.ClientCompatibilityManager;
import com.garden.api.voicechat.intercompatibility.CommonCompatibilityManager;
import me.shedaniel.clothconfig2.gui.ClothConfigScreen;
import net.minecraft.client.Minecraft;

public class ClothConfig {

    private static final Minecraft MC = Minecraft.getInstance();
    private static Boolean loaded;

    public static boolean isLoaded() {
        if (loaded == null) {
            loaded = checkLoaded();
        }
        return loaded;
    }

    private static boolean checkLoaded() {
        if (CommonCompatibilityManager.INSTANCE.isModLoaded("cloth-config") || CommonCompatibilityManager.INSTANCE.isModLoaded("cloth-config2") || CommonCompatibilityManager.INSTANCE.isModLoaded("cloth_config")) {
            try {
                Class.forName("me.shedaniel.clothconfig2.api.ConfigBuilder");
                Voicechat.LOGGER.info("Using Cloth Config GUI");
                return true;
            } catch (Exception e) {
                Voicechat.LOGGER.warn("Failed to load Cloth Config", e);
            }
        }
        return false;
    }

    public static void init() {
        if (isLoaded()) {
            ClientCompatibilityManager.INSTANCE.onClientTick(ClothConfig::onTick);
        }
    }

    private static void onTick() {
        if (isLoaded()) {
            if (MC.screen instanceof ClothConfigScreen screen) {
                if (screen.getSelectedCategory().equals(ClothConfigIntegration.OTHER_SETTINGS)) {
                    screen.selectedCategoryIndex = 0;
                    MC.setScreen(new VoiceChatSettingsScreen(MC.screen));
                }
            }
        }
    }

}
