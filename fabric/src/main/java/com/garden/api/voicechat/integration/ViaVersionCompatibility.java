package com.garden.api.voicechat.fabric.integration;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.intercompatibility.CommonCompatibilityManager;
import com.garden.api.voicechat.fabric.net.FabricNetManager;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;
import java.util.Set;

public class ViaVersionCompatibility {

    private static final String OLD_VOICECHAT_PREFIX = "vc";

    public static void register() {
        try {
            if (FabricLoader.getInstance().isModLoaded("viaversion")) {
                registerMappings();
                Voicechat.LOGGER.info("Successfully registered ViaVersion mappings");
            }
        } catch (Throwable t) {
            Voicechat.LOGGER.error("Failed to register ViaVersion mappings", t);
        }
    }

    private static void registerMappings() {
        try {
            Class<?> protocolClass = Class.forName("com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13");
            Field mappingsField = protocolClass.getField("MAPPINGS");
            Object mappings = mappingsField.get(null);
            @SuppressWarnings("unchecked")
            java.util.Map<String, String> channelMappings = (java.util.Map<String, String>) mappings.getClass().getMethod("getChannelMappings").invoke(mappings);
            Set<ResourceLocation> packets = ((FabricNetManager) CommonCompatibilityManager.INSTANCE.getNetManager()).getPackets();
            for (ResourceLocation id : packets) {
                channelMappings.put(String.format("%s:%s", OLD_VOICECHAT_PREFIX, id.getPath()), id.toString());
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to access ViaVersion channel mappings", e);
        }
    }

}
