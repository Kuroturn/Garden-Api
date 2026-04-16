package com.garden.api.voicechat.api.events;

import com.garden.api.voicechat.api.packets.StaticSoundPacket;

/**
 * This event is emitted when a static sound packet is about to get sent to a client.
 */
public interface StaticSoundPacketEvent extends SoundPacketEvent<StaticSoundPacket> {

}
