package com.garden.api.voicechat.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class ClientWorldEvents {

    public static final Event<Runnable> DISCONNECT = EventFactory.createArrayBacked(Runnable.class, (listeners) -> () -> {
        for (Runnable listener : listeners) {
            listener.run();
        }
    });
}
