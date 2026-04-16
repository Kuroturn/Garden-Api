package com.garden.api.voicechat.api.events;

import com.garden.api.voicechat.api.packets.MicrophonePacket;

/**
 * This event is emitted when a microphone packet arrives at the server.
 */
public interface MicrophonePacketEvent extends PacketEvent<MicrophonePacket> {

}
