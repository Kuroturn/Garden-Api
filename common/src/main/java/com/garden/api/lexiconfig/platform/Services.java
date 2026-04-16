package com.garden.api.lexiconfig.platform;

import com.garden.api.lexiconfig.Lexiconfig;
import com.garden.api.lexiconfig.platform.services.PlatformHelper;

public class Services {
    public static final PlatformHelper PLATFORM = Lexiconfig.loadService(PlatformHelper.class);
}