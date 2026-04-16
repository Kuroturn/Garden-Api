package com.garden.api.voicechat.api.events;

import com.garden.api.voicechat.api.packets.LocationalSoundPacket;

/**
 * This event is emitted when a locational sound packet is about to get sent to a client.
 */
public interface LocationalSoundPacketEvent extends SoundPacketEvent<LocationalSoundPacket> {

}
