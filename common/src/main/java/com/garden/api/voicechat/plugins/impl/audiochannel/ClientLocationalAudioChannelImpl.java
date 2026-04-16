package com.garden.api.voicechat.plugins.impl.audiochannel;

import com.garden.api.voicechat.api.Position;
import com.garden.api.voicechat.api.audiochannel.ClientLocationalAudioChannel;
import com.garden.api.voicechat.voice.client.ClientUtils;
import com.garden.api.voicechat.voice.common.LocationSoundPacket;
import com.garden.api.voicechat.voice.common.SoundPacket;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class ClientLocationalAudioChannelImpl extends ClientAudioChannelImpl implements ClientLocationalAudioChannel {

    private Position position;
    private float distance;

    public ClientLocationalAudioChannelImpl(UUID id, Position position) {
        super(id);
        this.position = position;
        this.distance = ClientUtils.getDefaultDistanceClient();
    }

    @Override
    protected SoundPacket<?> createSoundPacket(short[] rawAudio) {
        return new LocationSoundPacket(id, id, rawAudio, new Vec3(position.getX(), position.getY(), position.getZ()), distance, category);
    }

    @Override
    public void setLocation(Position position) {
        this.position = position;
    }

    @Override
    public Position getLocation() {
        return position;
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
