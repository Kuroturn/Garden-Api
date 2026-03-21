package com.garden.api;

import net.minecraft.resources.ResourceLocation;

import java.io.Serial;

/**
 * Generic {@link Exception} wrapper for GardenApi.<br>
 * Mostly just serves as a marker for internal error handling.
 */
public class GardenApiException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public GardenApiException(ResourceLocation fileLocation, String message) {
        super(fileLocation + ": " + message);
    }

    public GardenApiException(ResourceLocation fileLocation, String message, Throwable cause) {
        super(fileLocation + ": " + message, cause);
    }
}
