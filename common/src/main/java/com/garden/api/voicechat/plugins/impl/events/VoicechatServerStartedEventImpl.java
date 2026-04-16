package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.events.VoicechatServerStartedEvent;

public class VoicechatServerStartedEventImpl extends ServerEventImpl implements VoicechatServerStartedEvent {

    @Override
    public boolean isCancellable() {
        return false;
    }
}
