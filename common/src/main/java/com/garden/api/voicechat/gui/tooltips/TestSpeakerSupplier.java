package com.garden.api.voicechat.gui.tooltips;

import com.garden.api.voicechat.gui.widgets.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public class TestSpeakerSupplier implements ImageButton.TooltipSupplier {

    public static final Component TEST_SPEAKER = Component.translatable("message.voicechat.test_speaker");

    @Override
    public void updateTooltip(ImageButton button) {
        button.setTooltip(Tooltip.create(TEST_SPEAKER));
    }
}
