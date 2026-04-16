package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.Group;
import com.garden.api.voicechat.api.VoicechatConnection;
import com.garden.api.voicechat.api.events.CreateGroupEvent;

import javax.annotation.Nullable;

public class CreateGroupEventImpl extends GroupEventImpl implements CreateGroupEvent {

    public CreateGroupEventImpl(Group group, @Nullable VoicechatConnection connection) {
        super(group, connection);
    }
}
