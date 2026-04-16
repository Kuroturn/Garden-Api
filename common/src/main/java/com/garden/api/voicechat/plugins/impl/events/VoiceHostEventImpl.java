package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.events.VoiceHostEvent;
import com.garden.api.voicechat.plugins.impl.ServerPlayerImpl;
import net.minecraft.server.level.ServerPlayer;

public class VoiceHostEventImpl extends ServerEventImpl implements VoiceHostEvent {

    private final ServerPlayerImpl player;
    private String voiceHost;

    public VoiceHostEventImpl(ServerPlayer player, String voiceHost) {
        this.player = new ServerPlayerImpl(player);
        this.voiceHost = voiceHost;
    }

    @Override
    public String getVoiceHost() {
        return voiceHost;
    }

    @Override
    public void setVoiceHost(String voiceHost) {
        this.voiceHost = voiceHost;
    }

    @Override
    public com.garden.api.voicechat.api.ServerPlayer getPlayer() {
        return player;
    }

}
