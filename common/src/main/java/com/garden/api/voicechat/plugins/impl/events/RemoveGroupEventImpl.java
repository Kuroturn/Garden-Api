package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.Group;
import com.garden.api.voicechat.api.events.RemoveGroupEvent;

public class RemoveGroupEventImpl extends GroupEventImpl implements RemoveGroupEvent {

    public RemoveGroupEventImpl(Group group) {
        super(group, null);
    }

    @Override
    public boolean isCancellable() {
        return super.isCancellable() && group.isPersistent();
    }
}
