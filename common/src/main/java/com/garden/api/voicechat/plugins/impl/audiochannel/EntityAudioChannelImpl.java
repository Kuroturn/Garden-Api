package com.garden.api.voicechat.plugins.impl.audiochannel;

import com.garden.api.voicechat.api.Entity;
import com.garden.api.voicechat.api.audiochannel.EntityAudioChannel;
import com.garden.api.voicechat.api.events.SoundPacketEvent;
import com.garden.api.voicechat.api.packets.MicrophonePacket;
import com.garden.api.voicechat.plugins.impl.EntityImpl;
import com.garden.api.voicechat.plugins.impl.ServerPlayerImpl;
import com.garden.api.voicechat.voice.common.PlayerSoundPacket;
import com.garden.api.voicechat.voice.common.Utils;
import com.garden.api.voicechat.voice.server.Server;
import com.garden.api.voicechat.voice.server.ServerWorldUtils;
import net.minecraft.server.level.ServerLevel;

import java.util.UUID;

public class EntityAudioChannelImpl extends AudioChannelImpl implements EntityAudioChannel {

    protected Entity entity;
    protected boolean whispering;
    protected float distance;

    public EntityAudioChannelImpl(UUID channelId, Server server, Entity entity) {
        super(channelId, server);
        this.entity = entity;
        this.whispering = false;
        this.distance = Utils.getDefaultDistanceServer();
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
    public void updateEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public float getDistance() {
        return distance;
    }

    @Override
    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    public void send(byte[] opusData) {
        broadcast(new PlayerSoundPacket(channelId, entity.getUuid(), opusData, sequenceNumber.getAndIncrement(), whispering, distance, category));
    }

    @Override
    public void send(MicrophonePacket microphonePacket) {
        broadcast(new PlayerSoundPacket(channelId, entity.getUuid(), microphonePacket.getOpusEncodedData(), sequenceNumber.getAndIncrement(), whispering, distance, category));
    }

    @Override
    public void flush() {
        broadcast(new PlayerSoundPacket(channelId, entity.getUuid(), new byte[0], sequenceNumber.getAndIncrement(), whispering, distance, category));
    }

    private void broadcast(PlayerSoundPacket packet) {
        if (!(entity instanceof EntityImpl entityimpl)) {
            throw new IllegalArgumentException("entity is not an instance of EntityImpl");
        }
        server.broadcast(ServerWorldUtils.getPlayersInRange((ServerLevel) entityimpl.getRealEntity().level(), entityimpl.getRealEntity().getEyePosition(), server.getBroadcastRange(distance), filter == null ? player -> true : player -> filter.test(new ServerPlayerImpl(player))), packet, null, null, null, SoundPacketEvent.SOURCE_PLUGIN);

    }

}
