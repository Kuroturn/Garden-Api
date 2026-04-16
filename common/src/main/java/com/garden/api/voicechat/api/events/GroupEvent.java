package com.garden.api.voicechat.api.events;

import com.garden.api.voicechat.api.Group;
import com.garden.api.voicechat.api.VoicechatConnection;

import javax.annotation.Nullable;

public interface GroupEvent extends ServerEvent {

    /**
     * @return the group - <code>null</code> if there is no group
     */
    @Nullable
    Group getGroup();

    /**
     * @return the connection of the player
     */
    @Nullable
    VoicechatConnection getConnection();

}
