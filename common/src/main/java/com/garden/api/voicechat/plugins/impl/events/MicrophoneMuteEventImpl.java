package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.events.MicrophoneMuteEvent;

public class MicrophoneMuteEventImpl extends ClientEventImpl implements MicrophoneMuteEvent {

    private final boolean muted;

    public MicrophoneMuteEventImpl(boolean muted) {
        super();
        this.muted = muted;
    }

    @Override
    public boolean isDisabled() {
        return muted;
    }
}
