package com.garden.api.voicechat.api.events;

import com.garden.api.voicechat.api.Group;
import com.garden.api.voicechat.api.VoicechatConnection;

import javax.annotation.Nullable;

/**
 * This event is only cancellable if the group is persistent
 */
public interface RemoveGroupEvent extends GroupEvent {

    /**
     * @return the group that was removed
     */
    Group getGroup();

    /**
     * @return <code>null</code>
     */
    @Nullable
    @Deprecated
    VoicechatConnection getConnection();

}
