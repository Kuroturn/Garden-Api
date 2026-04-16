package com.garden.api.voicechat.gui.audiodevice;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.VoicechatClient;
import com.garden.api.voicechat.voice.client.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SpeakerAudioDeviceList extends AudioDeviceList {

    public static final ResourceLocation SPEAKER_ICON = new ResourceLocation(Voicechat.MODID, "textures/icons/speaker.png");
    public static final Component DEFAULT_SPEAKER = Component.translatable("message.voicechat.default_speaker");

    public SpeakerAudioDeviceList(int width, int height, int top) {
        super(width, height, top);
        defaultDeviceText = DEFAULT_SPEAKER;
        icon = SPEAKER_ICON;
        configEntry = VoicechatClient.CLIENT_CONFIG.speaker;
        setAudioDevices(SoundManager.getAllSpeakers());
    }

    @Override
    public AudioDeviceEntry createAudioDeviceEntry(String device, Component name, @Nullable ResourceLocation icon, Supplier<Boolean> isSelected) {
        return new SpeakerAudioDeviceEntry(device, name, icon, isSelected);
    }

}
