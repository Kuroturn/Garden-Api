package com.garden.api.voicechat.api.audiolistener;

import java.util.UUID;

public interface AudioListener {

    /**
     * @return the ID of the listener
     */
    UUID getListenerId();

}
