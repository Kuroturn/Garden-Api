package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.VoicechatSocket;
import com.garden.api.voicechat.api.events.VoicechatServerStartingEvent;

import javax.annotation.Nullable;

public class VoicechatServerStartingEventImpl extends ServerEventImpl implements VoicechatServerStartingEvent {

    @Nullable
    private VoicechatSocket socketImplementation;

    @Override
    public void setSocketImplementation(VoicechatSocket socket) {
        this.socketImplementation = socket;
    }

    @Nullable
    @Override
    public VoicechatSocket getSocketImplementation() {
        return socketImplementation;
    }

    @Override
    public boolean isCancellable() {
        return false;
    }
}
