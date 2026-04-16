package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.VoicechatConnection;
import com.garden.api.voicechat.api.events.MicrophonePacketEvent;
import com.garden.api.voicechat.api.packets.MicrophonePacket;

public class MicrophonePacketEventImpl extends PacketEventImpl<MicrophonePacket> implements MicrophonePacketEvent {

    public MicrophonePacketEventImpl(MicrophonePacket packet, VoicechatConnection connection) {
        super(packet, connection, null);
    }
}
