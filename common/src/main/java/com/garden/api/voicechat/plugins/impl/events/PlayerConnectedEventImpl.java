package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.VoicechatConnection;
import com.garden.api.voicechat.api.events.PlayerConnectedEvent;
import com.garden.api.voicechat.plugins.impl.VoicechatConnectionImpl;

public class PlayerConnectedEventImpl extends ServerEventImpl implements PlayerConnectedEvent {

    protected VoicechatConnectionImpl connection;

    public PlayerConnectedEventImpl(VoicechatConnectionImpl connection) {
        this.connection = connection;
    }

    @Override
    public VoicechatConnection getConnection() {
        return connection;
    }
}
