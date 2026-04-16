package com.garden.api.voicechat.gui.widgets;

import com.garden.api.voicechat.VoicechatClient;
import com.garden.api.voicechat.natives.RNNoiseManager;
import net.minecraft.network.chat.Component;

public class DenoiserButton extends BooleanConfigButton {

    private static final Component ENABLED = Component.translatable("message.voicechat.denoiser.on");
    private static final Component DISABLED = Component.translatable("message.voicechat.denoiser.off");

    public DenoiserButton(int x, int y, int width, int height) {
        super(x, y, width, height, VoicechatClient.CLIENT_CONFIG.denoiser, enabled -> {
            return Component.translatable("message.voicechat.denoiser", enabled ? ENABLED : DISABLED);
        });
        if (!RNNoiseManager.canUseDenoiser()) {
            active = false;
            setMessage(component.apply(false));
        }
    }

}
