package com.garden.api.voicechat.natives;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.intercompatibility.CrossSideManager;
import com.garden.api.voicechat.voice.client.ChatUtils;

public class ClientNativeManager {

    public static void onConnecting() {
        if (!CrossSideManager.get().useNatives()) {
            Voicechat.LOGGER.info("Not informing player about natives, since the user has disabled them");
            return;
        }
        if (!OpusManager.isFailed() && !RNNoiseManager.isFailed() && !SpeexManager.isFailed() && !LameManager.isFailed()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (OpusManager.isFailed()) {
            sb.append("Opus: ").append(OpusManager.getFailedMessage()).append("\n");
        }
        if (RNNoiseManager.isFailed()) {
            sb.append("RNNoise: ").append(RNNoiseManager.getFailedMessage()).append("\n");
        }
        if (SpeexManager.isFailed()) {
            sb.append("Speex: ").append(SpeexManager.getFailedMessage()).append("\n");
        }
        if (LameManager.isFailed()) {
            sb.append("LAME: ").append(LameManager.getFailedMessage()).append("\n");
        }
        ChatUtils.sendModErrorMessage("message.voicechat.native_error", sb.toString().trim());
    }

}
