package com.garden.api.voicechat.forge.events;

import net.minecraftforge.eventbus.api.GenericEvent;

public class ClientVoiceChatDisconnectedEvent extends GenericEvent<ClientVoiceChatDisconnectedEvent> {

    @Override
    public boolean isCancelable() {
        return false;
    }

}
