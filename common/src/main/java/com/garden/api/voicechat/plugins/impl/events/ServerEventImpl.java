package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.VoicechatServerApi;
import com.garden.api.voicechat.api.events.ServerEvent;
import com.garden.api.voicechat.plugins.impl.VoicechatServerApiImpl;

public class ServerEventImpl extends EventImpl implements ServerEvent {

    @Override
    public VoicechatServerApi getVoicechat() {
        return VoicechatServerApiImpl.instance();
    }

}
