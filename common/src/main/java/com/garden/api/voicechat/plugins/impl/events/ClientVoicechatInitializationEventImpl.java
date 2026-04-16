package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.api.ClientVoicechatSocket;
import com.garden.api.voicechat.api.events.ClientVoicechatInitializationEvent;

import javax.annotation.Nullable;

public class ClientVoicechatInitializationEventImpl extends ClientEventImpl implements ClientVoicechatInitializationEvent {

    @Nullable
    private ClientVoicechatSocket socketImplementation;

    @Override
    public void setSocketImplementation(ClientVoicechatSocket socket) {
        this.socketImplementation = socket;
    }

    @Nullable
    @Override
    public ClientVoicechatSocket getSocketImplementation() {
        return socketImplementation;
    }

    @Override
    public boolean isCancellable() {
        return false;
    }

}
