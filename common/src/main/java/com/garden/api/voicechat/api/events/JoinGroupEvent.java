package com.garden.api.voicechat.api.events;

import com.garden.api.voicechat.api.Group;
import com.garden.api.voicechat.api.VoicechatConnection;

public interface JoinGroupEvent extends GroupEvent {

    /**
     * @return the group that was joined
     */
    Group getGroup();

    /**
     * @return the connection of the player
     */
    VoicechatConnection getConnection();

}
