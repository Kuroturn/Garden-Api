package com.garden.api.voicechat.gui.tooltips;

import com.garden.api.voicechat.VoicechatClient;
import com.garden.api.voicechat.gui.widgets.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class HideTooltipSupplier implements ImageButton.TooltipSupplier {

    public static final Component HIDE_ICONS_ENABLED = Component.translatable("message.voicechat.hide_icons.enabled");
    public static final Component HIDE_ICONS_DISABLED = Component.translatable("message.voicechat.hide_icons.disabled");

    private final Screen screen;
    @Nullable
    private Boolean lastState;

    public HideTooltipSupplier(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void updateTooltip(ImageButton button) {
        boolean hide = VoicechatClient.CLIENT_CONFIG.hideIcons.get();
        if (lastState == null || lastState != hide) {
            lastState = hide;
            button.setTooltip(Tooltip.create(hide ? HIDE_ICONS_ENABLED : HIDE_ICONS_DISABLED));
        }
    }

}
