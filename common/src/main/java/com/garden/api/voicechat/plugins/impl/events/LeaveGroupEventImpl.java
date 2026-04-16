package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.Group;
import com.garden.api.voicechat.api.VoicechatConnection;
import com.garden.api.voicechat.api.events.LeaveGroupEvent;

import javax.annotation.Nullable;

public class LeaveGroupEventImpl extends GroupEventImpl implements LeaveGroupEvent {

    public LeaveGroupEventImpl(@Nullable Group group, VoicechatConnection connection) {
        super(group, connection);
    }
}
