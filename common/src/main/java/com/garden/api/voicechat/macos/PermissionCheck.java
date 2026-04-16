package com.garden.api.voicechat.macos;

import com.garden.api.voicechat.macos.avfoundation.AVAuthorizationStatus;
import com.garden.api.voicechat.macos.avfoundation.AVCaptureDevice;
import com.garden.api.voicechat.macos.foundation.NSString;

public class PermissionCheck {

    private static final NSString AVMediaTypeAudio = new NSString("soun");

    public static void requestMicrophonePermissions() {
        checkMicrophonePermissions(true);
    }

    public static AVAuthorizationStatus getMicrophonePermissions() {
        return checkMicrophonePermissions(false);
    }

    public static AVAuthorizationStatus checkMicrophonePermissions(boolean requestIfNeeded) {
        if (!VersionCheck.isMacOSNativeCompatible()) {
            return AVAuthorizationStatus.AUTHORIZED;
        }

        var status = AVCaptureDevice.getAuthorizationStatus(AVMediaTypeAudio);
        if (requestIfNeeded && status == AVAuthorizationStatus.NOT_DETERMINED) {
            AVCaptureDevice.requestAccessForMediaType(AVMediaTypeAudio);
        }

        return status;
    }
}
