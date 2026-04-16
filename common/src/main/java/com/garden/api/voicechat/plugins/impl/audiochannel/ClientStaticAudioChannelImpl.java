package com.garden.api.voicechat.plugins.impl.audiochannel;

import com.garden.api.voicechat.api.audiochannel.ClientStaticAudioChannel;
import com.garden.api.voicechat.voice.common.GroupSoundPacket;
import com.garden.api.voicechat.voice.common.SoundPacket;

import java.util.UUID;

public class ClientStaticAudioChannelImpl extends ClientAudioChannelImpl implements ClientStaticAudioChannel {

    public ClientStaticAudioChannelImpl(UUID id) {
        super(id);
    }

    @Override
    protected SoundPacket<?> createSoundPacket(short[] rawAudio) {
        return new GroupSoundPacket(id, id, rawAudio, category);
    }

}
