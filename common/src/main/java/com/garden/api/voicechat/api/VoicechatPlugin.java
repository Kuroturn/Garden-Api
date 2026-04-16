package com.garden.api.voicechat.api;

import com.garden.api.voicechat.api.events.EventRegistration;

public interface VoicechatPlugin {

    /**
     * @return the ID of this plugin - Has to be unique
     */
    String getPluginId();

    /**
     * Called after loading the plugin.
     */
    default void initialize(VoicechatApi api) {
    }

    /**
     * Register your events here - Only here!
     *
     * @param registration the event registration object, used to register events
     */
    default void registerEvents(EventRegistration registration) {
    }

}
