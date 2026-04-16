package com.garden.api.voicechat.plugins.impl.audiochannel;

import com.garden.api.voicechat.api.audiochannel.ClientEntityAudioChannel;
import com.garden.api.voicechat.voice.client.ClientUtils;
import com.garden.api.voicechat.voice.common.PlayerSoundPacket;
import com.garden.api.voicechat.voice.common.SoundPacket;

import java.util.UUID;

public class ClientEntityAudioChannelImpl extends ClientAudioChannelImpl implements ClientEntityAudioChannel {

    private UUID entityId;
    private boolean whispering;
    private float distance;

    public ClientEntityAudioChannelImpl(UUID id, UUID entityId) {
        super(id);
        this.entityId = entityId;
        this.whispering = false;
        this.distance = ClientUtils.getDefaultDistanceClient();
    }

    @Override
    protected SoundPacket<?> createSoundPacket(short[] rawAudio) {
        return new PlayerSoundPacket(id, id, rawAudio, whispering, distance, category);
    }

    @Override
    public UUID getEntityId() {
        return entityId;
    }

    @Override
    public void setWhispering(boolean whispering) {
        this.whispering = whispering;
    }

    @Override
    public boolean isWhispering() {
        return whispering;
    }

    @Override
    public float getDistance() {
        return distance;
    }

    @Override
    public void setDistance(float distance) {
        this.distance = distance;
    }

}
