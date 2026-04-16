package com.garden.api.voicechat.api.events;

import com.garden.api.voicechat.api.VoicechatServerApi;

public interface ServerEvent extends Event {

    /**
     * @return the voice chat server API
     */
    VoicechatServerApi getVoicechat();

}
