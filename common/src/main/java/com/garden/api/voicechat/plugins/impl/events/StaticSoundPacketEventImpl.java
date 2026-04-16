package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.VoicechatConnection;
import com.garden.api.voicechat.api.events.StaticSoundPacketEvent;
import com.garden.api.voicechat.api.packets.StaticSoundPacket;

import javax.annotation.Nullable;

public class StaticSoundPacketEventImpl extends SoundPacketEventImpl<StaticSoundPacket> implements StaticSoundPacketEvent {

    public StaticSoundPacketEventImpl(StaticSoundPacket packet, @Nullable VoicechatConnection senderConnection, VoicechatConnection receiverConnection, String source) {
        super(packet, senderConnection, receiverConnection, source);
    }
}
