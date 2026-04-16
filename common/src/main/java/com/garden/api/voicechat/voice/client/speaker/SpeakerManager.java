package com.garden.api.voicechat.voice.client.speaker;

import com.garden.api.voicechat.VoicechatClient;
import com.garden.api.voicechat.voice.client.SoundManager;
import com.garden.api.voicechat.voice.common.AudioUtils;

import javax.annotation.Nullable;
import java.util.UUID;

public class SpeakerManager {

    public static Speaker createSpeaker(SoundManager soundManager, @Nullable UUID audioChannel) throws SpeakerException {
        ALSpeakerBase speaker = switch (VoicechatClient.CLIENT_CONFIG.audioType.get()) {
            case NORMAL -> new ALSpeaker(soundManager, AudioUtils.SAMPLE_RATE, AudioUtils.FRAME_SIZE, audioChannel);
            case REDUCED -> new FakeALSpeaker(soundManager, AudioUtils.SAMPLE_RATE, AudioUtils.FRAME_SIZE, audioChannel);
            case OFF -> new MonoALSpeaker(soundManager, AudioUtils.SAMPLE_RATE, AudioUtils.FRAME_SIZE, audioChannel);
        };
        speaker.open();
        return speaker;
    }

}
