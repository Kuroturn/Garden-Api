package com.garden.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class GardenApi {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "garden_api";
    public static volatile boolean hasInitialized;

    private GardenApi() {
    }

    public static synchronized void initialize() {
        hasInitialized = true;
    }
}
