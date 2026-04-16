package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.Group;
import com.garden.api.voicechat.api.VoicechatConnection;
import com.garden.api.voicechat.api.events.JoinGroupEvent;

public class JoinGroupEventImpl extends GroupEventImpl implements JoinGroupEvent {

    public JoinGroupEventImpl(Group group, VoicechatConnection connection) {
        super(group, connection);
    }
}
