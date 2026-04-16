package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.VoicechatClientApi;
import com.garden.api.voicechat.api.events.ClientEvent;
import com.garden.api.voicechat.plugins.impl.VoicechatClientApiImpl;

public class ClientEventImpl extends EventImpl implements ClientEvent {

    @Override
    public VoicechatClientApi getVoicechat() {
        return VoicechatClientApiImpl.instance();
    }
}
