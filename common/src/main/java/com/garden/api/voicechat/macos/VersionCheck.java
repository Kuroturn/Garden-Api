package com.garden.api.voicechat.macos;

import com.sun.jna.Platform;
import com.garden.api.voicechat.util.Version;

public class VersionCheck {

    private static final Version VERSION_13 = new Version(13, 0, 0);

    private static Boolean isMacOSNativeCompatible;

    public static boolean isMacOSNativeCompatible() {
        if (isMacOSNativeCompatible == null) {
            isMacOSNativeCompatible = checkIsMacOSNativeCompatible();
        }
        return isMacOSNativeCompatible;
    }

    private static boolean checkIsMacOSNativeCompatible() {
        if (!Platform.isMac()) {
            return false;
        }
        String version = System.getProperty("os.version");
        if (version == null) {
            return false;
        }
        Version macOsVersion = Version.fromVersionString(version);
        if (macOsVersion == null) {
            return false;
        }
        return macOsVersion.compareTo(VERSION_13) >= 0;
    }

}
