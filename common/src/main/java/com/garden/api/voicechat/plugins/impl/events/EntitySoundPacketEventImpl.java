package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.VoicechatConnection;
import com.garden.api.voicechat.api.events.EntitySoundPacketEvent;
import com.garden.api.voicechat.api.packets.EntitySoundPacket;

import javax.annotation.Nullable;

public class EntitySoundPacketEventImpl extends SoundPacketEventImpl<EntitySoundPacket> implements EntitySoundPacketEvent {

    public EntitySoundPacketEventImpl(EntitySoundPacket packet, @Nullable VoicechatConnection senderConnection, VoicechatConnection receiverConnection, String source) {
        super(packet, senderConnection, receiverConnection, source);
    }
}
