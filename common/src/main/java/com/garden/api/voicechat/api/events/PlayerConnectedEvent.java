package com.garden.api.voicechat.api.events;

import com.garden.api.voicechat.api.VoicechatConnection;

public interface PlayerConnectedEvent extends ServerEvent {

    /**
     * @return the connection of the player
     */
    VoicechatConnection getConnection();

}
