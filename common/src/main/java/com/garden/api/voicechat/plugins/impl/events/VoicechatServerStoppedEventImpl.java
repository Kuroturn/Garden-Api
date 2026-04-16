package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.events.VoicechatServerStoppedEvent;

public class VoicechatServerStoppedEventImpl extends ServerEventImpl implements VoicechatServerStoppedEvent {

    @Override
    public boolean isCancellable() {
        return false;
    }
}
