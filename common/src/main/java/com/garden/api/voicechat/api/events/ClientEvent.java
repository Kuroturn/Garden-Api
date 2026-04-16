package com.garden.api.voicechat.api.events;

import com.garden.api.voicechat.api.VoicechatClientApi;

public interface ClientEvent extends Event {

    /**
     * @return the voice chat client API
     */
    VoicechatClientApi getVoicechat();

}
