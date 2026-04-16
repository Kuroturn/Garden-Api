package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.VoicechatConnection;
import com.garden.api.voicechat.api.events.LocationalSoundPacketEvent;
import com.garden.api.voicechat.api.packets.LocationalSoundPacket;

import javax.annotation.Nullable;

public class LocationalSoundPacketEventImpl extends SoundPacketEventImpl<LocationalSoundPacket> implements LocationalSoundPacketEvent {

    public LocationalSoundPacketEventImpl(LocationalSoundPacket packet, @Nullable VoicechatConnection senderConnection, VoicechatConnection receiverConnection, String source) {
        super(packet, senderConnection, receiverConnection, source);
    }
}
